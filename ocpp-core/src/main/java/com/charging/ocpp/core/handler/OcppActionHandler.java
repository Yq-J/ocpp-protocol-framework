package com.charging.ocpp.core.handler;

import com.charging.ocpp.core.enums.OcppVersion;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * OCPP Action 处理器接口。
 * 作者：JYq
 */
public interface OcppActionHandler {
    OcppVersion version();
    String action();

    /**
     * 处理充电桩上行 CALL。
     * <p>
     * 返回值可以是强类型响应 DTO（同步处理），也可以是 {@link java.util.concurrent.CompletionStage}
     * /{@link java.util.concurrent.CompletableFuture}（异步处理）。返回 CompletionStage 时，框架不会阻塞
     * WebSocket 容器 IO 线程，而是在该 Future 完成后再回 CALLRESULT/CALLERROR。
     * </p>
     * <p>
     * 注意：该方法默认运行在容器 IO 线程上。请勿在此线程内做长阻塞操作（DB、RPC），更不要对
     * <b>同一充电桩</b>同步调用 {@code OcppGateway.call(...).get()} 等待响应——响应需要由同一会话的
     * IO 线程处理，同步等待会造成死锁直至超时。耗时业务应放入业务自有线程池并返回 CompletionStage。
     * </p>
     */
    Object handle(OcppRequestContext context, JsonNode payload) throws Exception;

    /**
     * 是否为框架内置的示例/兜底处理器。
     * 业务处理器与框架默认处理器注册到同一 version + action 时，业务处理器优先。
     */
    default boolean frameworkDefault() {
        return false;
    }
}
