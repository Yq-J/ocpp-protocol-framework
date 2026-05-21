package com.charging.ocpp.starter.service;

import java.util.concurrent.CompletableFuture;

/**
 * 等待中的平台侧 CALL 请求。
 * 作者：JYq
 */
class PendingRequest<R> {
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
