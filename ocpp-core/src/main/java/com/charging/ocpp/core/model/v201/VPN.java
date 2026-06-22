package com.charging.ocpp.core.model.v201;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 VPN 复合协议实体类。
 * <p>
 * 描述 VPN 连接配置。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class VPN {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 VPN 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 服务器地址。
     * <p>
     * 用途：对应 OCPP 字段 {@code server}，在 OCPP 2.0.1 VPN 协议对象中传递服务器地址。
     * 字段类型为 {@code String}，用于承载服务器地址。该字段在官方规范中为必填字段。最大长度为 512 个字符。
     * </p>
     */
    private String server;
    /**
     * 用户名。
     * <p>
     * 用途：对应 OCPP 字段 {@code user}，在 OCPP 2.0.1 VPN 协议对象中传递用户名。
     * 字段类型为 {@code String}，用于承载用户名。该字段在官方规范中为必填字段。最大长度为 20 个字符。
     * </p>
     */
    private String user;
    /**
     * 组名。
     * <p>
     * 用途：对应 OCPP 字段 {@code group}，在 OCPP 2.0.1 VPN 协议对象中传递组名。
     * 字段类型为 {@code String}，用于承载组名。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 20 个字符。
     * </p>
     */
    private String group;
    /**
     * 密码。
     * <p>
     * 用途：对应 OCPP 字段 {@code password}，在 OCPP 2.0.1 VPN 协议对象中传递密码。
     * 字段类型为 {@code String}，用于承载密码。该字段在官方规范中为必填字段。最大长度为 20 个字符。
     * </p>
     */
    private String password;
    /**
     * 配置键。
     * <p>
     * 用途：对应 OCPP 字段 {@code key}，在 OCPP 2.0.1 VPN 协议对象中传递配置键。
     * 字段类型为 {@code String}，用于承载配置键。该字段在官方规范中为必填字段。最大长度为 255 个字符。
     * </p>
     */
    private String key;
    /**
     * 类型。
     * <p>
     * 用途：对应 OCPP 字段 {@code type}，在 OCPP 2.0.1 VPN 协议对象中传递类型。
     * 字段类型为 {@code String}，用于承载类型。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String type;
}
