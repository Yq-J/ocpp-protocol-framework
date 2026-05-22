package com.charging.ocpp.core.gateway;

import com.charging.ocpp.core.enums.OcppVersion;
import java.util.concurrent.CompletableFuture;

/**
 * 业务侧下发 OCPP 命令的统一网关。
 * 作者：JYq
 */
public interface OcppGateway {
    /*
     * 1. 这是业务系统主动向充电桩下发命令的抽象入口，例如远程启停、改配置、解锁枪等。
     * 2. 返回 CompletableFuture 是因为 WebSocket 调用天然异步：命令先发出去，充电桩稍后用相同 uniqueId 返回结果。
     * 3. 业务层依赖该接口而不是具体 OcppTemplate，可以在集群网关、消息队列转发等场景替换实现。
     */
    <R> CompletableFuture<R> call(String chargePointId, OcppVersion version, String action, Object payload, Class<R> responseType);
}
