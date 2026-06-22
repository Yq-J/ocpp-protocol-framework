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

    /**
     * 是否为框架内置的示例/兜底处理器。
     * 业务处理器与框架默认处理器注册到同一 version + action 时，业务处理器优先。
     */
    default boolean frameworkDefault() {
        return false;
    }
}
