package com.charging.ocpp.core.model.v201;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 NotifyReport 请求 payload 协议实体类。
 * <p>
 * 用途：承载 NotifyReport 操作的请求字段，用于上报设备模型报告数据场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class NotifyReportRequest {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 NotifyReportRequest 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 请求标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code requestId}，在 OCPP 2.0.1 NotifyReportRequest 协议对象中传递请求标识。
     * 字段类型为 {@code Integer}，用于承载请求标识。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer requestId;
    /**
     * generatedAt。
     * <p>
     * 用途：对应 OCPP 字段 {@code generatedAt}，在 OCPP 2.0.1 NotifyReportRequest 协议对象中传递generatedAt。
     * 字段类型为 {@code String}，用于承载generatedAt。该字段在官方规范中为必填字段。时间格式应符合 OCPP JSON Schema 的 date-time 约束。
     * </p>
     */
    private String generatedAt;
    /**
     * 报告数据列表。
     * <p>
     * 用途：对应 OCPP 字段 {@code reportData}，在 OCPP 2.0.1 NotifyReportRequest 协议对象中传递报告数据列表。
     * 字段类型为 {@code List<ReportData>}，用于承载一组报告数据列表。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。数组至少包含 1 个元素。
     * </p>
     */
    private List<ReportData> reportData;
    /**
     * tbc。
     * <p>
     * 用途：对应 OCPP 字段 {@code tbc}，在 OCPP 2.0.1 NotifyReportRequest 协议对象中传递tbc。
     * 字段类型为 {@code Boolean}，用于承载tbc。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Boolean tbc;
    /**
     * 事件序号。
     * <p>
     * 用途：对应 OCPP 字段 {@code seqNo}，在 OCPP 2.0.1 NotifyReportRequest 协议对象中传递事件序号。
     * 字段类型为 {@code Integer}，用于承载事件序号。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer seqNo;
}
