package com.charging.ocpp.core.model.v201;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * OCPP 2.0.1 的 IdTokenInfo 复合协议实体类。
 * <p>
 * 描述身份令牌授权结果、有效期、缓存策略和显示消息。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class IdTokenInfo {
    /**
     * 厂商自定义扩展数据。
     * <p>
     * 用途：对应 OCPP 字段 {@code customData}，在 OCPP 2.0.1 IdTokenInfo 协议对象中传递厂商自定义扩展数据。
     * 字段类型为 {@code CustomData}，用于承载厂商自定义扩展数据。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private CustomData customData;
    /**
     * 处理状态。
     * <p>
     * 用途：对应 OCPP 字段 {@code status}，在 OCPP 2.0.1 IdTokenInfo 协议对象中传递处理状态。
     * 字段类型为 {@code String}，用于承载处理状态。该字段在官方规范中为必填字段。
     * 取值范围：
     * {@code Accepted}：已接受，处理成功；
     * {@code Blocked}：被阻止，禁止使用；
     * {@code ConcurrentTx}：存在并发交易，当前不允许使用；
     * {@code Expired}：已过期；
     * {@code Invalid}：无效；
     * {@code NoCredit}：余额不足或无可用额度；
     * {@code NotAllowedTypeEVSE}：该令牌类型不允许用于该 EVSE；
     * {@code NotAtThisLocation}：不允许在当前位置使用；
     * {@code NotAtThisTime}：不允许在当前时间使用；
     * {@code Unknown}：未知，未匹配到目标对象。
     * </p>
     */
    private String status;
    /**
     * 缓存Expiry日期时间。
     * <p>
     * 用途：对应 OCPP 字段 {@code cacheExpiryDateTime}，在 OCPP 2.0.1 IdTokenInfo 协议对象中传递缓存Expiry日期时间。
     * 字段类型为 {@code String}，用于承载缓存Expiry日期时间。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。时间格式应符合 OCPP JSON Schema 的 date-time 约束。
     * </p>
     */
    private String cacheExpiryDateTime;
    /**
     * 充电优先级。
     * <p>
     * 用途：对应 OCPP 字段 {@code chargingPriority}，在 OCPP 2.0.1 IdTokenInfo 协议对象中传递充电优先级。
     * 字段类型为 {@code Integer}，用于承载充电优先级。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private Integer chargingPriority;
    /**
     * 语言1。
     * <p>
     * 用途：对应 OCPP 字段 {@code language1}，在 OCPP 2.0.1 IdTokenInfo 协议对象中传递语言1。
     * 字段类型为 {@code String}，用于承载语言1。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 8 个字符。
     * </p>
     */
    private String language1;
    /**
     * EVSE 编号。
     * <p>
     * 用途：对应 OCPP 字段 {@code evseId}，在 OCPP 2.0.1 IdTokenInfo 协议对象中传递EVSE 编号。
     * 字段类型为 {@code List<Integer>}，用于承载一组EVSE 编号。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。数组至少包含 1 个元素。
     * </p>
     */
    private List<Integer> evseId;
    /**
     * 分组标识令牌。
     * <p>
     * 用途：对应 OCPP 字段 {@code groupIdToken}，在 OCPP 2.0.1 IdTokenInfo 协议对象中传递分组标识令牌。
     * 字段类型为 {@code IdToken}，用于承载分组标识令牌。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private IdToken groupIdToken;
    /**
     * 语言2。
     * <p>
     * 用途：对应 OCPP 字段 {@code language2}，在 OCPP 2.0.1 IdTokenInfo 协议对象中传递语言2。
     * 字段类型为 {@code String}，用于承载语言2。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 8 个字符。
     * </p>
     */
    private String language2;
    /**
     * 个人消息。
     * <p>
     * 用途：对应 OCPP 字段 {@code personalMessage}，在 OCPP 2.0.1 IdTokenInfo 协议对象中传递个人消息。
     * 字段类型为 {@code MessageContent}，用于承载个人消息。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。具体合法性由官方 JSON Schema 和业务状态机共同约束。
     * </p>
     */
    private MessageContent personalMessage;
}
