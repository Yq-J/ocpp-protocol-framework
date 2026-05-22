package com.charging.ocpp.core.session;

import java.util.Collection;

/**
 * OCPP 在线会话仓储。
 * 作者：JYq
 */
public interface OcppSessionRepository {
    /*
     * 1. 会话仓库存储“哪台充电桩当前对应哪条连接”，平台下发命令时必须先通过它找到连接。
     * 2. save 在连接建立时调用，remove 在连接关闭时调用，get 在下发命令时调用。
     * 3. 单机可用内存实现，集群场景需要结合 Redis 或网关路由保存连接归属。
     */
    void save(OcppConnection connection);
    OcppConnection get(String chargePointId);
    void remove(String sessionId);
    Collection<OcppConnection> list();
}
