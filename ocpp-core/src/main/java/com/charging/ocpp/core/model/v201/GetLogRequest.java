package com.charging.ocpp.core.model.v201;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 GetLog 请求 payload 协议实体类。
 * <p>
 * 用途：承载 GetLog 操作的请求字段，用于请求日志上传场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GetLogRequest {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 GetLogRequest 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 日志参数。
     * <p>
     * 用途：对应 OCPP 字段 {@code log}，在 OCPP 2.0.1 GetLogRequest 协议对象中传递日志参数。
     * 字段类型为 {@code LogParameters}，用于承载日志参数。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private LogParameters log;
    /**
     * 日志类型。
     * <p>
     * 用途：对应 OCPP 字段 {@code logType}，在 OCPP 2.0.1 GetLogRequest 协议对象中传递日志类型。
     * 字段类型为 {@code String}，用于承载日志类型。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String logType;
    /**
     * 请求标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code requestId}，在 OCPP 2.0.1 GetLogRequest 协议对象中传递请求标识。
     * 字段类型为 {@code Integer}，用于承载请求标识。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer requestId;
    /**
     * 重试次数。
     * <p>
     * 用途：对应 OCPP 字段 {@code retries}，在 OCPP 2.0.1 GetLogRequest 协议对象中传递重试次数。
     * 字段类型为 {@code Integer}，用于承载重试次数。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer retries;
    /**
     * 重试间隔。
     * <p>
     * 用途：对应 OCPP 字段 {@code retryInterval}，在 OCPP 2.0.1 GetLogRequest 协议对象中传递重试间隔。
     * 字段类型为 {@code Integer}，用于承载重试间隔。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer retryInterval;
}
