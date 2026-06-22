package com.charging.ocpp.core.model.v201;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 Component 复合协议实体类。
 * <p>
 * 描述设备模型中的物理或逻辑组件。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Component {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 Component 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * EVSE 信息。
     * <p>
     * 用途：对应 OCPP 字段 {@code evse}，在 OCPP 2.0.1 Component 协议对象中传递EVSE 信息。
     * 字段类型为 {@code EVSE}，用于承载EVSE 信息。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private EVSE evse;
    /**
     * 名称。
     * <p>
     * 用途：对应 OCPP 字段 {@code name}，在 OCPP 2.0.1 Component 协议对象中传递名称。
     * 字段类型为 {@code String}，用于承载名称。该字段在官方规范中为必填字段。最大长度为 50 个字符。
     * </p>
     */
    private String name;
    /**
     * 实例名称。
     * <p>
     * 用途：对应 OCPP 字段 {@code instance}，在 OCPP 2.0.1 Component 协议对象中传递实例名称。
     * 字段类型为 {@code String}，用于承载实例名称。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 50 个字符。
     * </p>
     */
    private String instance;
}
