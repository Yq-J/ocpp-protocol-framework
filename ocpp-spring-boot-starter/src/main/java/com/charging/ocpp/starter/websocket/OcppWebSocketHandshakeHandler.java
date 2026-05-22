package com.charging.ocpp.starter.websocket;

import java.util.Arrays;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

/**
 * OCPP WebSocket 子协议选择器。
 * 作者：JYq
 */
public class OcppWebSocketHandshakeHandler extends DefaultHandshakeHandler {
    /*
     * 1. WebSocket 子协议由桩端在握手请求头 Sec-WebSocket-Protocol 中提出。
     * 2. 本类只接受 ocpp1.6 和 ocpp2.0.1，选择成功后 session.getAcceptedProtocol() 就能拿到最终协议。
     */
    @Override
    protected String selectProtocol(List<String> requestedProtocols, @NonNull WebSocketHandler webSocketHandler) {
        List<String> supported = Arrays.asList("ocpp1.6", "ocpp2.0.1");
        for (String item : requestedProtocols) {
            if (supported.contains(item)) { return item; }
        }
        return super.selectProtocol(requestedProtocols, webSocketHandler);
    }
}
