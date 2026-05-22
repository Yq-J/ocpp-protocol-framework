package com.charging.ocpp.core.model.v16;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * OCPP 1.6 StopTransaction 请求。
 * 作者：JYq
 * <p>
 * 作者：JYq。该 DTO 只描述协议 payload 字段，不包含数据库实体、订单逻辑或计费逻辑。
 * 业务层可以直接在 @OcppActionMapping 方法中声明该类型，框架会自动完成 JSON 与 Java 对象之间的转换。
 * 如厂商存在私有扩展字段，建议新增扩展 DTO、继承当前 DTO，或在业务处理器中改用 JsonNode 接收原始 payload。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StopTransactionRequest {
    /**
     * 需要停止的交易编号。
     */
    private Integer transactionId;
    /**
     * 停止交易时使用的用户授权标识。
     */
    private String idTag;
    /**
     * 交易结束时的电表读数。
     */
    private Integer meterStop;
    /**
     * 交易结束时间。
     */
    private String timestamp;
    /**
     * 交易停止原因。
     */
    private String reason;
    /**
     * 交易过程中的计量采样数据。
     */
    private List<JsonNode> transactionData;

}
