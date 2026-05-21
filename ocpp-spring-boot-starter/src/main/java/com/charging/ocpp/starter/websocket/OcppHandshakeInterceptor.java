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
