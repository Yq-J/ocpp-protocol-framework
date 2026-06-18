package com.charging.ocpp.core.gateway;

import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.model.v16.RemoteStartTransactionRequest;
import com.charging.ocpp.core.model.v16.RemoteStartTransactionResponse;
import com.charging.ocpp.core.model.v16.RemoteStopTransactionRequest;
import com.charging.ocpp.core.model.v16.RemoteStopTransactionResponse;
import com.charging.ocpp.core.model.v201.RequestStartTransactionRequest;
import com.charging.ocpp.core.model.v201.RequestStartTransactionResponse;
import com.charging.ocpp.core.model.v201.RequestStopTransactionRequest;
import com.charging.ocpp.core.model.v201.RequestStopTransactionResponse;
import java.util.concurrent.CompletableFuture;

/**
 * 业务侧下发 OCPP 命令的统一网关。
 * 作者：JYq
 */
public interface OcppGateway {
    <R> CompletableFuture<R> call(String chargePointId, OcppVersion version, String action, Object payload, Class<R> responseType);

    /**
     * OCPP 1.6 远程启动充电，避免业务代码手写 Action 字符串。
     */
    default CompletableFuture<RemoteStartTransactionResponse> remoteStartTransaction16(String chargePointId,
                                                                                        RemoteStartTransactionRequest request) {
        return call(chargePointId, OcppVersion.OCPP_16, "RemoteStartTransaction", request, RemoteStartTransactionResponse.class);
    }

    /**
     * OCPP 1.6 远程停止充电，避免业务代码手写 Action 字符串。
     */
    default CompletableFuture<RemoteStopTransactionResponse> remoteStopTransaction16(String chargePointId,
                                                                                      RemoteStopTransactionRequest request) {
        return call(chargePointId, OcppVersion.OCPP_16, "RemoteStopTransaction", request, RemoteStopTransactionResponse.class);
    }

    /**
     * OCPP 2.0.1 请求启动交易，避免业务代码手写 Action 字符串。
     */
    default CompletableFuture<RequestStartTransactionResponse> requestStartTransaction201(String chargePointId,
                                                                                           RequestStartTransactionRequest request) {
        return call(chargePointId, OcppVersion.OCPP_201, "RequestStartTransaction", request, RequestStartTransactionResponse.class);
    }

    /**
     * OCPP 2.0.1 请求停止交易，避免业务代码手写 Action 字符串。
     */
    default CompletableFuture<RequestStopTransactionResponse> requestStopTransaction201(String chargePointId,
                                                                                         RequestStopTransactionRequest request) {
        return call(chargePointId, OcppVersion.OCPP_201, "RequestStopTransaction", request, RequestStopTransactionResponse.class);
    }
}
