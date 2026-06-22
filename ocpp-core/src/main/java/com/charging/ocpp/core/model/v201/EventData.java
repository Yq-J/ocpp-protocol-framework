package com.charging.ocpp.core.model.v201;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 EventData 复合协议实体类。
 * <p>
 * 描述 NotifyEvent 上报的事件内容、触发方式、严重级别和关联组件变量。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EventData {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 EventData 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 事件标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code eventId}，在 OCPP 2.0.1 EventData 协议对象中传递事件标识。
     * 字段类型为 {@code Integer}，用于承载事件标识。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer eventId;
    /**
     * 事件时间。
     * <p>
     * 用途：对应 OCPP 字段 {@code timestamp}，在 OCPP 2.0.1 EventData 协议对象中传递事件时间。
     * 字段类型为 {@code String}，用于承载事件时间。该字段在官方规范中为必填字段。时间格式应符合 OCPP JSON Schema 的 date-time 约束。
     * </p>
     */
    private String timestamp;
    /**
     * 触发。
     * <p>
     * 用途：对应 OCPP 字段 {@code trigger}，在 OCPP 2.0.1 EventData 协议对象中传递触发。
     * 字段类型为 {@code String}，用于承载触发。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String trigger;
    /**
     * cause。
     * <p>
     * 用途：对应 OCPP 字段 {@code cause}，在 OCPP 2.0.1 EventData 协议对象中传递cause。
     * 字段类型为 {@code Integer}，用于承载cause。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer cause;
    /**
     * actual值。
     * <p>
     * 用途：对应 OCPP 字段 {@code actualValue}，在 OCPP 2.0.1 EventData 协议对象中传递actual值。
     * 字段类型为 {@code String}，用于承载actual值。该字段在官方规范中为必填字段。最大长度为 2500 个字符。
     * </p>
     */
    private String actualValue;
    /**
     * techCode。
     * <p>
     * 用途：对应 OCPP 字段 {@code techCode}，在 OCPP 2.0.1 EventData 协议对象中传递techCode。
     * 字段类型为 {@code String}，用于承载techCode。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 50 个字符。
     * </p>
     */
    private String techCode;
    /**
     * tech信息。
     * <p>
     * 用途：对应 OCPP 字段 {@code techInfo}，在 OCPP 2.0.1 EventData 协议对象中传递tech信息。
     * 字段类型为 {@code String}，用于承载tech信息。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 500 个字符。
     * </p>
     */
    private String techInfo;
    /**
     * cleared。
     * <p>
     * 用途：对应 OCPP 字段 {@code cleared}，在 OCPP 2.0.1 EventData 协议对象中传递cleared。
     * 字段类型为 {@code Boolean}，用于承载cleared。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Boolean cleared;
    /**
     * 交易标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code transactionId}，在 OCPP 2.0.1 EventData 协议对象中传递交易标识。
     * 字段类型为 {@code String}，用于承载交易标识。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 36 个字符。
     * </p>
     */
    private String transactionId;
    /**
     * 设备组件。
     * <p>
     * 用途：对应 OCPP 字段 {@code component}，在 OCPP 2.0.1 EventData 协议对象中传递设备组件。
     * 字段类型为 {@code Component}，用于承载设备组件。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Component component;
    /**
     * 变量监控标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code variableMonitoringId}，在 OCPP 2.0.1 EventData 协议对象中传递变量监控标识。
     * 字段类型为 {@code Integer}，用于承载变量监控标识。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer variableMonitoringId;
    /**
     * 事件Notification类型。
     * <p>
     * 用途：对应 OCPP 字段 {@code eventNotificationType}，在 OCPP 2.0.1 EventData 协议对象中传递事件Notification类型。
     * 字段类型为 {@code String}，用于承载事件Notification类型。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String eventNotificationType;
    /**
     * 设备变量。
     * <p>
     * 用途：对应 OCPP 字段 {@code variable}，在 OCPP 2.0.1 EventData 协议对象中传递设备变量。
     * 字段类型为 {@code Variable}，用于承载设备变量。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Variable variable;
}
