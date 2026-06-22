package com.charging.ocpp.starter.service;

import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.exception.OcppErrorCode;
import com.charging.ocpp.core.exception.OcppException;
import com.charging.ocpp.core.protocol.DefaultOcppCodec;
import com.charging.ocpp.core.protocol.OcppCallError;
import com.charging.ocpp.core.protocol.OcppCallResult;
import com.charging.ocpp.core.schema.NoopOcppSchemaValidator;
import com.charging.ocpp.core.schema.OcppSchemaValidator;
import com.charging.ocpp.core.session.InMemoryOcppSessionRepository;
import com.charging.ocpp.core.session.OcppConnection;
import com.charging.ocpp.starter.autoconfigure.OcppProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class OcppTemplateLifecycleTest {
    @Test
    void shutdownFailsPendingRequests() {
        ObjectMapper mapper = new ObjectMapper();
        InMemoryOcppSessionRepository repository = new InMemoryOcppSessionRepository();
        TestOcppConnection connection = openConnection();
        repository.save(connection);
        OcppTemplate template = new OcppTemplate(repository, new DefaultOcppCodec(mapper), mapper,
                new OcppProperties(), new NoopOcppSchemaValidator());

        CompletableFuture<Object> future = template.call("CP001", OcppVersion.OCPP_16, "Heartbeat",
                mapper.createObjectNode(), Object.class);
        template.shutdown();

        assertThrows(ExecutionException.class, future::get);
    }

    @Test
    void completeResultValidatesResponsePayload() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InMemoryOcppSessionRepository repository = new InMemoryOcppSessionRepository();
        TestOcppConnection connection = openConnection();
        repository.save(connection);
        AtomicBoolean responseValidated = new AtomicBoolean(false);
        OcppSchemaValidator validator = (version, action, request, payload) -> {
            if (!request && version == OcppVersion.OCPP_16 && "Heartbeat".equals(action)) {
                responseValidated.set(true);
            }
        };
        OcppTemplate template = new OcppTemplate(repository, new DefaultOcppCodec(mapper), mapper,
                new OcppProperties(), validator);

        CompletableFuture<Object> future = template.call("CP001", OcppVersion.OCPP_16, "Heartbeat",
                mapper.createObjectNode(), Object.class);
        String uniqueId = mapper.readTree(connection.getLastText()).get(1).asText();
        template.completeResult(new OcppCallResult(uniqueId, mapper.createObjectNode()));

        future.get();
        assertTrue(responseValidated.get());
        template.shutdown();
    }

    @Test
    void responseFromUnexpectedSessionDoesNotCompletePendingRequest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InMemoryOcppSessionRepository repository = new InMemoryOcppSessionRepository();
        TestOcppConnection connection = openConnection();
        repository.save(connection);
        OcppTemplate template = new OcppTemplate(repository, new DefaultOcppCodec(mapper), mapper,
                new OcppProperties(), new NoopOcppSchemaValidator());

        CompletableFuture<Object> future = template.call("CP001", OcppVersion.OCPP_16, "Heartbeat",
                mapper.createObjectNode(), Object.class);
        String uniqueId = mapper.readTree(connection.getLastText()).get(1).asText();

        template.completeResult("other-session", new OcppCallResult(uniqueId, mapper.createObjectNode()));

        assertFalse(future.isDone());
        template.completeResult(connection.getSessionId(), new OcppCallResult(uniqueId, mapper.createObjectNode()));
        future.get();
        template.shutdown();
    }

    @Test
    void callErrorPreservesOfficialErrorCode() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InMemoryOcppSessionRepository repository = new InMemoryOcppSessionRepository();
        TestOcppConnection connection = openConnection();
        repository.save(connection);
        OcppTemplate template = new OcppTemplate(repository, new DefaultOcppCodec(mapper), mapper,
                new OcppProperties(), new NoopOcppSchemaValidator());

        CompletableFuture<Object> future = template.call("CP001", OcppVersion.OCPP_16, "Heartbeat",
                mapper.createObjectNode(), Object.class);
        String uniqueId = mapper.readTree(connection.getLastText()).get(1).asText();
        template.completeError(connection.getSessionId(), new OcppCallError(uniqueId,
                OcppErrorCode.NotImplemented.name(), "not supported", mapper.createObjectNode()));

        ExecutionException exception = assertThrows(ExecutionException.class, future::get);
        OcppException cause = (OcppException) exception.getCause();
        assertEquals(OcppErrorCode.NotImplemented, cause.getErrorCode());
        template.shutdown();
    }

    private TestOcppConnection openConnection() {
        return new TestOcppConnection();
    }

    private static class TestOcppConnection implements OcppConnection {
        private String lastText;

        @Override public String getSessionId() { return "s1"; }
        @Override public String getChargePointId() { return "CP001"; }
        @Override public OcppVersion getVersion() { return OcppVersion.OCPP_16; }
        @Override public boolean isOpen() { return true; }
        @Override public void send(String text) throws IOException { this.lastText = text; }
        String getLastText() { return lastText; }
    }
}
