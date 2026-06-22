package com.charging.ocpp.core.model.v16;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 SampledValue2 复合协议实体类。
 * <p>
 * 描述停止交易补充数据中的单个计量采样值，结构与 MeterValues 采样值类似。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SampledValue2 {
    /**
     * 字段值。
     * <p>
     * 用途：对应 OCPP 字段 {@code value}，在 OCPP 1.6J SampledValue2 协议对象中传递字段值。
     * 字段类型为 {@code String}，用于承载字段值。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private String value;
    /**
     * 读数上下文。
     * <p>
     * 用途：对应 OCPP 字段 {@code context}，在 OCPP 1.6J SampledValue2 协议对象中传递读数上下文。
     * 字段类型为 {@code String}，用于承载读数上下文。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String context;
    /**
     * 数据格式。
     * <p>
     * 用途：对应 OCPP 字段 {@code format}，在 OCPP 1.6J SampledValue2 协议对象中传递数据格式。
     * 字段类型为 {@code String}，用于承载数据格式。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String format;
    /**
     * 计量量类型。
     * <p>
     * 用途：对应 OCPP 字段 {@code measurand}，在 OCPP 1.6J SampledValue2 协议对象中传递计量量类型。
     * 字段类型为 {@code String}，用于承载计量量类型。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String measurand;
    /**
     * 相位。
     * <p>
     * 用途：对应 OCPP 字段 {@code phase}，在 OCPP 1.6J SampledValue2 协议对象中传递相位。
     * 字段类型为 {@code String}，用于承载相位。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String phase;
    /**
     * 位置。
     * <p>
     * 用途：对应 OCPP 字段 {@code location}，在 OCPP 1.6J SampledValue2 协议对象中传递位置。
     * 字段类型为 {@code String}，用于承载位置。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String location;
    /**
     * 计量单位。
     * <p>
     * 用途：对应 OCPP 字段 {@code unit}，在 OCPP 1.6J SampledValue2 协议对象中传递计量单位。
     * 字段类型为 {@code String}，用于承载计量单位。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String unit;
}
