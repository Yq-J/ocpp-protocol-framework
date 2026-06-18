package com.charging.ocpp.core.model.v201;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * OCPP 2.0.1 ClearCache 响应模型。
 * <p>
 * 该 DTO 用于承载协议 payload，不包含订单、计费、设备台账等业务语义。
 * 当前随包 Schema 允许厂商扩展字段，因此模型提供 additionalProperties 保存未显式建模的字段。
 * 后续如果补充官方字段，可在本类中增加强类型字段，并保留 additionalProperties 兼容厂商私有扩展。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ClearCacheResponse {
    /**
     * 厂商或协议扩展字段集合。键为 JSON 字段名，值为字段原始内容。
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
