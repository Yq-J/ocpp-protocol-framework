package com.charging.ocpp.core.metadata;
import com.charging.ocpp.core.enums.OcppVersion;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * OCPP Action metadata: known actions plus request/response DTO and official JSON Schema bindings.
 */
public final class OcppActionMetadata {
    private static final Map<OcppVersion, Set<String>> ACTIONS_BY_VERSION = new EnumMap<>(OcppVersion.class);
    private static final Map<OcppVersion, Map<String, OcppActionDescriptor>> DESCRIPTORS_BY_VERSION = new EnumMap<>(OcppVersion.class);

    public static final List<String> OCPP_16_ACTIONS = unmodifiableList(
            "Authorize",
            "BootNotification",
            "CancelReservation",
            "ChangeAvailability",
            "ChangeConfiguration",
            "ClearCache",
            "ClearChargingProfile",
            "DataTransfer",
            "DiagnosticsStatusNotification",
            "FirmwareStatusNotification",
            "GetCompositeSchedule",
            "GetConfiguration",
            "GetDiagnostics",
            "GetLocalListVersion",
            "Heartbeat",
            "MeterValues",
            "RemoteStartTransaction",
            "RemoteStopTransaction",
            "ReserveNow",
            "Reset",
            "SendLocalList",
            "SetChargingProfile",
            "StartTransaction",
            "StatusNotification",
            "StopTransaction",
            "TriggerMessage",
            "UnlockConnector",
            "UpdateFirmware"
    );

    public static final List<String> OCPP_201_ACTIONS = unmodifiableList(
            "Authorize",
            "BootNotification",
            "CancelReservation",
            "CertificateSigned",
            "ChangeAvailability",
            "ClearCache",
            "ClearChargingProfile",
            "ClearDisplayMessage",
            "ClearVariableMonitoring",
            "ClearedChargingLimit",
            "CostUpdated",
            "CustomerInformation",
            "DataTransfer",
            "DeleteCertificate",
            "FirmwareStatusNotification",
            "Get15118EVCertificate",
            "GetBaseReport",
            "GetCertificateStatus",
            "GetChargingProfiles",
            "GetCompositeSchedule",
            "GetDisplayMessages",
            "GetInstalledCertificateIds",
            "GetLocalListVersion",
            "GetLog",
            "GetMonitoringReport",
            "GetReport",
            "GetTransactionStatus",
            "GetVariables",
            "Heartbeat",
            "InstallCertificate",
            "LogStatusNotification",
            "MeterValues",
            "NotifyChargingLimit",
            "NotifyCustomerInformation",
            "NotifyDisplayMessages",
            "NotifyEVChargingNeeds",
            "NotifyEVChargingSchedule",
            "NotifyEvent",
            "NotifyMonitoringReport",
            "NotifyReport",
            "PublishFirmware",
            "PublishFirmwareStatusNotification",
            "ReportChargingProfiles",
            "RequestStartTransaction",
            "RequestStopTransaction",
            "ReservationStatusUpdate",
            "ReserveNow",
            "Reset",
            "SecurityEventNotification",
            "SendLocalList",
            "SetChargingProfile",
            "SetDisplayMessage",
            "SetMonitoringBase",
            "SetMonitoringLevel",
            "SetNetworkProfile",
            "SetVariableMonitoring",
            "SetVariables",
            "SignCertificate",
            "StatusNotification",
            "TransactionEvent",
            "TriggerMessage",
            "UnlockConnector",
            "UnpublishFirmware",
            "UpdateFirmware"
    );

