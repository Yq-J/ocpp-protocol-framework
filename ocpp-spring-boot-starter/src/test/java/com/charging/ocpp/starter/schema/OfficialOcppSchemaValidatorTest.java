package com.charging.ocpp.starter.schema;

import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.exception.OcppException;
import com.charging.ocpp.core.model.v16.AuthorizeResponse;
import com.charging.ocpp.core.model.v16.IdTagInfo;
import com.charging.ocpp.core.model.v16.RemoteStartTransactionRequest;
import com.charging.ocpp.core.protocol.OcppObjectMapperFactory;
import com.charging.ocpp.starter.autoconfigure.OcppProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OfficialOcppSchemaValidatorTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OfficialOcppSchemaValidator validator = new OfficialOcppSchemaValidator();

    @Test
    void acceptsValidOfficialSchemaPayload() throws Exception {
        assertDoesNotThrow(() -> validator.validate(OcppVersion.OCPP_16, "BootNotification", true,
                objectMapper.readTree("{\"chargePointVendor\":\"ACME\",\"chargePointModel\":\"M1\"}")));
    }

    @Test
    void rejectsPayloadMissingRequiredOfficialFields() throws Exception {
        assertThrows(OcppException.class, () -> validator.validate(OcppVersion.OCPP_16, "BootNotification", true,
                objectMapper.readTree("{\"chargePointVendor\":\"ACME\"}")));
    }

    @Test
    void rejectsUnknownActionsBeforeHandlerFallback() throws Exception {
        assertThrows(OcppException.class, () -> validator.validate(OcppVersion.OCPP_201, "ChangeConfiguration", true,
                objectMapper.readTree("{}")));
    }

    @Test
    void allowsUnknownActionsWhenConfigured() throws Exception {
        OcppProperties properties = new OcppProperties();
        properties.setAllowUnknownActions(true);
        OfficialOcppSchemaValidator customValidator = new OfficialOcppSchemaValidator(properties);

        assertDoesNotThrow(() -> customValidator.validate(OcppVersion.OCPP_201, "VendorAction", true,
                objectMapper.readTree("{}")));
    }

    @Test
    void protocolMapperOutputWithOnlyRequiredResponseFieldsPassesSchema() {
        ObjectMapper protocolMapper = OcppObjectMapperFactory.create();
        IdTagInfo info = new IdTagInfo();
        info.setStatus("Accepted");
        AuthorizeResponse response = new AuthorizeResponse();
        response.setIdTagInfo(info);

        JsonNode payload = protocolMapper.valueToTree(response);

        assertDoesNotThrow(() -> validator.validate(OcppVersion.OCPP_16, "Authorize", false, payload));
    }

    @Test
    void protocolMapperOutputWithOnlyRequiredRequestFieldsPassesSchema() {
        ObjectMapper protocolMapper = OcppObjectMapperFactory.create();
        RemoteStartTransactionRequest request = new RemoteStartTransactionRequest();
        request.setIdTag("TAG001");

        JsonNode payload = protocolMapper.valueToTree(request);

        assertDoesNotThrow(() -> validator.validate(OcppVersion.OCPP_16, "RemoteStartTransaction", true, payload));
    }
}
