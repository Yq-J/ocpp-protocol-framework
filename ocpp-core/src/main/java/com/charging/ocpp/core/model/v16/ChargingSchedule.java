package com.charging.ocpp.core.model.v16;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 ChargingSchedule 复合协议实体类。
 * <p>
 * 描述一组按时间段生效的充电功率或电流计划。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ChargingSchedule {
    /**
     * 持续时间。
     * <p>
     * 用途：对应 OCPP 字段 {@code duration}，在 OCPP 1.6J ChargingSchedule 协议对象中传递持续时间。
     * 字段类型为 {@code Integer}，用于承载持续时间。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer duration;
    /**
     * 计划开始时间。
     * <p>
     * 用途：对应 OCPP 字段 {@code startSchedule}，在 OCPP 1.6J ChargingSchedule 协议对象中传递计划开始时间。
     * 字段类型为 {@code String}，用于承载计划开始时间。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。时间格式应符合 OCPP JSON Schema 的 date-time 约束。
     * </p>
     */
    private String startSchedule;
    /**
     * 充电速率单位。
     * <p>
     * 用途：对应 OCPP 字段 {@code chargingRateUnit}，在 OCPP 1.6J ChargingSchedule 协议对象中传递充电速率单位。
     * 字段类型为 {@code String}，用于承载充电速率单位。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String chargingRateUnit;
    /**
     * 充电计划时段。
     * <p>
     * 用途：对应 OCPP 字段 {@code chargingSchedulePeriod}，在 OCPP 1.6J ChargingSchedule 协议对象中传递充电计划时段。
     * 字段类型为 {@code List<ChargingSchedulePeriod>}，用于承载一组充电计划时段。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private List<ChargingSchedulePeriod> chargingSchedulePeriod;
    /**
     * 最低充电速率。
     * <p>
     * 用途：对应 OCPP 字段 {@code minChargingRate}，在 OCPP 1.6J ChargingSchedule 协议对象中传递最低充电速率。
     * 字段类型为 {@code BigDecimal}，用于承载最低充电速率。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。数值精度按 multipleOf 0.1 校验。
     * </p>
     */
    private BigDecimal minChargingRate;
}
