package com.charging.ocpp.core.model.v201;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 TransactionEvent 请求 payload 协议实体类。
 * <p>
 * 用途：承载 TransactionEvent 操作的请求字段，用于上报 OCPP 2.0.1 交易生命周期事件场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TransactionEventRequest {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 TransactionEventRequest 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 事件类型。
     * <p>
     * 用途：对应 OCPP 字段 {@code eventType}，在 OCPP 2.0.1 TransactionEventRequest 协议对象中传递事件类型。
     * 字段类型为 {@code String}，用于承载事件类型。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String eventType;
    /**
     * 电表采样值列表。
     * <p>
     * 用途：对应 OCPP 字段 {@code meterValue}，在 OCPP 2.0.1 TransactionEventRequest 协议对象中传递电表采样值列表。
     * 字段类型为 {@code List<MeterValue>}，用于承载一组电表采样值列表。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。数组至少包含 1 个元素。
     * </p>
     */
    private List<MeterValue> meterValue;
    /**
     * 事件时间。
     * <p>
     * 用途：对应 OCPP 字段 {@code timestamp}，在 OCPP 2.0.1 TransactionEventRequest 协议对象中传递事件时间。
     * 字段类型为 {@code String}，用于承载事件时间。该字段在官方规范中为必填字段。时间格式应符合 OCPP JSON Schema 的 date-time 约束。
     * </p>
     */
    private String timestamp;
    /**
     * 触发原因。
     * <p>
     * 用途：对应 OCPP 字段 {@code triggerReason}，在 OCPP 2.0.1 TransactionEventRequest 协议对象中传递触发原因。
     * 字段类型为 {@code String}，用于承载触发原因。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String triggerReason;
    /**
     * 事件序号。
     * <p>
     * 用途：对应 OCPP 字段 {@code seqNo}，在 OCPP 2.0.1 TransactionEventRequest 协议对象中传递事件序号。
     * 字段类型为 {@code Integer}，用于承载事件序号。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer seqNo;
    /**
     * 离线标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code offline}，在 OCPP 2.0.1 TransactionEventRequest 协议对象中传递离线标识。
     * 字段类型为 {@code Boolean}，用于承载离线标识。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Boolean offline;
    /**
     * 实际使用相数。
     * <p>
     * 用途：对应 OCPP 字段 {@code numberOfPhasesUsed}，在 OCPP 2.0.1 TransactionEventRequest 协议对象中传递实际使用相数。
     * 字段类型为 {@code Integer}，用于承载实际使用相数。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer numberOfPhasesUsed;
    /**
     * 线缆最大电流。
     * <p>
     * 用途：对应 OCPP 字段 {@code cableMaxCurrent}，在 OCPP 2.0.1 TransactionEventRequest 协议对象中传递线缆最大电流。
     * 字段类型为 {@code Integer}，用于承载线缆最大电流。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer cableMaxCurrent;
    /**
     * 预约标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code reservationId}，在 OCPP 2.0.1 TransactionEventRequest 协议对象中传递预约标识。
     * 字段类型为 {@code Integer}，用于承载预约标识。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer reservationId;
    /**
     * 交易上下文信息。
     * <p>
     * 用途：对应 OCPP 字段 {@code transactionInfo}，在 OCPP 2.0.1 TransactionEventRequest 协议对象中传递交易上下文信息。
     * 字段类型为 {@code Transaction}，用于承载交易上下文信息。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Transaction transactionInfo;
    /**
     * EVSE 信息。
     * <p>
     * 用途：对应 OCPP 字段 {@code evse}，在 OCPP 2.0.1 TransactionEventRequest 协议对象中传递EVSE 信息。
     * 字段类型为 {@code EVSE}，用于承载EVSE 信息。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private EVSE evse;
    /**
     * 身份令牌。
     * <p>
     * 用途：对应 OCPP 字段 {@code idToken}，在 OCPP 2.0.1 TransactionEventRequest 协议对象中传递身份令牌。
     * 字段类型为 {@code IdToken}，用于承载身份令牌。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private IdToken idToken;
}
