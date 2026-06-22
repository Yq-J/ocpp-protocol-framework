package com.charging.ocpp.core.model.v16;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 GetDiagnostics 请求 payload 协议实体类。
 * <p>
 * 用途：承载 GetDiagnostics 操作的请求字段，用于请求上传诊断文件场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GetDiagnosticsRequest {
    /**
     * 位置。
     * <p>
     * 用途：对应 OCPP 字段 {@code location}，在 OCPP 1.6J GetDiagnosticsRequest 协议对象中传递位置。
     * 字段类型为 {@code String}，用于承载位置。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private String location;
    /**
     * 重试次数。
     * <p>
     * 用途：对应 OCPP 字段 {@code retries}，在 OCPP 1.6J GetDiagnosticsRequest 协议对象中传递重试次数。
     * 字段类型为 {@code Integer}，用于承载重试次数。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer retries;
    /**
     * 重试间隔。
     * <p>
     * 用途：对应 OCPP 字段 {@code retryInterval}，在 OCPP 1.6J GetDiagnosticsRequest 协议对象中传递重试间隔。
     * 字段类型为 {@code Integer}，用于承载重试间隔。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer retryInterval;
    /**
     * 开始时间。
     * <p>
     * 用途：对应 OCPP 字段 {@code startTime}，在 OCPP 1.6J GetDiagnosticsRequest 协议对象中传递开始时间。
     * 字段类型为 {@code String}，用于承载开始时间。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。时间格式应符合 OCPP JSON Schema 的 date-time 约束。
     * </p>
     */
    private String startTime;
    /**
     * 停止时间。
     * <p>
     * 用途：对应 OCPP 字段 {@code stopTime}，在 OCPP 1.6J GetDiagnosticsRequest 协议对象中传递停止时间。
     * 字段类型为 {@code String}，用于承载停止时间。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。时间格式应符合 OCPP JSON Schema 的 date-time 约束。
     * </p>
     */
    private String stopTime;
}
