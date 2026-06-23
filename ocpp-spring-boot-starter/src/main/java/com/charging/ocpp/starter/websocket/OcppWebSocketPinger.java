package com.charging.ocpp.starter.websocket;

import com.charging.ocpp.core.session.OcppConnection;
import com.charging.ocpp.core.session.OcppSessionRepository;
import com.charging.ocpp.starter.autoconfigure.OcppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * OCPP 连接活性探测器。
 * <p>
 * 作者：JYq。
 * 当 {@code ocpp.ping-interval-seconds > 0} 时启用：定时向所有在线 OCPP 会话发送 WebSocket Ping。
 * 活跃充电桩会回 Pong（刷新容器空闲计时并证明连接存活），发送 Ping 失败（底层 TCP 已断）的会话会被主动关闭，
 * 由 {@code afterConnectionClosed} 回调统一清理会话仓储与等待中的 pending 请求，避免半开连接长期滞留。
 * </p>
 */
@Slf4j
public class OcppWebSocketPinger implements DisposableBean {
    private final OcppSessionRepository sessionRepository;
    private final OcppProperties properties;
    private final ScheduledExecutorService scheduler;
    private volatile boolean enabled;

    public OcppWebSocketPinger(OcppSessionRepository sessionRepository, OcppProperties properties) {
        this.sessionRepository = sessionRepository;
        this.properties = properties;
        long interval = pingIntervalSeconds();
        if (interval <= 0) {
            this.scheduler = null;
            this.enabled = false;
            return;
        }
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r, "ocpp-ws-pinger");
            thread.setDaemon(true);
            return thread;
        });
        this.enabled = true;
        this.scheduler.scheduleAtFixedRate(this::sweep, interval, interval, TimeUnit.SECONDS);
    }

    /**
     * 执行一次活性探测，对所有在线会话发送 Ping，发送失败的会话主动关闭。
     */
    void sweep() {
        try {
            for (OcppConnection connection : sessionRepository.list()) {
                pingQuietly(connection);
            }
        } catch (Exception e) {
            log.warn("OCPP 连接活性探测执行异常", e);
        }
    }

    private void pingQuietly(OcppConnection connection) {
        if (!(connection instanceof SpringOcppConnection) || !connection.isOpen()) {
            return;
        }
        SpringOcppConnection springConnection = (SpringOcppConnection) connection;
        try {
            springConnection.sendPing();
        } catch (Exception e) {
            log.warn("OCPP 会话 Ping 失败，主动关闭半开连接：chargePointId={}, sessionId={}",
                    connection.getChargePointId(), connection.getSessionId(), e);
            closeQuietly(springConnection);
        }
    }

    private void closeQuietly(SpringOcppConnection connection) {
        try {
            connection.close();
        } catch (Exception e) {
            // close 失败说明底层连接已不可用，兜底从仓储移除，避免悬挂引用。
            sessionRepository.remove(connection.getSessionId());
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    private long pingIntervalSeconds() {
        return properties.getPingIntervalSeconds() == null ? 0L : properties.getPingIntervalSeconds().longValue();
    }

    @Override
    public void destroy() {
        enabled = false;
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }
}
