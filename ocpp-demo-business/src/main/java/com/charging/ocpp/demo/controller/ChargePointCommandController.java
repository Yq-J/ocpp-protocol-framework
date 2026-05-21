package com.charging.ocpp.demo.controller;

import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.model.v16.RemoteStartTransactionRequest;
import com.charging.ocpp.core.model.v16.RemoteStartTransactionResponse;
import com.charging.ocpp.core.model.v16.RemoteStopTransactionRequest;
import com.charging.ocpp.core.model.v16.RemoteStopTransactionResponse;
import com.charging.ocpp.core.model.v201.RequestStartTransactionRequest;
import com.charging.ocpp.core.model.v201.RequestStartTransactionResponse;
import com.charging.ocpp.starter.service.OcppTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 平台向充电桩下发命令示例。
 * 作者：JYq
 */
@RestController
@RequestMapping("/api/charge-points")
public class ChargePointCommandController {
    private final OcppTemplate ocppTemplate;

    public ChargePointCommandController(OcppTemplate ocppTemplate) {
        this.ocppTemplate = ocppTemplate;
    }

    @PostMapping("/{chargePointId}/ocpp16/remote-start")
    public CompletableFuture<RemoteStartTransactionResponse> remoteStart16(@PathVariable String chargePointId,
                                                                            @RequestBody RemoteStartTransactionRequest request) {
        return ocppTemplate.call(chargePointId, OcppVersion.OCPP_16, "RemoteStartTransaction", request, RemoteStartTransactionResponse.class);
    }

    @PostMapping("/{chargePointId}/ocpp16/remote-stop")
    public CompletableFuture<RemoteStopTransactionResponse> remoteStop16(@PathVariable String chargePointId,
                                                                          @RequestBody RemoteStopTransactionRequest request) {
        return ocppTemplate.call(chargePointId, OcppVersion.OCPP_16, "RemoteStopTransaction", request, RemoteStopTransactionResponse.class);
    }

    @PostMapping("/{chargePointId}/ocpp201/request-start")
    public CompletableFuture<RequestStartTransactionResponse> requestStart201(@PathVariable String chargePointId,
                                                                              @RequestBody RequestStartTransactionRequest request) {
        return ocppTemplate.call(chargePointId, OcppVersion.OCPP_201, "RequestStartTransaction", request, RequestStartTransactionResponse.class);
    }

    @PostMapping("/{chargePointId}/ping")
    public Map<String, Object> ping(@PathVariable String chargePointId) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("chargePointId", chargePointId);
        data.put("message", "业务服务已启动；请先让充电桩通过 WebSocket 连接 /ocpp/{chargePointId}");
        return data;
    }
}
