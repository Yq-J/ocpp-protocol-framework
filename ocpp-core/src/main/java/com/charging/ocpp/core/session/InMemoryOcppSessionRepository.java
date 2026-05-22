package com.charging.ocpp.core.session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单机内存会话仓储。
 * <p>
 * 作者：JYq
 * 说明：适合开发测试；集群部署时建议替换为业务自己的实现。
 * </p>
 */
public class InMemoryOcppSessionRepository implements OcppSessionRepository {
    /*
     * 1. 这是单 JVM 内存实现，适合本地开发、单实例部署或测试。
     * 2. byChargePointId 用于按桩编号查连接；sessionToChargePointId 用于连接断开时从 sessionId 反查桩编号并清理。
     * 3. ConcurrentHashMap 保证 WebSocket 多线程读写时不需要额外加锁。
     */
    private final Map<String, OcppConnection> byChargePointId = new ConcurrentHashMap<>();
    private final Map<String, String> sessionToChargePointId = new ConcurrentHashMap<>();

    @Override
    public void save(OcppConnection connection) {
        if (connection == null) { return; }
        byChargePointId.put(connection.getChargePointId(), connection);
        sessionToChargePointId.put(connection.getSessionId(), connection.getChargePointId());
    }

    @Override
    public OcppConnection get(String chargePointId) { return byChargePointId.get(chargePointId); }

    @Override
    public void remove(String sessionId) {
        String chargePointId = sessionToChargePointId.remove(sessionId);
        if (chargePointId != null) {
            byChargePointId.remove(chargePointId);
        }
    }

    @Override
    public Collection<OcppConnection> list() { return new ArrayList<>(byChargePointId.values()); }
}
