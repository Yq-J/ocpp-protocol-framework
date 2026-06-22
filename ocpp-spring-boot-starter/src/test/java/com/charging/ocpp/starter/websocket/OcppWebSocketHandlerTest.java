package com.charging.ocpp.starter.websocket;

import org.junit.jupiter.api.Test;
import org.springframework.web.socket.TextMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OcppWebSocketHandlerTest {
    @Test
    void payloadLengthBytesCountsUtf8BytesInsteadOfCharacters() {
        assertEquals(6, OcppWebSocketHandler.payloadLengthBytes(new TextMessage("心跳")));
    }
}
