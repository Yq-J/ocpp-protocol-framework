package com.charging.ocpp.core.schema;

import com.charging.ocpp.core.enums.OcppVersion;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * OCPP JSON Schema 校验扩展点。
 * <p>
 * 作者：JYq
 * 说明：生产环境可实现该接口并注入官方 Schema 校验能力。
 * </p>
 */
public interface OcppSchemaValidator {
    void validate(OcppVersion version, String action, boolean request, JsonNode payload);
}
