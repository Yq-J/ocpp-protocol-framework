package com.charging.ocpp.core.protocol;

import com.charging.ocpp.core.exception.OcppException;
import com.charging.ocpp.core.model.EmptyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultOcppCodecTest {
    private final DefaultOcppCodec codec = new DefaultOcppCodec(new ObjectMapper());

    @Test
    void rejectsNonIntegerMessageType() {
        assertThrows(OcppException.class, () -> codec.decode("[\"2\",\"uid\",\"Heartbeat\",{}]"));
    }

    @Test
    void rejectsNonObjectPayload() {
        assertThrows(OcppException.class, () -> codec.decode("[2,\"uid\",\"Heartbeat\",[]]"));
    }

    @Test
    void rejectsBlankUniqueId() {
        assertThrows(OcppException.class, () -> codec.decode("[2,\"\",\"Heartbeat\",{}]"));
    }

    @Test
    void encodesEmptyResponseAsEmptyObjectPayload() {
        assertEquals("[3,\"uid\",{}]", codec.encodeCallResult("uid", new EmptyResponse()));
    }
}
