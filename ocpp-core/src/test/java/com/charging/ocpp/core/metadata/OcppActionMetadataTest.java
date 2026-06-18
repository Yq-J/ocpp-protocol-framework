package com.charging.ocpp.core.metadata;

import com.charging.ocpp.core.enums.OcppVersion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OcppActionMetadataTest {
    @Test
    void containsAllOcpp16JsonActions() {
        assertEquals(28, OcppActionMetadata.actionCount(OcppVersion.OCPP_16));
        assertTrue(OcppActionMetadata.isKnownAction(OcppVersion.OCPP_16, "BootNotification"));
        assertTrue(OcppActionMetadata.isKnownAction(OcppVersion.OCPP_16, "RemoteStartTransaction"));
        assertFalse(OcppActionMetadata.isKnownAction(OcppVersion.OCPP_16, "TransactionEvent"));
    }

    @Test
    void containsAllOcpp201JsonActions() {
        assertEquals(64, OcppActionMetadata.actionCount(OcppVersion.OCPP_201));
        assertTrue(OcppActionMetadata.isKnownAction(OcppVersion.OCPP_201, "TransactionEvent"));
        assertTrue(OcppActionMetadata.isKnownAction(OcppVersion.OCPP_201, "Get15118EVCertificate"));
        assertFalse(OcppActionMetadata.isKnownAction(OcppVersion.OCPP_201, "ChangeConfiguration"));
    }

    @Test
    void exposesDtoAndSchemaMappingsForEveryAction() {
        for (OcppVersion version : new OcppVersion[] {OcppVersion.OCPP_16, OcppVersion.OCPP_201}) {
            OcppActionMetadata.descriptors(version).forEach(descriptor -> {
                assertNotNull(descriptor.getRequestType());
                assertNotNull(descriptor.getResponseType());
                assertTrue(descriptor.getRequestSchemaPath().endsWith(descriptor.getAction() + "Request.json"));
                assertTrue(descriptor.getResponseSchemaPath().endsWith(descriptor.getAction() + "Response.json"));
            });
        }
    }
}
