package com.charging.ocpp.starter.service;

import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.exception.OcppErrorCode;
import com.charging.ocpp.core.exception.OcppException;
import com.charging.ocpp.starter.autoconfigure.OcppProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.lang.NonNull;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Redis 集群转发器。
 * <p>
 * 通过节点私有 channel（ocpp:cluster:node:{nodeId}）在实例间转发下行命令，
 * 并使用 requestId 关联请求与响应。
 * </p>
 */
@Slf4j
public class RedisOcppClusterForwarder implements MessageListener {
    private static final String CHANNEL_PREFIX = "ocpp:cluster:node:";
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;
    private final OcppProperties properties;
    private final ConcurrentMap<String, CompletableFuture<Object>> responseWaiters = new ConcurrentHashMap<>();
    @Setter
    private LocalCommandDispatcher localDispatcher;

    public RedisOcppClusterForwarder(StringRedisTemplate redisTemplate, ObjectMapper objectMapper, OcppProperties properties,
                                     RedisMessageListenerContainer listenerContainer) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.properties = properties;
        /*
         * 每个服务实例启动时只订阅自己的节点私有 channel：
         *   ocpp:cluster:node:{当前 nodeId}
         *
         * 其它节点转发请求时，会先从 Redis 会话注册表查到 chargePointId 所属的 targetNodeId，
         * 然后 publish 到 ocpp:cluster:node:{targetNodeId}。因此只有配置了相同 nodeId
         * 并订阅该 channel 的实例会收到这条消息。
         *
         * 注意：
         * 1. nodeId 必须全局唯一，否则多个实例会订阅同一个 channel，导致同一转发消息被多个实例收到。
         * 2. Redis Pub/Sub 不持久化消息，目标节点离线或订阅中断时，这条转发消息会丢失。
         */
        listenerContainer.addMessageListener(this, ChannelTopic.of(channelOf(properties.getNodeId())));
    }

    /**
     * 向目标节点转发一次 OCPP 下行请求。
     * <p>
     * 当前节点本地没有 chargePointId 对应的 WebSocket 连接时，会调用该方法把下行命令发给
     * Redis 会话注册表中记录的目标节点。该方法只负责节点间转发，不直接接触 WebSocket。
     * </p>
     * <p>
     * requestId 用于关联本次跨节点请求和后续跨节点响应：
     * 1. 转发前先把 requestId -> CompletableFuture 放入 responseWaiters。
     * 2. 目标节点执行完成后，会带着同一个 requestId publish response 回来源节点。
     * 3. 来源节点收到 response 后，在 handleResponse 中取出对应 Future 并完成它。
     * </p>
     */
    public <R> CompletableFuture<R> forward(String targetNodeId, String chargePointId, OcppVersion version,
                                            String action, Object payload, Class<R> responseType) {
        String requestId = UUID.randomUUID().toString();
        CompletableFuture<Object> waiter = new CompletableFuture<>();
        responseWaiters.put(requestId, waiter);
        try {
            /*
             * kind=request 表示这是一条跨节点下行命令。
             * sourceNodeId 用于目标节点执行完成后知道应该把 response 发回哪个节点。
             * chargePointId/version/action/payload 是目标节点执行本地 OCPP 下发所需的完整参数。
             */
            Map<String, Object> command = new HashMap<>();
            command.put("kind", "request");
            command.put("requestId", requestId);
            command.put("sourceNodeId", properties.getNodeId());
            command.put("chargePointId", chargePointId);
            command.put("version", version.name());
            command.put("action", action);
            command.put("payload", payload);
            /*
             * 关键路由点：
             * channelOf(targetNodeId) 会生成 ocpp:cluster:node:{targetNodeId}。
             * 只有目标节点在构造函数中订阅了这个 channel，才会进入它自己的 onMessage 方法。
             */
            redisTemplate.convertAndSend(channelOf(targetNodeId), objectMapper.writeValueAsString(command));
        } catch (Exception ex) {
            responseWaiters.remove(requestId);
            CompletableFuture<R> failed = new CompletableFuture<>();
            failed.completeExceptionally(new OcppException(OcppErrorCode.InternalError, "跨节点转发发送失败", null, ex));
            return failed;
        }
        return waiter.thenApply(responseType::cast);
    }

    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        try {
            /*
             * 所有订阅到当前节点私有 channel 的 Redis 消息都会进入这里。
             * 消息体统一使用 JSON Map，通过 kind 字段区分方向：
             * - request：其它节点请求当前节点代为下发 OCPP 命令。
             * - response：其它节点返回当前节点之前发起的跨节点调用结果。
             */
            String body = new String(message.getBody(), StandardCharsets.UTF_8);
            Map<String, Object> data = objectMapper.readValue(body, new TypeReference<Map<String, Object>>() {
            });
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

    /**
     * 处理其它节点转发到当前节点的下行请求。
     * <p>
     * 当前节点收到 request 后，不再查 Redis 路由，而是通过 localDispatcher 进入本节点本地发送链路。
     * localDispatcher 在 OcppTemplate 构造函数中绑定为 callLocal(...)，因此最终会：
     * 1. 从当前 JVM 的 sessionRepository.get(chargePointId) 查找 WebSocket 连接。
     * 2. 通过 connection.send(...) 把 OCPP CALL 发给充电桩。
     * 3. 等待充电桩返回 CALLRESULT/CALLERROR 后完成 Future。
     * 4. 在 whenComplete 中调用 publishResponse，把结果发回来源节点。
     * </p>
     */
    private void handleRequest(Map<String, Object> data) {
        if (localDispatcher == null) {
            return;
        }
        String sourceNodeId = String.valueOf(data.get("sourceNodeId"));
        String requestId = String.valueOf(data.get("requestId"));
        String chargePointId = String.valueOf(data.get("chargePointId"));
        String action = String.valueOf(data.get("action"));
        OcppVersion version = OcppVersion.valueOf(String.valueOf(data.get("version")));
        Object payload = data.get("payload");
        localDispatcher.dispatch(chargePointId, version, action, payload)
                .whenComplete((value, throwable) -> publishResponse(sourceNodeId, requestId, value, throwable));
    }

    /**
     * 将当前节点本地执行结果返回给来源节点。
     * <p>
     * response 会发布到 ocpp:cluster:node:{sourceNodeId}，也就是最初发起 forward 的节点。
     * requestId 必须原样带回，来源节点依靠它从 responseWaiters 中找到对应的 CompletableFuture。
     * </p>
     */
    private void publishResponse(String sourceNodeId, String requestId, Object value, Throwable throwable) {
        try {
            Map<String, Object> response = new HashMap<>();
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

    /**
     * 处理其它节点返回的跨节点调用响应。
     * <p>
     * 当前节点在 forward(...) 中创建了 requestId -> Future 的等待关系。
     * 收到 response 后，先按 requestId 移除并取出 Future，再根据 ok 字段决定正常完成或异常完成。
     * 如果 requestId 不存在，通常说明调用已经超时、重复响应或本节点重启过，此时直接忽略。
     * </p>
     */
    private void handleResponse(Map<String, Object> data) {
        String requestId = String.valueOf(data.get("requestId"));
        CompletableFuture<Object> waiter = responseWaiters.remove(requestId);
        if (waiter == null) {
            return;
        }
        boolean ok = Boolean.parseBoolean(String.valueOf(data.get("ok")));
        if (ok) {
            waiter.complete(data.get("payload"));
            return;
        }
        waiter.completeExceptionally(new OcppException(OcppErrorCode.GenericError, String.valueOf(data.get("error"))));
    }

    /**
     * 节点私有 Redis Pub/Sub channel。
     * <p>
     * nodeId 和 channel 一一对应：
     * - nodeId = ocpp-node-a -> ocpp:cluster:node:ocpp-node-a
     * - nodeId = ocpp-node-b -> ocpp:cluster:node:ocpp-node-b
     * </p>
     */
    private String channelOf(String nodeId) {
        return CHANNEL_PREFIX + nodeId;
    }

    public interface LocalCommandDispatcher {
        /**
         * 在收到跨节点 request 的目标节点上执行本地 OCPP 下发。
         * <p>
         * 该接口由 OcppTemplate 注入实现，当前实现绑定到 callLocal(...)。
         * 这样 Redis 转发层不需要知道 WebSocket、PendingRequest、OCPP 编码等细节。
         * </p>
         */
        CompletableFuture<Object> dispatch(String chargePointId, OcppVersion version, String action, Object payload);
    }
}
