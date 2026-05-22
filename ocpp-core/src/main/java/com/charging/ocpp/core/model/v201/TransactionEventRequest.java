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
     * 交易事件类型。
     */
    private String eventType;
    /**
     * 交易事件发生时间。
     */
    private String timestamp;
    /**
     * 触发交易事件的原因。
     */
    private String triggerReason;
    /**
     * 交易事件序号。
     */
    private Integer seqNo;
    /**
     * 交易基础信息。
     */
    private JsonNode transactionInfo;
    /**
     * 交易关联的身份令牌。
     */
    private JsonNode idToken;
    /**
     * 充电电缆允许的最大电流。
     */
    private Integer cableMaxCurrent;
    /**
     * 交易事件携带的计量采样值列表。
     */
    private List<JsonNode> meterValue;
    /**
     * 交易关联的 EVSE 信息。
     */
    private JsonNode evse;

}
