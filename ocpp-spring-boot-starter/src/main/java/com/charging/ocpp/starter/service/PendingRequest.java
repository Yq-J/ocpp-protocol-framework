package com.charging.ocpp.starter.service;

import java.util.concurrent.CompletableFuture;

/**
 * 等待中的平台侧 CALL 请求。
 * 作者：JYq
 */
class PendingRequest<R> {
    /*
     * 1. 这是 OcppTemplate 内部使用的小对象，保存一次平台下行 CALL 的等待状态。
     * 2. responseType 用于把 JsonNode 响应转换成业务期待的 DTO；future 用于通知调用方；deadlineMillis 用于超时清理。
     */
    private final Class<R> responseType;
    private final CompletableFuture<R> future;
    private final long deadlineMillis;

    PendingRequest(Class<R> responseType, CompletableFuture<R> future, long deadlineMillis) {
        this.responseType = responseType;
        this.future = future;
        this.deadlineMillis = deadlineMillis;
    }

    Class<R> getResponseType() { return responseType; }
    CompletableFuture<R> getFuture() { return future; }
    long getDeadlineMillis() { return deadlineMillis; }
}
