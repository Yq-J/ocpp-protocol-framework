package com.charging.ocpp.starter.registry;

import com.charging.ocpp.core.annotation.OcppActionMapping;
import com.charging.ocpp.core.handler.OcppHandlerRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

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
}
