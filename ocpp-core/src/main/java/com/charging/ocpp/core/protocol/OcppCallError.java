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
    /*
     * 1. CALLERROR 表示某个 CALL 的异常响应，包含错误码、错误描述和可选详情。
     * 2. 平台收到充电桩的 CALLERROR 后，会让对应 CompletableFuture 以异常方式完成。
     */
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
