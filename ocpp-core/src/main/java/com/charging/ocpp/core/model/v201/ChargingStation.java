package com.charging.ocpp.core.model.v201;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 ChargingStation 复合协议实体类。
 * <p>
 * 用途：描述充电充电站相关的结构化数据，作为 OCPP 2.0.1 请求或响应 payload 中的嵌套对象复用。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ChargingStation {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 ChargingStation 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 证书序列号。
     * <p>
     * 用途：对应 OCPP 字段 {@code serialNumber}，在 OCPP 2.0.1 ChargingStation 协议对象中传递证书序列号。
     * 字段类型为 {@code String}，用于承载证书序列号。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 25 个字符。
     * </p>
     */
    private String serialNumber;
    /**
     * 型号。
     * <p>
     * 用途：对应 OCPP 字段 {@code model}，在 OCPP 2.0.1 ChargingStation 协议对象中传递型号。
     * 字段类型为 {@code String}，用于承载型号。该字段在官方规范中为必填字段。最大长度为 20 个字符。
     * </p>
     */
    private String model;
    /**
     * modem。
     * <p>
     * 用途：对应 OCPP 字段 {@code modem}，在 OCPP 2.0.1 ChargingStation 协议对象中传递modem。
     * 字段类型为 {@code Modem}，用于承载modem。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Modem modem;
    /**
     * vendorName。
     * <p>
     * 用途：对应 OCPP 字段 {@code vendorName}，在 OCPP 2.0.1 ChargingStation 协议对象中传递vendorName。
     * 字段类型为 {@code String}，用于承载vendorName。该字段在官方规范中为必填字段。最大长度为 50 个字符。
     * </p>
     */
    private String vendorName;
    /**
     * 固件版本。
     * <p>
     * 用途：对应 OCPP 字段 {@code firmwareVersion}，在 OCPP 2.0.1 ChargingStation 协议对象中传递固件版本。
     * 字段类型为 {@code String}，用于承载固件版本。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 50 个字符。
     * </p>
     */
    private String firmwareVersion;
}
