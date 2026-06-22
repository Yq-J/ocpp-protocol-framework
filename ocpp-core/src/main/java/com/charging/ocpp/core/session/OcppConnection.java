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

    /**
     * 主动关闭连接。
     * <p>
     * 默认实现保持兼容；具体传输层可覆盖该方法，在重复登录、节点下线或运维隔离时释放底层连接。
     * </p>
     */
    default void close() throws IOException {
        // 默认无操作，避免破坏已有自定义 OcppConnection 实现。
    }
}
