package com.charging.ocpp.starter.service;

import com.charging.ocpp.core.enums.OcppVersion;

import java.util.concurrent.CompletableFuture;

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
    private final String chargePointId;
    private final String sessionId;

    PendingRequest(Class<R> responseType, CompletableFuture<R> future, long deadlineMillis,
                   OcppVersion version, String action, String chargePointId, String sessionId) {
        this.responseType = responseType;
        this.future = future;
        this.deadlineMillis = deadlineMillis;
        this.version = version;
        this.action = action;
        this.chargePointId = chargePointId;
        this.sessionId = sessionId;
    }

    Class<R> getResponseType() { return responseType; }
    CompletableFuture<R> getFuture() { return future; }
    long getDeadlineMillis() { return deadlineMillis; }
    OcppVersion getVersion() { return version; }
    String getAction() { return action; }
    String getChargePointId() { return chargePointId; }
    String getSessionId() { return sessionId; }
}
