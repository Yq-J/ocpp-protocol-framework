package com.charging.ocpp.core.metadata;

import com.charging.ocpp.core.enums.OcppVersion;

/**
 * Structured metadata for one OCPP Action, including DTO and JSON Schema bindings.
 */
public final class OcppActionDescriptor {
    private final OcppVersion version;
    private final String action;
    private final Class<?> requestType;
    private final Class<?> responseType;
    private final String requestSchemaPath;
    private final String responseSchemaPath;

    public OcppActionDescriptor(OcppVersion version, String action, Class<?> requestType, Class<?> responseType,
                                String requestSchemaPath, String responseSchemaPath) {
        this.version = version;
        this.action = action;
        this.requestType = requestType;
        this.responseType = responseType;
        this.requestSchemaPath = requestSchemaPath;
        this.responseSchemaPath = responseSchemaPath;
    }

    public OcppVersion getVersion() { return version; }
    public String getAction() { return action; }
    public Class<?> getRequestType() { return requestType; }
    public Class<?> getResponseType() { return responseType; }
    public String getRequestSchemaPath() { return requestSchemaPath; }
    public String getResponseSchemaPath() { return responseSchemaPath; }

    public String schemaPath(boolean request) { return request ? requestSchemaPath : responseSchemaPath; }
    public Class<?> payloadType(boolean request) { return request ? requestType : responseType; }
}
