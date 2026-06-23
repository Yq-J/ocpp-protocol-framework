package com.charging.ocpp.starter.websocket;

import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.exception.OcppErrorCode;
import com.charging.ocpp.core.exception.OcppException;
import com.charging.ocpp.core.handler.OcppActionHandler;
import com.charging.ocpp.core.handler.OcppHandlerRegistry;
import com.charging.ocpp.core.handler.OcppRequestContext;
import com.charging.ocpp.core.protocol.DefaultOcppCodec;
import com.charging.ocpp.core.schema.NoopOcppSchemaValidator;
import com.charging.ocpp.core.session.InMemoryOcppSessionRepository;
import com.charging.ocpp.core.session.OcppSessionRepository;
import com.charging.ocpp.starter.autoconfigure.OcppProperties;
import com.charging.ocpp.starter.service.OcppTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.TextMessage;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OcppWebSocketHandlerAsyncTest {

    @Test
    void asyncHandlerDoesNotBlockAndSendsCallResultWhenFutureCompletes() throws Exception {
        CompletableFuture<Object> future = new CompletableFuture<>();
        OcppHandlerRegistry registry = new OcppHandlerRegistry();
        registry.register(handler("Heartbeat", (c, p) -> future));
        FakeWebSocketSession session = new FakeWebSocketSession("s1", "ocpp1.6", "CP001");
        OcppWebSocketHandler handler = newHandler(registry);

        handler.handleTextMessage(session, new TextMessage("[2,\"u1\",\"Heartbeat\",{}]"));

        // Future 未完成时不应阻塞，也不应发送任何响应。
        assertNull(session.getLastText());

        future.complete(new ObjectMapper().createObjectNode());
        JsonNode frame = new ObjectMapper().readTree(session.getLastText());
        assertEquals(3, frame.get(0).asInt());
        assertEquals("u1", frame.get(1).asText());
    }

    @Test
    void asyncHandlerExceptionBecomesCallError() throws Exception {
        OcppHandlerRegistry registry = new OcppHandlerRegistry();
        registry.register(handler("Heartbeat", (c, p) -> {
            CompletableFuture<Object> failed = new CompletableFuture<>();
            failed.completeExceptionally(new OcppException(OcppErrorCode.NotSupported, "nope"));
            return failed;
        }));
        FakeWebSocketSession session = new FakeWebSocketSession("s1", "ocpp1.6", "CP001");
        OcppWebSocketHandler handler = newHandler(registry);

        handler.handleTextMessage(session, new TextMessage("[2,\"u2\",\"Heartbeat\",{}]"));

        JsonNode frame = new ObjectMapper().readTree(session.getLastText());
        assertEquals(4, frame.get(0).asInt());
        assertEquals("u2", frame.get(1).asText());
        assertEquals(OcppErrorCode.NotSupported.name(), frame.get(2).asText());
    }

    @Test
    void synchronousHandlerStillReturnsCallResult() throws Exception {
        OcppHandlerRegistry registry = new OcppHandlerRegistry();
        registry.register(handler("Heartbeat", (c, p) -> new ObjectMapper().createObjectNode()));
        FakeWebSocketSession session = new FakeWebSocketSession("s1", "ocpp1.6", "CP001");
        OcppWebSocketHandler handler = newHandler(registry);

        handler.handleTextMessage(session, new TextMessage("[2,\"u3\",\"Heartbeat\",{}]"));

        JsonNode frame = new ObjectMapper().readTree(session.getLastText());
        assertEquals(3, frame.get(0).asInt());
        assertEquals("u3", frame.get(1).asText());
    }

    private OcppWebSocketHandler newHandler(OcppHandlerRegistry registry) {
        ObjectMapper mapper = new ObjectMapper();
        OcppSessionRepository repository = new InMemoryOcppSessionRepository();
        OcppProperties properties = new OcppProperties();
        OcppTemplate template = new OcppTemplate(repository, new DefaultOcppCodec(mapper), mapper,
                properties, new NoopOcppSchemaValidator());
        template.shutdown();
        return new OcppWebSocketHandler(new DefaultOcppCodec(mapper), registry, repository,
                new NoopOcppSchemaValidator(), template, properties, mapper);
    }

    private OcppActionHandler handler(String action, Invoker invoker) {
        return new OcppActionHandler() {
            @Override
            public OcppVersion version() {
                return OcppVersion.OCPP_16;
            }

            @Override
            public String action() {
                return action;
            }

            @Override
            public Object handle(OcppRequestContext context, JsonNode payload) {
                return invoker.apply(context, payload);
            }
        };
    }

    private interface Invoker {
        Object apply(OcppRequestContext context, JsonNode payload);
    }
}
