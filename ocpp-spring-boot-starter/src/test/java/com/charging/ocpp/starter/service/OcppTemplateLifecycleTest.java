package com.charging.ocpp.starter.service;

import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.protocol.DefaultOcppCodec;
import com.charging.ocpp.core.schema.NoopOcppSchemaValidator;
import com.charging.ocpp.core.session.InMemoryOcppSessionRepository;
import com.charging.ocpp.core.session.OcppConnection;
import com.charging.ocpp.starter.autoconfigure.OcppProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class OcppTemplateLifecycleTest {
    @Test
    void shutdownFailsPendingRequests() {
        ObjectMapper mapper = new ObjectMapper();
        InMemoryOcppSessionRepository repository = new InMemoryOcppSessionRepository();
        repository.save(new OcppConnection() {
            @Override public String getSessionId() { return "s1"; }
            @Override public String getChargePointId() { return "CP001"; }
            @Override public OcppVersion getVersion() { return OcppVersion.OCPP_16; }
            @Override public boolean isOpen() { return true; }
            @Override public void send(String text) throws IOException { }
        });
        OcppTemplate template = new OcppTemplate(repository, new DefaultOcppCodec(mapper), mapper,
                new OcppProperties(), new NoopOcppSchemaValidator());

        CompletableFuture<Object> future = template.call("CP001", OcppVersion.OCPP_16, "Heartbeat",
                mapper.createObjectNode(), Object.class);
        template.shutdown();

        assertThrows(ExecutionException.class, future::get);
    }
}
