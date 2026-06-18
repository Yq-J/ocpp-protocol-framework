package com.charging.ocpp.starter.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     */
    private Integer maxTextMessageBytes = 262144;
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
}
