package com.charging.ocpp.core.protocol;

import com.charging.ocpp.core.enums.OcppMessageType;
import com.charging.ocpp.core.exception.OcppErrorCode;
import com.charging.ocpp.core.exception.OcppException;
import com.charging.ocpp.core.model.EmptyResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * 默认 OCPP-J 编解码器。
 * <p>
 * 作者：JYq
 * 说明：只处理 OCPP-J 通用帧结构，不包含业务语义。
 * </p>
 */
public class DefaultOcppCodec implements OcppCodec {
    private final ObjectMapper objectMapper;

    public DefaultOcppCodec() {
        this(OcppObjectMapperFactory.create());
    }

    public DefaultOcppCodec(ObjectMapper objectMapper) {
        this.objectMapper = OcppObjectMapperFactory.copyOf(objectMapper);
    }

    @Override
    public OcppFrame decode(String text) {
        try {
            JsonNode root = objectMapper.readTree(text);
            if (root == null || !root.isArray() || root.size() < 3) {
                throw new OcppException(OcppErrorCode.ProtocolError, "OCPP 消息必须是长度合法的 JSON 数组");
            }
            OcppMessageType type = OcppMessageType.fromCode(readMessageType(root.get(0)));
            if (type == null) {
                throw new OcppException(OcppErrorCode.ProtocolError, "不支持的 OCPP 消息类型：" + root.get(0).asInt());
            }
            String uniqueId = readRequiredText(root.get(1), "uniqueId", false);
            if (type == OcppMessageType.CALL) {
                if (root.size() != 4) { throw new OcppException(OcppErrorCode.ProtocolError, "CALL 消息长度必须为 4"); }
                return new OcppCall(uniqueId, readRequiredText(root.get(2), "action", false), readPayload(root.get(3)));
            }
            if (type == OcppMessageType.CALL_RESULT) {
                if (root.size() != 3) { throw new OcppException(OcppErrorCode.ProtocolError, "CALLRESULT 消息长度必须为 3"); }
                return new OcppCallResult(uniqueId, readPayload(root.get(2)));
            }
            if (root.size() != 5) { throw new OcppException(OcppErrorCode.ProtocolError, "CALLERROR 消息长度必须为 5"); }
            return new OcppCallError(uniqueId, readRequiredText(root.get(2), "errorCode", false),
                    readRequiredText(root.get(3), "errorDescription", true), readPayload(root.get(4)));
        } catch (OcppException e) {
            throw e;
        } catch (Exception e) {
            throw new OcppException(OcppErrorCode.FormationViolation, "OCPP 消息 JSON 解析失败", null, e);
        }
    }

    @Override
    public String encodeCall(String uniqueId, String action, Object payload) {
        ArrayNode array = objectMapper.createArrayNode();
        array.add(OcppMessageType.CALL.getCode());
        array.add(uniqueId);
        array.add(action);
        array.add(toPayloadNode(payload));
        return write(array);
    }

    @Override
    public String encodeCallResult(String uniqueId, Object payload) {
        ArrayNode array = objectMapper.createArrayNode();
        array.add(OcppMessageType.CALL_RESULT.getCode());
        array.add(uniqueId);
        array.add(toPayloadNode(payload));
        return write(array);
    }

    @Override
    public String encodeCallError(String uniqueId, String errorCode, String errorDescription, Object details) {
        ArrayNode array = objectMapper.createArrayNode();
        array.add(OcppMessageType.CALL_ERROR.getCode());
        array.add(uniqueId == null ? "" : uniqueId);
        array.add(errorCode == null ? OcppErrorCode.InternalError.name() : errorCode);
        array.add(errorDescription == null ? "" : errorDescription);
        array.add(objectMapper.valueToTree(details == null ? objectMapper.createObjectNode() : details));
        return write(array);
    }

    private JsonNode toPayloadNode(Object payload) {
        if (payload == null || payload instanceof EmptyResponse) {
            return objectMapper.createObjectNode();
        }
        return objectMapper.valueToTree(payload);
    }

    private String write(ArrayNode array) {
        try {
            return objectMapper.writeValueAsString(array);
        } catch (Exception e) {
            throw new OcppException(OcppErrorCode.InternalError, "编码 OCPP 消息失败", null, e);
        }
    }

    private int readMessageType(JsonNode node) {
        if (node == null || !node.isIntegralNumber()) {
            throw new OcppException(OcppErrorCode.ProtocolError, "OCPP 消息类型必须是整数");
        }
        return node.asInt();
    }

    private String readRequiredText(JsonNode node, String fieldName, boolean allowEmpty) {
        if (node == null || !node.isTextual()) {
            throw new OcppException(OcppErrorCode.ProtocolError, "OCPP " + fieldName + " 必须是字符串");
        }
        String value = node.asText();
        if (!allowEmpty && value.trim().isEmpty()) {
            throw new OcppException(OcppErrorCode.ProtocolError, "OCPP " + fieldName + " 不能为空");
        }
        return value;
    }

    private JsonNode readPayload(JsonNode node) {
        if (node == null || !node.isObject()) {
            throw new OcppException(OcppErrorCode.FormationViolation, "OCPP Payload 必须是 JSON 对象");
        }
        return node;
    }
}
