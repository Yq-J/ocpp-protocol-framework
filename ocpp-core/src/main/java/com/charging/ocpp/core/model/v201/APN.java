package com.charging.ocpp.core.model.v201;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 APN 复合协议实体类。
 * <p>
 * 描述蜂窝网络接入点配置。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class APN {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 APN 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 蜂窝网络 APN。
     * <p>
     * 用途：对应 OCPP 字段 {@code apn}，在 OCPP 2.0.1 APN 协议对象中传递蜂窝网络 APN。
     * 字段类型为 {@code String}，用于承载蜂窝网络 APN。该字段在官方规范中为必填字段。最大长度为 512 个字符。
     * </p>
     */
    private String apn;
    /**
     * APN 用户名。
     * <p>
     * 用途：对应 OCPP 字段 {@code apnUserName}，在 OCPP 2.0.1 APN 协议对象中传递APN 用户名。
     * 字段类型为 {@code String}，用于承载APN 用户名。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 20 个字符。
     * </p>
     */
    private String apnUserName;
    /**
     * APN 密码。
     * <p>
     * 用途：对应 OCPP 字段 {@code apnPassword}，在 OCPP 2.0.1 APN 协议对象中传递APN 密码。
     * 字段类型为 {@code String}，用于承载APN 密码。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 20 个字符。
     * </p>
     */
    private String apnPassword;
    /**
     * SIM 卡 PIN。
     * <p>
     * 用途：对应 OCPP 字段 {@code simPin}，在 OCPP 2.0.1 APN 协议对象中传递SIM 卡 PIN。
     * 字段类型为 {@code Integer}，用于承载SIM 卡 PIN。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer simPin;
    /**
     * 首选网络。
     * <p>
     * 用途：对应 OCPP 字段 {@code preferredNetwork}，在 OCPP 2.0.1 APN 协议对象中传递首选网络。
     * 字段类型为 {@code String}，用于承载首选网络。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 6 个字符。
     * </p>
     */
    private String preferredNetwork;
    /**
     * 是否只使用首选网络。
     * <p>
     * 用途：对应 OCPP 字段 {@code useOnlyPreferredNetwork}，在 OCPP 2.0.1 APN 协议对象中传递是否只使用首选网络。
     * 字段类型为 {@code Boolean}，用于承载是否只使用首选网络。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Boolean useOnlyPreferredNetwork;
    /**
     * APN 认证方式。
     * <p>
     * 用途：对应 OCPP 字段 {@code apnAuthentication}，在 OCPP 2.0.1 APN 协议对象中传递APN 认证方式。
     * 字段类型为 {@code String}，用于承载APN 认证方式。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String apnAuthentication;
}
