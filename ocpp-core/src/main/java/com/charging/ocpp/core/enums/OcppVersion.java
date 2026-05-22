package com.charging.ocpp.core.enums;

import lombok.Getter;

/**
 * OCPP 协议版本枚举。
 * <p>
 * 作者：JYq
 * 说明：框架内部统一使用枚举表示协议版本，避免业务层直接依赖 WebSocket 子协议字符串。
 * 后续扩展更高版本时，应新增枚举值并补充子协议映射。
 * </p>
 */
@Getter
public enum OcppVersion {
    /*
     * 1. WebSocket 握手时会协商子协议，充电桩常传 ocpp1.6 或 ocpp2.0.1。
     * 2. 框架内部用枚举代替裸字符串，能减少拼写错误，也方便在注册处理器时按版本隔离。
     * 3. 后续扩展新协议版本时，需要在这里增加枚举值，并补齐子协议字符串和默认处理器。
     */
    /** OCPP 1.6 JSON over WebSocket。 */
    OCPP_16("1.6", "ocpp1.6"),
    /** OCPP 2.0.1 JSON over WebSocket。 */
    OCPP_201("2.0.1", "ocpp2.0.1");

    private final String version;
    private final String subProtocol;

    OcppVersion(String version, String subProtocol) {
        this.version = version;
        this.subProtocol = subProtocol;
    }

    /** 根据 WebSocket 子协议解析版本。 */
    public static OcppVersion fromSubProtocol(String subProtocol) {
        if (subProtocol == null) { return null; }
        for (OcppVersion item : values()) {
            if (item.subProtocol.equalsIgnoreCase(subProtocol)) {
                return item;
            }
        }
        return null;
    }

    /** 根据文本解析版本，支持 1.6、2.0.1、ocpp1.6、ocpp2.0.1。 */
    public static OcppVersion fromText(String text) {
        if (text == null) { return null; }
        for (OcppVersion item : values()) {
            if (item.version.equalsIgnoreCase(text) || item.subProtocol.equalsIgnoreCase(text)) {
                return item;
            }
        }
        return null;
    }
}
