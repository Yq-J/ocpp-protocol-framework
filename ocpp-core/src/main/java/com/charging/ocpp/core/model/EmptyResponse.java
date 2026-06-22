package com.charging.ocpp.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 无业务字段的空响应协议实体类。
 * <p>
 * 用途：用于 OCPP 中响应 payload 为空对象的场景，例如只需要确认收到报文、无需返回业务字段的 CALLRESULT。
 * 该类只表达协议层的空 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 如果某个 Action 后续需要返回扩展字段，应新增明确字段的响应 DTO 或继承该类后显式建模。
 * </p>
 */
@Data
@NoArgsConstructor
public class EmptyResponse {
}
