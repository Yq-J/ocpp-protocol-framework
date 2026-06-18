package com.charging.ocpp.starter.websocket;

import com.charging.ocpp.starter.autoconfigure.OcppProperties;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

/**
 * OCPP WebSocket 子协议选择器。
 * 作者：JYq
 */
public class OcppWebSocketHandshakeHandler extends DefaultHandshakeHandler {
    private final OcppProperties properties;

    public OcppWebSocketHandshakeHandler(OcppProperties properties) {
        this.properties = properties;
    }

    @Override
    protected String selectProtocol(List<String> requestedProtocols, @NonNull WebSocketHandler webSocketHandler) {
        List<String> supported = properties.getSupportedSubProtocols();
        if (supported == null || supported.isEmpty()) {
            return super.selectProtocol(requestedProtocols, webSocketHandler);
        }
        for (String item : requestedProtocols) {
            for (String candidate : supported) {
                if (candidate != null && candidate.equalsIgnoreCase(item)) { return candidate; }
            }
        }
        return super.selectProtocol(requestedProtocols, webSocketHandler);
    }
}
