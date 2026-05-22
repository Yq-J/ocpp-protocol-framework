package com.charging.ocpp.starter.service;

import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.exception.OcppErrorCode;
import com.charging.ocpp.core.exception.OcppException;
import com.charging.ocpp.starter.autoconfigure.OcppProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.core.StringRedisTemplate;

@Slf4j
/**
 * Redis 集群转发器。
 * <p>
 * 通过节点私有 channel（ocpp:cluster:node:{nodeId}）在实例间转发下行命令，
 * 并使用 requestId 关联请求与响应。
 * </p>
 */
public class RedisOcppClusterForwarder implements MessageListener {
    private static final String CHANNEL_PREFIX = "ocpp:cluster:node:";
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;
    private final OcppProperties properties;
    private final RedisMessageListenerContainer listenerContainer;
    private final ConcurrentMap<String, CompletableFuture<Object>> responseWaiters = new ConcurrentHashMap<>();
    @Setter
    private LocalCommandDispatcher localDispatcher;

    public RedisOcppClusterForwarder(StringRedisTemplate redisTemplate, ObjectMapper objectMapper, OcppProperties properties,
                                     RedisMessageListenerContainer listenerContainer) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.properties = properties;
        this.listenerContainer = listenerContainer;
        this.listenerContainer.addMessageListener(this, ChannelTopic.of(channelOf(properties.getNodeId())));
    }

    /**
     * 向目标节点转发一次 OCPP 下行请求。
     */
    public <R> CompletableFuture<R> forward(String targetNodeId, String chargePointId, OcppVersion version,
                                            String action, Object payload, Class<R> responseType) {
        String requestId = UUID.randomUUID().toString();
        CompletableFuture<Object> waiter = new CompletableFuture<>();
        responseWaiters.put(requestId, waiter);
        try {
            Map<String, Object> command = new java.util.HashMap<String, Object>();
            command.put("kind", "request");
            command.put("requestId", requestId);
            command.put("sourceNodeId", properties.getNodeId());
            command.put("chargePointId", chargePointId);
            command.put("version", version.name());
            command.put("action", action);
            command.put("payload", payload);
            redisTemplate.convertAndSend(channelOf(targetNodeId), objectMapper.writeValueAsString(command));
        } catch (Exception ex) {
            responseWaiters.remove(requestId);
            CompletableFuture<R> failed = new CompletableFuture<R>();
            failed.completeExceptionally(new OcppException(OcppErrorCode.InternalError, "跨节点转发发送失败", null, ex));
            return failed;
        }
        return waiter.thenApply(responseType::cast);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String body = new String(message.getBody(), StandardCharsets.UTF_8);
            Map<String, Object> data = objectMapper.readValue(body, new TypeReference<Map<String, Object>>() {});
            String kind = String.valueOf(data.get("kind"));
            if ("request".equals(kind)) {
                handleRequest(data);
                return;
            }
            if ("response".equals(kind)) {
                handleResponse(data);
            }
        } catch (Exception ex) {
            log.warn("处理跨节点消息失败", ex);
        }
    }

    private void handleRequest(Map<String, Object> data) {
        if (localDispatcher == null) { return; }
        String sourceNodeId = String.valueOf(data.get("sourceNodeId"));
        String requestId = String.valueOf(data.get("requestId"));
        String chargePointId = String.valueOf(data.get("chargePointId"));
        String action = String.valueOf(data.get("action"));
        OcppVersion version = OcppVersion.valueOf(String.valueOf(data.get("version")));
        Object payload = data.get("payload");
        localDispatcher.dispatch(chargePointId, version, action, payload)
                .whenComplete((value, throwable) -> publishResponse(sourceNodeId, requestId, value, throwable));
    }

    private void publishResponse(String sourceNodeId, String requestId, Object value, Throwable throwable) {
        try {
            Map<String, Object> response = new java.util.HashMap<String, Object>();
            response.put("kind", "response");
            response.put("requestId", requestId);
            response.put("ok", throwable == null);
            if (throwable == null) {
                response.put("payload", value);
            } else {
                Throwable cause = throwable.getCause() == null ? throwable : throwable.getCause();
                response.put("error", cause.getMessage());
            }
            redisTemplate.convertAndSend(channelOf(sourceNodeId), objectMapper.writeValueAsString(response));
        } catch (Exception ex) {
            log.warn("发布跨节点响应失败", ex);
        }
    }

    private void handleResponse(Map<String, Object> data) {
        String requestId = String.valueOf(data.get("requestId"));
        CompletableFuture<Object> waiter = responseWaiters.remove(requestId);
        if (waiter == null) { return; }
        Boolean ok = Boolean.valueOf(String.valueOf(data.get("ok")));
        if (ok) {
            waiter.complete(data.get("payload"));
            return;
        }
        waiter.completeExceptionally(new OcppException(OcppErrorCode.GenericError, String.valueOf(data.get("error"))));
    }

    private String channelOf(String nodeId) { return CHANNEL_PREFIX + nodeId; }

    public interface LocalCommandDispatcher {
        CompletableFuture<Object> dispatch(String chargePointId, OcppVersion version, String action, Object payload);
    }
}
