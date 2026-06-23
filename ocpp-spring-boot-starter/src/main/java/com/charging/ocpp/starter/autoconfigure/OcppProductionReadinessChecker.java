package com.charging.ocpp.starter.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * OCPP 生产化配置检查器。
 * <p>
 * 作者：JYq。
 * 该检查器只校验框架层可感知的高风险配置，业务状态机、证书体系、计费和交易幂等等仍需业务系统自行纳入上线检查。
 * </p>
 */
@Slf4j
public class OcppProductionReadinessChecker implements SmartInitializingSingleton {
    private final OcppProperties properties;

    public OcppProductionReadinessChecker(OcppProperties properties) {
        this.properties = properties;
    }

    @Override
    public void afterSingletonsInstantiated() {
        if (!Boolean.TRUE.equals(properties.getProductionReadinessCheck())) {
            return;
        }
        List<String> warnings = inspect();
        if (warnings.isEmpty()) {
            log.info("OCPP production readiness check passed");
            return;
        }
        for (String warning : warnings) {
            log.warn("OCPP production readiness warning: {}", warning);
        }
        if (Boolean.TRUE.equals(properties.getFailOnUnsafeProductionConfig())) {
            throw new IllegalStateException("OCPP production readiness check failed: " + warnings);
        }
    }

    List<String> inspect() {
        List<String> warnings = new ArrayList<>();
        if (CollectionUtils.isEmpty(properties.getAllowedOrigins()) || properties.getAllowedOrigins().contains("*")) {
            warnings.add("allowed-origins 包含 *，生产环境应收敛为可信域名");
        }
        if (!Boolean.TRUE.equals(properties.getRequireAuthToken())) {
            warnings.add("require-auth-token 未开启，生产环境应启用握手 Token 或由网关提供等效鉴权");
        } else if (CollectionUtils.isEmpty(properties.getChargePointTokens())) {
            warnings.add("require-auth-token 已开启但 charge-point-tokens 为空，所有充电桩握手都会失败");
        }
        if (Boolean.TRUE.equals(properties.getAllowUnknownActions())) {
            warnings.add("allow-unknown-actions 已开启，未知 Action 会被空响应吞掉");
        }
        if (Boolean.TRUE.equals(properties.getEnableDefaultHandlers())) {
            warnings.add("enable-default-handlers 已开启，内置示例处理器不应承载生产业务");
        }
        if (properties.getMaxTextMessageBytes() == null || properties.getMaxTextMessageBytes() <= 0) {
            warnings.add("max-text-message-bytes 未设置有效正数，无法限制异常大报文");
        }
        if (properties.getConnectionTimeoutSeconds() == null || properties.getConnectionTimeoutSeconds() <= 0) {
            warnings.add("connection-timeout-seconds 未设置有效正数，主动命令可能无法按预期超时");
        }
        boolean idleTimeoutDisabled = properties.getSessionIdleTimeoutSeconds() == null || properties.getSessionIdleTimeoutSeconds() <= 0;
        boolean pingDisabled = properties.getPingIntervalSeconds() == null || properties.getPingIntervalSeconds() <= 0;
        if (idleTimeoutDisabled && pingDisabled) {
            warnings.add("session-idle-timeout-seconds 与 ping-interval-seconds 均未启用，半开/掉线连接无法及时回收，建议至少启用其一");
        }
        if (properties.getDuplicateConnectionPolicy() == OcppProperties.DuplicateConnectionPolicy.REPLACE) {
            warnings.add("duplicate-connection-policy=REPLACE 不会主动关闭旧连接，生产环境建议使用 CLOSE_OLD 或 REJECT_NEW");
        }
        return warnings;
    }
}
