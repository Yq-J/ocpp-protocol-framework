package com.charging.ocpp.core.model.v16;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 StopTransaction 请求 payload 协议实体类。
 * <p>
 * 用途：承载 StopTransaction 操作的请求字段，用于上报交易结束场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StopTransactionRequest {
    /**
     * OCPP 1.6 用户标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code idTag}，在 OCPP 1.6J StopTransactionRequest 协议对象中传递OCPP 1.6 用户标识。
     * 字段类型为 {@code String}，用于承载OCPP 1.6 用户标识。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 20 个字符。
     * </p>
     */
    private String idTag;
    /**
     * 交易结束电表读数。
     * <p>
     * 用途：对应 OCPP 字段 {@code meterStop}，在 OCPP 1.6J StopTransactionRequest 协议对象中传递交易结束电表读数。
     * 字段类型为 {@code Integer}，用于承载交易结束电表读数。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer meterStop;
    /**
     * 事件时间。
     * <p>
     * 用途：对应 OCPP 字段 {@code timestamp}，在 OCPP 1.6J StopTransactionRequest 协议对象中传递事件时间。
     * 字段类型为 {@code String}，用于承载事件时间。该字段在官方规范中为必填字段。时间格式应符合 OCPP JSON Schema 的 date-time 约束。
     * </p>
     */
    private String timestamp;
    /**
     * 交易标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code transactionId}，在 OCPP 1.6J StopTransactionRequest 协议对象中传递交易标识。
     * 字段类型为 {@code Integer}，用于承载交易标识。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer transactionId;
    /**
     * 原因。
     * <p>
     * 用途：对应 OCPP 字段 {@code reason}，在 OCPP 1.6J StopTransactionRequest 协议对象中传递原因。
     * 字段类型为 {@code String}，用于承载原因。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。取值由官方 JSON Schema 的枚举约束校验。
     * </p>
     */
    private String reason;
    /**
     * 交易补充计量数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code transactionData}，在 OCPP 1.6J StopTransactionRequest 协议对象中传递交易补充计量数据。
     * 字段类型为 {@code List<TransactionData>}，用于承载一组交易补充计量数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private List<TransactionData> transactionData;
}
