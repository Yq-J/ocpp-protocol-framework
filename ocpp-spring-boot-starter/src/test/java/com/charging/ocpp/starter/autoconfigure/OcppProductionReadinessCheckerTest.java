package com.charging.ocpp.starter.autoconfigure;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OcppProductionReadinessCheckerTest {
    @Test
    void inspectReportsUnsafeDefaults() {
        OcppProperties properties = new OcppProperties();

        assertTrue(new OcppProductionReadinessChecker(properties).inspect().size() >= 2);
    }

    @Test
    void inspectPassesForHardenedConfiguration() {
        OcppProperties properties = new OcppProperties();
        properties.setAllowedOrigins(Collections.singletonList("https://csms.example.com"));
        properties.setRequireAuthToken(true);
        properties.getChargePointTokens().put("CP001", "strong-token");
        properties.setAllowUnknownActions(false);
        properties.setEnableDefaultHandlers(false);
        properties.setDuplicateConnectionPolicy(OcppProperties.DuplicateConnectionPolicy.CLOSE_OLD);
        properties.setMaxTextMessageBytes(262144);
        properties.setConnectionTimeoutSeconds(60);

        assertEquals(Collections.emptyList(), new OcppProductionReadinessChecker(properties).inspect());
    }
}
