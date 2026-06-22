package com.charging.ocpp.core.model.v201;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 GetVariableResult 复合协议实体类。
 * <p>
 * 用途：描述Get变量结果相关的结构化数据，作为 OCPP 2.0.1 请求或响应 payload 中的嵌套对象复用。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GetVariableResult {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 GetVariableResult 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 属性状态信息。
     * <p>
     * 用途：对应 OCPP 字段 {@code attributeStatusInfo}，在 OCPP 2.0.1 GetVariableResult 协议对象中传递属性状态信息。
     * 字段类型为 {@code StatusInfo}，用于承载属性状态信息。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private StatusInfo attributeStatusInfo;
    /**
     * 属性状态。
     * <p>
     * 用途：对应 OCPP 字段 {@code attributeStatus}，在 OCPP 2.0.1 GetVariableResult 协议对象中传递属性状态。
     * 字段类型为 {@code String}，用于承载属性状态。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String attributeStatus;
    /**
     * 属性类型。
     * <p>
     * 用途：对应 OCPP 字段 {@code attributeType}，在 OCPP 2.0.1 GetVariableResult 协议对象中传递属性类型。
     * 字段类型为 {@code String}，用于承载属性类型。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String attributeType;
    /**
     * 属性值。
     * <p>
     * 用途：对应 OCPP 字段 {@code attributeValue}，在 OCPP 2.0.1 GetVariableResult 协议对象中传递属性值。
     * 字段类型为 {@code String}，用于承载属性值。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 2500 个字符。
     * </p>
     */
    private String attributeValue;
    /**
     * 设备组件。
     * <p>
     * 用途：对应 OCPP 字段 {@code component}，在 OCPP 2.0.1 GetVariableResult 协议对象中传递设备组件。
     * 字段类型为 {@code Component}，用于承载设备组件。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Component component;
    /**
     * 设备变量。
     * <p>
     * 用途：对应 OCPP 字段 {@code variable}，在 OCPP 2.0.1 GetVariableResult 协议对象中传递设备变量。
     * 字段类型为 {@code Variable}，用于承载设备变量。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Variable variable;
}
