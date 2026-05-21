package com.charging.ocpp.core.schema;

import com.charging.ocpp.core.enums.OcppVersion;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 默认空 Schema 校验器。
 * 作者：JYq
 */
public class NoopOcppSchemaValidator implements OcppSchemaValidator {
    @Override
    public void validate(OcppVersion version, String action, boolean request, JsonNode payload) {
        // 默认不校验。业务系统提供 OcppSchemaValidator Bean 后会覆盖该实现。
    }
}
