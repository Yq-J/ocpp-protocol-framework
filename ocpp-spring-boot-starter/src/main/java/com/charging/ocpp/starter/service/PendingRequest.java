package com.charging.ocpp.starter.service;

import java.util.concurrent.CompletableFuture;
import com.charging.ocpp.core.enums.OcppVersion;

/**
 * 等待中的平台侧 CALL 请求。
 * 作者：JYq
 */
class PendingRequest<R> {
    private final Class<R> responseType;
    private final CompletableFuture<R> future;
    private final long deadlineMillis;
    private final OcppVersion version;
    private final String action;

    PendingRequest(Class<R> responseType, CompletableFuture<R> future, long deadlineMillis,
                   OcppVersion version, String action) {
        this.responseType = responseType;
        this.future = future;
        this.deadlineMillis = deadlineMillis;
        this.version = version;
        this.action = action;
    }

    Class<R> getResponseType() { return responseType; }
    CompletableFuture<R> getFuture() { return future; }
    long getDeadlineMillis() { return deadlineMillis; }
    OcppVersion getVersion() { return version; }
    String getAction() { return action; }
}
