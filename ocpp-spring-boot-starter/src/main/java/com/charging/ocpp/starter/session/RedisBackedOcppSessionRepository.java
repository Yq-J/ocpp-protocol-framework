package com.charging.ocpp.starter.session;

import com.charging.ocpp.core.session.OcppConnection;
import com.charging.ocpp.core.session.OcppSessionRepository;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 基于 Redis 的会话注册仓储（本地连接 + 全局索引）。
 */
@Slf4j
public class RedisBackedOcppSessionRepository implements OcppSessionRepository {
    /*
     * 1. 该实现同时维护本地连接表和 Redis 全局索引：真正的 WebSocket 连接仍在当前 JVM，本地发送最快。
     * 2. Redis 中只记录某台桩归属哪个节点，便于集群中其它节点判断请求该转发到哪里。
     * 3. TTL 用于避免节点异常退出后 Redis 残留脏数据；正常断开时 remove 会主动删除当前节点写入的 key。
     */
    /** Redis 键前缀。 */
    private static final String SESSION_KEY_PREFIX = "ocpp:session:cp:";
    /** 本地 chargePointId 到连接对象的索引，用于低延迟下发。 */
    private final Map<String, OcppConnection> localByChargePointId = new ConcurrentHashMap<>();
    /** 本地 sessionId 到 chargePointId 的反向索引，用于快速断链清理。 */
    private final Map<String, String> localSessionToChargePointId = new ConcurrentHashMap<>();
    /** Redis 模板，用于写入全局注册信息。 */
    private final StringRedisTemplate redisTemplate;
    /** 当前节点标识，用于标记连接归属。 */
    private final String nodeId;
    /** Redis 注册 TTL，防止节点异常退出后永久残留。 */
    private final Duration ttl;

    public RedisBackedOcppSessionRepository(StringRedisTemplate redisTemplate, String nodeId, Duration ttl) {
        this.redisTemplate = redisTemplate;
        this.nodeId = nodeId;
        this.ttl = ttl;
    }

    @Override
    public void save(OcppConnection connection) {
        if (connection == null) {
            return;
        }
        /*
         * 本地索引用于当前节点直接下发：
         * OcppTemplate.callLocal(...) 会通过 chargePointId 从 localByChargePointId 找到 WebSocket 连接。
         */
        localByChargePointId.put(connection.getChargePointId(), connection);
        localSessionToChargePointId.put(connection.getSessionId(), connection.getChargePointId());
        try {
            /*
             * Redis 全局索引用于其它节点判断路由归属：
             *   key   = ocpp:session:cp:{chargePointId}
             *   value = 当前节点 nodeId
             *
             * 其它节点本地没有该桩连接时，会读取这个 key 得到 targetNodeId，
             * 再通过 Redis Pub/Sub 发布到 ocpp:cluster:node:{targetNodeId}。
             * TTL 用来降低节点异常退出时脏路由长期残留的风险。
             */
            redisTemplate.opsForValue().set(redisKey(connection.getChargePointId()), nodeId, ttl);
        } catch (Exception ex) {
            log.warn("写入 Redis 会话注册失败，chargePointId={}", connection.getChargePointId(), ex);
        }
    }

    @Override
    public OcppConnection get(String chargePointId) {
        return localByChargePointId.get(chargePointId);
    }

    @Override
    public void remove(String sessionId) {
        String chargePointId = localSessionToChargePointId.remove(sessionId);
        if (chargePointId == null) {
            return;
        }
        localByChargePointId.remove(chargePointId);
        try {
            String key = redisKey(chargePointId);
            String registeredNode = redisTemplate.opsForValue().get(key);
            /*
             * 只删除仍归属于当前节点的 Redis key。
             * 如果同一 chargePointId 已经重连到其它节点，Redis value 会变成其它 nodeId，
             * 此时当前节点不能删除，否则会误删新的有效路由。
             */
            if (nodeId.equals(registeredNode)) {
                redisTemplate.delete(key);
            }
        } catch (Exception ex) {
            log.warn("清理 Redis 会话注册失败，sessionId={}, chargePointId={}", sessionId, chargePointId, ex);
        }
    }

    @Override
    public Collection<OcppConnection> list() {
        return new ArrayList<>(localByChargePointId.values());
    }

    /**
     * 查询桩归属节点，供跨节点转发路由使用。
     * <p>
     * 该方法只返回 Redis 中记录的 nodeId，不返回连接对象。
     * WebSocket 连接不能跨 JVM 共享；调用方拿到 nodeId 后，需要通过 RedisOcppClusterForwarder
     * 把请求发布到目标节点私有 channel，再由目标节点使用本地连接发送。
     * </p>
     */
    public String lookupNodeId(String chargePointId) {
        try {
            return redisTemplate.opsForValue().get(redisKey(chargePointId));
        } catch (Exception ex) {
            log.warn("查询 Redis 会话归属失败，chargePointId={}", chargePointId, ex);
            return null;
        }
    }

    private String redisKey(String chargePointId) {
        return SESSION_KEY_PREFIX + chargePointId;
    }
}
