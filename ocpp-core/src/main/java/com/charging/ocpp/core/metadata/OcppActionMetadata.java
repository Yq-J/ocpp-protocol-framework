package com.charging.ocpp.core.metadata;

import com.charging.ocpp.core.enums.OcppVersion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * OCPP 动作元数据。
 * <p>
 * 维护 OCPP 1.6J 与 OCPP 2.0.1 官方 JSON Schema 中定义的 Action 名称，作为路由、校验、文档和后续
 * DTO/Schema 生成的统一来源。该类只描述协议动作集合，不承载业务逻辑。
 * </p>
 */
public final class OcppActionMetadata {
    private static final Map<OcppVersion, Set<String>> ACTIONS_BY_VERSION = new EnumMap<>(OcppVersion.class);

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
        ACTIONS_BY_VERSION.put(OcppVersion.OCPP_16, new LinkedHashSet<>(OCPP_16_ACTIONS));
        ACTIONS_BY_VERSION.put(OcppVersion.OCPP_201, new LinkedHashSet<>(OCPP_201_ACTIONS));
    }

    private OcppActionMetadata() { }

    public static boolean isKnownAction(OcppVersion version, String action) {
        Set<String> actions = ACTIONS_BY_VERSION.get(version);
        return actions != null && actions.contains(action);
    }

    public static List<String> actions(OcppVersion version) {
        Set<String> actions = ACTIONS_BY_VERSION.get(version);
        if (actions == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(new ArrayList<>(actions));
    }

    public static int actionCount(OcppVersion version) {
        Set<String> actions = ACTIONS_BY_VERSION.get(version);
        return actions == null ? 0 : actions.size();
    }

    private static List<String> unmodifiableList(String... actions) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, actions);
        return Collections.unmodifiableList(list);
    }
}
