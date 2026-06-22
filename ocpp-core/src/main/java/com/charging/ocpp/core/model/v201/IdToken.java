package com.charging.ocpp.core.model.v201;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 IdToken 复合协议实体类。
 * <p>
 * 描述 OCPP 2.0.1 身份令牌，用于卡号、即插即充凭证或其他授权凭据。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class IdToken {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 IdToken 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 附加身份信息。
     * <p>
     * 用途：对应 OCPP 字段 {@code additionalInfo}，在 OCPP 2.0.1 IdToken 协议对象中传递附加身份信息。
     * 字段类型为 {@code List<AdditionalInfo>}，用于承载一组附加身份信息。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。数组至少包含 1 个元素。
     * </p>
     */
    private List<AdditionalInfo> additionalInfo;
    /**
     * 身份令牌。
     * <p>
     * 用途：对应 OCPP 字段 {@code idToken}，在 OCPP 2.0.1 IdToken 协议对象中传递身份令牌。
     * 字段类型为 {@code String}，用于承载身份令牌。该字段在官方规范中为必填字段。最大长度为 36 个字符。
     * </p>
     */
    private String idToken;
    /**
     * 类型。
     * <p>
     * 用途：对应 OCPP 字段 {@code type}，在 OCPP 2.0.1 IdToken 协议对象中传递类型。
     * 字段类型为 {@code String}，用于承载类型。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String type;
}
