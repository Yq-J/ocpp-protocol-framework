package com.charging.ocpp.core.model.v201;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 CustomerInformation 请求 payload 协议实体类。
 * <p>
 * 用途：承载 CustomerInformation 操作的请求字段，用于请求客户信息导出或清除场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CustomerInformationRequest {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 CustomerInformationRequest 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * customer证书。
     * <p>
     * 用途：对应 OCPP 字段 {@code customerCertificate}，在 OCPP 2.0.1 CustomerInformationRequest 协议对象中传递customer证书。
     * 字段类型为 {@code CertificateHashData}，用于承载customer证书。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CertificateHashData customerCertificate;
    /**
     * 身份令牌。
     * <p>
     * 用途：对应 OCPP 字段 {@code idToken}，在 OCPP 2.0.1 CustomerInformationRequest 协议对象中传递身份令牌。
     * 字段类型为 {@code IdToken}，用于承载身份令牌。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private IdToken idToken;
    /**
     * 请求标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code requestId}，在 OCPP 2.0.1 CustomerInformationRequest 协议对象中传递请求标识。
     * 字段类型为 {@code Integer}，用于承载请求标识。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer requestId;
    /**
     * 报告。
     * <p>
     * 用途：对应 OCPP 字段 {@code report}，在 OCPP 2.0.1 CustomerInformationRequest 协议对象中传递报告。
     * 字段类型为 {@code Boolean}，用于承载报告。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Boolean report;
    /**
     * 清除。
     * <p>
     * 用途：对应 OCPP 字段 {@code clear}，在 OCPP 2.0.1 CustomerInformationRequest 协议对象中传递清除。
     * 字段类型为 {@code Boolean}，用于承载清除。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Boolean clear;
    /**
     * customerIdentifier。
     * <p>
     * 用途：对应 OCPP 字段 {@code customerIdentifier}，在 OCPP 2.0.1 CustomerInformationRequest 协议对象中传递customerIdentifier。
     * 字段类型为 {@code String}，用于承载customerIdentifier。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 64 个字符。
     * </p>
     */
    private String customerIdentifier;
}
