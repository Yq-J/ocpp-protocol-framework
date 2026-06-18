package com.charging.ocpp.starter.schema;

import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.exception.OcppException;
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
}
