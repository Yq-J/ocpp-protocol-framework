package com.charging.ocpp.core.model.v201;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 StatusNotification 请求 payload 协议实体类。
 * <p>
 * 用途：承载 StatusNotification 操作的请求字段，用于上报连接器或 EVSE 状态场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StatusNotificationRequest {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 StatusNotificationRequest 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 事件时间。
     * <p>
     * 用途：对应 OCPP 字段 {@code timestamp}，在 OCPP 2.0.1 StatusNotificationRequest 协议对象中传递事件时间。
     * 字段类型为 {@code String}，用于承载事件时间。该字段在官方规范中为必填字段。时间格式应符合 OCPP JSON Schema 的 date-time 约束。
     * </p>
     */
    private String timestamp;
    /**
     * 连接器状态。
     * <p>
     * 用途：对应 OCPP 字段 {@code connectorStatus}，在 OCPP 2.0.1 StatusNotificationRequest 协议对象中传递连接器状态。
     * 字段类型为 {@code String}，用于承载连接器状态。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String connectorStatus;
    /**
     * EVSE 编号。
     * <p>
     * 用途：对应 OCPP 字段 {@code evseId}，在 OCPP 2.0.1 StatusNotificationRequest 协议对象中传递EVSE 编号。
     * 字段类型为 {@code Integer}，用于承载EVSE 编号。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer evseId;
    /**
     * 连接器编号。
     * <p>
     * 用途：对应 OCPP 字段 {@code connectorId}，在 OCPP 2.0.1 StatusNotificationRequest 协议对象中传递连接器编号。
     * 字段类型为 {@code Integer}，用于承载连接器编号。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer connectorId;
}
