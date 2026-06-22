package com.charging.ocpp.core.model.v201;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 TransactionEvent 响应 payload 协议实体类。
 * <p>
 * 用途：承载 TransactionEvent 操作的响应字段，用于上报 OCPP 2.0.1 交易生命周期事件场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TransactionEventResponse {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 TransactionEventResponse 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 累计费用。
     * <p>
     * 用途：对应 OCPP 字段 {@code totalCost}，在 OCPP 2.0.1 TransactionEventResponse 协议对象中传递累计费用。
     * 字段类型为 {@code BigDecimal}，用于承载累计费用。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private BigDecimal totalCost;
    /**
     * 充电优先级。
     * <p>
     * 用途：对应 OCPP 字段 {@code chargingPriority}，在 OCPP 2.0.1 TransactionEventResponse 协议对象中传递充电优先级。
     * 字段类型为 {@code Integer}，用于承载充电优先级。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer chargingPriority;
    /**
     * 身份令牌授权结果。
     * <p>
     * 用途：对应 OCPP 字段 {@code idTokenInfo}，在 OCPP 2.0.1 TransactionEventResponse 协议对象中传递身份令牌授权结果。
     * 字段类型为 {@code IdTokenInfo}，用于承载身份令牌授权结果。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private IdTokenInfo idTokenInfo;
    /**
     * updated个人消息。
     * <p>
     * 用途：对应 OCPP 字段 {@code updatedPersonalMessage}，在 OCPP 2.0.1 TransactionEventResponse 协议对象中传递updated个人消息。
     * 字段类型为 {@code MessageContent}，用于承载updated个人消息。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private MessageContent updatedPersonalMessage;
}
