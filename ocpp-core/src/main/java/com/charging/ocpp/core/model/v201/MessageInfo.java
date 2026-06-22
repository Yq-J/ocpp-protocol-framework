package com.charging.ocpp.core.model.v201;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 MessageInfo 复合协议实体类。
 * <p>
 * 描述显示消息的时间、优先级、状态和内容。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MessageInfo {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 MessageInfo 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 显示屏信息。
     * <p>
     * 用途：对应 OCPP 字段 {@code display}，在 OCPP 2.0.1 MessageInfo 协议对象中传递显示屏信息。
     * 字段类型为 {@code Component}，用于承载显示屏信息。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Component display;
    /**
     * 对象标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code id}，在 OCPP 2.0.1 MessageInfo 协议对象中传递对象标识。
     * 字段类型为 {@code Integer}，用于承载对象标识。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer id;
    /**
     * 优先级。
     * <p>
     * 用途：对应 OCPP 字段 {@code priority}，在 OCPP 2.0.1 MessageInfo 协议对象中传递优先级。
     * 字段类型为 {@code String}，用于承载优先级。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String priority;
    /**
     * 状态。
     * <p>
     * 用途：对应 OCPP 字段 {@code state}，在 OCPP 2.0.1 MessageInfo 协议对象中传递状态。
     * 字段类型为 {@code String}，用于承载状态。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String state;
    /**
     * 开始日期时间。
     * <p>
     * 用途：对应 OCPP 字段 {@code startDateTime}，在 OCPP 2.0.1 MessageInfo 协议对象中传递开始日期时间。
     * 字段类型为 {@code String}，用于承载开始日期时间。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。时间格式应符合 OCPP JSON Schema 的 date-time 约束。
     * </p>
     */
    private String startDateTime;
    /**
     * end日期时间。
     * <p>
     * 用途：对应 OCPP 字段 {@code endDateTime}，在 OCPP 2.0.1 MessageInfo 协议对象中传递end日期时间。
     * 字段类型为 {@code String}，用于承载end日期时间。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。时间格式应符合 OCPP JSON Schema 的 date-time 约束。
     * </p>
     */
    private String endDateTime;
    /**
     * 交易标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code transactionId}，在 OCPP 2.0.1 MessageInfo 协议对象中传递交易标识。
     * 字段类型为 {@code String}，用于承载交易标识。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 36 个字符。
     * </p>
     */
    private String transactionId;
    /**
     * 消息。
     * <p>
     * 用途：对应 OCPP 字段 {@code message}，在 OCPP 2.0.1 MessageInfo 协议对象中传递消息。
     * 字段类型为 {@code MessageContent}，用于承载消息。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private MessageContent message;
}
