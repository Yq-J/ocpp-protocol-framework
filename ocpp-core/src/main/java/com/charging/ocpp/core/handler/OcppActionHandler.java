package com.charging.ocpp.core.handler;

import com.charging.ocpp.core.enums.OcppVersion;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * OCPP Action 处理器接口。
 * 作者：JYq
 */
public interface OcppActionHandler {
    OcppVersion version();
    String action();
    Object handle(OcppRequestContext context, JsonNode payload) throws Exception;
}
