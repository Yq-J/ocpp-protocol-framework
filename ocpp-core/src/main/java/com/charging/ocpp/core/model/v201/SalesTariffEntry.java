package com.charging.ocpp.core.model.v201;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 SalesTariffEntry 复合协议实体类。
 * <p>
 * 用途：描述销售费率Entry相关的结构化数据，作为 OCPP 2.0.1 请求或响应 payload 中的嵌套对象复用。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SalesTariffEntry {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 SalesTariffEntry 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 相对时间区间。
     * <p>
     * 用途：对应 OCPP 字段 {@code relativeTimeInterval}，在 OCPP 2.0.1 SalesTariffEntry 协议对象中传递相对时间区间。
     * 字段类型为 {@code RelativeTimeInterval}，用于承载相对时间区间。该字段在官方规范中为必填字段。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private RelativeTimeInterval relativeTimeInterval;
    /**
     * e价格级别。
     * <p>
     * 用途：对应 OCPP 字段 {@code ePriceLevel}，在 OCPP 2.0.1 SalesTariffEntry 协议对象中传递e价格级别。
     * 字段类型为 {@code Integer}，用于承载e价格级别。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。数值下限为 0。
     * </p>
     */
    private Integer ePriceLevel;
    /**
     * 用量费用。
     * <p>
     * 用途：对应 OCPP 字段 {@code consumptionCost}，在 OCPP 2.0.1 SalesTariffEntry 协议对象中传递用量费用。
     * 字段类型为 {@code List<ConsumptionCost>}，用于承载一组用量费用。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。数组至少包含 1 个元素。数组最多包含 3 个元素。
     * </p>
     */
    private List<ConsumptionCost> consumptionCost;
}
