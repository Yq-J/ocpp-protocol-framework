package com.charging.ocpp.core.model.v16;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 ConfigurationKey 复合协议实体类。
 * <p>
 * 用途：描述配置密钥相关的结构化数据，作为 OCPP 1.6J 请求或响应 payload 中的嵌套对象复用。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ConfigurationKey {
    /**
     * 配置键。
     * <p>
     * 用途：对应 OCPP 字段 {@code key}，在 OCPP 1.6J ConfigurationKey 协议对象中传递配置键。
     * 字段类型为 {@code String}，用于承载配置键。该字段在官方规范中为必填字段。最大长度为 50 个字符。
     * </p>
     */
    private String key;
    /**
     * 是否只读。
     * <p>
     * 用途：对应 OCPP 字段 {@code readonly}，在 OCPP 1.6J ConfigurationKey 协议对象中传递是否只读。
     * 字段类型为 {@code Boolean}，用于承载是否只读。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Boolean readonly;
    /**
     * 字段值。
     * <p>
     * 用途：对应 OCPP 字段 {@code value}，在 OCPP 1.6J ConfigurationKey 协议对象中传递字段值。
     * 字段类型为 {@code String}，用于承载字段值。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 500 个字符。
     * </p>
     */
    private String value;
}
