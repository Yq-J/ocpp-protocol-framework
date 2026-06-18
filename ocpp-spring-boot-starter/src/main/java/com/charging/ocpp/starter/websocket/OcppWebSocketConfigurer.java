package com.charging.ocpp.starter.websocket;

import lombok.RequiredArgsConstructor;
import com.charging.ocpp.starter.autoconfigure.OcppProperties;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * OCPP WebSocket 注册器。
 * <p>
 * 作者：JYq。
 * 本类通过 Spring WebSocket 的 WebSocketConfigurer 注册原生 WebSocket 端点，
 * 不是 STOMP，也不是 SockJS，更不是 Netty。OCPP JSON over WebSocket 需要的是一个持续传输文本帧的原生 WebSocket 通道，
 * 因此这里直接把 OcppWebSocketHandler 绑定到配置路径，例如 /ocpp/{chargePointId}。
 * </p>
 */
@RequiredArgsConstructor
public class OcppWebSocketConfigurer implements WebSocketConfigurer {
    private final OcppWebSocketHandler handler;
    private final OcppProperties properties;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler, properties.getPath())
                .setAllowedOrigins("*")
                .addInterceptors(new OcppHandshakeInterceptor(properties.getPath()))
                .setHandshakeHandler(new OcppWebSocketHandshakeHandler(properties));
    }
}
