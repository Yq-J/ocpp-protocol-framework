package com.charging.ocpp.core.model.v16;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 TransactionData 复合协议实体类。
 * <p>
 * 用途：描述交易数据相关的结构化数据，作为 OCPP 1.6J 请求或响应 payload 中的嵌套对象复用。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TransactionData {
    /**
     * 事件时间。
     * <p>
     * 用途：对应 OCPP 字段 {@code timestamp}，在 OCPP 1.6J TransactionData 协议对象中传递事件时间。
     * 字段类型为 {@code String}，用于承载事件时间。该字段在官方规范中为必填字段。时间格式应符合 OCPP JSON Schema 的 date-time 约束。
     * </p>
     */
    private String timestamp;
    /**
     * 单个采样值列表。
     * <p>
     * 用途：对应 OCPP 字段 {@code sampledValue}，在 OCPP 1.6J TransactionData 协议对象中传递单个采样值列表。
     * 字段类型为 {@code List<SampledValue2>}，用于承载一组单个采样值列表。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private List<SampledValue2> sampledValue;
}
