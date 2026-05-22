package com.charging.ocpp.core.schema;

import com.charging.ocpp.core.enums.OcppVersion;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 默认空 Schema 校验器。
 * 作者：JYq
 */
public class NoopOcppSchemaValidator implements OcppSchemaValidator {
    /*
     * 1. Noop 表示“不做任何事”，这是默认实现，保证项目不配置 Schema 时也能启动。
     * 2. 生产环境如果需要严格遵守协议字段，应提供自己的 OcppSchemaValidator Bean 覆盖它。
     */
    @Override
    public void validate(OcppVersion version, String action, boolean request, JsonNode payload) {
        // 默认不校验。业务系统提供 OcppSchemaValidator Bean 后会覆盖该实现。
    }
}
