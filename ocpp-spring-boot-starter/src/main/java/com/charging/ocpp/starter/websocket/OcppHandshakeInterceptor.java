package com.charging.ocpp.starter.websocket;

import com.charging.ocpp.starter.autoconfigure.OcppProperties;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

/**
 * 握手拦截器。
 * 作者：JYq
 */
public class OcppHandshakeInterceptor implements HandshakeInterceptor {
    private final OcppProperties properties;

    public OcppHandshakeInterceptor(OcppProperties properties) { this.properties = properties; }

    @Override
    public boolean beforeHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                                   @NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) {
        String chargePointId = null;
        try {
            Map<String, String> variables = new UriTemplate(properties.getPath()).match(request.getURI().getPath());
            chargePointId = variables.get("chargePointId");
            attributes.put("chargePointId", chargePointId);
        } catch (Exception ignored) {
            // 连接建立后会统一校验 chargePointId。
        }
        if (!Boolean.TRUE.equals(properties.getRequireAuthToken())) {
            return true;
        }
        if (!StringUtils.hasText(chargePointId)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }
        String expected = properties.getChargePointTokens() == null ? null : properties.getChargePointTokens().get(chargePointId);
        if (!StringUtils.hasText(expected) || !expected.equals(resolveToken(request))) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }
        return true;
    }

    @Override
    public void afterHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                               @NonNull WebSocketHandler wsHandler, Exception exception) {
        // 无需后置处理。
    }

    private String resolveToken(ServerHttpRequest request) {
        List<String> header = request.getHeaders().get(properties.getAuthTokenHeader());
        if (header != null && !header.isEmpty() && StringUtils.hasText(header.get(0))) {
            return header.get(0);
        }
        return UriComponentsBuilder.fromUri(request.getURI()).build().getQueryParams()
                .getFirst(properties.getAuthTokenQueryParameter());
    }
}
