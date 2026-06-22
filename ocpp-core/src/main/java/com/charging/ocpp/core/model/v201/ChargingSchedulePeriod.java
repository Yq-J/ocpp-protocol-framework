package com.charging.ocpp.core.model.v201;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 ChargingSchedulePeriod 复合协议实体类。
 * <p>
 * 描述充电计划中的单个时间段及该时间段的限制值。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ChargingSchedulePeriod {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 ChargingSchedulePeriod 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 时段开始偏移。
     * <p>
     * 用途：对应 OCPP 字段 {@code startPeriod}，在 OCPP 2.0.1 ChargingSchedulePeriod 协议对象中传递时段开始偏移。
     * 字段类型为 {@code Integer}，用于承载时段开始偏移。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer startPeriod;
    /**
     * 功率或电流限制值。
     * <p>
     * 用途：对应 OCPP 字段 {@code limit}，在 OCPP 2.0.1 ChargingSchedulePeriod 协议对象中传递功率或电流限制值。
     * 字段类型为 {@code BigDecimal}，用于承载功率或电流限制值。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private BigDecimal limit;
    /**
     * 使用相数。
     * <p>
     * 用途：对应 OCPP 字段 {@code numberPhases}，在 OCPP 2.0.1 ChargingSchedulePeriod 协议对象中传递使用相数。
     * 字段类型为 {@code Integer}，用于承载使用相数。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer numberPhases;
    /**
     * 相位结束Use。
     * <p>
     * 用途：对应 OCPP 字段 {@code phaseToUse}，在 OCPP 2.0.1 ChargingSchedulePeriod 协议对象中传递相位结束Use。
     * 字段类型为 {@code Integer}，用于承载相位结束Use。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer phaseToUse;
}
