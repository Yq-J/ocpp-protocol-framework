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
     * 等待 CALLRESULT/CALLERROR 的业务请求超时时间，单位秒。
     * <p>
     * 说明：
     * 1. 该配置用于中心系统主动下发指令（CALL）后，等待充电桩返回结果的超时控制；
     * 2. 当该值为空或小于等于 0 时，将回退使用 connectionTimeoutSeconds；
     * 3. 保留 connectionTimeoutSeconds 主要是为了兼容历史配置，避免已有项目升级时行为突变。
     * </p>
     */
    private Integer requestTimeoutSeconds;
    /**
     * 未知 Action 是否自动返回空对象。
     */
    private Boolean allowUnknownActions = false;

}
