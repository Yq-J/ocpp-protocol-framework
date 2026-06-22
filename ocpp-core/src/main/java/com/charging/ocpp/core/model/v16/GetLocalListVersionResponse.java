package com.charging.ocpp.core.model.v16;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 GetLocalListVersion 响应 payload 协议实体类。
 * <p>
 * 用途：承载 GetLocalListVersion 操作的响应字段，用于读取本地授权列表版本场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GetLocalListVersionResponse {
    /**
     * 列表版本。
     * <p>
     * 用途：对应 OCPP 字段 {@code listVersion}，在 OCPP 1.6J GetLocalListVersionResponse 协议对象中传递列表版本。
     * 字段类型为 {@code Integer}，用于承载列表版本。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer listVersion;
}
