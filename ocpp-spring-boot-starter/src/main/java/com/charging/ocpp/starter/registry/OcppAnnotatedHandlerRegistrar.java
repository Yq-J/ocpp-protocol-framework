package com.charging.ocpp.starter.registry;

import com.charging.ocpp.core.annotation.OcppActionMapping;
import com.charging.ocpp.core.handler.OcppHandlerRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * {@code @OcppActionMapping} 注解扫描注册器。
 * 作者：JYq
 */
public class OcppAnnotatedHandlerRegistrar implements SmartInitializingSingleton {
    private final ApplicationContext applicationContext;
    private final OcppHandlerRegistry registry;
    private final ObjectMapper objectMapper;

    public OcppAnnotatedHandlerRegistrar(ApplicationContext applicationContext, OcppHandlerRegistry registry, ObjectMapper objectMapper) {
        this.applicationContext = applicationContext;
        this.registry = registry;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterSingletonsInstantiated() {
        for (String name : applicationContext.getBeanDefinitionNames()) {
            // 先解析类型（不触发 FactoryBean 初始化），仅当类型可能携带 @OcppActionMapping 时才 getBean，
            // 避免强制提前实例化 @Lazy、作用域或带副作用的业务 Bean。
            Class<?> type;
            try {
                type = applicationContext.getType(name, false);
            } catch (Exception ignored) {
                continue;
            }
            // 对具体类（含 CGLIB 代理）可据类型直接判定；JDK 动态代理/接口类型的注解在目标类上，
            // 无法仅凭暴露类型判断，需放行到下方按目标类扫描，避免漏注册代理后的业务 Handler。
            if (type != null && !mayCarryMapping(type) && !hasActionMapping(ClassUtils.getUserClass(type))) {
                continue;
            }
            final Object bean;
            try {
                bean = applicationContext.getBean(name);
            } catch (Exception ignored) {
                continue;
            }
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            ReflectionUtils.doWithMethods(targetClass, method -> {
                OcppActionMapping mapping = AnnotationUtils.findAnnotation(method, OcppActionMapping.class);
                if (mapping != null) {
                    registry.register(new MethodInvokingOcppActionHandler(bean, method, mapping.version(), mapping.action(), objectMapper));
                }
            });
        }
    }

    private boolean mayCarryMapping(Class<?> type) {
        // JDK 动态代理与接口暴露类型不携带实现方法上的注解，无法在不实例化的情况下排除。
        return type.isInterface() || Proxy.isProxyClass(type);
    }

    private boolean hasActionMapping(Class<?> targetClass) {
        AtomicBoolean found = new AtomicBoolean(false);
        ReflectionUtils.doWithMethods(targetClass, method -> found.set(true),
                method -> !found.get() && AnnotationUtils.findAnnotation(method, OcppActionMapping.class) != null);
        return found.get();
    }
}
