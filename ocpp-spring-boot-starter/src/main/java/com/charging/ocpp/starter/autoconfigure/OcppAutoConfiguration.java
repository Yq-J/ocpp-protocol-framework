package com.charging.ocpp.starter.autoconfigure;

import com.charging.ocpp.core.handler.OcppActionHandler;
import com.charging.ocpp.core.handler.OcppHandlerRegistry;
import com.charging.ocpp.core.protocol.DefaultOcppCodec;
import com.charging.ocpp.core.protocol.OcppCodec;
import com.charging.ocpp.core.schema.NoopOcppSchemaValidator;
import com.charging.ocpp.core.schema.OcppSchemaValidator;
import com.charging.ocpp.core.session.InMemoryOcppSessionRepository;
import com.charging.ocpp.core.session.OcppSessionRepository;
import com.charging.ocpp.starter.session.RedisBackedOcppSessionRepository;
import com.charging.ocpp.starter.handler.DefaultOcpp16Handlers;
import com.charging.ocpp.starter.handler.DefaultOcpp201Handlers;
import com.charging.ocpp.starter.registry.OcppAnnotatedHandlerRegistrar;
import com.charging.ocpp.starter.service.OcppTemplate;
import com.charging.ocpp.starter.service.OcppHighLoadGuard;
import com.charging.ocpp.starter.service.RedisOcppClusterForwarder;
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
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
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
    /*
     * 1. Spring Boot 会自动加载该配置类，为业务系统装配 OCPP 所需的默认 Bean。
     * 2. @ConditionalOnMissingBean 表示“用户没有自定义时才创建默认实现”，因此业务系统可以很容易覆盖编解码器、校验器或会话仓库。
     * 3. 默认处理器只保证协议链路可跑通，真实业务通常应使用 @OcppActionMapping 或自定义 OcppActionHandler 替换。
     * 4. 这里同时注册 WebSocket 入口、处理器注册表、下行调用模板和高负载保护组件，是 starter 的装配中心。
     */
    @Bean
    @ConditionalOnMissingBean
    public OcppCodec ocppCodec(ObjectMapper objectMapper) { return new DefaultOcppCodec(objectMapper); }

    @Bean
    @ConditionalOnMissingBean
    public OcppSchemaValidator ocppSchemaValidator() { return new NoopOcppSchemaValidator(); }

    @Bean
    @ConditionalOnMissingBean
    public OcppSessionRepository ocppSessionRepository(OcppProperties properties,
                                                       org.springframework.beans.factory.ObjectProvider<StringRedisTemplate> redisTemplateProvider) {
        StringRedisTemplate redisTemplate = redisTemplateProvider.getIfAvailable();
        if (Boolean.TRUE.equals(properties.getRedisSessionRegistryEnabled()) && redisTemplate != null) {
            return new RedisBackedOcppSessionRepository(
                    redisTemplate,
                    properties.getNodeId(),
                    java.time.Duration.ofSeconds(properties.getRedisSessionRegistryTtlSeconds())
            );
        }
        return new InMemoryOcppSessionRepository();
    }

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
    public RedisMessageListenerContainer redisMessageListenerContainer(org.springframework.beans.factory.ObjectProvider<org.springframework.data.redis.connection.RedisConnectionFactory> connectionFactoryProvider) {
        org.springframework.data.redis.connection.RedisConnectionFactory connectionFactory = connectionFactoryProvider.getIfAvailable();
        if (connectionFactory == null) {
            return null;
        }
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisOcppClusterForwarder redisOcppClusterForwarder(OcppProperties properties,
                                                               ObjectMapper objectMapper,
                                                               org.springframework.beans.factory.ObjectProvider<StringRedisTemplate> redisTemplateProvider,
                                                               org.springframework.beans.factory.ObjectProvider<RedisMessageListenerContainer> containerProvider) {
        if (!Boolean.TRUE.equals(properties.getCrossNodeForwardEnabled())) {
            return null;
        }
        StringRedisTemplate redisTemplate = redisTemplateProvider.getIfAvailable();
        RedisMessageListenerContainer container = containerProvider.getIfAvailable();
        if (redisTemplate == null || container == null) {
            return null;
        }
        return new RedisOcppClusterForwarder(redisTemplate, objectMapper, properties, container);
    }
    @Bean
    @ConditionalOnMissingBean
    public OcppTemplate ocppTemplate(OcppSessionRepository sessionRepository, OcppCodec ocppCodec,
                                     ObjectMapper objectMapper, OcppProperties properties,
                                     org.springframework.beans.factory.ObjectProvider<RedisOcppClusterForwarder> forwarderProvider) {
        return new OcppTemplate(sessionRepository, ocppCodec, objectMapper, properties, forwarderProvider.getIfAvailable());
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
