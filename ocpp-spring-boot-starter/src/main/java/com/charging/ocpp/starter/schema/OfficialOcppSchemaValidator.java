package com.charging.ocpp.starter.schema;

import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.exception.OcppErrorCode;
import com.charging.ocpp.core.exception.OcppException;
import com.charging.ocpp.core.metadata.OcppActionDescriptor;
import com.charging.ocpp.core.metadata.OcppActionMetadata;
import com.charging.ocpp.core.schema.OcppSchemaValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Validator backed by bundled official OCPP JSON Schema resources.
 */
public class OfficialOcppSchemaValidator implements OcppSchemaValidator {
    private final JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
    private final Map<String, JsonSchema> schemas = new ConcurrentHashMap<>();

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
        OcppActionDescriptor descriptor = OcppActionMetadata.descriptor(version, action);
        if (descriptor == null) {
            throw new OcppException(OcppErrorCode.NotImplemented, "当前协议版本不支持 Action：" + version + "/" + action);
        }
        JsonSchema schema = schema(descriptor.schemaPath(request));
        Set<ValidationMessage> messages = schema.validate(payload);
        if (!messages.isEmpty()) {
            throw new OcppException(OcppErrorCode.PropertyConstraintViolation,
                    "OCPP Payload 不符合官方 JSON Schema：" + messages.iterator().next().getMessage(), messages.toString());
        }
    }

    private JsonSchema schema(String path) {
        JsonSchema schema = schemas.get(path);
        if (schema != null) {
            return schema;
        }
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (input == null) {
            throw new OcppException(OcppErrorCode.InternalError, "缺少官方 JSON Schema 资源：" + path);
        }
        JsonSchema loaded = schemaFactory.getSchema(input);
        schemas.put(path, loaded);
        return loaded;
    }
}
