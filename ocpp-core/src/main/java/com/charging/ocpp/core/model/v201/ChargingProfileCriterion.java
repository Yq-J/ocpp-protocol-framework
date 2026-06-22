package com.charging.ocpp.core.model.v201;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 ChargingProfileCriterion 复合协议实体类。
 * <p>
 * 用途：描述充电配置条件相关的结构化数据，作为 OCPP 2.0.1 请求或响应 payload 中的嵌套对象复用。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ChargingProfileCriterion {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 ChargingProfileCriterion 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 充电配置用途。
     * <p>
     * 用途：对应 OCPP 字段 {@code chargingProfilePurpose}，在 OCPP 2.0.1 ChargingProfileCriterion 协议对象中传递充电配置用途。
     * 字段类型为 {@code String}，用于承载充电配置用途。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String chargingProfilePurpose;
    /**
     * 配置优先级层级。
     * <p>
     * 用途：对应 OCPP 字段 {@code stackLevel}，在 OCPP 2.0.1 ChargingProfileCriterion 协议对象中传递配置优先级层级。
     * 字段类型为 {@code Integer}，用于承载配置优先级层级。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer stackLevel;
    /**
     * 充电配置标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code chargingProfileId}，在 OCPP 2.0.1 ChargingProfileCriterion 协议对象中传递充电配置标识。
     * 字段类型为 {@code List<Integer>}，用于承载一组充电配置标识。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。数组至少包含 1 个元素。
     * </p>
     */
    private List<Integer> chargingProfileId;
    /**
     * 充电限制Source。
     * <p>
     * 用途：对应 OCPP 字段 {@code chargingLimitSource}，在 OCPP 2.0.1 ChargingProfileCriterion 协议对象中传递充电限制Source。
     * 字段类型为 {@code List<String>}，用于承载一组充电限制Source。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。数组至少包含 1 个元素。数组最多包含 4 个元素。
     * </p>
     */
    private List<String> chargingLimitSource;
}
