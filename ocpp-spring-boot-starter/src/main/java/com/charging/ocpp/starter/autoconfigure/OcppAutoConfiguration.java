package com.charging.ocpp.starter.autoconfigure;

import com.charging.ocpp.core.handler.OcppActionHandler;
import com.charging.ocpp.core.handler.OcppHandlerRegistry;
import com.charging.ocpp.core.protocol.DefaultOcppCodec;
import com.charging.ocpp.core.protocol.OcppCodec;
import com.charging.ocpp.core.schema.NoopOcppSchemaValidator;
import com.charging.ocpp.core.schema.OcppSchemaValidator;
import com.charging.ocpp.core.session.InMemoryOcppSessionRepository;
import com.charging.ocpp.core.session.OcppSessionRepository;
import com.charging.ocpp.starter.handler.DefaultOcpp16Handlers;
import com.charging.ocpp.starter.handler.DefaultOcpp201Handlers;
import com.charging.ocpp.starter.registry.OcppAnnotatedHandlerRegistrar;
import com.charging.ocpp.starter.service.OcppTemplate;
import com.charging.ocpp.starter.service.OcppHighLoadGuard;
import com.charging.ocpp.starter.websocket.OcppWebSocketConfigurer;
import com.charging.ocpp.starter.websocket.OcppWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * OCPP Spring Boot 自动配置。
 * <p>
 * 作者：JYq
 * 说明：业务系统引入 starter 后，自动获得 WebSocket 接入、编解码、会话管理和命令下发能力。
 * </p>
 */
@Configuration
@EnableWebSocket
@EnableConfigurationProperties(OcppProperties.class)
public class OcppAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public OcppCodec ocppCodec(ObjectMapper objectMapper) { return new DefaultOcppCodec(objectMapper); }

    @Bean
    @ConditionalOnMissingBean
    public OcppSchemaValidator ocppSchemaValidator() { return new NoopOcppSchemaValidator(); }

    @Bean
    @ConditionalOnMissingBean
    public OcppSessionRepository ocppSessionRepository() { return new InMemoryOcppSessionRepository(); }

    @Bean
    @ConditionalOnMissingBean
    public DefaultOcpp16Handlers defaultOcpp16Handlers(ObjectMapper objectMapper) { return new DefaultOcpp16Handlers(objectMapper); }

    @Bean
    @ConditionalOnMissingBean
    public DefaultOcpp201Handlers defaultOcpp201Handlers(ObjectMapper objectMapper) { return new DefaultOcpp201Handlers(objectMapper); }

    @Bean public OcppActionHandler ocpp16Boot(DefaultOcpp16Handlers h) { return h.boot(); }
    @Bean public OcppActionHandler ocpp16Heartbeat(DefaultOcpp16Handlers h) { return h.heartbeat(); }
    @Bean public OcppActionHandler ocpp16Authorize(DefaultOcpp16Handlers h) { return h.authorize(); }
    @Bean public OcppActionHandler ocpp16Status(DefaultOcpp16Handlers h) { return h.status(); }
    @Bean public OcppActionHandler ocpp16MeterValues(DefaultOcpp16Handlers h) { return h.meterValues(); }
    @Bean public OcppActionHandler ocpp16StartTransaction(DefaultOcpp16Handlers h) { return h.startTransaction(); }
    @Bean public OcppActionHandler ocpp16StopTransaction(DefaultOcpp16Handlers h) { return h.stopTransaction(); }

    @Bean public OcppActionHandler ocpp201Boot(DefaultOcpp201Handlers h) { return h.boot(); }
    @Bean public OcppActionHandler ocpp201Heartbeat(DefaultOcpp201Handlers h) { return h.heartbeat(); }
    @Bean public OcppActionHandler ocpp201Authorize(DefaultOcpp201Handlers h) { return h.authorize(); }
    @Bean public OcppActionHandler ocpp201Status(DefaultOcpp201Handlers h) { return h.status(); }
    @Bean public OcppActionHandler ocpp201MeterValues(DefaultOcpp201Handlers h) { return h.meterValues(); }
    @Bean public OcppActionHandler ocpp201TransactionEvent(DefaultOcpp201Handlers h) { return h.transactionEvent(); }

    @Bean
    @ConditionalOnMissingBean
    public OcppHandlerRegistry ocppHandlerRegistry(List<OcppActionHandler> handlers) {
        OcppHandlerRegistry registry = new OcppHandlerRegistry();
        registry.registerAll(handlers);
        return registry;
    }

    @Bean
    @ConditionalOnMissingBean
    public OcppTemplate ocppTemplate(OcppSessionRepository sessionRepository, OcppCodec ocppCodec,
                                     ObjectMapper objectMapper, OcppProperties properties) {
        return new OcppTemplate(sessionRepository, ocppCodec, objectMapper, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public OcppHighLoadGuard ocppHighLoadGuard(OcppProperties properties, org.springframework.beans.factory.ObjectProvider<StringRedisTemplate> redisTemplateProvider) {
        return new OcppHighLoadGuard(properties, redisTemplateProvider.getIfAvailable());
    }

    @Bean
    @ConditionalOnMissingBean
    public OcppWebSocketHandler ocppWebSocketHandler(OcppCodec ocppCodec, OcppHandlerRegistry handlerRegistry,
                                                     OcppSessionRepository sessionRepository, OcppSchemaValidator schemaValidator,
                                                     OcppTemplate ocppTemplate, OcppProperties properties, OcppHighLoadGuard highLoadGuard) {
        return new OcppWebSocketHandler(ocppCodec, handlerRegistry, sessionRepository, schemaValidator, ocppTemplate, properties, highLoadGuard);
    }

    @Bean
    @ConditionalOnMissingBean
    public OcppWebSocketConfigurer ocppWebSocketConfigurer(OcppWebSocketHandler handler, OcppProperties properties) {
        return new OcppWebSocketConfigurer(handler, properties);
    }

    @Bean
    public SmartInitializingSingleton ocppAnnotatedHandlerRegistrar(ApplicationContext applicationContext, OcppHandlerRegistry registry,
                                                                    ObjectMapper objectMapper) {
        return new OcppAnnotatedHandlerRegistrar(applicationContext, registry, objectMapper);
    }
}
