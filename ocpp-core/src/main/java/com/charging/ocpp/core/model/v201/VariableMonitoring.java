package com.charging.ocpp.core.model.v201;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 VariableMonitoring 复合协议实体类。
 * <p>
 * 描述变量监控规则及阈值配置。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class VariableMonitoring {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 VariableMonitoring 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 对象标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code id}，在 OCPP 2.0.1 VariableMonitoring 协议对象中传递对象标识。
     * 字段类型为 {@code Integer}，用于承载对象标识。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer id;
    /**
     * 交易。
     * <p>
     * 用途：对应 OCPP 字段 {@code transaction}，在 OCPP 2.0.1 VariableMonitoring 协议对象中传递交易。
     * 字段类型为 {@code Boolean}，用于承载交易。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Boolean transaction;
    /**
     * 字段值。
     * <p>
     * 用途：对应 OCPP 字段 {@code value}，在 OCPP 2.0.1 VariableMonitoring 协议对象中传递字段值。
     * 字段类型为 {@code BigDecimal}，用于承载字段值。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private BigDecimal value;
    /**
     * 类型。
     * <p>
     * 用途：对应 OCPP 字段 {@code type}，在 OCPP 2.0.1 VariableMonitoring 协议对象中传递类型。
     * 字段类型为 {@code String}，用于承载类型。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String type;
    /**
     * severity。
     * <p>
     * 用途：对应 OCPP 字段 {@code severity}，在 OCPP 2.0.1 VariableMonitoring 协议对象中传递severity。
     * 字段类型为 {@code Integer}，用于承载severity。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer severity;
}