    static {
        registerVersion(OcppVersion.OCPP_16, OCPP_16_ACTIONS, new OcppActionDescriptor[] {
                descriptor(OcppVersion.OCPP_16, "Authorize", com.charging.ocpp.core.model.v16.AuthorizeRequest.class, com.charging.ocpp.core.model.v16.AuthorizeResponse.class, "schemas/ocpp1.6/AuthorizeRequest.json", "schemas/ocpp1.6/AuthorizeResponse.json"),
                descriptor(OcppVersion.OCPP_16, "BootNotification", com.charging.ocpp.core.model.v16.BootNotificationRequest.class, com.charging.ocpp.core.model.v16.BootNotificationResponse.class, "schemas/ocpp1.6/BootNotificationRequest.json", "schemas/ocpp1.6/BootNotificationResponse.json"),
                descriptor(OcppVersion.OCPP_16, "CancelReservation", JsonNode.class, JsonNode.class, "schemas/ocpp1.6/CancelReservationRequest.json", "schemas/ocpp1.6/CancelReservationResponse.json"),
                descriptor(OcppVersion.OCPP_16, "ChangeAvailability", JsonNode.class, JsonNode.class, "schemas/ocpp1.6/ChangeAvailabilityRequest.json", "schemas/ocpp1.6/ChangeAvailabilityResponse.json"),
                descriptor(OcppVersion.OCPP_16, "ChangeConfiguration", JsonNode.class, JsonNode.class, "schemas/ocpp1.6/ChangeConfigurationRequest.json", "schemas/ocpp1.6/ChangeConfigurationResponse.json"),
                descriptor(OcppVersion.OCPP_16, "ClearCache", JsonNode.class, JsonNode.class, "schemas/ocpp1.6/ClearCacheRequest.json", "schemas/ocpp1.6/ClearCacheResponse.json"),
                descriptor(OcppVersion.OCPP_16, "ClearChargingProfile", JsonNode.class, JsonNode.class, "schemas/ocpp1.6/ClearChargingProfileRequest.json", "schemas/ocpp1.6/ClearChargingProfileResponse.json"),
                descriptor(OcppVersion.OCPP_16, "DataTransfer", JsonNode.class, JsonNode.class, "schemas/ocpp1.6/DataTransferRequest.json", "schemas/ocpp1.6/DataTransferResponse.json"),
                descriptor(OcppVersion.OCPP_16, "DiagnosticsStatusNotification", JsonNode.class, JsonNode.class, "schemas/ocpp1.6/DiagnosticsStatusNotificationRequest.json", "schemas/ocpp1.6/DiagnosticsStatusNotificationResponse.json"),
                descriptor(OcppVersion.OCPP_16, "FirmwareStatusNotification", JsonNode.class, JsonNode.class, "schemas/ocpp1.6/FirmwareStatusNotificationRequest.json", "schemas/ocpp1.6/FirmwareStatusNotificationResponse.json"),
                descriptor(OcppVersion.OCPP_16, "GetCompositeSchedule", JsonNode.class, JsonNode.class, "schemas/ocpp1.6/GetCompositeScheduleRequest.json", "schemas/ocpp1.6/GetCompositeScheduleResponse.json"),
                descriptor(OcppVersion.OCPP_16, "GetConfiguration", JsonNode.class, JsonNode.class, "schemas/ocpp1.6/GetConfigurationRequest.json", "schemas/ocpp1.6/GetConfigurationResponse.json"),
                descriptor(OcppVersion.OCPP_16, "GetDiagnostics", JsonNode.class, JsonNode.class, "schemas/ocpp1.6/GetDiagnosticsRequest.json", "schemas/ocpp1.6/GetDiagnosticsResponse.json"),
                descriptor(OcppVersion.OCPP_16, "GetLocalListVersion", JsonNode.class, JsonNode.class, "schemas/ocpp1.6/GetLocalListVersionRequest.json", "schemas/ocpp1.6/GetLocalListVersionResponse.json"),
                descriptor(OcppVersion.OCPP_16, "Heartbeat", com.charging.ocpp.core.model.v16.HeartbeatRequest.class, com.charging.ocpp.core.model.v16.HeartbeatResponse.class, "schemas/ocpp1.6/HeartbeatRequest.json", "schemas/ocpp1.6/HeartbeatResponse.json"),
                descriptor(OcppVersion.OCPP_16, "MeterValues", com.charging.ocpp.core.model.v16.MeterValuesRequest.class, com.charging.ocpp.core.model.EmptyResponse.class, "schemas/ocpp1.6/MeterValuesRequest.json", "schemas/ocpp1.6/MeterValuesResponse.json"),
                descriptor(OcppVersion.OCPP_16, "RemoteStartTransaction", com.charging.ocpp.core.model.v16.RemoteStartTransactionRequest.class, com.charging.ocpp.core.model.v16.RemoteStartTransactionResponse.class, "schemas/ocpp1.6/RemoteStartTransactionRequest.json", "schemas/ocpp1.6/RemoteStartTransactionResponse.json"),
                descriptor(OcppVersion.OCPP_16, "RemoteStopTransaction", com.charging.ocpp.core.model.v16.RemoteStopTransactionRequest.class, com.charging.ocpp.core.model.v16.RemoteStopTransactionResponse.class, "schemas/ocpp1.6/RemoteStopTransactionRequest.json", "schemas/ocpp1.6/RemoteStopTransactionResponse.json"),
                descriptor(OcppVersion.OCPP_16, "ReserveNow", JsonNode.class, JsonNode.class, "schemas/ocpp1.6/ReserveNowRequest.json", "schemas/ocpp1.6/ReserveNowResponse.json"),
                descriptor(OcppVersion.OCPP_16, "Reset", JsonNode.class, JsonNode.class, "schemas/ocpp1.6/ResetRequest.json", "schemas/ocpp1.6/ResetResponse.json"),
                descriptor(OcppVersion.OCPP_16, "SendLocalList", JsonNode.class, JsonNode.class, "schemas/ocpp1.6/SendLocalListRequest.json", "schemas/ocpp1.6/SendLocalListResponse.json"),
                descriptor(OcppVersion.OCPP_16, "SetChargingProfile", JsonNode.class, JsonNode.class, "schemas/ocpp1.6/SetChargingProfileRequest.json", "schemas/ocpp1.6/SetChargingProfileResponse.json"),
                descriptor(OcppVersion.OCPP_16, "StartTransaction", com.charging.ocpp.core.model.v16.StartTransactionRequest.class, com.charging.ocpp.core.model.v16.StartTransactionResponse.class, "schemas/ocpp1.6/StartTransactionRequest.json", "schemas/ocpp1.6/StartTransactionResponse.json"),
                descriptor(OcppVersion.OCPP_16, "StatusNotification", com.charging.ocpp.core.model.v16.StatusNotificationRequest.class, com.charging.ocpp.core.model.EmptyResponse.class, "schemas/ocpp1.6/StatusNotificationRequest.json", "schemas/ocpp1.6/StatusNotificationResponse.json"),
                descriptor(OcppVersion.OCPP_16, "StopTransaction", com.charging.ocpp.core.model.v16.StopTransactionRequest.class, com.charging.ocpp.core.model.v16.StopTransactionResponse.class, "schemas/ocpp1.6/StopTransactionRequest.json", "schemas/ocpp1.6/StopTransactionResponse.json"),
                descriptor(OcppVersion.OCPP_16, "TriggerMessage", JsonNode.class, JsonNode.class, "schemas/ocpp1.6/TriggerMessageRequest.json", "schemas/ocpp1.6/TriggerMessageResponse.json"),
                descriptor(OcppVersion.OCPP_16, "UnlockConnector", JsonNode.class, JsonNode.class, "schemas/ocpp1.6/UnlockConnectorRequest.json", "schemas/ocpp1.6/UnlockConnectorResponse.json"),
                descriptor(OcppVersion.OCPP_16, "UpdateFirmware", JsonNode.class, JsonNode.class, "schemas/ocpp1.6/UpdateFirmwareRequest.json", "schemas/ocpp1.6/UpdateFirmwareResponse.json")
        });
        registerVersion(OcppVersion.OCPP_201, OCPP_201_ACTIONS, new OcppActionDescriptor[] {
                descriptor(OcppVersion.OCPP_201, "Authorize", com.charging.ocpp.core.model.v201.AuthorizeRequest.class, com.charging.ocpp.core.model.v201.AuthorizeResponse.class, "schemas/ocpp2.0.1/AuthorizeRequest.json", "schemas/ocpp2.0.1/AuthorizeResponse.json"),
                descriptor(OcppVersion.OCPP_201, "BootNotification", com.charging.ocpp.core.model.v201.BootNotificationRequest.class, com.charging.ocpp.core.model.v201.BootNotificationResponse.class, "schemas/ocpp2.0.1/BootNotificationRequest.json", "schemas/ocpp2.0.1/BootNotificationResponse.json"),
                descriptor(OcppVersion.OCPP_201, "CancelReservation", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/CancelReservationRequest.json", "schemas/ocpp2.0.1/CancelReservationResponse.json"),
                descriptor(OcppVersion.OCPP_201, "CertificateSigned", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/CertificateSignedRequest.json", "schemas/ocpp2.0.1/CertificateSignedResponse.json"),
                descriptor(OcppVersion.OCPP_201, "ChangeAvailability", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/ChangeAvailabilityRequest.json", "schemas/ocpp2.0.1/ChangeAvailabilityResponse.json"),
                descriptor(OcppVersion.OCPP_201, "ClearCache", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/ClearCacheRequest.json", "schemas/ocpp2.0.1/ClearCacheResponse.json"),
                descriptor(OcppVersion.OCPP_201, "ClearChargingProfile", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/ClearChargingProfileRequest.json", "schemas/ocpp2.0.1/ClearChargingProfileResponse.json"),
                descriptor(OcppVersion.OCPP_201, "ClearDisplayMessage", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/ClearDisplayMessageRequest.json", "schemas/ocpp2.0.1/ClearDisplayMessageResponse.json"),
                descriptor(OcppVersion.OCPP_201, "ClearVariableMonitoring", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/ClearVariableMonitoringRequest.json", "schemas/ocpp2.0.1/ClearVariableMonitoringResponse.json"),
                descriptor(OcppVersion.OCPP_201, "ClearedChargingLimit", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/ClearedChargingLimitRequest.json", "schemas/ocpp2.0.1/ClearedChargingLimitResponse.json"),
                descriptor(OcppVersion.OCPP_201, "CostUpdated", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/CostUpdatedRequest.json", "schemas/ocpp2.0.1/CostUpdatedResponse.json"),
                descriptor(OcppVersion.OCPP_201, "CustomerInformation", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/CustomerInformationRequest.json", "schemas/ocpp2.0.1/CustomerInformationResponse.json"),
                descriptor(OcppVersion.OCPP_201, "DataTransfer", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/DataTransferRequest.json", "schemas/ocpp2.0.1/DataTransferResponse.json"),
                descriptor(OcppVersion.OCPP_201, "DeleteCertificate", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/DeleteCertificateRequest.json", "schemas/ocpp2.0.1/DeleteCertificateResponse.json"),
                descriptor(OcppVersion.OCPP_201, "FirmwareStatusNotification", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/FirmwareStatusNotificationRequest.json", "schemas/ocpp2.0.1/FirmwareStatusNotificationResponse.json"),
                descriptor(OcppVersion.OCPP_201, "Get15118EVCertificate", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/Get15118EVCertificateRequest.json", "schemas/ocpp2.0.1/Get15118EVCertificateResponse.json"),
                descriptor(OcppVersion.OCPP_201, "GetBaseReport", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/GetBaseReportRequest.json", "schemas/ocpp2.0.1/GetBaseReportResponse.json"),
                descriptor(OcppVersion.OCPP_201, "GetCertificateStatus", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/GetCertificateStatusRequest.json", "schemas/ocpp2.0.1/GetCertificateStatusResponse.json"),
                descriptor(OcppVersion.OCPP_201, "GetChargingProfiles", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/GetChargingProfilesRequest.json", "schemas/ocpp2.0.1/GetChargingProfilesResponse.json"),
                descriptor(OcppVersion.OCPP_201, "GetCompositeSchedule", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/GetCompositeScheduleRequest.json", "schemas/ocpp2.0.1/GetCompositeScheduleResponse.json"),
                descriptor(OcppVersion.OCPP_201, "GetDisplayMessages", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/GetDisplayMessagesRequest.json", "schemas/ocpp2.0.1/GetDisplayMessagesResponse.json"),
                descriptor(OcppVersion.OCPP_201, "GetInstalledCertificateIds", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/GetInstalledCertificateIdsRequest.json", "schemas/ocpp2.0.1/GetInstalledCertificateIdsResponse.json"),
                descriptor(OcppVersion.OCPP_201, "GetLocalListVersion", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/GetLocalListVersionRequest.json", "schemas/ocpp2.0.1/GetLocalListVersionResponse.json"),
                descriptor(OcppVersion.OCPP_201, "GetLog", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/GetLogRequest.json", "schemas/ocpp2.0.1/GetLogResponse.json"),
                descriptor(OcppVersion.OCPP_201, "GetMonitoringReport", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/GetMonitoringReportRequest.json", "schemas/ocpp2.0.1/GetMonitoringReportResponse.json"),
                descriptor(OcppVersion.OCPP_201, "GetReport", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/GetReportRequest.json", "schemas/ocpp2.0.1/GetReportResponse.json"),
                descriptor(OcppVersion.OCPP_201, "GetTransactionStatus", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/GetTransactionStatusRequest.json", "schemas/ocpp2.0.1/GetTransactionStatusResponse.json"),
                descriptor(OcppVersion.OCPP_201, "GetVariables", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/GetVariablesRequest.json", "schemas/ocpp2.0.1/GetVariablesResponse.json"),
                descriptor(OcppVersion.OCPP_201, "Heartbeat", com.charging.ocpp.core.model.v201.HeartbeatRequest.class, com.charging.ocpp.core.model.v201.HeartbeatResponse.class, "schemas/ocpp2.0.1/HeartbeatRequest.json", "schemas/ocpp2.0.1/HeartbeatResponse.json"),
                descriptor(OcppVersion.OCPP_201, "InstallCertificate", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/InstallCertificateRequest.json", "schemas/ocpp2.0.1/InstallCertificateResponse.json"),
                descriptor(OcppVersion.OCPP_201, "LogStatusNotification", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/LogStatusNotificationRequest.json", "schemas/ocpp2.0.1/LogStatusNotificationResponse.json"),
                descriptor(OcppVersion.OCPP_201, "MeterValues", com.charging.ocpp.core.model.v201.MeterValuesRequest.class, com.charging.ocpp.core.model.EmptyResponse.class, "schemas/ocpp2.0.1/MeterValuesRequest.json", "schemas/ocpp2.0.1/MeterValuesResponse.json"),
                descriptor(OcppVersion.OCPP_201, "NotifyChargingLimit", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/NotifyChargingLimitRequest.json", "schemas/ocpp2.0.1/NotifyChargingLimitResponse.json"),
                descriptor(OcppVersion.OCPP_201, "NotifyCustomerInformation", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/NotifyCustomerInformationRequest.json", "schemas/ocpp2.0.1/NotifyCustomerInformationResponse.json"),
                descriptor(OcppVersion.OCPP_201, "NotifyDisplayMessages", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/NotifyDisplayMessagesRequest.json", "schemas/ocpp2.0.1/NotifyDisplayMessagesResponse.json"),
                descriptor(OcppVersion.OCPP_201, "NotifyEVChargingNeeds", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/NotifyEVChargingNeedsRequest.json", "schemas/ocpp2.0.1/NotifyEVChargingNeedsResponse.json"),
                descriptor(OcppVersion.OCPP_201, "NotifyEVChargingSchedule", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/NotifyEVChargingScheduleRequest.json", "schemas/ocpp2.0.1/NotifyEVChargingScheduleResponse.json"),
                descriptor(OcppVersion.OCPP_201, "NotifyEvent", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/NotifyEventRequest.json", "schemas/ocpp2.0.1/NotifyEventResponse.json"),
                descriptor(OcppVersion.OCPP_201, "NotifyMonitoringReport", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/NotifyMonitoringReportRequest.json", "schemas/ocpp2.0.1/NotifyMonitoringReportResponse.json"),
                descriptor(OcppVersion.OCPP_201, "NotifyReport", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/NotifyReportRequest.json", "schemas/ocpp2.0.1/NotifyReportResponse.json"),
                descriptor(OcppVersion.OCPP_201, "PublishFirmware", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/PublishFirmwareRequest.json", "schemas/ocpp2.0.1/PublishFirmwareResponse.json"),
                descriptor(OcppVersion.OCPP_201, "PublishFirmwareStatusNotification", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/PublishFirmwareStatusNotificationRequest.json", "schemas/ocpp2.0.1/PublishFirmwareStatusNotificationResponse.json"),
                descriptor(OcppVersion.OCPP_201, "ReportChargingProfiles", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/ReportChargingProfilesRequest.json", "schemas/ocpp2.0.1/ReportChargingProfilesResponse.json"),
                descriptor(OcppVersion.OCPP_201, "RequestStartTransaction", com.charging.ocpp.core.model.v201.RequestStartTransactionRequest.class, com.charging.ocpp.core.model.v201.RequestStartTransactionResponse.class, "schemas/ocpp2.0.1/RequestStartTransactionRequest.json", "schemas/ocpp2.0.1/RequestStartTransactionResponse.json"),
                descriptor(OcppVersion.OCPP_201, "RequestStopTransaction", com.charging.ocpp.core.model.v201.RequestStopTransactionRequest.class, com.charging.ocpp.core.model.v201.RequestStopTransactionResponse.class, "schemas/ocpp2.0.1/RequestStopTransactionRequest.json", "schemas/ocpp2.0.1/RequestStopTransactionResponse.json"),
                descriptor(OcppVersion.OCPP_201, "ReservationStatusUpdate", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/ReservationStatusUpdateRequest.json", "schemas/ocpp2.0.1/ReservationStatusUpdateResponse.json"),
                descriptor(OcppVersion.OCPP_201, "ReserveNow", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/ReserveNowRequest.json", "schemas/ocpp2.0.1/ReserveNowResponse.json"),
                descriptor(OcppVersion.OCPP_201, "Reset", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/ResetRequest.json", "schemas/ocpp2.0.1/ResetResponse.json"),
                descriptor(OcppVersion.OCPP_201, "SecurityEventNotification", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/SecurityEventNotificationRequest.json", "schemas/ocpp2.0.1/SecurityEventNotificationResponse.json"),
                descriptor(OcppVersion.OCPP_201, "SendLocalList", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/SendLocalListRequest.json", "schemas/ocpp2.0.1/SendLocalListResponse.json"),
                descriptor(OcppVersion.OCPP_201, "SetChargingProfile", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/SetChargingProfileRequest.json", "schemas/ocpp2.0.1/SetChargingProfileResponse.json"),
                descriptor(OcppVersion.OCPP_201, "SetDisplayMessage", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/SetDisplayMessageRequest.json", "schemas/ocpp2.0.1/SetDisplayMessageResponse.json"),
                descriptor(OcppVersion.OCPP_201, "SetMonitoringBase", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/SetMonitoringBaseRequest.json", "schemas/ocpp2.0.1/SetMonitoringBaseResponse.json"),
                descriptor(OcppVersion.OCPP_201, "SetMonitoringLevel", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/SetMonitoringLevelRequest.json", "schemas/ocpp2.0.1/SetMonitoringLevelResponse.json"),
                descriptor(OcppVersion.OCPP_201, "SetNetworkProfile", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/SetNetworkProfileRequest.json", "schemas/ocpp2.0.1/SetNetworkProfileResponse.json"),
                descriptor(OcppVersion.OCPP_201, "SetVariableMonitoring", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/SetVariableMonitoringRequest.json", "schemas/ocpp2.0.1/SetVariableMonitoringResponse.json"),
                descriptor(OcppVersion.OCPP_201, "SetVariables", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/SetVariablesRequest.json", "schemas/ocpp2.0.1/SetVariablesResponse.json"),
                descriptor(OcppVersion.OCPP_201, "SignCertificate", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/SignCertificateRequest.json", "schemas/ocpp2.0.1/SignCertificateResponse.json"),
                descriptor(OcppVersion.OCPP_201, "StatusNotification", com.charging.ocpp.core.model.v201.StatusNotificationRequest.class, com.charging.ocpp.core.model.EmptyResponse.class, "schemas/ocpp2.0.1/StatusNotificationRequest.json", "schemas/ocpp2.0.1/StatusNotificationResponse.json"),
                descriptor(OcppVersion.OCPP_201, "TransactionEvent", com.charging.ocpp.core.model.v201.TransactionEventRequest.class, com.charging.ocpp.core.model.v201.TransactionEventResponse.class, "schemas/ocpp2.0.1/TransactionEventRequest.json", "schemas/ocpp2.0.1/TransactionEventResponse.json"),
                descriptor(OcppVersion.OCPP_201, "TriggerMessage", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/TriggerMessageRequest.json", "schemas/ocpp2.0.1/TriggerMessageResponse.json"),
                descriptor(OcppVersion.OCPP_201, "UnlockConnector", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/UnlockConnectorRequest.json", "schemas/ocpp2.0.1/UnlockConnectorResponse.json"),
                descriptor(OcppVersion.OCPP_201, "UnpublishFirmware", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/UnpublishFirmwareRequest.json", "schemas/ocpp2.0.1/UnpublishFirmwareResponse.json"),
                descriptor(OcppVersion.OCPP_201, "UpdateFirmware", JsonNode.class, JsonNode.class, "schemas/ocpp2.0.1/UpdateFirmwareRequest.json", "schemas/ocpp2.0.1/UpdateFirmwareResponse.json")
        });
    }

    private OcppActionMetadata() { }

    public static boolean isKnownAction(OcppVersion version, String action) {
        return descriptor(version, action) != null;
    }

    public static OcppActionDescriptor descriptor(OcppVersion version, String action) {
        Map<String, OcppActionDescriptor> descriptors = DESCRIPTORS_BY_VERSION.get(version);
        return descriptors == null ? null : descriptors.get(action);
    }

    public static List<OcppActionDescriptor> descriptors(OcppVersion version) {
        Map<String, OcppActionDescriptor> descriptors = DESCRIPTORS_BY_VERSION.get(version);
        if (descriptors == null) { return Collections.emptyList(); }
        return Collections.unmodifiableList(new ArrayList<>(descriptors.values()));
    }

    public static List<String> actions(OcppVersion version) {
        Set<String> actions = ACTIONS_BY_VERSION.get(version);
        if (actions == null) { return Collections.emptyList(); }
        return Collections.unmodifiableList(new ArrayList<>(actions));
    }

    public static int actionCount(OcppVersion version) {
        Set<String> actions = ACTIONS_BY_VERSION.get(version);
        return actions == null ? 0 : actions.size();
    }

    private static void registerVersion(OcppVersion version, List<String> actions, OcppActionDescriptor[] descriptors) {
        ACTIONS_BY_VERSION.put(version, new LinkedHashSet<>(actions));
        Map<String, OcppActionDescriptor> byAction = new LinkedHashMap<>();
        for (OcppActionDescriptor descriptor : descriptors) { byAction.put(descriptor.getAction(), descriptor); }
        DESCRIPTORS_BY_VERSION.put(version, Collections.unmodifiableMap(byAction));
    }

    private static OcppActionDescriptor descriptor(OcppVersion version, String action, Class<?> requestType, Class<?> responseType,
                                                   String requestSchemaPath, String responseSchemaPath) {
        return new OcppActionDescriptor(version, action, requestType, responseType, requestSchemaPath, responseSchemaPath);
    }

    private static List<String> unmodifiableList(String... actions) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, actions);
        return Collections.unmodifiableList(list);
    }
}
