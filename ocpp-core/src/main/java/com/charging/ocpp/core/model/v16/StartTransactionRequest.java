package com.charging.ocpp.core.model.v16;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 StartTransaction 请求 payload 协议实体类。
 * <p>
 * 用途：承载 StartTransaction 操作的请求字段，用于上报交易开始场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StartTransactionRequest {
    /**
     * 连接器编号。
     * <p>
     * 用途：对应 OCPP 字段 {@code connectorId}，在 OCPP 1.6J StartTransactionRequest 协议对象中传递连接器编号。
     * 字段类型为 {@code Integer}，用于承载连接器编号。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer connectorId;
    /**
     * OCPP 1.6 用户标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code idTag}，在 OCPP 1.6J StartTransactionRequest 协议对象中传递OCPP 1.6 用户标识。
     * 字段类型为 {@code String}，用于承载OCPP 1.6 用户标识。该字段在官方规范中为必填字段。最大长度为 20 个字符。
     * </p>
     */
    private String idTag;
    /**
     * 交易开始电表读数。
     * <p>
     * 用途：对应 OCPP 字段 {@code meterStart}，在 OCPP 1.6J StartTransactionRequest 协议对象中传递交易开始电表读数。
     * 字段类型为 {@code Integer}，用于承载交易开始电表读数。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer meterStart;
    /**
     * 预约标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code reservationId}，在 OCPP 1.6J StartTransactionRequest 协议对象中传递预约标识。
     * 字段类型为 {@code Integer}，用于承载预约标识。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer reservationId;
    /**
     * 事件时间。
     * <p>
     * 用途：对应 OCPP 字段 {@code timestamp}，在 OCPP 1.6J StartTransactionRequest 协议对象中传递事件时间。
     * 字段类型为 {@code String}，用于承载事件时间。该字段在官方规范中为必填字段。时间格式应符合 OCPP JSON Schema 的 date-time 约束。
     * </p>
     */
    private String timestamp;
}
