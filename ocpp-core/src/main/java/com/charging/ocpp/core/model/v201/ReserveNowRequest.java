package com.charging.ocpp.core.model.v201;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 ReserveNow 请求 payload 协议实体类。
 * <p>
 * 用途：承载 ReserveNow 操作的请求字段，用于创建充电预约场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReserveNowRequest {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 ReserveNowRequest 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 对象标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code id}，在 OCPP 2.0.1 ReserveNowRequest 协议对象中传递对象标识。
     * 字段类型为 {@code Integer}，用于承载对象标识。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer id;
    /**
     * expiry日期时间。
     * <p>
     * 用途：对应 OCPP 字段 {@code expiryDateTime}，在 OCPP 2.0.1 ReserveNowRequest 协议对象中传递expiry日期时间。
     * 字段类型为 {@code String}，用于承载expiry日期时间。该字段在官方规范中为必填字段。时间格式应符合 OCPP JSON Schema 的 date-time 约束。
     * </p>
     */
    private String expiryDateTime;
    /**
     * 连接器类型。
     * <p>
     * 用途：对应 OCPP 字段 {@code connectorType}，在 OCPP 2.0.1 ReserveNowRequest 协议对象中传递连接器类型。
     * 字段类型为 {@code String}，用于承载连接器类型。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String connectorType;
    /**
     * 身份令牌。
     * <p>
     * 用途：对应 OCPP 字段 {@code idToken}，在 OCPP 2.0.1 ReserveNowRequest 协议对象中传递身份令牌。
     * 字段类型为 {@code IdToken}，用于承载身份令牌。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private IdToken idToken;
    /**
     * EVSE 编号。
     * <p>
     * 用途：对应 OCPP 字段 {@code evseId}，在 OCPP 2.0.1 ReserveNowRequest 协议对象中传递EVSE 编号。
     * 字段类型为 {@code Integer}，用于承载EVSE 编号。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer evseId;
    /**
     * 分组标识令牌。
     * <p>
     * 用途：对应 OCPP 字段 {@code groupIdToken}，在 OCPP 2.0.1 ReserveNowRequest 协议对象中传递分组标识令牌。
     * 字段类型为 {@code IdToken}，用于承载分组标识令牌。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private IdToken groupIdToken;
}
