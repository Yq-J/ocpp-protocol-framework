package com.charging.ocpp.core.session;

import java.util.Collection;

/**
 * OCPP 在线会话仓储。
 * 作者：JYq
 */
public interface OcppSessionRepository {
    void save(OcppConnection connection);
    OcppConnection get(String chargePointId);
    void remove(String sessionId);
    Collection<OcppConnection> list();
}
