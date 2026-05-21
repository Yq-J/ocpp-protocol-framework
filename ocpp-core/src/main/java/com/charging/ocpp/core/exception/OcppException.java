package com.charging.ocpp.core.exception;

import lombok.Getter;

/**
 * OCPP 协议异常。
 * <p>
 * 作者：JYq
 * 说明：业务层可抛出该异常，框架会自动转换为 CALLERROR。
 * </p>
 */
@Getter
public class OcppException extends RuntimeException {
    private final OcppErrorCode errorCode;
    private final Object details;

    public OcppException(OcppErrorCode errorCode, String message) {
        this(errorCode, message, null, null);
    }

    public OcppException(OcppErrorCode errorCode, String message, Object details) {
        this(errorCode, message, details, null);
    }

    public OcppException(OcppErrorCode errorCode, String message, Object details, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode == null ? OcppErrorCode.InternalError : errorCode;
        this.details = details;
    }

}
