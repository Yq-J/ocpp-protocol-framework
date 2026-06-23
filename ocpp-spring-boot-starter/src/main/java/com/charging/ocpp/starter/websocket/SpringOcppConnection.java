package com.charging.ocpp.starter.websocket;

import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.session.OcppConnection;
import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Spring WebSocketSession 到 OcppConnection 的适配器。
 * 作者：JYq
 */
public class SpringOcppConnection implements OcppConnection {
    static final String SEND_LOCK_ATTRIBUTE = SpringOcppConnection.class.getName() + ".SEND_LOCK";

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

    static Object sendLock(WebSocketSession session) {
        Object lock = session.getAttributes().get(SEND_LOCK_ATTRIBUTE);
        if (lock != null) {
            return lock;
        }
        synchronized (session.getAttributes()) {
            lock = session.getAttributes().get(SEND_LOCK_ATTRIBUTE);
            if (lock == null) {
                lock = new Object();
                session.getAttributes().put(SEND_LOCK_ATTRIBUTE, lock);
            }
            return lock;
        }
    }

    @Override
    public void send(String text) throws IOException {
        synchronized (sendLock(session)) {
            session.sendMessage(new TextMessage(text));
        }
    }

    /**
     * 发送 WebSocket Ping 帧用于连接活性探测。
     * <p>
     * 复用与文本帧相同的发送锁，避免与业务下发帧并发写导致的帧交错。
     * </p>
     */
    public void sendPing() throws IOException {
        synchronized (sendLock(session)) {
            session.sendMessage(new PingMessage(ByteBuffer.allocate(0)));
        }
    }

    @Override
    public void close() throws IOException {
        synchronized (sendLock(session)) {
            if (session.isOpen()) {
                session.close();
            }
        }
    }
}
