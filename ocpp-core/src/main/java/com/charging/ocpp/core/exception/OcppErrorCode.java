package com.charging.ocpp.core.exception;

/**
 * OCPP CALLERROR 错误码。
 * 作者：JYq
 */
public enum OcppErrorCode {
    /*
     * 1. 这些名称对应 OCPP CALLERROR 帧中的 errorCode 字段，用来告诉对端失败原因。
     * 2. ProtocolError、FormationViolation 偏向协议格式错误；InternalError 偏向平台内部异常；NotImplemented 表示该 action 没有实现。
     * 3. 业务处理器可以抛出 OcppException 并指定这里的错误码，WebSocket 层会自动编码成 CALLERROR。
     */
    /** 未实现：平台或充电桩没有实现当前请求的 Action，例如没有注册对应的业务处理器。 */
    NotImplemented,
    /** 不支持：当前 Action、功能、配置项或协议能力被识别出来了，但当前系统明确不支持。 */
    NotSupported,
    /** 内部错误：平台或充电桩内部处理异常，例如程序异常、依赖服务失败、发送消息失败等。 */
    InternalError,
    /** 协议错误：OCPP 外层协议帧不符合要求，例如消息类型不合法、数组长度不正确、uniqueId 缺失等。 */
    ProtocolError,
    /** 安全错误：认证、授权、签名、证书或安全策略校验失败时使用。 */
    SecurityError,
    /** 格式错误：JSON 结构无法解析，或消息不是合法 JSON、不是合法 OCPP-J 数组。 */
    FormationViolation,
    /** 属性约束错误：字段值违反协议约束，例如枚举值非法、字符串长度超限、数值范围不合法。 */
    PropertyConstraintViolation,
    /** 出现次数约束错误：字段出现次数不符合要求，例如必填字段缺失、数组元素数量过多或过少。 */
    OccurrenceConstraintViolation,
    /** 类型约束错误：字段类型不符合协议要求，例如本应是数字却传了字符串，本应是对象却传了数组。 */
    TypeConstraintViolation,
    /** 通用错误：无法归类到以上错误码的普通业务失败或兜底错误。 */
    GenericError
}
