package com.charging.ocpp.starter.registry;

import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.exception.OcppErrorCode;
import com.charging.ocpp.core.exception.OcppException;
import com.charging.ocpp.core.handler.OcppActionHandler;
import com.charging.ocpp.core.handler.OcppRequestContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Method;

/**
 * 注解方法调用型处理器。
 * 作者：JYq
 */
public class MethodInvokingOcppActionHandler implements OcppActionHandler {
    /*
     * 1. 该类把一个带 @OcppActionMapping 的普通 Spring Bean 方法适配成 OcppActionHandler。
     * 2. handle 时会根据方法参数类型自动组装参数：OcppRequestContext 给上下文，JsonNode 给原始 payload，其它类型会用 ObjectMapper 从 payload 转 DTO。
     * 3. 这就是业务方法可以直接声明强类型入参的原因，例如 handleBoot(OcppRequestContext ctx, BootNotificationRequest req)。
     */
    private final Object bean;
    private final Method method;
    private final OcppVersion version;
    private final String action;
    private final ObjectMapper objectMapper;

    public MethodInvokingOcppActionHandler(Object bean, Method method, OcppVersion version, String action, ObjectMapper objectMapper) {
        this.bean = bean;
        this.method = method;
        this.version = version;
        this.action = action;
        this.objectMapper = objectMapper;
        this.method.setAccessible(true);
    }

    @Override
    public OcppVersion version() {
        return version;
    }

    @Override
    public String action() {
        return action;
    }

    @Override
    public Object handle(OcppRequestContext context, JsonNode payload) {
        Class<?>[] types = method.getParameterTypes();
        Object[] args = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            if (OcppRequestContext.class.isAssignableFrom(types[i])) {
                args[i] = context;
            } else if (JsonNode.class.isAssignableFrom(types[i])) {
                args[i] = payload;
            } else {
                args[i] = objectMapper.convertValue(payload, types[i]);
            }
        }
        try {
            return method.invoke(bean, args);
        } catch (Exception e) {
            Throwable t = e.getCause() == null ? e : e.getCause();
            if (t instanceof OcppException) {
                throw (OcppException) t;
            }
            throw new OcppException(OcppErrorCode.InternalError, "执行业务方法失败：" + method.getName(), null, t);
        }
    }
}
