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
    private final Map<String, OcppConnection> byChargePointId = new ConcurrentHashMap<>();
    private final Map<String, String> sessionToChargePointId = new ConcurrentHashMap<>();

    @Override
    public void save(OcppConnection connection) {
        if (connection == null) { return; }
        OcppConnection previous = byChargePointId.put(connection.getChargePointId(), connection);
        if (previous != null && !previous.getSessionId().equals(connection.getSessionId())) {
            sessionToChargePointId.remove(previous.getSessionId());
        }
        sessionToChargePointId.put(connection.getSessionId(), connection.getChargePointId());
    }

    @Override
    public OcppConnection get(String chargePointId) { return byChargePointId.get(chargePointId); }

    @Override
    public void remove(String sessionId) {
        String chargePointId = sessionToChargePointId.remove(sessionId);
        if (chargePointId != null) {
            byChargePointId.computeIfPresent(chargePointId, (key, connection) ->
                    sessionId.equals(connection.getSessionId()) ? null : connection);
        }
    }

    @Override
    public Collection<OcppConnection> list() { return new ArrayList<>(byChargePointId.values()); }
}
