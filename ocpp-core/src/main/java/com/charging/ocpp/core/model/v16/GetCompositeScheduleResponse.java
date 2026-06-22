package com.charging.ocpp.core.model.v16;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 GetCompositeSchedule 响应 payload 协议实体类。
 * <p>
 * 用途：承载 GetCompositeSchedule 操作的响应字段，用于获取合成后的充电计划场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GetCompositeScheduleResponse {
    /**
     * 处理状态。
     * <p>
     * 用途：对应 OCPP 字段 {@code status}，在 OCPP 1.6J GetCompositeScheduleResponse 协议对象中传递处理状态。
     * 字段类型为 {@code String}，用于承载处理状态。该字段在官方规范中为必填字段。
     * 取值范围：
     * {@code Accepted}：已接受，处理成功；
     * {@code Rejected}：已拒绝，处理失败。
     * </p>
     */
    private String status;
    /**
     * 连接器编号。
     * <p>
     * 用途：对应 OCPP 字段 {@code connectorId}，在 OCPP 1.6J GetCompositeScheduleResponse 协议对象中传递连接器编号。
     * 字段类型为 {@code Integer}，用于承载连接器编号。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer connectorId;
    /**
     * 计划开始。
     * <p>
     * 用途：对应 OCPP 字段 {@code scheduleStart}，在 OCPP 1.6J GetCompositeScheduleResponse 协议对象中传递计划开始。
     * 字段类型为 {@code String}，用于承载计划开始。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。时间格式应符合 OCPP JSON Schema 的 date-time 约束。
     * </p>
     */
    private String scheduleStart;
    /**
     * 充电计划。
     * <p>
     * 用途：对应 OCPP 字段 {@code chargingSchedule}，在 OCPP 1.6J GetCompositeScheduleResponse 协议对象中传递充电计划。
     * 字段类型为 {@code ChargingSchedule}，用于承载充电计划。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private ChargingSchedule chargingSchedule;
}
