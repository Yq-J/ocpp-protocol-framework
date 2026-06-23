package com.charging.ocpp.core.handler;

import com.charging.ocpp.core.enums.OcppVersion;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OcppHandlerRegistryTest {
    @Test
    void duplicateHandlerRegistrationFailsFast() {
        OcppHandlerRegistry registry = new OcppHandlerRegistry();
        OcppActionHandler first = handler("Heartbeat", false);
        OcppActionHandler second = handler("Heartbeat", false);

        registry.register(first);

        assertThrows(IllegalStateException.class, () -> registry.register(second));
        assertSame(first, registry.get(OcppVersion.OCPP_16, "Heartbeat"));
    }

    @Test
    void businessHandlerReplacesFrameworkDefaultHandler() {
        OcppHandlerRegistry registry = new OcppHandlerRegistry();
        OcppActionHandler frameworkDefault = handler("Heartbeat", true);
        OcppActionHandler business = handler("Heartbeat", false);

        registry.register(frameworkDefault);
        registry.register(business);

        assertSame(business, registry.get(OcppVersion.OCPP_16, "Heartbeat"));
    }

    @Test
    void frameworkDefaultDoesNotReplaceBusinessHandler() {
        OcppHandlerRegistry registry = new OcppHandlerRegistry();
        OcppActionHandler business = handler("Heartbeat", false);
        OcppActionHandler frameworkDefault = handler("Heartbeat", true);

        registry.register(business);
        registry.register(frameworkDefault);

        assertSame(business, registry.get(OcppVersion.OCPP_16, "Heartbeat"));
    }

    private OcppActionHandler handler(String action, boolean frameworkDefault) {
        return new OcppActionHandler() {
            @Override public OcppVersion version() { return OcppVersion.OCPP_16; }
            @Override public String action() { return action; }
            @Override public Object handle(OcppRequestContext context, JsonNode payload) { return null; }
            @Override public boolean frameworkDefault() { return frameworkDefault; }
        };
    }
}
