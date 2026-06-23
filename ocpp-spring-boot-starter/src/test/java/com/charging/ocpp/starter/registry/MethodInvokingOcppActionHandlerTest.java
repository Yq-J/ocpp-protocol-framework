package com.charging.ocpp.starter.registry;

import com.charging.ocpp.core.annotation.OcppActionMapping;
import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.handler.OcppRequestContext;
import com.charging.ocpp.core.model.v16.HeartbeatRequest;
import com.charging.ocpp.core.model.v16.HeartbeatResponse;
import com.charging.ocpp.core.protocol.OcppObjectMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MethodInvokingOcppActionHandlerTest {
    private final ObjectMapper objectMapper = OcppObjectMapperFactory.create();

    @Test
    void invokesJdkProxyUsingInvocableInterfaceMethod() throws Exception {
        AtomicBoolean adviceInvoked = new AtomicBoolean(false);
        ProxyFactory proxyFactory = new ProxyFactory(new JdkProxiedHeartbeatHandler());
        proxyFactory.setInterfaces(HeartbeatHandlerApi.class);
        proxyFactory.addAdvice((MethodInterceptor) invocation -> {
            adviceInvoked.set(true);
            return invocation.proceed();
        });
        Object proxy = proxyFactory.getProxy();
        Method targetMethod = JdkProxiedHeartbeatHandler.class.getMethod("heartbeat", HeartbeatRequest.class);

        MethodInvokingOcppActionHandler handler = new MethodInvokingOcppActionHandler(proxy, targetMethod,
                OcppVersion.OCPP_16, "Heartbeat", objectMapper);

        HeartbeatResponse response = (HeartbeatResponse) handler.handle(context(), objectMapper.createObjectNode());

        assertTrue(adviceInvoked.get());
        assertEquals("2026-06-22T00:00:00Z", response.getCurrentTime());
    }

    @Test
    void invokesClassBasedProxyUsingSpecificProxyMethod() throws Exception {
        AtomicBoolean adviceInvoked = new AtomicBoolean(false);
        ProxyFactory proxyFactory = new ProxyFactory(new ClassProxiedHeartbeatHandler());
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvice((MethodInterceptor) invocation -> {
            adviceInvoked.set(true);
            return invocation.proceed();
        });
        Object proxy = proxyFactory.getProxy();
        Method targetMethod = ClassProxiedHeartbeatHandler.class.getMethod("heartbeat", HeartbeatRequest.class);

        MethodInvokingOcppActionHandler handler = new MethodInvokingOcppActionHandler(proxy, targetMethod,
                OcppVersion.OCPP_16, "Heartbeat", objectMapper);

        HeartbeatResponse response = (HeartbeatResponse) handler.handle(context(), objectMapper.createObjectNode());

        assertTrue(adviceInvoked.get());
        assertEquals("2026-06-22T01:00:00Z", response.getCurrentTime());
    }

    private OcppRequestContext context() {
        return new OcppRequestContext("CP001", "session-1", OcppVersion.OCPP_16, "Heartbeat", "uid-1");
    }

    interface HeartbeatHandlerApi {
        HeartbeatResponse heartbeat(HeartbeatRequest request);
    }

    static class JdkProxiedHeartbeatHandler implements HeartbeatHandlerApi {
        @Override
        @OcppActionMapping(version = OcppVersion.OCPP_16, action = "Heartbeat")
        public HeartbeatResponse heartbeat(HeartbeatRequest request) {
            HeartbeatResponse response = new HeartbeatResponse();
            response.setCurrentTime("2026-06-22T00:00:00Z");
            return response;
        }
    }

    static class ClassProxiedHeartbeatHandler {
        @OcppActionMapping(version = OcppVersion.OCPP_16, action = "Heartbeat")
        public HeartbeatResponse heartbeat(HeartbeatRequest request) {
            HeartbeatResponse response = new HeartbeatResponse();
            response.setCurrentTime("2026-06-22T01:00:00Z");
            return response;
        }
    }
}
