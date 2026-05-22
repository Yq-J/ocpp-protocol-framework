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
    /*
     * 1. 这是框架专用异常，除了普通异常消息，还携带 OCPP 错误码和 details 详情对象。
     * 2. details 会进入 CALLERROR 的第 4 个数组元素，通常用于放结构化错误信息；没有详情时传 null 即可。
     * 3. 构造方法会把空 errorCode 兜底为 InternalError，避免发出不合法的错误帧。
     */
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
