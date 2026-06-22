package com.charging.ocpp.core.model.v16;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 SendLocalList 响应 payload 协议实体类。
 * <p>
 * 用途：承载 SendLocalList 操作的响应字段，用于下发本地授权列表场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SendLocalListResponse {
    /**
     * 处理状态。
     * <p>
     * 用途：对应 OCPP 字段 {@code status}，在 OCPP 1.6J SendLocalListResponse 协议对象中传递处理状态。
     * 字段类型为 {@code String}，用于承载处理状态。该字段在官方规范中为必填字段。
     * 取值范围：
     * {@code Accepted}：已接受，处理成功；
     * {@code Failed}：失败；
     * {@code NotSupported}：不支持该操作或能力；
     * {@code VersionMismatch}：版本不匹配。
     * </p>
     */
    private String status;
}
