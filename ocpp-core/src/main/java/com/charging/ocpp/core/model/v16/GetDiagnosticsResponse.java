package com.charging.ocpp.core.model.v16;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OCPP 1.6J 的 GetDiagnostics 响应 payload 协议实体类。
 * <p>
 * 用途：承载 GetDiagnostics 操作的响应字段，用于请求上传诊断文件场景下的 OCPP CALL/CALLRESULT payload 序列化与反序列化。
 * 该类只表达 OCPP 协议 payload 结构，不包含数据库实体、订单、计费或设备台账等业务持久化语义。
 * 字段含义、必填性、枚举、长度和嵌套结构以随包官方 JSON Schema 为准。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GetDiagnosticsResponse {
    /**
     * 文件名。
     * <p>
     * 用途：对应 OCPP 字段 {@code fileName}，在 OCPP 1.6J GetDiagnosticsResponse 协议对象中传递文件名。
     * 字段类型为 {@code String}，用于承载文件名。该字段在官方规范中为可选字段，未提供时由业务语义或对端默认行为决定。最大长度为 255 个字符。
     * </p>
     */
    private String fileName;
}
