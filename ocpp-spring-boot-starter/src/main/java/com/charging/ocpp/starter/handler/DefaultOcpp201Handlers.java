package com.charging.ocpp.starter.handler;

import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.handler.OcppActionHandler;
import com.charging.ocpp.core.handler.OcppRequestContext;
import com.charging.ocpp.core.model.EmptyResponse;
import com.charging.ocpp.core.model.v201.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * OCPP 2.0.1 默认处理器工厂。
 * 作者：JYq
 */
@Getter
public class DefaultOcpp201Handlers {
    private final ObjectMapper objectMapper;

    public DefaultOcpp201Handlers(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public OcppActionHandler boot() {
        return handler("BootNotification", (c, p) -> bootResponse());
    }

    public OcppActionHandler heartbeat() {
        return handler("Heartbeat", (c, p) -> heartbeatResponse());
    }

    public OcppActionHandler authorize() {
        return handler("Authorize", (c, p) -> authorizeResponse());
    }

    public OcppActionHandler status() {
        return handler("StatusNotification", (c, p) -> new EmptyResponse());
    }

    public OcppActionHandler meterValues() {
        return handler("MeterValues", (c, p) -> new EmptyResponse());
    }

    public OcppActionHandler transactionEvent() {
        return handler("TransactionEvent", (c, p) -> new TransactionEventResponse());
    }

    private OcppActionHandler handler(final String action, final Invoker invoker) {
        return new OcppActionHandler() {
            @Override
            public OcppVersion version() {
                return OcppVersion.OCPP_201;
            }

            @Override
            public String action() {
                return action;
            }

            @Override
            public Object handle(OcppRequestContext context, JsonNode payload) throws Exception {
                return invoker.apply(context, payload);
            }
        };
    }

    private BootNotificationResponse bootResponse() {
        BootNotificationResponse r = new BootNotificationResponse();
        r.setCurrentTime(now());
        r.setInterval(300);
        r.setStatus("Accepted");
        return r;
    }

    private HeartbeatResponse heartbeatResponse() {
        HeartbeatResponse r = new HeartbeatResponse();
        r.setCurrentTime(now());
        return r;
    }

    private AuthorizeResponse authorizeResponse() {
        IdTokenInfo info = new IdTokenInfo();
        info.setStatus("Accepted");
        AuthorizeResponse r = new AuthorizeResponse();
        r.setIdTokenInfo(info);
        return r;
    }

    private String now() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        f.setTimeZone(TimeZone.getTimeZone("UTC"));
        return f.format(new Date());
    }

    private interface Invoker {
        Object apply(OcppRequestContext c, JsonNode p) throws Exception;
    }
}
