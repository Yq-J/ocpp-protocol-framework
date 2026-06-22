package com.charging.ocpp.core.model.v201;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 2.0.1 的 CustomData 复合协议实体类。
 * <p>
 * 描述 OCPP 2.0.1 的厂商扩展数据结构，标准字段为 vendorId，其他厂商私有字段通过额外属性透传。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CustomData {
    /**
     * 厂商标识。
     * <p>
     * 用途：对应 OCPP 字段 {@code vendorId}，在 OCPP 2.0.1 CustomData 协议对象中传递厂商标识。
     * 字段类型为 {@code String}，用于承载厂商标识。该字段在官方规范中为必填字段。最大长度为 255 个字符。
     * </p>
     */
    private String vendorId;

    /**
     * 厂商扩展字段集合。
     * <p>
     * 用途：保存 {@code customData} 中除 {@code vendorId} 之外的未显式建模字段，序列化时会展开为同级 JSON 属性。
     * 该集合只用于协议扩展透传，不承载框架内部状态或业务持久化语义。
     * </p>
     */
    @JsonIgnore
    @Builder.Default
    private Map<String, Object> additionalProperties = new LinkedHashMap<>();

    @JsonAnyGetter
    public Map<String, Object> any() {
        return additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        additionalProperties.put(name, value);
    }
}
