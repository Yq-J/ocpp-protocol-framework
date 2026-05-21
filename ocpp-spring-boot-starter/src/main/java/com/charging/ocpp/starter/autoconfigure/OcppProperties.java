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

}
