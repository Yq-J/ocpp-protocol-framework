package com.charging.ocpp.core.handler;

import com.charging.ocpp.core.enums.OcppVersion;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * OCPP Action 处理器接口。
 * 作者：JYq
 */
public interface OcppActionHandler {
    /*
     * 1. 每个 OCPP Action 最终都会被包装成这个接口，无论它来自默认处理器还是 @OcppActionMapping 注解方法。
     * 2. handle 的 payload 使用 JsonNode，是为了先保留原始 JSON，再由具体处理器决定转成哪个 DTO。
     * 3. 返回值会被框架序列化为 CALLRESULT 的 payload；抛出异常则会被转换成 CALLERROR。
     */
    OcppVersion version();
    String action();
    Object handle(OcppRequestContext context, JsonNode payload) throws Exception;
}
