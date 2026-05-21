package com.charging.ocpp.core.protocol;

import com.charging.ocpp.core.enums.OcppMessageType;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

/**
 * OCPP CALLERROR 异常响应帧。
 * 作者：JYq
 */
@Getter
public class OcppCallError extends OcppFrame {
    private final String errorCode;
    private final String errorDescription;
    private final JsonNode errorDetails;

    public OcppCallError(String uniqueId, String errorCode, String errorDescription, JsonNode errorDetails) {
        super(OcppMessageType.CALL_ERROR, uniqueId);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.errorDetails = errorDetails;
    }

}
