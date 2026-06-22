package com.charging.ocpp.core.metadata;

import com.charging.ocpp.core.enums.OcppVersion;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

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
                assertFalse(JsonNode.class.equals(descriptor.getRequestType()));
                assertFalse(JsonNode.class.equals(descriptor.getResponseType()));
                assertTrue(descriptor.getRequestSchemaPath().endsWith(descriptor.getAction() + "Request.json"));
                assertTrue(descriptor.getResponseSchemaPath().endsWith(descriptor.getAction() + "Response.json"));
            });
        }
    }

    @Test
    void actionModelGraphsDoNotUseJsonNodeFields() {
        Set<Class<?>> visited = new HashSet<>();
        for (OcppVersion version : new OcppVersion[] {OcppVersion.OCPP_16, OcppVersion.OCPP_201}) {
            OcppActionMetadata.descriptors(version).forEach(descriptor -> {
                assertNoJsonNodeFields(descriptor.getRequestType(), visited);
                assertNoJsonNodeFields(descriptor.getResponseType(), visited);
            });
        }
    }

    private void assertNoJsonNodeFields(Class<?> type, Set<Class<?>> visited) {
        if (type == null || !type.getName().startsWith("com.charging.ocpp.core.model") || !visited.add(type)) {
            return;
        }
        for (Field field : type.getDeclaredFields()) {
            assertFalse(JsonNode.class.isAssignableFrom(field.getType()),
                    () -> "Model field must not use JsonNode: " + type.getName() + "." + field.getName());
            assertGenericDoesNotUseJsonNode(field.getGenericType(), type, field.getName());
            inspectNestedModelType(field.getType(), field.getGenericType(), visited);
        }
    }

    private void assertGenericDoesNotUseJsonNode(Type genericType, Class<?> owner, String fieldName) {
        if (!(genericType instanceof ParameterizedType)) {
            return;
        }
        ParameterizedType parameterizedType = (ParameterizedType) genericType;
        for (Type typeArgument : parameterizedType.getActualTypeArguments()) {
            if (typeArgument instanceof Class<?>) {
                assertFalse(JsonNode.class.isAssignableFrom((Class<?>) typeArgument),
                        () -> "Model generic field must not use JsonNode: " + owner.getName() + "." + fieldName);
            }
            assertGenericDoesNotUseJsonNode(typeArgument, owner, fieldName);
        }
    }

    private void inspectNestedModelType(Class<?> rawType, Type genericType, Set<Class<?>> visited) {
        assertNoJsonNodeFields(rawType, visited);
        if (!(genericType instanceof ParameterizedType)) {
            return;
        }
        ParameterizedType parameterizedType = (ParameterizedType) genericType;
        for (Type typeArgument : parameterizedType.getActualTypeArguments()) {
            if (typeArgument instanceof Class<?>) {
                assertNoJsonNodeFields((Class<?>) typeArgument, visited);
            }
        }
    }
}
