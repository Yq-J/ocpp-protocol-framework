package com.charging.ocpp.core.model.v201;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 ChargingProfile 复合协议实体类。
 * <p>
 * 描述充电配置及其充电计划，用于下发、上报或筛选充电功率/电流限制策略。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ChargingProfile {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 ChargingProfile 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 对象标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code id}，在 OCPP 2.0.1 ChargingProfile 协议对象中传递对象标识。
     * 字段类型为 {@code Integer}，用于承载对象标识。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer id;
    /**
     * 配置优先级层级。
     * <p>
     * 用途：对应 OCPP 字段 {@code stackLevel}，在 OCPP 2.0.1 ChargingProfile 协议对象中传递配置优先级层级。
     * 字段类型为 {@code Integer}，用于承载配置优先级层级。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer stackLevel;
    /**
     * 充电配置用途。
     * <p>
     * 用途：对应 OCPP 字段 {@code chargingProfilePurpose}，在 OCPP 2.0.1 ChargingProfile 协议对象中传递充电配置用途。
     * 字段类型为 {@code String}，用于承载充电配置用途。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String chargingProfilePurpose;
    /**
     * 充电配置类型。
     * <p>
     * 用途：对应 OCPP 字段 {@code chargingProfileKind}，在 OCPP 2.0.1 ChargingProfile 协议对象中传递充电配置类型。
     * 字段类型为 {@code String}，用于承载充电配置类型。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String chargingProfileKind;
    /**
     * 重复周期类型。
     * <p>
     * 用途：对应 OCPP 字段 {@code recurrencyKind}，在 OCPP 2.0.1 ChargingProfile 协议对象中传递重复周期类型。
     * 字段类型为 {@code String}，用于承载重复周期类型。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String recurrencyKind;
    /**
     * 生效开始时间。
     * <p>
     * 用途：对应 OCPP 字段 {@code validFrom}，在 OCPP 2.0.1 ChargingProfile 协议对象中传递生效开始时间。
     * 字段类型为 {@code String}，用于承载生效开始时间。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。时间格式应符合 OCPP JSON Schema 的 date-time 约束。
     * </p>
     */
    private String validFrom;
    /**
     * 生效结束时间。
     * <p>
     * 用途：对应 OCPP 字段 {@code validTo}，在 OCPP 2.0.1 ChargingProfile 协议对象中传递生效结束时间。
     * 字段类型为 {@code String}，用于承载生效结束时间。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。时间格式应符合 OCPP JSON Schema 的 date-time 约束。
     * </p>
     */
    private String validTo;
    /**
     * 充电计划。
     * <p>
     * 用途：对应 OCPP 字段 {@code chargingSchedule}，在 OCPP 2.0.1 ChargingProfile 协议对象中传递充电计划。
     * 字段类型为 {@code List<ChargingSchedule>}，用于承载一组充电计划。该字段在官方规范中为必填字段。数组至少包含 1 个元素。数组最多包含 3 个元素。
     * </p>
     */
    private List<ChargingSchedule> chargingSchedule;
    /**
     * 交易标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code transactionId}，在 OCPP 2.0.1 ChargingProfile 协议对象中传递交易标识。
     * 字段类型为 {@code String}，用于承载交易标识。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 36 个字符。
     * </p>
     */
    private String transactionId;
}
