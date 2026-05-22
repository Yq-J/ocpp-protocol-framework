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
    /** 充电桩厂商名称。 */
    private String chargePointVendor;
    /** 充电桩型号。 */
    private String chargePointModel;
    /** 充电桩序列号。 */
    private String chargePointSerialNumber;
    /** 充电桩控制箱序列号。 */
    private String chargeBoxSerialNumber;
    /** 固件版本号。 */
    private String firmwareVersion;
    /** SIM 卡 ICCID。 */
    private String iccid;
    /** SIM 卡 IMSI。 */
    private String imsi;
    /** 电表类型。 */
    private String meterType;
    /** 电表序列号。 */
    private String meterSerialNumber;

}
