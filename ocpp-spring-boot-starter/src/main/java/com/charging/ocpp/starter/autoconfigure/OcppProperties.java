package com.charging.ocpp.starter.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * OCPP Starter 配置属性。
 * 作者：JYq
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "ocpp")
public class OcppProperties {
    /**
     * OCPP WebSocket 接入路径。
     */
    private String path = "/ocpp/{chargePointId}";
    /**
     * 等待 CALLRESULT/CALLERROR 的超时时间，单位秒。
     */
    private Integer connectionTimeoutSeconds = 60;
    /**
     * 未知 Action 是否自动返回空对象。
     */
    private Boolean allowUnknownActions = false;
    /**
     * 入站消息处理线程数。
     * <p>
     * 生产建议按 CPU 核数或压测结果配置，十万连接场景通常应避免由 WebSocket I/O 线程串行执行业务逻辑，
     * 因此这里提供独立线程池进行异步卸载，降低 I/O 线程阻塞概率。
     * </p>
     */
    private Integer inboundWorkerThreads = Math.max(8, Runtime.getRuntime().availableProcessors() * 2);
    /**
     * 入站消息队列容量。
     * <p>
     * 当瞬时流量激增时，队列可用于短时削峰；超限后将触发拒绝策略并快速返回系统繁忙错误，
     * 避免 JVM 堆无限增长导致 Full GC 或 OOM。
     * </p>
     */
    private Integer inboundQueueCapacity = 200000;
    /**
     * 清理等待请求的时间轮/扫描周期，单位毫秒。
     */
    private Integer pendingCleanupIntervalMillis = 1000;
    /**
     * 是否开启高性能模式日志降采样。
     * <p>
     * 在大规模连接场景中，过量 INFO 日志会造成磁盘和 CPU 压力；启用后仅输出关键异常日志。
     * </p>
     */
    private Boolean highLoadLogSamplingEnabled = true;
    /**
     * 每秒允许处理的入站消息上限（单机或全局）。
     * <=0 表示不限制。
     */
    private Integer maxInboundMessagesPerSecond = 0;
    /**
     * 是否启用 Redis 全局限流保护。
     * <p>
     * 开启后会按秒在 Redis 中聚合消息量，适用于多实例场景下统一削峰。
     * </p>
     */
    private Boolean redisGlobalGuardEnabled = false;
    /**
     * 是否启用 Redis 会话注册。
     * <p>
     * 开启后会将 chargePointId 与节点归属写入 Redis，便于集群下实现快速会话归属判断。
     * </p>
     */
    private Boolean redisSessionRegistryEnabled = false;
    /**
     * Redis 会话注册过期时间，单位秒。
     */
    private Integer redisSessionRegistryTtlSeconds = 120;
    /**
     * 当前节点标识。
     * <p>
     * 默认使用主机名 + 进程号，若容器弹性伸缩建议显式配置为 podName 或实例 ID。
     * </p>
     */
    private String nodeId = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();

}
