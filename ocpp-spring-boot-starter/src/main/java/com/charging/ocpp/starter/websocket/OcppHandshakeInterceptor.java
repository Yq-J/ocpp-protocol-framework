package com.charging.ocpp.starter.websocket;

import java.util.Map;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriTemplate;

/**
 * 握手拦截器。
 * 作者：JYq
 */
public class OcppHandshakeInterceptor implements HandshakeInterceptor {
    /*
     * 1. WebSocket 握手阶段还没有进入 OcppWebSocketHandler，本拦截器先从 URL 路径中提取 chargePointId。
     * 2. 提取到的值放进 session attributes，连接建立后 handler 就能知道是哪台桩。
     * 3. 这里不做强校验，真正校验放在 afterConnectionEstablished，便于统一关闭不合法连接。
     */
    private final String path;

    public OcppHandshakeInterceptor(String path) { this.path = path; }

    @Override
    public boolean beforeHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response, @NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) {
        try {
            Map<String, String> variables = new UriTemplate(path).match(request.getURI().getPath());
            attributes.put("chargePointId", variables.get("chargePointId"));
        } catch (Exception ignored) {
            // 连接建立后会统一校验 chargePointId。
        }
        return true;
    }

    @Override
    public void afterHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response, @NonNull WebSocketHandler wsHandler, Exception exception) {
        // 无需后置处理。
    }
}
