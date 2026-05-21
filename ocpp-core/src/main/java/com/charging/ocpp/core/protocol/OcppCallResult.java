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
    private final JsonNode payload;

    public OcppCallResult(String uniqueId, JsonNode payload) {
        super(OcppMessageType.CALL_RESULT, uniqueId);
        this.payload = payload;
    }

}
