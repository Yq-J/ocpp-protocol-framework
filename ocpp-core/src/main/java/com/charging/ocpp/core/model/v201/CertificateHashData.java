package com.charging.ocpp.core.model.v201;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 CertificateHashData 复合协议实体类。
 * <p>
 * 描述证书哈希定位信息，用于安装、查询、删除或校验证书。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CertificateHashData {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 CertificateHashData 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 哈希算法。
     * <p>
     * 用途：对应 OCPP 字段 {@code hashAlgorithm}，在 OCPP 2.0.1 CertificateHashData 协议对象中传递哈希算法。
     * 字段类型为 {@code String}，用于承载哈希算法。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String hashAlgorithm;
    /**
     * 签发者名称哈希。
     * <p>
     * 用途：对应 OCPP 字段 {@code issuerNameHash}，在 OCPP 2.0.1 CertificateHashData 协议对象中传递签发者名称哈希。
     * 字段类型为 {@code String}，用于承载签发者名称哈希。该字段在官方规范中为必填字段。最大长度为 128 个字符。
     * </p>
     */
    private String issuerNameHash;
    /**
     * 签发者密钥哈希。
     * <p>
     * 用途：对应 OCPP 字段 {@code issuerKeyHash}，在 OCPP 2.0.1 CertificateHashData 协议对象中传递签发者密钥哈希。
     * 字段类型为 {@code String}，用于承载签发者密钥哈希。该字段在官方规范中为必填字段。最大长度为 128 个字符。
     * </p>
     */
    private String issuerKeyHash;
    /**
     * 证书序列号。
     * <p>
     * 用途：对应 OCPP 字段 {@code serialNumber}，在 OCPP 2.0.1 CertificateHashData 协议对象中传递证书序列号。
     * 字段类型为 {@code String}，用于承载证书序列号。该字段在官方规范中为必填字段。最大长度为 40 个字符。
     * </p>
     */
    private String serialNumber;
}
