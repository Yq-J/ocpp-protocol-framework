package com.charging.ocpp.starter.registry;

import com.charging.ocpp.core.annotation.OcppActionMapping;
import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.handler.OcppHandlerRegistry;
import com.charging.ocpp.core.model.EmptyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OcppAnnotatedHandlerRegistrarTest {

    private static final AtomicBoolean LAZY_BEAN_INSTANTIATED = new AtomicBoolean(false);

    @Test
    void registersAnnotatedHandlerWithoutForcingInstantiationOfUnrelatedLazyBeans() {
        LAZY_BEAN_INSTANTIATED.set(false);
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class)) {
            OcppHandlerRegistry registry = new OcppHandlerRegistry();
            new OcppAnnotatedHandlerRegistrar(context, registry, new ObjectMapper()).afterSingletonsInstantiated();

            assertNotNull(registry.get(OcppVersion.OCPP_16, "Heartbeat"));
            assertFalse(LAZY_BEAN_INSTANTIATED.get(), "无关的 @Lazy Bean 不应被注解扫描强制实例化");
        }
    }

    @Configuration
    static class Config {
        @Bean
        HandlerBean handlerBean() {
            return new HandlerBean();
        }

        @Bean
        @Lazy
        LazyNonHandlerBean lazyNonHandlerBean() {
            return new LazyNonHandlerBean();
        }
    }

    static class HandlerBean {
        @OcppActionMapping(version = OcppVersion.OCPP_16, action = "Heartbeat")
        public EmptyResponse heartbeat() {
            return new EmptyResponse();
        }
    }

    static class LazyNonHandlerBean {
        LazyNonHandlerBean() {
            LAZY_BEAN_INSTANTIATED.set(true);
        }
    }
}
