package com.charging.ocpp.core.model.v201;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 VariableCharacteristics 复合协议实体类。
 * <p>
 * 描述变量的数据类型、单位、取值范围、枚举值和监控能力。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class VariableCharacteristics {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 VariableCharacteristics 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 计量单位。
     * <p>
     * 用途：对应 OCPP 字段 {@code unit}，在 OCPP 2.0.1 VariableCharacteristics 协议对象中传递计量单位。
     * 字段类型为 {@code String}，用于承载计量单位。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 16 个字符。
     * </p>
     */
    private String unit;
    /**
     * 数据类型。
     * <p>
     * 用途：对应 OCPP 字段 {@code dataType}，在 OCPP 2.0.1 VariableCharacteristics 协议对象中传递数据类型。
     * 字段类型为 {@code String}，用于承载数据类型。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String dataType;
    /**
     * 最小限制。
     * <p>
     * 用途：对应 OCPP 字段 {@code minLimit}，在 OCPP 2.0.1 VariableCharacteristics 协议对象中传递最小限制。
     * 字段类型为 {@code BigDecimal}，用于承载最小限制。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private BigDecimal minLimit;
    /**
     * 最大限制。
     * <p>
     * 用途：对应 OCPP 字段 {@code maxLimit}，在 OCPP 2.0.1 VariableCharacteristics 协议对象中传递最大限制。
     * 字段类型为 {@code BigDecimal}，用于承载最大限制。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private BigDecimal maxLimit;
    /**
     * 值列表。
     * <p>
     * 用途：对应 OCPP 字段 {@code valuesList}，在 OCPP 2.0.1 VariableCharacteristics 协议对象中传递值列表。
     * 字段类型为 {@code String}，用于承载值列表。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 1000 个字符。
     * </p>
     */
    private String valuesList;
    /**
     * 是否支持监控。
     * <p>
     * 用途：对应 OCPP 字段 {@code supportsMonitoring}，在 OCPP 2.0.1 VariableCharacteristics 协议对象中传递是否支持监控。
     * 字段类型为 {@code Boolean}，用于承载是否支持监控。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Boolean supportsMonitoring;
}
