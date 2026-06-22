package com.charging.ocpp.core.model.v201;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 DCChargingParameters 复合协议实体类。
 * <p>
 * 用途：描述DC充电Parameters相关的结构化数据，作为 OCPP 2.0.1 请求或响应 payload 中的嵌套对象复用。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DCChargingParameters {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 DCChargingParameters 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * EV最大电流。
     * <p>
     * 用途：对应 OCPP 字段 {@code evMaxCurrent}，在 OCPP 2.0.1 DCChargingParameters 协议对象中传递EV最大电流。
     * 字段类型为 {@code Integer}，用于承载EV最大电流。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer evMaxCurrent;
    /**
     * EV最大电压。
     * <p>
     * 用途：对应 OCPP 字段 {@code evMaxVoltage}，在 OCPP 2.0.1 DCChargingParameters 协议对象中传递EV最大电压。
     * 字段类型为 {@code Integer}，用于承载EV最大电压。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer evMaxVoltage;
    /**
     * 电量数量。
     * <p>
     * 用途：对应 OCPP 字段 {@code energyAmount}，在 OCPP 2.0.1 DCChargingParameters 协议对象中传递电量数量。
     * 字段类型为 {@code Integer}，用于承载电量数量。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer energyAmount;
    /**
     * EV最大功率。
     * <p>
     * 用途：对应 OCPP 字段 {@code evMaxPower}，在 OCPP 2.0.1 DCChargingParameters 协议对象中传递EV最大功率。
     * 字段类型为 {@code Integer}，用于承载EV最大功率。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer evMaxPower;
    /**
     * 状态Of充电。
     * <p>
     * 用途：对应 OCPP 字段 {@code stateOfCharge}，在 OCPP 2.0.1 DCChargingParameters 协议对象中传递状态Of充电。
     * 字段类型为 {@code Integer}，用于承载状态Of充电。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。数值下限为 0。数值上限为 100。
     * </p>
     */
    private Integer stateOfCharge;
    /**
     * EV电量Capacity。
     * <p>
     * 用途：对应 OCPP 字段 {@code evEnergyCapacity}，在 OCPP 2.0.1 DCChargingParameters 协议对象中传递EV电量Capacity。
     * 字段类型为 {@code Integer}，用于承载EV电量Capacity。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer evEnergyCapacity;
    /**
     * fullSoC。
     * <p>
     * 用途：对应 OCPP 字段 {@code fullSoC}，在 OCPP 2.0.1 DCChargingParameters 协议对象中传递fullSoC。
     * 字段类型为 {@code Integer}，用于承载fullSoC。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。数值下限为 0。数值上限为 100。
     * </p>
     */
    private Integer fullSoC;
    /**
     * bulkSoC。
     * <p>
     * 用途：对应 OCPP 字段 {@code bulkSoC}，在 OCPP 2.0.1 DCChargingParameters 协议对象中传递bulkSoC。
     * 字段类型为 {@code Integer}，用于承载bulkSoC。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。数值下限为 0。数值上限为 100。
     * </p>
     */
    private Integer bulkSoC;
}
