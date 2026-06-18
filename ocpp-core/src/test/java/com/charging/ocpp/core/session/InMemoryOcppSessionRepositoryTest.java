package com.charging.ocpp.core.session;

import com.charging.ocpp.core.enums.OcppVersion;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class InMemoryOcppSessionRepositoryTest {
    @Test
    void closingReplacedSessionDoesNotRemoveCurrentConnection() {
        InMemoryOcppSessionRepository repository = new InMemoryOcppSessionRepository();
        TestConnection first = new TestConnection("session-1", "CP001");
        TestConnection second = new TestConnection("session-2", "CP001");

        repository.save(first);
        repository.save(second);
        repository.remove(first.getSessionId());

        assertSame(second, repository.get("CP001"));
        assertEquals(1, repository.list().size());
    }

    @Test
    void closingCurrentSessionRemovesConnection() {
        InMemoryOcppSessionRepository repository = new InMemoryOcppSessionRepository();
        TestConnection connection = new TestConnection("session-1", "CP001");

        repository.save(connection);
        repository.remove(connection.getSessionId());

        assertNull(repository.get("CP001"));
        assertEquals(0, repository.list().size());
    }

    private static class TestConnection implements OcppConnection {
        private final String sessionId;
        private final String chargePointId;

        TestConnection(String sessionId, String chargePointId) {
            this.sessionId = sessionId;
            this.chargePointId = chargePointId;
        }

        @Override
        public String getSessionId() { return sessionId; }

        @Override
        public String getChargePointId() { return chargePointId; }

        @Override
        public OcppVersion getVersion() { return OcppVersion.OCPP_16; }

        @Override
        public boolean isOpen() { return true; }

        @Override
        public void send(String text) throws IOException { }
    }
}
