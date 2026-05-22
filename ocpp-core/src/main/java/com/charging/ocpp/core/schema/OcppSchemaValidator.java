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
    /*
     * 1. 这是协议字段校验扩展点，适合接入官方 JSON Schema 或业务自定义校验。
     * 2. request=true 表示校验请求 payload，false 可用于校验响应 payload。
     * 3. 校验失败时建议抛 OcppException，并选择 PropertyConstraintViolation、TypeConstraintViolation 等错误码。
     */
    void validate(OcppVersion version, String action, boolean request, JsonNode payload);
}
