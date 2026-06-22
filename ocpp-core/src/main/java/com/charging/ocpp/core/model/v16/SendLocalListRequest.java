package com.charging.ocpp.core.model.v16;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 SendLocalList 请求 payload 协议实体类。
 * <p>
 * 用途：承载 SendLocalList 操作的请求字段，用于下发本地授权列表场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SendLocalListRequest {
    /**
     * 列表版本。
     * <p>
     * 用途：对应 OCPP 字段 {@code listVersion}，在 OCPP 1.6J SendLocalListRequest 协议对象中传递列表版本。
     * 字段类型为 {@code Integer}，用于承载列表版本。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer listVersion;
    /**
     * 本地Authorization列表。
     * <p>
     * 用途：对应 OCPP 字段 {@code localAuthorizationList}，在 OCPP 1.6J SendLocalListRequest 协议对象中传递本地Authorization列表。
     * 字段类型为 {@code List<LocalAuthorizationList>}，用于承载一组本地Authorization列表。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private List<LocalAuthorizationList> localAuthorizationList;
    /**
     * 更新类型。
     * <p>
     * 用途：对应 OCPP 字段 {@code updateType}，在 OCPP 1.6J SendLocalListRequest 协议对象中传递更新类型。
     * 字段类型为 {@code String}，用于承载更新类型。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String updateType;
}
