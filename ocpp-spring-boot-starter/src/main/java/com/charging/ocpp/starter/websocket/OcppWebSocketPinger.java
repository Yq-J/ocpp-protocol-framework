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
 * 当 {@code ocpp.ping-interval-seconds > 0} 时启用，每个探测周期：
 * <ol>
 *   <li>若某会话在 {@code pingIntervalSeconds * MISSED_PING_FACTOR} 时间内没有任何入站活动
 *       （文本帧或对此前 Ping 的 Pong 应答），判定为掉线/半开连接并主动关闭；</li>
 *   <li>否则向其发送一次 WebSocket Ping，活跃充电桩会回 Pong 刷新入站活动时间；</li>
 *   <li>发送 Ping 时底层 TCP 已断会直接抛错，立即关闭该会话。</li>
 * </ol>
 * 该判定完全基于应用层入站活动时间，不依赖容器空闲超时（容器的会话级空闲超时会被服务端自身发出的 Ping
 * 刷新写计时而失效），因此对静默断开的 TCP 也能在确定的时间内回收。关闭后由 {@code afterConnectionClosed}
 * 回调统一清理会话仓储与等待中的 pending 请求。
 * </p>
 */
@Slf4j
public class OcppWebSocketPinger implements DisposableBean {
    /** 容忍的连续丢失 Ping/Pong 次数：超过 间隔 ×(该值+1) 无入站活动即判定掉线。 */
    static final int MISSED_PING_FACTOR = 3;

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
     * 执行一次活性探测：回收超时无应答的会话，对其余会话发送 Ping。
     */
    void sweep() {
        long deadlineMillis = pingIntervalSeconds() * MISSED_PING_FACTOR * 1000L;
        long now = System.currentTimeMillis();
        try {
            for (OcppConnection connection : sessionRepository.list()) {
                probe(connection, now, deadlineMillis);
            }
        } catch (Exception e) {
            log.warn("OCPP 连接活性探测执行异常", e);
        }
    }

    private void probe(OcppConnection connection, long now, long deadlineMillis) {
        if (!(connection instanceof SpringOcppConnection) || !connection.isOpen()) {
            return;
        }
        SpringOcppConnection springConnection = (SpringOcppConnection) connection;
        if (deadlineMillis > 0 && now - springConnection.lastActivityMillis() > deadlineMillis) {
            log.warn("OCPP 会话超过 {}ms 无入站活动，判定掉线并关闭：chargePointId={}, sessionId={}",
                    deadlineMillis, connection.getChargePointId(), connection.getSessionId());
            closeQuietly(springConnection);
            return;
        }
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
