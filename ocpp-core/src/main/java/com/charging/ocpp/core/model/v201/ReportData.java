package com.charging.ocpp.core.model.v201;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 ReportData 复合协议实体类。
 * <p>
 * 描述设备模型报告中的组件、变量、属性和特征数据。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReportData {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 ReportData 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 设备组件。
     * <p>
     * 用途：对应 OCPP 字段 {@code component}，在 OCPP 2.0.1 ReportData 协议对象中传递设备组件。
     * 字段类型为 {@code Component}，用于承载设备组件。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Component component;
    /**
     * 设备变量。
     * <p>
     * 用途：对应 OCPP 字段 {@code variable}，在 OCPP 2.0.1 ReportData 协议对象中传递设备变量。
     * 字段类型为 {@code Variable}，用于承载设备变量。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Variable variable;
    /**
     * 变量属性。
     * <p>
     * 用途：对应 OCPP 字段 {@code variableAttribute}，在 OCPP 2.0.1 ReportData 协议对象中传递变量属性。
     * 字段类型为 {@code List<VariableAttribute>}，用于承载一组变量属性。该字段在官方规范中为必填字段。数组至少包含 1 个元素。数组最多包含 4 个元素。
     * </p>
     */
    private List<VariableAttribute> variableAttribute;
    /**
     * 变量特征。
     * <p>
     * 用途：对应 OCPP 字段 {@code variableCharacteristics}，在 OCPP 2.0.1 ReportData 协议对象中传递变量特征。
     * 字段类型为 {@code VariableCharacteristics}，用于承载变量特征。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private VariableCharacteristics variableCharacteristics;
}
