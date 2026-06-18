package com.charging.ocpp.core.model.v16;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6 BootNotification 请求。
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
public class BootNotificationRequest {
    /** OCPP payload 的 chargePointVendor 字段。 */
    private String chargePointVendor;
    /** OCPP payload 的 chargePointModel 字段。 */
    private String chargePointModel;
    /** OCPP payload 的 chargePointSerialNumber 字段。 */
    private String chargePointSerialNumber;
    /** OCPP payload 的 chargeBoxSerialNumber 字段。 */
    private String chargeBoxSerialNumber;
    /** OCPP payload 的 firmwareVersion 字段。 */
    private String firmwareVersion;
    /** OCPP payload 的 iccid 字段。 */
    private String iccid;
    /** OCPP payload 的 imsi 字段。 */
    private String imsi;
    /** OCPP payload 的 meterType 字段。 */
    private String meterType;
    /** OCPP payload 的 meterSerialNumber 字段。 */
    private String meterSerialNumber;

}
