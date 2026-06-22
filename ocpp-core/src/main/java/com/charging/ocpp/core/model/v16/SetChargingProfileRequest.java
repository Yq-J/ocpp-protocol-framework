package com.charging.ocpp.core.model.v16;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 SetChargingProfile 请求 payload 协议实体类。
 * <p>
 * 用途：承载 SetChargingProfile 操作的请求字段，用于设置充电配置或充电计划场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SetChargingProfileRequest {
    /**
     * 连接器编号。
     * <p>
     * 用途：对应 OCPP 字段 {@code connectorId}，在 OCPP 1.6J SetChargingProfileRequest 协议对象中传递连接器编号。
     * 字段类型为 {@code Integer}，用于承载连接器编号。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer connectorId;
    /**
     * cs充电Profiles。
     * <p>
     * 用途：对应 OCPP 字段 {@code csChargingProfiles}，在 OCPP 1.6J SetChargingProfileRequest 协议对象中传递cs充电Profiles。
     * 字段类型为 {@code ChargingProfile}，用于承载cs充电Profiles。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private ChargingProfile csChargingProfiles;
}
