package com.charging.ocpp.core.model.v16;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 IdTagInfo 复合协议实体类。
 * <p>
 * 描述 OCPP 1.6 idTag 授权结果、有效期和父级 idTag。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class IdTagInfo {
    /**
     * expiry日期。
     * <p>
     * 用途：对应 OCPP 字段 {@code expiryDate}，在 OCPP 1.6J IdTagInfo 协议对象中传递expiry日期。
     * 字段类型为 {@code String}，用于承载expiry日期。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。时间格式应符合 OCPP JSON Schema 的 date-time 约束。
     * </p>
     */
    private String expiryDate;
    /**
     * 父级标识标签。
     * <p>
     * 用途：对应 OCPP 字段 {@code parentIdTag}，在 OCPP 1.6J IdTagInfo 协议对象中传递父级标识标签。
     * 字段类型为 {@code String}，用于承载父级标识标签。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 20 个字符。
     * </p>
     */
    private String parentIdTag;
    /**
     * 处理状态。
     * <p>
     * 用途：对应 OCPP 字段 {@code status}，在 OCPP 1.6J IdTagInfo 协议对象中传递处理状态。
     * 字段类型为 {@code String}，用于承载处理状态。该字段在官方规范中为必填字段。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String status;
}
