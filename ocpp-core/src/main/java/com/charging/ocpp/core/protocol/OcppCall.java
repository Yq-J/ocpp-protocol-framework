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
    private final String action;
    private final JsonNode payload;

    public OcppCall(String uniqueId, String action, JsonNode payload) {
        super(OcppMessageType.CALL, uniqueId);
        this.action = action;
        this.payload = payload;
    }

}
