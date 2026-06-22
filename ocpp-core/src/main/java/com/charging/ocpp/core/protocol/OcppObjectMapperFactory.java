package com.charging.ocpp.core.protocol;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory for the ObjectMapper used on the OCPP wire protocol boundary.
 */
public final class OcppObjectMapperFactory {
    private OcppObjectMapperFactory() {
    }

    public static ObjectMapper create() {
        return configure(new ObjectMapper());
    }

    public static ObjectMapper copyOf(ObjectMapper objectMapper) {
        return configure(objectMapper == null ? new ObjectMapper() : objectMapper.copy());
    }

    private static ObjectMapper configure(ObjectMapper mapper) {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(null);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}
