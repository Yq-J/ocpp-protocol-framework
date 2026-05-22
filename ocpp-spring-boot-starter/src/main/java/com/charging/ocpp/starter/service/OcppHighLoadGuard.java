package com.charging.ocpp.starter.service;

import com.charging.ocpp.starter.autoconfigure.OcppProperties;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 高负荷保护器（可选 Redis 全局限流）。
 * <p>
 * 设计目标：
 * 1) 单机模式下，使用本地计数实现每秒消息阈值保护；
 * 2) 多实例模式下，若注入 RedisTemplate，则使用 Redis 计数实现全局阈值保护；
 * 3) 超阈值时快速拒绝，避免核心线程池和堆内存被突发流量击穿。
 * </p>
 */
public class OcppHighLoadGuard {
    private final OcppProperties properties;
    private final StringRedisTemplate redisTemplate;
    private final AtomicLong secondWindow = new AtomicLong(0L);
    private final LongAdder localCounter = new LongAdder();

    public OcppHighLoadGuard(OcppProperties properties, StringRedisTemplate redisTemplate) {
        this.properties = properties;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 判断当前是否允许继续处理消息。
     */
    public boolean allow() {
        Integer max = properties.getMaxInboundMessagesPerSecond();
        if (max == null || max <= 0) {
            return true;
        }
        if (Boolean.TRUE.equals(properties.getRedisGlobalGuardEnabled()) && redisTemplate != null) {
            return allowByRedis(max);
        }
        return allowByLocal(max);
    }

    private boolean allowByLocal(int max) {
        long nowSecond = Instant.now().getEpochSecond();
        long prev = secondWindow.get();
        if (prev != nowSecond && secondWindow.compareAndSet(prev, nowSecond)) {
            localCounter.reset();
        }
        localCounter.increment();
        return localCounter.sum() <= max;
    }

    private boolean allowByRedis(int max) {
        String key = "ocpp:inbound:qps:" + Instant.now().getEpochSecond();
        Long value = redisTemplate.opsForValue().increment(key);
        if (value != null && value == 1L) {
            redisTemplate.expire(key, java.time.Duration.ofSeconds(2));
        }
        return value == null || value <= max;
    }
}
