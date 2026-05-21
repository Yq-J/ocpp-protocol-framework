package com.charging.ocpp.core.model.v16;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OCPP 1.6 Heartbeat 请求。
 * 作者：JYq
  * <p>
 * 作者：JYq。该 DTO 只描述协议 payload 字段，不包含数据库实体、订单逻辑或计费逻辑。
 * 业务层可以直接在 @OcppActionMapping 方法中声明该类型，框架会自动完成 JSON 与 Java 对象之间的转换。
 * 如厂商存在私有扩展字段，建议新增扩展 DTO、继承当前 DTO，或在业务处理器中改用 JsonNode 接收原始 payload。
 * </p>
 */
@Data
@NoArgsConstructor
public class HeartbeatRequest {

}
