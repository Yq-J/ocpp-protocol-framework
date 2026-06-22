package com.charging.ocpp.core.model.v16;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 Reset 请求 payload 协议实体类。
 * <p>
 * 用途：承载 Reset 操作的请求字段，用于请求充电站复位场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ResetRequest {
    /**
     * 类型。
     * <p>
     * 用途：对应 OCPP 字段 {@code type}，在 OCPP 1.6J ResetRequest 协议对象中传递类型。
     * 字段类型为 {@code String}，用于承载类型。该字段在官方规范中为必填字段。
     * 取值范围：
     * {@code Hard}：硬重启，立即断开并重启设备；
     * {@code Soft}：软重启，尽量平滑结束当前流程后重启。
     * </p>
     */
    private String type;
}
