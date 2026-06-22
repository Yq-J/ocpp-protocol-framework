package com.charging.ocpp.core.model.v16;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 Authorize 响应 payload 协议实体类。
 * <p>
 * 用途：承载 Authorize 操作的响应字段，用于授权身份令牌并返回是否允许充电场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AuthorizeResponse {
    /**
     * 用户标识授权结果。
     * <p>
     * 用途：对应 OCPP 字段 {@code idTagInfo}，在 OCPP 1.6J AuthorizeResponse 协议对象中传递用户标识授权结果。
     * 字段类型为 {@code IdTagInfo}，用于承载用户标识授权结果。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private IdTagInfo idTagInfo;
}
