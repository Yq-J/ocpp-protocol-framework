package com.charging.ocpp.starter.registry;

import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.exception.OcppErrorCode;
import com.charging.ocpp.core.exception.OcppException;
import com.charging.ocpp.core.handler.OcppActionHandler;
import com.charging.ocpp.core.handler.OcppRequestContext;
import com.charging.ocpp.core.protocol.OcppObjectMapperFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * 注解方法调用型处理器。
 * 作者：JYq
 */
public class MethodInvokingOcppActionHandler implements OcppActionHandler {
    private final Object bean;
    private final Method method;
    private final OcppVersion version;
    private final String action;
    private final ObjectMapper objectMapper;

    public MethodInvokingOcppActionHandler(Object bean, Method method, OcppVersion version, String action, ObjectMapper objectMapper) {
        this.bean = bean;
        this.method = resolveInvocableMethod(bean, method);
        this.version = version;
        this.action = action;
        this.objectMapper = OcppObjectMapperFactory.copyOf(objectMapper);
        ReflectionUtils.makeAccessible(this.method);
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

    private Method resolveInvocableMethod(Object bean, Method method) {
        try {
            return AopUtils.selectInvocableMethod(method, bean.getClass());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("OCPP 处理方法无法通过当前 Spring AOP 代理调用："
                    + method.toGenericString()
                    + "。如果该 Bean 使用 JDK 动态代理，请确保处理方法声明在业务接口中，"
                    + "或启用 CGLIB/class-based proxy。", e);
        }
    }
}
