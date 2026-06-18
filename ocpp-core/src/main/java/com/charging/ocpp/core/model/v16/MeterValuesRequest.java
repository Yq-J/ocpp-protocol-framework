package com.charging.ocpp.core.model.v16;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * OCPP 1.6 MeterValues 请求。
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
public class MeterValuesRequest {
    /** OCPP payload 的 connectorId 字段。 */
    private Integer connectorId;
    /** OCPP payload 的 transactionId 字段。 */
    private Integer transactionId;
    /** OCPP payload 的 meterValue 字段。 */
    private List<JsonNode> meterValue;

}
