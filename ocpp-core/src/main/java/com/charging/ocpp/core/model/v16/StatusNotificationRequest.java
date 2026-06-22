package com.charging.ocpp.core.model.v16;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 StatusNotification 请求 payload 协议实体类。
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
     * 连接器编号。
     * <p>
     * 用途：对应 OCPP 字段 {@code connectorId}，在 OCPP 1.6J StatusNotificationRequest 协议对象中传递连接器编号。
     * 字段类型为 {@code Integer}，用于承载连接器编号。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer connectorId;
    /**
     * errorCode。
     * <p>
     * 用途：对应 OCPP 字段 {@code errorCode}，在 OCPP 1.6J StatusNotificationRequest 协议对象中传递errorCode。
     * 字段类型为 {@code String}，用于承载errorCode。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String errorCode;
    /**
     * 信息。
     * <p>
     * 用途：对应 OCPP 字段 {@code info}，在 OCPP 1.6J StatusNotificationRequest 协议对象中传递信息。
     * 字段类型为 {@code String}，用于承载信息。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 50 个字符。
     * </p>
     */
    private String info;
    /**
     * 处理状态。
     * <p>
     * 用途：对应 OCPP 字段 {@code status}，在 OCPP 1.6J StatusNotificationRequest 协议对象中传递处理状态。
     * 字段类型为 {@code String}，用于承载处理状态。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String status;
    /**
     * 事件时间。
     * <p>
     * 用途：对应 OCPP 字段 {@code timestamp}，在 OCPP 1.6J StatusNotificationRequest 协议对象中传递事件时间。
     * 字段类型为 {@code String}，用于承载事件时间。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。时间格式应符合 OCPP JSON Schema 的 date-time 约束。
     * </p>
     */
    private String timestamp;
    /**
     * 厂商标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code vendorId}，在 OCPP 1.6J StatusNotificationRequest 协议对象中传递厂商标识。
     * 字段类型为 {@code String}，用于承载厂商标识。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 255 个字符。
     * </p>
     */
    private String vendorId;
    /**
     * vendorErrorCode。
     * <p>
     * 用途：对应 OCPP 字段 {@code vendorErrorCode}，在 OCPP 1.6J StatusNotificationRequest 协议对象中传递vendorErrorCode。
     * 字段类型为 {@code String}，用于承载vendorErrorCode。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 50 个字符。
     * </p>
     */
    private String vendorErrorCode;
}
