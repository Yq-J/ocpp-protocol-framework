package com.charging.ocpp.core.handler;

import com.charging.ocpp.core.enums.OcppVersion;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * OCPP 请求上下文。
 * <p>
 * 作者：JYq
 * 说明：业务层可通过该对象获取充电桩编号、协议版本、Action、UniqueId 和会话 ID。
 * </p>
 */
@Getter
public class OcppRequestContext {
    private final String chargePointId;
    private final String sessionId;
    private final OcppVersion version;
    private final String action;
    private final String uniqueId;
    private final Map<String, Object> attributes = new HashMap<>();

    public OcppRequestContext(String chargePointId, String sessionId, OcppVersion version, String action, String uniqueId) {
        this.chargePointId = chargePointId;
        this.sessionId = sessionId;
        this.version = version;
        this.action = action;
        this.uniqueId = uniqueId;
    }

    public void putAttribute(String key, Object value) {
        attributes.put(key, value);
    }
}
