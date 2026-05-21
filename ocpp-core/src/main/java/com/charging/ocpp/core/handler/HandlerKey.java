package com.charging.ocpp.core.handler;

import com.charging.ocpp.core.enums.OcppVersion;

import java.util.Objects;

/**
 * 处理器路由键。
 * 作者：JYq
 */
public class HandlerKey {
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
