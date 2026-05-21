package com.charging.ocpp.starter.handler;

import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.handler.OcppActionHandler;
import com.charging.ocpp.core.handler.OcppRequestContext;
import com.charging.ocpp.core.model.EmptyResponse;
import com.charging.ocpp.core.model.v16.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * OCPP 1.6 默认处理器工厂。
 * <p>
 * 作者：JYq
 * 说明：默认行为仅保证框架可运行；业务层可以通过 @OcppActionMapping 覆盖同 Action。
 * </p>
 */
public class DefaultOcpp16Handlers {
    @Getter
    private final ObjectMapper objectMapper;
    private final AtomicInteger transactionIdGenerator = new AtomicInteger(100000);

    public DefaultOcpp16Handlers(ObjectMapper objectMapper) {
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

    public OcppActionHandler startTransaction() {
        return handler("StartTransaction", (c, p) -> startTransactionResponse());
    }

    public OcppActionHandler stopTransaction() {
        return handler("StopTransaction", (c, p) -> stopTransactionResponse());
    }

    private OcppActionHandler handler(final String action, final Invoker invoker) {
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
        IdTagInfo info = new IdTagInfo();
        info.setStatus("Accepted");
        AuthorizeResponse r = new AuthorizeResponse();
        r.setIdTagInfo(info);
        return r;
    }

    private StartTransactionResponse startTransactionResponse() {
        IdTagInfo info = new IdTagInfo();
        info.setStatus("Accepted");
        StartTransactionResponse r = new StartTransactionResponse();
        r.setTransactionId(transactionIdGenerator.incrementAndGet());
        r.setIdTagInfo(info);
        return r;
    }

    private StopTransactionResponse stopTransactionResponse() {
        IdTagInfo info = new IdTagInfo();
        info.setStatus("Accepted");
        StopTransactionResponse r = new StopTransactionResponse();
        r.setIdTagInfo(info);
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
