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
        localByChargePointId.put(connection.getChargePointId(), connection);
        localSessionToChargePointId.put(connection.getSessionId(), connection.getChargePointId());
        try {
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

    private String redisKey(String chargePointId) {
        return SESSION_KEY_PREFIX + chargePointId;
    }
}
