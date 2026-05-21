package com.charging.ocpp.core.model.v201;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * OCPP 2.0.1 TransactionEvent 请求。
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
public class TransactionEventRequest {
    /**
     * eventType 字段。
     */
    private String eventType;
    /**
     * timestamp 字段。
     */
    private String timestamp;
    /**
     * triggerReason 字段。
     */
    private String triggerReason;
    /**
     * seqNo 字段。
     */
    private Integer seqNo;
    /**
     * transactionInfo 字段。
     */
    private JsonNode transactionInfo;
    /**
     * idToken 字段。
     */
    private JsonNode idToken;
    /**
     * cableMaxCurrent 字段。
     */
    private Integer cableMaxCurrent;
    /**
     * meterValue 字段。
     */
    private List<JsonNode> meterValue;
    /**
     * evse 字段。
     */
    private JsonNode evse;

}
