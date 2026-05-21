package com.charging.ocpp.core.enums;

import lombok.Getter;

/**
 * OCPP-J 消息类型。
 * <p>
 * 作者：JYq
 * 说明：OCPP JSON over WebSocket 使用 JSON 数组承载消息，首位为消息类型。
 * 2 = CALL；3 = CALLRESULT；4 = CALLERROR。
 * </p>
 */
@Getter
public enum OcppMessageType {
    CALL(2), CALL_RESULT(3), CALL_ERROR(4);

    private final int code;

    OcppMessageType(int code) { this.code = code; }

    public static OcppMessageType fromCode(int code) {
        for (OcppMessageType item : values()) {
            if (item.code == code) { return item; }
        }
        return null;
    }
}
