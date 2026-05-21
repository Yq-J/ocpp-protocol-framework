package com.charging.ocpp.core.protocol;

import com.charging.ocpp.core.enums.OcppMessageType;
import lombok.Getter;

/**
 * OCPP 帧基础类。
 * 作者：JYq
 */
@Getter
public abstract class OcppFrame {
    private final OcppMessageType messageType;
    private final String uniqueId;

    protected OcppFrame(OcppMessageType messageType, String uniqueId) {
        this.messageType = messageType;
        this.uniqueId = uniqueId;
    }

}
