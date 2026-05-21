package com.charging.ocpp.starter.service;

import lombok.extern.slf4j.Slf4j;
import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.exception.OcppErrorCode;
import com.charging.ocpp.core.exception.OcppException;
import com.charging.ocpp.core.gateway.OcppGateway;
import com.charging.ocpp.core.protocol.OcppCallError;
import com.charging.ocpp.core.protocol.OcppCallResult;
import com.charging.ocpp.core.protocol.OcppCodec;
import com.charging.ocpp.core.session.OcppConnection;
import com.charging.ocpp.core.session.OcppSessionRepository;
import com.charging.ocpp.starter.autoconfigure.OcppProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PreDestroy;

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
public class OcppTemplate implements OcppGateway {
    private final OcppSessionRepository sessionRepository;
    private final OcppCodec ocppCodec;
    private final ObjectMapper objectMapper;
    private final OcppProperties properties;
    private final Map<String, PendingRequest<?>> pendingRequests = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public OcppTemplate(OcppSessionRepository sessionRepository, OcppCodec ocppCodec, ObjectMapper objectMapper, OcppProperties properties) {
        this.sessionRepository = sessionRepository;
        this.ocppCodec = ocppCodec;
        this.objectMapper = objectMapper;
        this.properties = properties;
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

        String uniqueId = UUID.randomUUID().toString();
        CompletableFuture<R> future = new CompletableFuture<>();
        long timeoutSeconds = resolveTimeoutSeconds();
        pendingRequests.put(uniqueId, new PendingRequest<>(responseType, future, System.currentTimeMillis() + timeoutSeconds * 1000L));

        try {
            connection.send(ocppCodec.encodeCall(uniqueId, action, payload));
        } catch (IOException e) {
            pendingRequests.remove(uniqueId);
            future.completeExceptionally(new OcppException(OcppErrorCode.InternalError, "发送 OCPP 命令失败", null, e));
        }
        return future;
    }

    @SuppressWarnings("unchecked")
    public void completeResult(OcppCallResult result) {
        PendingRequest<?> pending = pendingRequests.remove(result.getUniqueId());
        if (pending == null) { return; }
        Object value = objectMapper.convertValue(result.getPayload(), pending.getResponseType());
        ((CompletableFuture<Object>) pending.getFuture()).complete(value);
    }

    public void completeError(OcppCallError error) {
        PendingRequest<?> pending = pendingRequests.remove(error.getUniqueId());
        if (pending == null) { return; }
        pending.getFuture().completeExceptionally(new OcppException(OcppErrorCode.GenericError, error.getErrorDescription(), error.getErrorDetails()));
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

    /**
     * 解析业务请求超时时间。
     * <p>
     * 优先级：
     * 1. requestTimeoutSeconds（推荐新配置）；
     * 2. connectionTimeoutSeconds（兼容历史配置）；
     * 3. 默认值 60 秒。
     * </p>
     */
    private long resolveTimeoutSeconds() {
        Integer requestTimeoutSeconds = properties.getRequestTimeoutSeconds();
        if (requestTimeoutSeconds != null && requestTimeoutSeconds > 0) {
            return requestTimeoutSeconds.longValue();
        }
        Integer legacyTimeoutSeconds = properties.getConnectionTimeoutSeconds();
        if (legacyTimeoutSeconds != null && legacyTimeoutSeconds > 0) {
            return legacyTimeoutSeconds.longValue();
        }
        return 60L;
    }

    /**
     * 容器关闭时优雅关闭后台清理线程。
     * <p>
     * 目的：
     * 1. 避免应用重启/热部署后遗留非守护线程造成资源泄漏；
     * 2. 让线程池生命周期与 Spring 容器一致，便于生产环境运维。
     * </p>
     */
    @PreDestroy
    public void destroy() {
        scheduler.shutdownNow();
    }
}
