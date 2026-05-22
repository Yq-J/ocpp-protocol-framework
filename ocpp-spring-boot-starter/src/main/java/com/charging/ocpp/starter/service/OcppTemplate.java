package com.charging.ocpp.starter.service;

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
import com.charging.ocpp.starter.session.RedisBackedOcppSessionRepository;
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
import lombok.extern.slf4j.Slf4j;

/**
 * OCPP 主动下发模板。
 * <p>
 * 下发链路优先本地直发；当本地无连接且开启跨节点转发时，
 * 会基于 Redis 会话注册表查询目标节点并通过 Redis Pub/Sub 转发。
 * </p>
 */
@Slf4j
public class OcppTemplate implements OcppGateway {
    private final OcppSessionRepository sessionRepository;
    private final OcppCodec ocppCodec;
    private final ObjectMapper objectMapper;
    private final OcppProperties properties;
    private final RedisOcppClusterForwarder clusterForwarder;
    private final Map<String, PendingRequest<?>> pendingRequests = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread thread = new Thread(r, "ocpp-pending-cleaner");
        thread.setDaemon(true);
        return thread;
    });

    public OcppTemplate(OcppSessionRepository sessionRepository, OcppCodec ocppCodec, ObjectMapper objectMapper,
                        OcppProperties properties, RedisOcppClusterForwarder clusterForwarder) {
        this.sessionRepository = sessionRepository;
        this.ocppCodec = ocppCodec;
        this.objectMapper = objectMapper;
        this.properties = properties;
        this.clusterForwarder = clusterForwarder;
        if (this.clusterForwarder != null) {
            this.clusterForwarder.setLocalDispatcher((chargePointId, version, action, payload)
                    -> callLocal(chargePointId, version, action, payload, Object.class));
        }
        startCleaner();
    }

    /**
     * 下发命令：本地优先，按配置可回退到跨节点转发。
     */
    @Override
    public <R> CompletableFuture<R> call(String chargePointId, OcppVersion version, String action, Object payload, Class<R> responseType) {
        OcppConnection local = sessionRepository.get(chargePointId);
        if (local != null && local.isOpen()) {
            return callLocal(chargePointId, version, action, payload, responseType);
        }
        /*
         * 本地没有连接时，才尝试走 Redis 跨节点转发。
         *
         * 需要同时满足：
         * 1. cross-node-forward-enabled=true：允许节点间转发下行命令。
         * 2. redis-session-registry-enabled=true：Redis 中存在 chargePointId -> nodeId 的归属索引。
         * 3. clusterForwarder != null：Redis Pub/Sub 转发组件已成功初始化。
         * 4. sessionRepository 是 RedisBackedOcppSessionRepository：可以查询 Redis 归属节点。
         *
         * Redis 归属索引只告诉当前节点“这台桩在哪个 nodeId 上”，真正的 WebSocket 发送仍由目标节点
         * 收到 Pub/Sub request 后在本地执行 callLocal(...) 完成。
         */
        if (Boolean.TRUE.equals(properties.getCrossNodeForwardEnabled())
                && Boolean.TRUE.equals(properties.getRedisSessionRegistryEnabled())
                && clusterForwarder != null
                && sessionRepository instanceof RedisBackedOcppSessionRepository) {
            String targetNodeId = ((RedisBackedOcppSessionRepository) sessionRepository).lookupNodeId(chargePointId);
            if (targetNodeId != null && !properties.getNodeId().equals(targetNodeId)) {
                /*
                 * 将请求发布到目标节点私有 channel：
                 *   ocpp:cluster:node:{targetNodeId}
                 * 目标节点的 RedisOcppClusterForwarder.onMessage(...) 会收到并处理该请求。
                 */
                return clusterForwarder.forward(targetNodeId, chargePointId, version, action, payload, responseType);
            }
        }
        CompletableFuture<R> failed = new CompletableFuture<>();
        failed.completeExceptionally(new OcppException(OcppErrorCode.GenericError, "充电桩不在线：" + chargePointId));
        return failed;
    }

    /**
     * 本地节点直连下发。
     */
    private <R> CompletableFuture<R> callLocal(String chargePointId, OcppVersion version, String action, Object payload, Class<R> responseType) {
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
        long timeoutSeconds = properties.getConnectionTimeoutSeconds() == null ? 60L : properties.getConnectionTimeoutSeconds().longValue();
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
        long interval = properties.getPendingCleanupIntervalMillis() == null ? 1000L : Math.max(200L, properties.getPendingCleanupIntervalMillis());
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
        }, interval, interval, TimeUnit.MILLISECONDS);
    }
}
