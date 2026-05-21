package com.charging.ocpp.core.handler;

import com.charging.ocpp.core.enums.OcppVersion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * OCPP 处理器注册表。
 * <p>
 * 作者：JYq
 * 说明：根据协议版本和 Action 查找业务处理器，是业务解耦的核心组件。
 * </p>
 */
public class OcppHandlerRegistry {
    private final Map<HandlerKey, OcppActionHandler> handlers = new ConcurrentHashMap<>();

    public void register(OcppActionHandler handler) {
        if (handler == null || handler.version() == null || handler.action() == null) {
            return;
        }
        handlers.put(new HandlerKey(handler.version(), handler.action()), handler);
    }

    public void registerAll(List<OcppActionHandler> actionHandlers) {
        if (actionHandlers == null) {
            return;
        }
        for (OcppActionHandler handler : actionHandlers) {
            register(handler);
        }
    }

    public OcppActionHandler get(OcppVersion version, String action) {
        return handlers.get(new HandlerKey(version, action));
    }

    public List<OcppActionHandler> listHandlers() {
        return Collections.unmodifiableList(new ArrayList<>(handlers.values()));
    }
}
