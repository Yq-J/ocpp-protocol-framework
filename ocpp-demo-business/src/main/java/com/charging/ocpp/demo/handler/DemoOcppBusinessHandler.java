package com.charging.ocpp.demo.handler;

import lombok.extern.slf4j.Slf4j;
import com.charging.ocpp.core.annotation.OcppActionMapping;
import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.handler.OcppRequestContext;
import com.charging.ocpp.core.model.EmptyResponse;
import com.charging.ocpp.core.model.v16.*;
import com.charging.ocpp.core.model.v201.TransactionEventRequest;
import com.charging.ocpp.core.model.v201.TransactionEventResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

/**
 * 业务侧 OCPP 处理示例。
 * <p>
 * 作者：JYq。
 * 该类展示业务系统如何接入协议框架：开发者只需要声明 @OcppActionMapping，
 * 然后在方法参数中接收 OcppRequestContext 和强类型请求 DTO，返回强类型响应 DTO。
 * WebSocket、JSON 数组、uniqueId、CALLRESULT、CALLERROR、超时、会话查找等协议细节全部由框架处理。
 * </p>
 */
@Component
@Slf4j
public class DemoOcppBusinessHandler {
    private final AtomicInteger transactionIdGenerator = new AtomicInteger(200000);

    @OcppActionMapping(version = OcppVersion.OCPP_16, action = "BootNotification")
    public BootNotificationResponse onBoot16(OcppRequestContext context, BootNotificationRequest request) {
        log.info("收到 1.6 BootNotification，桩号={}，厂商={}，型号={}", context.getChargePointId(), request.getChargePointVendor(), request.getChargePointModel());
        BootNotificationResponse r = new BootNotificationResponse();
        r.setCurrentTime(now());
        r.setInterval(300);
        r.setStatus("Accepted");
        return r;
    }

    @OcppActionMapping(version = OcppVersion.OCPP_16, action = "DataTransfer")
    public BootNotificationResponse onDataTransfer16(OcppRequestContext context, BootNotificationRequest request) {
        log.info("收到 1.6 DataTransfer，桩号={}", context.getChargePointId());
        BootNotificationResponse r = new BootNotificationResponse();
        r.setStatus("Accepted");
        return r;
    }

    @OcppActionMapping(version = OcppVersion.OCPP_16, action = "Authorize")
    public AuthorizeResponse onAuthorize16(OcppRequestContext context, AuthorizeRequest request) {
        log.info("收到 1.6 Authorize，桩号={}，idTag={}", context.getChargePointId(), request.getIdTag());
        IdTagInfo info = new IdTagInfo();
        info.setStatus("Accepted");
        AuthorizeResponse r = new AuthorizeResponse();
        r.setIdTagInfo(info);
        return r;
    }

    @OcppActionMapping(version = OcppVersion.OCPP_16, action = "StartTransaction")
    public StartTransactionResponse onStart16(OcppRequestContext context, StartTransactionRequest request) {
        log.info("收到 1.6 StartTransaction，桩号={}，枪={}，idTag={}", context.getChargePointId(), request.getConnectorId(), request.getIdTag());
        IdTagInfo info = new IdTagInfo();
        info.setStatus("Accepted");
        StartTransactionResponse r = new StartTransactionResponse();
        r.setTransactionId(transactionIdGenerator.incrementAndGet());
        r.setIdTagInfo(info);
        return r;
    }

    @OcppActionMapping(version = OcppVersion.OCPP_16, action = "StopTransaction")
    public StopTransactionResponse onStop16(OcppRequestContext context, StopTransactionRequest request) {
        log.info("收到 1.6 StopTransaction，桩号={}，交易={}", context.getChargePointId(), request.getTransactionId());
        IdTagInfo info = new IdTagInfo();
        info.setStatus("Accepted");
        StopTransactionResponse r = new StopTransactionResponse();
        r.setIdTagInfo(info);
        return r;
    }

    @OcppActionMapping(version = OcppVersion.OCPP_16, action = "StatusNotification")
    public EmptyResponse onStatus16(OcppRequestContext context, StatusNotificationRequest request) {
        log.info("收到 1.6 StatusNotification，桩号={}，枪={}，状态={}", context.getChargePointId(), request.getConnectorId(), request.getStatus());
        return new EmptyResponse();
    }

    @OcppActionMapping(version = OcppVersion.OCPP_201, action = "TransactionEvent")
    public TransactionEventResponse onTransactionEvent201(OcppRequestContext context, TransactionEventRequest request) {
        log.info("收到 2.0.1 TransactionEvent，桩号={}，事件={}，原因={}", context.getChargePointId(), request.getEventType(), request.getTriggerReason());
        return new TransactionEventResponse();
    }

    private String now() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        f.setTimeZone(TimeZone.getTimeZone("UTC"));
        return f.format(new Date());
    }
}
