package com.charging.ocpp.starter.schema;

import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.exception.OcppErrorCode;
import com.charging.ocpp.core.exception.OcppException;
import com.charging.ocpp.core.metadata.OcppActionMetadata;
import com.charging.ocpp.core.schema.OcppSchemaValidator;
import com.charging.ocpp.starter.autoconfigure.OcppProperties;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 基于协议元数据的基础校验器。
 * <p>
 * 该校验器提供生产环境必须具备的第一道防线：校验协议版本、Action 是否属于对应 OCPP 版本、Payload 是否为 JSON 对象。
 * 如果需要字段级 required、enum、长度和嵌套结构校验，可替换为接入官方 JSON Schema 的 OcppSchemaValidator 实现。
 * </p>
 */
public class MetadataOcppSchemaValidator implements OcppSchemaValidator {
    private final OcppProperties properties;

    public MetadataOcppSchemaValidator(OcppProperties properties) {
        this.properties = properties;
    }

    @Override
    public void validate(OcppVersion version, String action, boolean request, JsonNode payload) {
        if (version == null) {
            throw new OcppException(OcppErrorCode.ProtocolError, "缺少或不支持的 OCPP 协议版本");
        }
        if (action == null || action.trim().isEmpty()) {
            throw new OcppException(OcppErrorCode.ProtocolError, "OCPP Action 不能为空");
        }
        if (payload == null || !payload.isObject()) {
            throw new OcppException(OcppErrorCode.FormationViolation, "OCPP Payload 必须是 JSON 对象");
        }
        if (!Boolean.TRUE.equals(properties.getAllowUnknownActions())
                && Boolean.TRUE.equals(properties.getValidateKnownActions())
                && !OcppActionMetadata.isKnownAction(version, action)) {
            throw new OcppException(OcppErrorCode.NotImplemented, "当前协议版本不支持 Action：" + version + "/" + action);
        }
    }
}
