package com.charging.ocpp.core.protocol;

/**
 * OCPP 帧编解码器。
 * <p>
 * 作者：JYq
 * 说明：隔离 OCPP JSON 数组格式与业务代码，业务层不需要直接拼接或解析数组。
 * </p>
 */
public interface OcppCodec {
    /*
     * 1. 编解码器把协议数组格式隔离在 core 层，业务代码不需要手写 [2,"id","Action",{}] 这类数组。
     * 2. decode 用于入站消息，encodeCall、encodeCallResult、encodeCallError 分别用于三类出站消息。
     * 3. 如果未来要支持更严格的日志脱敏、追踪字段或不同 JSON 库，可以替换该接口实现。
     */
    OcppFrame decode(String text);
    String encodeCall(String uniqueId, String action, Object payload);
    String encodeCallResult(String uniqueId, Object payload);
    String encodeCallError(String uniqueId, String errorCode, String errorDescription, Object details);
}
