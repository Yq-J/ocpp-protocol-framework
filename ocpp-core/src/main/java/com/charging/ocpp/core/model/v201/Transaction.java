package com.charging.ocpp.core.model.v201;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 Transaction 复合协议实体类。
 * <p>
 * 描述 OCPP 2.0.1 交易上下文，包括交易标识、充电状态和停止原因。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Transaction {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 Transaction 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 交易标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code transactionId}，在 OCPP 2.0.1 Transaction 协议对象中传递交易标识。
     * 字段类型为 {@code String}，用于承载交易标识。该字段在官方规范中为必填字段。最大长度为 36 个字符。
     * </p>
     */
    private String transactionId;
    /**
     * 充电状态。
     * <p>
     * 用途：对应 OCPP 字段 {@code chargingState}，在 OCPP 2.0.1 Transaction 协议对象中传递充电状态。
     * 字段类型为 {@code String}，用于承载充电状态。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String chargingState;
    /**
     * 时间Spent充电。
     * <p>
     * 用途：对应 OCPP 字段 {@code timeSpentCharging}，在 OCPP 2.0.1 Transaction 协议对象中传递时间Spent充电。
     * 字段类型为 {@code Integer}，用于承载时间Spent充电。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer timeSpentCharging;
    /**
     * 停止原因。
     * <p>
     * 用途：对应 OCPP 字段 {@code stoppedReason}，在 OCPP 2.0.1 Transaction 协议对象中传递停止原因。
     * 字段类型为 {@code String}，用于承载停止原因。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String stoppedReason;
    /**
     * 远程启动标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code remoteStartId}，在 OCPP 2.0.1 Transaction 协议对象中传递远程启动标识。
     * 字段类型为 {@code Integer}，用于承载远程启动标识。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer remoteStartId;
}
