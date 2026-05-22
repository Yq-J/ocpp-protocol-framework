package com.charging.ocpp.core.handler;

import com.charging.ocpp.core.enums.OcppVersion;

import java.util.Objects;

/**
 * 处理器路由键。
 * 作者：JYq
 */
public class HandlerKey {
    /*
     * 1. 注册表需要用“协议版本 + Action 名称”定位处理器，本类就是这个组合键。
     * 2. equals 和 hashCode 必须同时实现，否则放进 ConcurrentHashMap 后可能查不到已注册的处理器。
     * 3. action 使用字符串是因为 OCPP action 来自协议文本，同时也允许厂商扩展 action。
     */
    private final OcppVersion version;
    private final String action;

    public HandlerKey(OcppVersion version, String action) {
        this.version = version;
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HandlerKey)) {
            return false;
        }
        HandlerKey other = (HandlerKey) o;
        if (version != other.version) {
            return false;
        }
        return Objects.equals(action, other.action);
    }

    @Override
    public int hashCode() {
        int result = version != null ? version.hashCode() : 0;
        result = 31 * result + (action != null ? action.hashCode() : 0);
        return result;
    }
}
