package com.charging.ocpp.core.model.v16;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 MeterValues 响应 payload 协议实体类。
 * <p>
 * 用途：承载 MeterValues 操作的响应字段，用于上报电表采样值场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@Accessors(chain = true)
public class MeterValuesResponse {
}
