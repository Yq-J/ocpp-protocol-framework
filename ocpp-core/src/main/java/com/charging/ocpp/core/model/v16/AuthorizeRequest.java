package com.charging.ocpp.core.model.v16;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 Authorize 请求 payload 协议实体类。
 * <p>
 * 用途：承载 Authorize 操作的请求字段，用于授权身份令牌并返回是否允许充电场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AuthorizeRequest {
    /**
     * OCPP 1.6 用户标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code idTag}，在 OCPP 1.6J AuthorizeRequest 协议对象中传递OCPP 1.6 用户标识。
     * 字段类型为 {@code String}，用于承载OCPP 1.6 用户标识。该字段在官方规范中为必填字段。最大长度为 20 个字符。
     * </p>
     */
    private String idTag;
}
