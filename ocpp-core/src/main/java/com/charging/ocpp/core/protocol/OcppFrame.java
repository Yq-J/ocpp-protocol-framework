package com.charging.ocpp.core.protocol;

import com.charging.ocpp.core.enums.OcppMessageType;
import lombok.Getter;

/**
 * OCPP 帧基础类。
 * 作者：JYq
 */
@Getter
public abstract class OcppFrame {
    /*
     * 1. 这是三类 OCPP 帧的共同父类，保存所有帧都拥有的 messageType 和 uniqueId。
     * 2. uniqueId 是请求-响应匹配的关键，平台主动下发命令后会靠它找到对应的 CompletableFuture。
     */
    private final OcppMessageType messageType;
    private final String uniqueId;

    protected OcppFrame(OcppMessageType messageType, String uniqueId) {
        this.messageType = messageType;
        this.uniqueId = uniqueId;
    }

}
