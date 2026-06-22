package com.charging.ocpp.core.model.v16;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 BootNotification 请求 payload 协议实体类。
 * <p>
 * 用途：承载 BootNotification 操作的请求字段，用于充电站启动或重连时向 CSMS 注册设备信息场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BootNotificationRequest {
    /**
     * 充电点厂商。
     * <p>
     * 用途：对应 OCPP 字段 {@code chargePointVendor}，在 OCPP 1.6J BootNotificationRequest 协议对象中传递充电点厂商。
     * 字段类型为 {@code String}，用于承载充电点厂商。该字段在官方规范中为必填字段。最大长度为 20 个字符。
     * </p>
     */
    private String chargePointVendor;
    /**
     * 充电点型号。
     * <p>
     * 用途：对应 OCPP 字段 {@code chargePointModel}，在 OCPP 1.6J BootNotificationRequest 协议对象中传递充电点型号。
     * 字段类型为 {@code String}，用于承载充电点型号。该字段在官方规范中为必填字段。最大长度为 20 个字符。
     * </p>
     */
    private String chargePointModel;
    /**
     * 充电点序列号。
     * <p>
     * 用途：对应 OCPP 字段 {@code chargePointSerialNumber}，在 OCPP 1.6J BootNotificationRequest 协议对象中传递充电点序列号。
     * 字段类型为 {@code String}，用于承载充电点序列号。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 25 个字符。
     * </p>
     */
    private String chargePointSerialNumber;
    /**
     * 充电盒序列号。
     * <p>
     * 用途：对应 OCPP 字段 {@code chargeBoxSerialNumber}，在 OCPP 1.6J BootNotificationRequest 协议对象中传递充电盒序列号。
     * 字段类型为 {@code String}，用于承载充电盒序列号。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 25 个字符。
     * </p>
     */
    private String chargeBoxSerialNumber;
    /**
     * 固件版本。
     * <p>
     * 用途：对应 OCPP 字段 {@code firmwareVersion}，在 OCPP 1.6J BootNotificationRequest 协议对象中传递固件版本。
     * 字段类型为 {@code String}，用于承载固件版本。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 50 个字符。
     * </p>
     */
    private String firmwareVersion;
    /**
     * SIM 卡 ICCID。
     * <p>
     * 用途：对应 OCPP 字段 {@code iccid}，在 OCPP 1.6J BootNotificationRequest 协议对象中传递SIM 卡 ICCID。
     * 字段类型为 {@code String}，用于承载SIM 卡 ICCID。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 20 个字符。
     * </p>
     */
    private String iccid;
    /**
     * SIM 卡 IMSI。
     * <p>
     * 用途：对应 OCPP 字段 {@code imsi}，在 OCPP 1.6J BootNotificationRequest 协议对象中传递SIM 卡 IMSI。
     * 字段类型为 {@code String}，用于承载SIM 卡 IMSI。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 20 个字符。
     * </p>
     */
    private String imsi;
    /**
     * 电表类型。
     * <p>
     * 用途：对应 OCPP 字段 {@code meterType}，在 OCPP 1.6J BootNotificationRequest 协议对象中传递电表类型。
     * 字段类型为 {@code String}，用于承载电表类型。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 25 个字符。
     * </p>
     */
    private String meterType;
    /**
     * 电表序列号。
     * <p>
     * 用途：对应 OCPP 字段 {@code meterSerialNumber}，在 OCPP 1.6J BootNotificationRequest 协议对象中传递电表序列号。
     * 字段类型为 {@code String}，用于承载电表序列号。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 25 个字符。
     * </p>
     */
    private String meterSerialNumber;
}
