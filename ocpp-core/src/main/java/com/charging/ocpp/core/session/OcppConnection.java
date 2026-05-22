package com.charging.ocpp.core.session;

import com.charging.ocpp.core.enums.OcppVersion;
import java.io.IOException;

/**
 * OCPP 连接抽象。
 * <p>
 * 作者：JYq
 * 说明：core 模块不依赖 Spring，仅定义连接元数据和发送文本所需接口。
 * </p>
 */
public interface OcppConnection {
    /*

     * 1. core 模块不依赖 Spring，因此用这个接口抽象一条可发送文本的 OCPP 连接。
     * 2. starter 中的 SpringOcppConnection 会把 Spring WebSocketSession 适配成该接口。
     * 3. send 只发送已经编码好的 OCPP 文本帧，不关心 payload 的业务含义。
     */
    String getSessionId();
    String getChargePointId();
    OcppVersion getVersion();
    boolean isOpen();
    void send(String text) throws IOException;
}
