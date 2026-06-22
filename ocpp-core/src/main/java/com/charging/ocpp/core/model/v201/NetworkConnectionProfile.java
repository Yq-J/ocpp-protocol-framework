package com.charging.ocpp.core.model.v201;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 NetworkConnectionProfile 复合协议实体类。
 * <p>
 * 描述 OCPP 网络连接配置，包括传输、版本、安全和网络参数。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class NetworkConnectionProfile {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 NetworkConnectionProfile 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 蜂窝网络 APN。
     * <p>
     * 用途：对应 OCPP 字段 {@code apn}，在 OCPP 2.0.1 NetworkConnectionProfile 协议对象中传递蜂窝网络 APN。
     * 字段类型为 {@code APN}，用于承载蜂窝网络 APN。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private APN apn;
    /**
     * OCPP版本。
     * <p>
     * 用途：对应 OCPP 字段 {@code ocppVersion}，在 OCPP 2.0.1 NetworkConnectionProfile 协议对象中传递OCPP版本。
     * 字段类型为 {@code String}，用于承载OCPP版本。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String ocppVersion;
    /**
     * OCPP传输。
     * <p>
     * 用途：对应 OCPP 字段 {@code ocppTransport}，在 OCPP 2.0.1 NetworkConnectionProfile 协议对象中传递OCPP传输。
     * 字段类型为 {@code String}，用于承载OCPP传输。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String ocppTransport;
    /**
     * OCPPCsmsURL。
     * <p>
     * 用途：对应 OCPP 字段 {@code ocppCsmsUrl}，在 OCPP 2.0.1 NetworkConnectionProfile 协议对象中传递OCPPCsmsURL。
     * 字段类型为 {@code String}，用于承载OCPPCsmsURL。该字段在官方规范中为必填字段。最大长度为 512 个字符。
     * </p>
     */
    private String ocppCsmsUrl;
    /**
     * 消息Timeout。
     * <p>
     * 用途：对应 OCPP 字段 {@code messageTimeout}，在 OCPP 2.0.1 NetworkConnectionProfile 协议对象中传递消息Timeout。
     * 字段类型为 {@code Integer}，用于承载消息Timeout。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer messageTimeout;
    /**
     * 安全配置。
     * <p>
     * 用途：对应 OCPP 字段 {@code securityProfile}，在 OCPP 2.0.1 NetworkConnectionProfile 协议对象中传递安全配置。
     * 字段类型为 {@code Integer}，用于承载安全配置。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer securityProfile;
    /**
     * OCPP接口。
     * <p>
     * 用途：对应 OCPP 字段 {@code ocppInterface}，在 OCPP 2.0.1 NetworkConnectionProfile 协议对象中传递OCPP接口。
     * 字段类型为 {@code String}，用于承载OCPP接口。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String ocppInterface;
    /**
     * VPN 配置。
     * <p>
     * 用途：对应 OCPP 字段 {@code vpn}，在 OCPP 2.0.1 NetworkConnectionProfile 协议对象中传递VPN 配置。
     * 字段类型为 {@code VPN}，用于承载VPN 配置。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private VPN vpn;
}
