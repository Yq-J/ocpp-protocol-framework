package com.charging.ocpp.starter.service;

import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.exception.OcppErrorCode;
import com.charging.ocpp.core.exception.OcppException;
import com.charging.ocpp.core.gateway.OcppGateway;
import com.charging.ocpp.core.protocol.OcppCallError;
import com.charging.ocpp.core.protocol.OcppCallResult;
import com.charging.ocpp.core.protocol.OcppCodec;
import com.charging.ocpp.core.protocol.OcppObjectMapperFactory;
import com.charging.ocpp.core.schema.OcppSchemaValidator;
import com.charging.ocpp.core.session.OcppConnection;
import com.charging.ocpp.core.session.OcppSessionRepository;
import com.charging.ocpp.starter.autoconfigure.OcppProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * OCPP 业务调用模板。
 * <p>
 * 作者：JYq。
 * 业务层向充电桩下发命令时，只需要调用 call 方法并指定 chargePointId、协议版本、Action、请求 DTO 和响应 DTO 类型。
 * 模板会负责以下协议细节：生成 uniqueId、编码 CALL 数组帧、通过连接发送文本消息、保存 PendingRequest、
 * 在收到 CALLRESULT/CALLERROR 时完成 CompletableFuture，以及在超时后清理等待中的请求。
 * </p>
 * <p>
 * 该类实现 OcppGateway，因此业务系统可以依赖接口编程；后续如果需要把传输层替换为集群消息网关，
 * 可以替换 OcppGateway 实现而不改业务服务代码。
 * </p>
 */
