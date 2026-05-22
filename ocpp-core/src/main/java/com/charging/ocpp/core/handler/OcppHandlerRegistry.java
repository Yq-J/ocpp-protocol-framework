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
    /*
     * 1. 这是 OCPP Action 的路由表，内部用 ConcurrentHashMap 支持并发读写。
     * 2. Spring 启动时会把默认处理器和注解扫描到的处理器都注册进来，运行时按 version + action 查找。
     * 3. 后注册的同 key 处理器会覆盖先注册的处理器，这使业务自定义实现可以替换 starter 的默认实现。
     */
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
