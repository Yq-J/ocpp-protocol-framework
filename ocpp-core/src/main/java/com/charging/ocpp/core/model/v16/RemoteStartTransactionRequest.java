package com.charging.ocpp.core.model.v16;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * OCPP 1.6 RemoteStartTransaction 请求。
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
public class RemoteStartTransactionRequest {

    /** 指定启动交易的连接器编号。 */
    private Integer connectorId;
    /** 用于远程启动授权的用户标识。 */
    private String idTag;
    /** 远程启动时下发的充电配置。 */
    private JsonNode chargingProfile;

}
