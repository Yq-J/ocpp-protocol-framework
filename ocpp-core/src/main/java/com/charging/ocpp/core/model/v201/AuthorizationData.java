package com.charging.ocpp.core.model.v201;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 AuthorizationData 复合协议实体类。
 * <p>
 * 用途：描述Authorization数据相关的结构化数据，作为 OCPP 2.0.1 请求或响应 payload 中的嵌套对象复用。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AuthorizationData {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 AuthorizationData 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 身份令牌。
     * <p>
     * 用途：对应 OCPP 字段 {@code idToken}，在 OCPP 2.0.1 AuthorizationData 协议对象中传递身份令牌。
     * 字段类型为 {@code IdToken}，用于承载身份令牌。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private IdToken idToken;
    /**
     * 身份令牌授权结果。
     * <p>
     * 用途：对应 OCPP 字段 {@code idTokenInfo}，在 OCPP 2.0.1 AuthorizationData 协议对象中传递身份令牌授权结果。
     * 字段类型为 {@code IdTokenInfo}，用于承载身份令牌授权结果。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private IdTokenInfo idTokenInfo;
}
