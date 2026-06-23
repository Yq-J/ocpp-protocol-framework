package com.charging.ocpp.starter.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.*;

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
     * 是否校验 Action 必须属于对应 OCPP 版本的官方动作集合。
     */
    private Boolean validateKnownActions = true;
    /**
     * 是否注册 starter 内置示例处理器。默认关闭，生产环境应显式提供业务 Handler。
     */
    private Boolean enableDefaultHandlers = false;
    /**
     * WebSocket 握手允许协商的 OCPP 子协议。
     */
    private List<String> supportedSubProtocols = Arrays.asList("ocpp1.6", "ocpp2.0.1");
    /**
     * 允许跨域访问的 Origin 列表。生产环境必须按域名收敛，避免使用默认的 *。
     */
    private List<String> allowedOrigins = Collections.singletonList("*");
    /**
     * 单条 WebSocket 文本消息最大字节数，超过后直接返回 CALLERROR，防止异常大报文冲击内存。
     * <p>
     * 该值同时用于设置底层 WebSocket 容器的文本缓冲区大小（maxTextMessageBufferSize）。
     * 容器默认文本缓冲仅 8KB，若不放大，OCPP 2.0.1 证书类等大报文会在缓冲区上限被容器强制关闭，
     * 应用层根本收不到完整报文。设为 null 或非正数时不修改容器默认值。
     * </p>
     */
    private Integer maxTextMessageBytes = 262144;
    /**
     * WebSocket 会话空闲超时时间，单位秒。映射到容器 maxSessionIdleTimeout。
     * <p>
     * 大于 0 时启用：在该时间内未收到任何消息（含充电桩 Heartbeat 与 Pong）即关闭会话，用于回收半开连接。
     * 取值应大于充电桩 Heartbeat 间隔与 {@link #pingIntervalSeconds}，否则可能误断活跃连接。
     * 默认 0（不设置，沿用容器默认行为）。注意该设置作用于整个应用的 WebSocket 容器。
     * </p>
     */
    private Integer sessionIdleTimeoutSeconds = 0;
    /**
     * 服务端主动向充电桩发送 WebSocket Ping 的间隔，单位秒。
     * <p>
     * 大于 0 时启用：定时向所有在线 OCPP 会话发送 Ping，活跃充电桩会回 Pong（刷新空闲计时并证明存活），
     * 发送 Ping 失败（TCP 已断）的会话会被主动关闭并清理。建议与 {@link #sessionIdleTimeoutSeconds} 配合使用。
     * 默认 0（不启用）。
     * </p>
     */
    private Integer pingIntervalSeconds = 0;
    /**
     * 同一 chargePointId 重复建立连接时的处理策略。
     */
    private DuplicateConnectionPolicy duplicateConnectionPolicy = DuplicateConnectionPolicy.CLOSE_OLD;
    /**
     * 是否启用握手 Token 校验。生产环境建议开启，并为每个 chargePointId 配置独立 Token。
     */
    private Boolean requireAuthToken = false;
    /**
     * 握手 Token 的查询参数名，例如 /ocpp/CP001?token=xxx。
     */
    private String authTokenQueryParameter = "token";
    /**
     * 握手 Token 的 HTTP Header 名。Header 优先级高于查询参数。
     */
    private String authTokenHeader = "X-OCPP-Token";
    /**
     * 充电桩 ID 到握手 Token 的映射。仅 require-auth-token=true 时生效。
     */
    private Map<String, String> chargePointTokens = new HashMap<>();
    /**
     * 启动时是否执行生产安全配置检查。
     */
    private Boolean productionReadinessCheck = true;
    /**
     * 生产安全配置检查发现高风险配置时是否阻止应用启动。
     */
    private Boolean failOnUnsafeProductionConfig = false;

    public enum DuplicateConnectionPolicy {
        /**
         * 保留旧连接并拒绝新连接。
         */
        REJECT_NEW,
        /**
         * 关闭旧连接并保存新连接。
         */
        CLOSE_OLD,
        /**
         * 仅替换会话仓储中的连接引用，不主动关闭旧连接。
         */
        REPLACE
    }
}
