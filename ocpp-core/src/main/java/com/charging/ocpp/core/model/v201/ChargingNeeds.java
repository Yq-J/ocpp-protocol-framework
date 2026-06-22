package com.charging.ocpp.core.model.v201;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 ChargingNeeds 复合协议实体类。
 * <p>
 * 用途：描述充电Needs相关的结构化数据，作为 OCPP 2.0.1 请求或响应 payload 中的嵌套对象复用。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ChargingNeeds {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 ChargingNeeds 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * ac充电Parameters。
     * <p>
     * 用途：对应 OCPP 字段 {@code acChargingParameters}，在 OCPP 2.0.1 ChargingNeeds 协议对象中传递ac充电Parameters。
     * 字段类型为 {@code ACChargingParameters}，用于承载ac充电Parameters。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private ACChargingParameters acChargingParameters;
    /**
     * dc充电Parameters。
     * <p>
     * 用途：对应 OCPP 字段 {@code dcChargingParameters}，在 OCPP 2.0.1 ChargingNeeds 协议对象中传递dc充电Parameters。
     * 字段类型为 {@code DCChargingParameters}，用于承载dc充电Parameters。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private DCChargingParameters dcChargingParameters;
    /**
     * requested电量Transfer。
     * <p>
     * 用途：对应 OCPP 字段 {@code requestedEnergyTransfer}，在 OCPP 2.0.1 ChargingNeeds 协议对象中传递requested电量Transfer。
     * 字段类型为 {@code String}，用于承载requested电量Transfer。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String requestedEnergyTransfer;
    /**
     * departure时间。
     * <p>
     * 用途：对应 OCPP 字段 {@code departureTime}，在 OCPP 2.0.1 ChargingNeeds 协议对象中传递departure时间。
     * 字段类型为 {@code String}，用于承载departure时间。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。时间格式应符合 OCPP JSON Schema 的 date-time 约束。
     * </p>
     */
    private String departureTime;
}