@Slf4j
public class OcppTemplate implements OcppGateway, DisposableBean {
    private final OcppSessionRepository sessionRepository;
    private final OcppCodec ocppCodec;
    private final ObjectMapper objectMapper;
    private final OcppProperties properties;
    private final OcppSchemaValidator schemaValidator;
    private final Map<String, PendingRequest<?>> pendingRequests = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread thread = new Thread(r, "ocpp-pending-request-cleaner");
        thread.setDaemon(true);
        return thread;
    });

    public OcppTemplate(OcppSessionRepository sessionRepository, OcppCodec ocppCodec, ObjectMapper objectMapper,
                        OcppProperties properties, OcppSchemaValidator schemaValidator) {
        this.sessionRepository = sessionRepository;
        this.ocppCodec = ocppCodec;
        this.objectMapper = OcppObjectMapperFactory.copyOf(objectMapper);
        this.properties = properties;
        this.schemaValidator = schemaValidator;
        startCleaner();
    }

    @Override
    public <R> CompletableFuture<R> call(String chargePointId, OcppVersion version, String action, Object payload, Class<R> responseType) {
        OcppConnection connection = sessionRepository.get(chargePointId);
        if (connection == null || !connection.isOpen()) {
            CompletableFuture<R> failed = new CompletableFuture<>();
            failed.completeExceptionally(new OcppException(OcppErrorCode.GenericError, "充电桩不在线：" + chargePointId));
            return failed;
        }
        if (version != null && connection.getVersion() != version) {
            CompletableFuture<R> failed = new CompletableFuture<>();
            failed.completeExceptionally(new OcppException(OcppErrorCode.ProtocolError, "调用版本与充电桩连接版本不一致"));
            return failed;
        }

        OcppVersion actualVersion = version == null ? connection.getVersion() : version;
        JsonNode requestPayload = objectMapper.valueToTree(payload == null ? objectMapper.createObjectNode() : payload);
        try {
            schemaValidator.validate(actualVersion, action, true, requestPayload);
        } catch (OcppException e) {
            CompletableFuture<R> failed = new CompletableFuture<>();
            failed.completeExceptionally(e);
            return failed;
        }

        String uniqueId = UUID.randomUUID().toString();
        CompletableFuture<R> future = new CompletableFuture<>();
        long timeoutSeconds = properties.getConnectionTimeoutSeconds() == null ? 60L : properties.getConnectionTimeoutSeconds().longValue();
        pendingRequests.put(uniqueId, new PendingRequest<>(responseType, future,
                System.currentTimeMillis() + timeoutSeconds * 1000L, actualVersion, action,
                connection.getChargePointId(), connection.getSessionId()));

        try {
            connection.send(ocppCodec.encodeCall(uniqueId, action, requestPayload));
        } catch (IOException e) {
            pendingRequests.remove(uniqueId);
            future.completeExceptionally(new OcppException(OcppErrorCode.InternalError, "发送 OCPP 命令失败", null, e));
        }
        return future;
    }

    @SuppressWarnings("unchecked")
    public void completeResult(OcppCallResult result) {
        completeResult(null, result);
    }

    @SuppressWarnings("unchecked")
    public void completeResult(String sessionId, OcppCallResult result) {
        PendingRequest<?> pending = pendingRequests.get(result.getUniqueId());
        if (pending == null) { return; }
        if (!isExpectedSession(sessionId, pending)) {
            log.warn("忽略来源会话不匹配的 OCPP CALLRESULT：uniqueId={}, expectedSessionId={}, actualSessionId={}",
                    result.getUniqueId(), pending.getSessionId(), sessionId);
            return;
        }
        if (!pendingRequests.remove(result.getUniqueId(), pending)) { return; }
        try {
            schemaValidator.validate(pending.getVersion(), pending.getAction(), false, result.getPayload());
            Object value = objectMapper.convertValue(result.getPayload(), pending.getResponseType());
            ((CompletableFuture<Object>) pending.getFuture()).complete(value);
        } catch (Exception e) {
            pending.getFuture().completeExceptionally(e);
        }
    }

    public void completeError(OcppCallError error) {
        completeError(null, error);
    }

    public void completeError(String sessionId, OcppCallError error) {
        PendingRequest<?> pending = pendingRequests.get(error.getUniqueId());
        if (pending == null) { return; }
        if (!isExpectedSession(sessionId, pending)) {
            log.warn("忽略来源会话不匹配的 OCPP CALLERROR：uniqueId={}, expectedSessionId={}, actualSessionId={}",
                    error.getUniqueId(), pending.getSessionId(), sessionId);
            return;
        }
        if (!pendingRequests.remove(error.getUniqueId(), pending)) { return; }
        pending.getFuture().completeExceptionally(new OcppException(resolveErrorCode(error.getErrorCode()),
                error.getErrorDescription(), error.getErrorDetails()));
    }

    public int cancelPendingRequestsForSession(String sessionId, String reason) {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            return 0;
        }
        String message = reason == null || reason.trim().isEmpty() ? "OCPP 会话已关闭，等待中的请求已取消" : reason;
        int canceled = 0;
        for (Map.Entry<String, PendingRequest<?>> entry : pendingRequests.entrySet()) {
            PendingRequest<?> pending = entry.getValue();
            if (sessionId.equals(pending.getSessionId()) && pendingRequests.remove(entry.getKey(), pending)) {
                pending.getFuture().completeExceptionally(new OcppException(OcppErrorCode.GenericError, message));
                canceled++;
            }
        }
        return canceled;
    }

    @Override
    public void destroy() {
        shutdown();
    }

    public void shutdown() {
        scheduler.shutdownNow();
        for (PendingRequest<?> pending : pendingRequests.values()) {
            pending.getFuture().completeExceptionally(new OcppException(OcppErrorCode.GenericError, "OCPP 模板关闭，等待中的请求已取消"));
        }
        pendingRequests.clear();
    }

    private void startCleaner() {
        scheduler.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            Iterator<Map.Entry<String, PendingRequest<?>>> iterator = pendingRequests.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, PendingRequest<?>> entry = iterator.next();
                if (entry.getValue().getDeadlineMillis() < now) {
                    iterator.remove();
                    entry.getValue().getFuture().completeExceptionally(new OcppException(OcppErrorCode.GenericError, "等待 OCPP 响应超时"));
                }
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    private boolean isExpectedSession(String actualSessionId, PendingRequest<?> pending) {
        return actualSessionId == null || actualSessionId.equals(pending.getSessionId());
    }

    private OcppErrorCode resolveErrorCode(String errorCode) {
        if (errorCode == null) {
            return OcppErrorCode.GenericError;
        }
        try {
            return OcppErrorCode.valueOf(errorCode);
        } catch (IllegalArgumentException e) {
            return OcppErrorCode.GenericError;
        }
    }
}
