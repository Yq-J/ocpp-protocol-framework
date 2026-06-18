package com.charging.ocpp.starter.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import java.util.Arrays;
import java.util.List;
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

}
