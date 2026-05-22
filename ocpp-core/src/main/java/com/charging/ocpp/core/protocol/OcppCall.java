package com.charging.ocpp.core.protocol;

import com.charging.ocpp.core.enums.OcppMessageType;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

/**
 * OCPP CALL 请求帧。
 * 作者：JYq
 */
@Getter
public class OcppCall extends OcppFrame {
    /*
     * 1. CALL 表示“请求帧”，既可能是充电桩发给平台，也可能是平台发给充电桩。
     * 2. action 决定要执行哪类协议动作，payload 保存该动作携带的业务字段。
     */
    private final String action;
    private final JsonNode payload;

    public OcppCall(String uniqueId, String action, JsonNode payload) {
        super(OcppMessageType.CALL, uniqueId);
        this.action = action;
        this.payload = payload;
    }

}
