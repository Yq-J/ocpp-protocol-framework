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
    String getSessionId();
    String getChargePointId();
    OcppVersion getVersion();
    boolean isOpen();
    void send(String text) throws IOException;
}
