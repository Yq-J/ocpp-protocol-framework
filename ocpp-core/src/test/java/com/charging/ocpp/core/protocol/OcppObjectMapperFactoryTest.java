package com.charging.ocpp.core.protocol;

import com.charging.ocpp.core.model.v16.AuthorizeResponse;
import com.charging.ocpp.core.model.v16.IdTagInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OcppObjectMapperFactoryTest {
    @Test
    void protocolMapperOmitsNullDtoProperties() {
        ObjectMapper objectMapper = OcppObjectMapperFactory.create();
        IdTagInfo info = new IdTagInfo();
        info.setStatus("Accepted");
        AuthorizeResponse response = new AuthorizeResponse();
        response.setIdTagInfo(info);

        JsonNode payload = objectMapper.valueToTree(response);

        assertFalse(payload.get("idTagInfo").has("expiryDate"));
        assertFalse(payload.get("idTagInfo").has("parentIdTag"));
    }

    @Test
    void protocolMapperCopyResetsBusinessJacksonNamingAndNullPolicy() {
        ObjectMapper businessMapper = new ObjectMapper();
        businessMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        businessMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        IdTagInfo info = new IdTagInfo();
        info.setStatus("Accepted");
        AuthorizeResponse response = new AuthorizeResponse();
        response.setIdTagInfo(info);

        JsonNode payload = OcppObjectMapperFactory.copyOf(businessMapper).valueToTree(response);

        assertTrue(payload.has("idTagInfo"));
        assertFalse(payload.has("id_tag_info"));
        assertFalse(payload.get("idTagInfo").has("expiryDate"));
    }
}
