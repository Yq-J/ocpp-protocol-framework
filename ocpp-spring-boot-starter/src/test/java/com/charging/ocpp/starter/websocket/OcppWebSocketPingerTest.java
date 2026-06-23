package com.charging.ocpp.starter.websocket;

import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.session.InMemoryOcppSessionRepository;
import com.charging.ocpp.starter.autoconfigure.OcppProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OcppWebSocketPingerTest {

    @Test
    void sweepClosesSessionWhenPingFails() {
        InMemoryOcppSessionRepository repository = new InMemoryOcppSessionRepository();
        FakeWebSocketSession session = new FakeWebSocketSession("s1", "ocpp1.6", "CP001");
        session.setFailOnPing(true);
        repository.save(new SpringOcppConnection(session, "CP001", OcppVersion.OCPP_16));

        OcppProperties properties = new OcppProperties();
        properties.setPingIntervalSeconds(240);
        OcppWebSocketPinger pinger = new OcppWebSocketPinger(repository, properties);
        try {
            pinger.sweep();
            assertTrue(session.isCloseCalled());
        } finally {
            pinger.destroy();
        }
    }

    @Test
    void sweepKeepsAliveSessionOpen() {
        InMemoryOcppSessionRepository repository = new InMemoryOcppSessionRepository();
        FakeWebSocketSession session = new FakeWebSocketSession("s1", "ocpp1.6", "CP001");
        repository.save(new SpringOcppConnection(session, "CP001", OcppVersion.OCPP_16));

        OcppProperties properties = new OcppProperties();
        properties.setPingIntervalSeconds(240);
        OcppWebSocketPinger pinger = new OcppWebSocketPinger(repository, properties);
        try {
            pinger.sweep();
            assertFalse(session.isCloseCalled());
            assertTrue(session.isOpen());
        } finally {
            pinger.destroy();
        }
    }

    @Test
    void pingerDisabledWhenIntervalNotPositive() {
        OcppProperties properties = new OcppProperties();
        properties.setPingIntervalSeconds(0);
        OcppWebSocketPinger pinger = new OcppWebSocketPinger(new InMemoryOcppSessionRepository(), properties);
        try {
            assertFalse(pinger.isEnabled());
        } finally {
            pinger.destroy();
        }
    }
}
