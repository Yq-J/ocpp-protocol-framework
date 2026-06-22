package com.charging.ocpp.starter.autoconfigure;

import com.charging.ocpp.core.handler.OcppActionHandler;
import com.charging.ocpp.core.handler.OcppHandlerRegistry;
import com.charging.ocpp.core.protocol.DefaultOcppCodec;
import com.charging.ocpp.core.protocol.OcppCodec;
import com.charging.ocpp.core.protocol.OcppObjectMapperFactory;
import com.charging.ocpp.core.schema.OcppSchemaValidator;
import com.charging.ocpp.core.session.InMemoryOcppSessionRepository;
import com.charging.ocpp.core.session.OcppSessionRepository;
import com.charging.ocpp.starter.handler.DefaultOcpp16Handlers;
import com.charging.ocpp.starter.handler.DefaultOcpp201Handlers;
import com.charging.ocpp.starter.registry.OcppAnnotatedHandlerRegistrar;
import com.charging.ocpp.starter.schema.OfficialOcppSchemaValidator;
import com.charging.ocpp.starter.service.OcppTemplate;
import com.charging.ocpp.starter.websocket.OcppWebSocketConfigurer;
import com.charging.ocpp.starter.websocket.OcppWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.util.List;

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
    public static final String OCPP_PROTOCOL_OBJECT_MAPPER_BEAN_NAME = "ocppProtocolObjectMapper";

    @Bean(name = OCPP_PROTOCOL_OBJECT_MAPPER_BEAN_NAME)
    @ConditionalOnMissingBean(name = OCPP_PROTOCOL_OBJECT_MAPPER_BEAN_NAME)
    public ObjectMapper ocppProtocolObjectMapper() { return OcppObjectMapperFactory.create(); }

    @Bean
    @ConditionalOnMissingBean
    public OcppCodec ocppCodec(@Qualifier(OCPP_PROTOCOL_OBJECT_MAPPER_BEAN_NAME) ObjectMapper objectMapper) {
        return new DefaultOcppCodec(objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public OcppSchemaValidator ocppSchemaValidator(OcppProperties properties) { return new OfficialOcppSchemaValidator(properties); }

    @Bean
    @ConditionalOnMissingBean
    public OcppSessionRepository ocppSessionRepository() { return new InMemoryOcppSessionRepository(); }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "ocpp", name = "enable-default-handlers", havingValue = "true")
    public DefaultOcpp16Handlers defaultOcpp16Handlers(@Qualifier(OCPP_PROTOCOL_OBJECT_MAPPER_BEAN_NAME) ObjectMapper objectMapper) { return new DefaultOcpp16Handlers(objectMapper); }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "ocpp", name = "enable-default-handlers", havingValue = "true")
    public DefaultOcpp201Handlers defaultOcpp201Handlers(@Qualifier(OCPP_PROTOCOL_OBJECT_MAPPER_BEAN_NAME) ObjectMapper objectMapper) { return new DefaultOcpp201Handlers(objectMapper); }

    @Bean
    @ConditionalOnProperty(prefix = "ocpp", name = "enable-default-handlers", havingValue = "true")
    public OcppActionHandler ocpp16Boot(DefaultOcpp16Handlers h) { return h.boot(); }
    @Bean
    @ConditionalOnProperty(prefix = "ocpp", name = "enable-default-handlers", havingValue = "true")
    public OcppActionHandler ocpp16Heartbeat(DefaultOcpp16Handlers h) { return h.heartbeat(); }
    @Bean
    @ConditionalOnProperty(prefix = "ocpp", name = "enable-default-handlers", havingValue = "true")
    public OcppActionHandler ocpp16Authorize(DefaultOcpp16Handlers h) { return h.authorize(); }
    @Bean
    @ConditionalOnProperty(prefix = "ocpp", name = "enable-default-handlers", havingValue = "true")
    public OcppActionHandler ocpp16Status(DefaultOcpp16Handlers h) { return h.status(); }
    @Bean
    @ConditionalOnProperty(prefix = "ocpp", name = "enable-default-handlers", havingValue = "true")
    public OcppActionHandler ocpp16MeterValues(DefaultOcpp16Handlers h) { return h.meterValues(); }
    @Bean
    @ConditionalOnProperty(prefix = "ocpp", name = "enable-default-handlers", havingValue = "true")
    public OcppActionHandler ocpp16StartTransaction(DefaultOcpp16Handlers h) { return h.startTransaction(); }
    @Bean
    @ConditionalOnProperty(prefix = "ocpp", name = "enable-default-handlers", havingValue = "true")
    public OcppActionHandler ocpp16StopTransaction(DefaultOcpp16Handlers h) { return h.stopTransaction(); }

    @Bean
    @ConditionalOnProperty(prefix = "ocpp", name = "enable-default-handlers", havingValue = "true")
    public OcppActionHandler ocpp201Boot(DefaultOcpp201Handlers h) { return h.boot(); }
    @Bean
    @ConditionalOnProperty(prefix = "ocpp", name = "enable-default-handlers", havingValue = "true")
    public OcppActionHandler ocpp201Heartbeat(DefaultOcpp201Handlers h) { return h.heartbeat(); }
    @Bean
    @ConditionalOnProperty(prefix = "ocpp", name = "enable-default-handlers", havingValue = "true")
    public OcppActionHandler ocpp201Authorize(DefaultOcpp201Handlers h) { return h.authorize(); }
    @Bean
    @ConditionalOnProperty(prefix = "ocpp", name = "enable-default-handlers", havingValue = "true")
    public OcppActionHandler ocpp201Status(DefaultOcpp201Handlers h) { return h.status(); }
    @Bean
    @ConditionalOnProperty(prefix = "ocpp", name = "enable-default-handlers", havingValue = "true")
    public OcppActionHandler ocpp201MeterValues(DefaultOcpp201Handlers h) { return h.meterValues(); }
    @Bean
    @ConditionalOnProperty(prefix = "ocpp", name = "enable-default-handlers", havingValue = "true")
    public OcppActionHandler ocpp201TransactionEvent(DefaultOcpp201Handlers h) { return h.transactionEvent(); }

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
                                     @Qualifier(OCPP_PROTOCOL_OBJECT_MAPPER_BEAN_NAME) ObjectMapper objectMapper, OcppProperties properties,
                                     OcppSchemaValidator schemaValidator) {
        return new OcppTemplate(sessionRepository, ocppCodec, objectMapper, properties, schemaValidator);
    }

    @Bean
    @ConditionalOnMissingBean
    public OcppWebSocketHandler ocppWebSocketHandler(OcppCodec ocppCodec, OcppHandlerRegistry handlerRegistry,
                                                       OcppSessionRepository sessionRepository, OcppSchemaValidator schemaValidator,
                                                       OcppTemplate ocppTemplate, OcppProperties properties,
                                                       @Qualifier(OCPP_PROTOCOL_OBJECT_MAPPER_BEAN_NAME) ObjectMapper objectMapper) {
        return new OcppWebSocketHandler(ocppCodec, handlerRegistry, sessionRepository, schemaValidator, ocppTemplate,
                properties, objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public OcppWebSocketConfigurer ocppWebSocketConfigurer(OcppWebSocketHandler handler, OcppProperties properties) {
        return new OcppWebSocketConfigurer(handler, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public OcppProductionReadinessChecker ocppProductionReadinessChecker(OcppProperties properties) {
        return new OcppProductionReadinessChecker(properties);
    }

    @Bean
    public SmartInitializingSingleton ocppAnnotatedHandlerRegistrar(ApplicationContext applicationContext, OcppHandlerRegistry registry,
                                                                    @Qualifier(OCPP_PROTOCOL_OBJECT_MAPPER_BEAN_NAME) ObjectMapper objectMapper) {
        return new OcppAnnotatedHandlerRegistrar(applicationContext, registry, objectMapper);
    }
}
