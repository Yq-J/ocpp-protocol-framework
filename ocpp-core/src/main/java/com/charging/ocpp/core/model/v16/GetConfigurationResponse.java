package com.charging.ocpp.core.model.v16;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 GetConfiguration 响应 payload 协议实体类。
 * <p>
 * 用途：承载 GetConfiguration 操作的响应字段，用于读取 OCPP 1.6 配置键场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GetConfigurationResponse {
    /**
     * 配置键值列表。
     * <p>
     * 用途：对应 OCPP 字段 {@code configurationKey}，在 OCPP 1.6J GetConfigurationResponse 协议对象中传递配置键值列表。
     * 字段类型为 {@code List<ConfigurationKey>}，用于承载一组配置键值列表。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private List<ConfigurationKey> configurationKey;
    /**
     * 未知配置键列表。
     * <p>
     * 用途：对应 OCPP 字段 {@code unknownKey}，在 OCPP 1.6J GetConfigurationResponse 协议对象中传递未知配置键列表。
     * 字段类型为 {@code List<String>}，用于承载一组未知配置键列表。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private List<String> unknownKey;
}
