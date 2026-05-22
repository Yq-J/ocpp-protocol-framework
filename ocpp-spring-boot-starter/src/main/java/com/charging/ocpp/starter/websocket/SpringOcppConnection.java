package com.charging.ocpp.starter.websocket;

import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.session.OcppConnection;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * Spring WebSocketSession 到 OcppConnection 的适配器。
 * 作者：JYq
 */
public class SpringOcppConnection implements OcppConnection {
    /*
     * 1. 这是适配器模式：把 Spring 的 WebSocketSession 包装成 core 层认识的 OcppConnection。
     * 2. send 方法加 synchronized，是为了避免多个线程同时向同一条 WebSocket 连接写消息导致发送顺序或底层状态异常。
     */
    private final WebSocketSession session;
    private final String chargePointId;
    private final OcppVersion version;

    public SpringOcppConnection(WebSocketSession session, String chargePointId, OcppVersion version) {
        this.session = session;
        this.chargePointId = chargePointId;
        this.version = version;
    }

    @Override
    public String getSessionId() {
        return session.getId();
    }

    @Override
    public String getChargePointId() {
        return chargePointId;
    }

    @Override
    public OcppVersion getVersion() {
        return version;
    }

    @Override
    public boolean isOpen() {
        return session.isOpen();
    }

    @Override
    public synchronized void send(String text) throws IOException {
        session.sendMessage(new TextMessage(text));
    }
}
