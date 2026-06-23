package com.charging.ocpp.starter.websocket;

import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 测试用的 {@link WebSocketSession} 假实现，捕获发送的文本帧并可模拟 Ping 失败。
 */
class FakeWebSocketSession implements WebSocketSession {
    private final String id;
    private final String acceptedProtocol;
    private final Map<String, Object> attributes = new ConcurrentHashMap<>();
    private final List<String> sentTexts = Collections.synchronizedList(new ArrayList<>());
    private volatile boolean open = true;
    private volatile boolean closeCalled = false;
    private volatile boolean failOnPing = false;

    FakeWebSocketSession(String id, String acceptedProtocol, String chargePointId) {
        this.id = id;
        this.acceptedProtocol = acceptedProtocol;
        if (chargePointId != null) {
            this.attributes.put("chargePointId", chargePointId);
        }
    }

    void setFailOnPing(boolean failOnPing) {
        this.failOnPing = failOnPing;
    }

    List<String> getSentTexts() {
        return sentTexts;
    }

    String getLastText() {
        synchronized (sentTexts) {
            return sentTexts.isEmpty() ? null : sentTexts.get(sentTexts.size() - 1);
        }
    }

    boolean isCloseCalled() {
        return closeCalled;
    }

    @Override
    public void sendMessage(WebSocketMessage<?> message) throws IOException {
        if (message instanceof PingMessage) {
            if (failOnPing) {
                throw new IOException("simulated dead connection");
            }
            return;
        }
        if (message instanceof TextMessage) {
            sentTexts.add(((TextMessage) message).getPayload());
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getAcceptedProtocol() {
        return acceptedProtocol;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public void close() throws IOException {
        this.closeCalled = true;
        this.open = false;
    }

    @Override
    public void close(CloseStatus status) throws IOException {
        close();
    }

    @Override
    public URI getUri() {
        return URI.create("ws://localhost/ocpp/" + attributes.get("chargePointId"));
    }

    @Override
    public HttpHeaders getHandshakeHeaders() {
        return new HttpHeaders();
    }

    @Override
    public Principal getPrincipal() {
        return null;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return null;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return null;
    }

    @Override
    public int getTextMessageSizeLimit() {
        return 0;
    }

    @Override
    public void setTextMessageSizeLimit(int messageSizeLimit) {
    }

    @Override
    public int getBinaryMessageSizeLimit() {
        return 0;
    }

    @Override
    public void setBinaryMessageSizeLimit(int messageSizeLimit) {
    }

    @Override
    public List<WebSocketExtension> getExtensions() {
        return Collections.emptyList();
    }
}
