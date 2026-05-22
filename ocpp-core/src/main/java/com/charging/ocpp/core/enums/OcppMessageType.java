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
    /*
     * 1. OCPP-J 的每条 WebSocket 文本消息都是 JSON 数组，数组第 0 位就是消息类型编号。
     * 2. CALL=2 表示请求，CALL_RESULT=3 表示正常响应，CALL_ERROR=4 表示异常响应。
     * 3. uniqueId 会把一次 CALL 与后续 CALL_RESULT 或 CALL_ERROR 关联起来，类似一次异步请求的流水号。
     */
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
