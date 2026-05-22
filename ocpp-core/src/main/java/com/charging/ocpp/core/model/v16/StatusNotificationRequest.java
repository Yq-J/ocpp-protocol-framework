package com.charging.ocpp.core.model.v16;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6 StatusNotification 请求。
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
public class StatusNotificationRequest {

    /**
     * 上报状态的连接器编号。
     */
    private Integer connectorId;
    /**
     * 连接器当前错误码。
     */
    private String errorCode;
    /**
     * 连接器当前运行状态。
     */
    private String status;
    /**
     * 状态或错误的补充说明。
     */
    private String info;
    /**
     * 状态产生时间。
     */
    private String timestamp;
    /**
     * 厂商标识。
     */
    private String vendorId;
    /**
     * 厂商自定义错误码。
     */
    private String vendorErrorCode;

}
