package com.charging.ocpp.core.protocol;

/**
 * OCPP 帧编解码器。
 * <p>
 * 作者：JYq
 * 说明：隔离 OCPP JSON 数组格式与业务代码，业务层不需要直接拼接或解析数组。
 * </p>
 */
public interface OcppCodec {
    OcppFrame decode(String text);
    String encodeCall(String uniqueId, String action, Object payload);
    String encodeCallResult(String uniqueId, Object payload);
    String encodeCallError(String uniqueId, String errorCode, String errorDescription, Object details);
}
