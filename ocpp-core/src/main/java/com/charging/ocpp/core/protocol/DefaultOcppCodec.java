package com.charging.ocpp.core.protocol;

import com.charging.ocpp.core.enums.OcppMessageType;
import com.charging.ocpp.core.exception.OcppErrorCode;
import com.charging.ocpp.core.exception.OcppException;
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
    /*
     * 1. OCPP-J 不使用普通 JSON 对象作为外层帧，而使用数组：CALL=[2, uniqueId, action, payload]。
     * 2. 本类只负责“数组帧”和 Java 帧对象之间的转换，不判断业务字段是否正确。
     * 3. decode 先识别消息类型，再按类型检查数组长度；长度不对会抛 OcppException，最终发给对端 CALLERROR。
     * 4. encode 方法会把 null payload 统一转成空 JSON 对象 {}，避免产生协议上不好处理的 null payload。
     * 5. ObjectMapper 是 Jackson 的 JSON 工具，readTree 解析原始文本，valueToTree 把 Java 对象变成 JsonNode。
     */
    private final ObjectMapper objectMapper;

    public DefaultOcppCodec(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public OcppFrame decode(String text) {
        try {
            JsonNode root = objectMapper.readTree(text);
            if (root == null || !root.isArray() || root.size() < 3) {
                throw new OcppException(OcppErrorCode.ProtocolError, "OCPP 消息必须是长度合法的 JSON 数组");
            }
            OcppMessageType type = OcppMessageType.fromCode(root.get(0).asInt());
            if (type == null) {
                throw new OcppException(OcppErrorCode.ProtocolError, "不支持的 OCPP 消息类型：" + root.get(0).asInt());
            }
            String uniqueId = root.get(1).asText();
            if (type == OcppMessageType.CALL) {
                if (root.size() != 4) { throw new OcppException(OcppErrorCode.ProtocolError, "CALL 消息长度必须为 4"); }
                return new OcppCall(uniqueId, root.get(2).asText(), root.get(3));
            }
            if (type == OcppMessageType.CALL_RESULT) {
                if (root.size() != 3) { throw new OcppException(OcppErrorCode.ProtocolError, "CALLRESULT 消息长度必须为 3"); }
                return new OcppCallResult(uniqueId, root.get(2));
            }
            if (root.size() != 5) { throw new OcppException(OcppErrorCode.ProtocolError, "CALLERROR 消息长度必须为 5"); }
            return new OcppCallError(uniqueId, root.get(2).asText(), root.get(3).asText(), root.get(4));
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
        array.add(objectMapper.valueToTree(payload == null ? objectMapper.createObjectNode() : payload));
        return write(array);
    }

    @Override
    public String encodeCallResult(String uniqueId, Object payload) {
        ArrayNode array = objectMapper.createArrayNode();
        array.add(OcppMessageType.CALL_RESULT.getCode());
        array.add(uniqueId);
        array.add(objectMapper.valueToTree(payload == null ? objectMapper.createObjectNode() : payload));
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

    private String write(ArrayNode array) {
        try {
            return objectMapper.writeValueAsString(array);
        } catch (Exception e) {
            throw new OcppException(OcppErrorCode.InternalError, "编码 OCPP 消息失败", null, e);
        }
    }
}
