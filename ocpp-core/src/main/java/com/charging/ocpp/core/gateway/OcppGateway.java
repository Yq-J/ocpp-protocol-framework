package com.charging.ocpp.core.gateway;

import com.charging.ocpp.core.enums.OcppVersion;
import java.util.concurrent.CompletableFuture;

/**
 * 业务侧下发 OCPP 命令的统一网关。
 * 作者：JYq
 */
public interface OcppGateway {
    <R> CompletableFuture<R> call(String chargePointId, OcppVersion version, String action, Object payload, Class<R> responseType);
}
