package com.charging.ocpp.core.protocol;

import com.charging.ocpp.core.enums.OcppMessageType;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

/**
 * OCPP CALLRESULT 响应帧。
 * 作者：JYq
 */
@Getter
public class OcppCallResult extends OcppFrame {
    /*
     * 1. CALLRESULT 表示某个 CALL 的正常响应，uniqueId 必须和原请求一致。
     * 2. payload 会在 OcppTemplate.completeResult 中转换为调用方声明的响应 DTO 类型。
     */
    private final JsonNode payload;

    public OcppCallResult(String uniqueId, JsonNode payload) {
        super(OcppMessageType.CALL_RESULT, uniqueId);
        this.payload = payload;
    }

}
