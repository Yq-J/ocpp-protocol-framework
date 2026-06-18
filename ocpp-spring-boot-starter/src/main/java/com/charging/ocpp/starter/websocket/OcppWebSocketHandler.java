package com.charging.ocpp.starter.websocket;

import lombok.extern.slf4j.Slf4j;
import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.exception.OcppErrorCode;
import com.charging.ocpp.core.exception.OcppException;
import com.charging.ocpp.core.handler.OcppActionHandler;
import com.charging.ocpp.core.handler.OcppHandlerRegistry;
import com.charging.ocpp.core.handler.OcppRequestContext;
import com.charging.ocpp.core.model.EmptyResponse;
import com.charging.ocpp.core.protocol.OcppCall;
import com.charging.ocpp.core.protocol.OcppCallError;
import com.charging.ocpp.core.protocol.OcppCallResult;
import com.charging.ocpp.core.protocol.OcppCodec;
import com.charging.ocpp.core.protocol.OcppFrame;
import com.charging.ocpp.core.schema.OcppSchemaValidator;
import com.charging.ocpp.core.session.OcppSessionRepository;
import com.charging.ocpp.starter.autoconfigure.OcppProperties;
import com.charging.ocpp.starter.service.OcppTemplate;
import java.util.Map;

import org.springframework.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * OCPP WebSocket 协议入口，基于 Spring Framework 的 spring-websocket 实现。
 * <p>
 * 作者：JYq。
 * 本类是协议框架的传输边界，职责范围被严格限制在“连接、编解码、路由、响应、异常转换”五类协议问题内，
 * 不包含订单、计费、站点、账户、营销、电价等任何业务概念。这样业务系统只需要通过注解处理强类型 DTO，
 * 不需要关心 OCPP-J 数组帧、WebSocketSession 生命周期、CALLRESULT 关联和 CALLERROR 组装。
 * </p>
 * <p>
 * 工作流程：
 * 1. 连接建立时，从握手属性中读取 chargePointId，并根据 WebSocket 子协议解析 OCPP 版本；
 * 2. 收到文本帧后，使用 OcppCodec 解码为 OcppFrame；
 * 3. 如果是 CALL，则按 version + action 查找业务处理器；
 * 4. 如果是 CALLRESULT 或 CALLERROR，则交给 OcppTemplate 完成中心系统主动命令的异步 Future；
 * 5. 任何协议错误或业务异常都会被转换为 OCPP CALLERROR，避免底层异常泄露到充电桩侧。
 * </p>
 */
@Slf4j
public class OcppWebSocketHandler extends TextWebSocketHandler {
    private final OcppCodec ocppCodec;
    private final OcppHandlerRegistry handlerRegistry;
    private final OcppSessionRepository sessionRepository;
    private final OcppSchemaValidator schemaValidator;
    private final OcppTemplate ocppTemplate;
    private final OcppProperties properties;

    public OcppWebSocketHandler(OcppCodec ocppCodec, OcppHandlerRegistry handlerRegistry, OcppSessionRepository sessionRepository,
                                OcppSchemaValidator schemaValidator, OcppTemplate ocppTemplate, OcppProperties properties) {
        this.ocppCodec = ocppCodec;
        this.handlerRegistry = handlerRegistry;
        this.sessionRepository = sessionRepository;
        this.schemaValidator = schemaValidator;
        this.ocppTemplate = ocppTemplate;
        this.properties = properties;
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        String chargePointId = readChargePointId(session);
        OcppVersion version = OcppVersion.fromSubProtocol(session.getAcceptedProtocol());
        if (chargePointId == null || version == null) {
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("缺少 chargePointId 或不支持的 OCPP 子协议"));
            return;
        }
        sessionRepository.save(new SpringOcppConnection(session, chargePointId, version));
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) throws Exception {
        if (properties.getMaxTextMessageBytes() != null
                && message.getPayloadLength() > properties.getMaxTextMessageBytes()) {
            session.sendMessage(new TextMessage(ocppCodec.encodeCallError("", OcppErrorCode.FormationViolation.name(),
                    "OCPP 消息超过最大长度限制", null)));
            return;
        }

        OcppFrame frame;
        try {
            frame = ocppCodec.decode(message.getPayload());
        } catch (OcppException e) {
            session.sendMessage(new TextMessage(ocppCodec.encodeCallError("", e.getErrorCode().name(), e.getMessage(), e.getDetails())));
            return;
        }

        if (frame instanceof OcppCall) {
            handleCall(session, (OcppCall) frame);
        } else if (frame instanceof OcppCallResult) {
            ocppTemplate.completeResult((OcppCallResult) frame);
        } else if (frame instanceof OcppCallError) {
            ocppTemplate.completeError((OcppCallError) frame);
        }
    }

    private void handleCall(WebSocketSession session, OcppCall call) throws Exception {
        OcppVersion version = OcppVersion.fromSubProtocol(session.getAcceptedProtocol());
        String chargePointId = readChargePointId(session);
        OcppRequestContext context = new OcppRequestContext(chargePointId, session.getId(), version, call.getAction(), call.getUniqueId());
        try {
            schemaValidator.validate(version, call.getAction(), true, call.getPayload());
            OcppActionHandler handler = handlerRegistry.get(version, call.getAction());
            if (handler == null) {
                if (Boolean.TRUE.equals(properties.getAllowUnknownActions())) {
                    session.sendMessage(new TextMessage(ocppCodec.encodeCallResult(call.getUniqueId(), new EmptyResponse())));
                    return;
                }
                throw new OcppException(OcppErrorCode.NotImplemented, "未找到 OCPP 处理器：" + version + "/" + call.getAction());
            }
            Object response = handler.handle(context, call.getPayload());
            session.sendMessage(new TextMessage(ocppCodec.encodeCallResult(call.getUniqueId(), response)));
        } catch (OcppException e) {
            session.sendMessage(new TextMessage(ocppCodec.encodeCallError(call.getUniqueId(), e.getErrorCode().name(), e.getMessage(), e.getDetails())));
        } catch (Exception e) {
            session.sendMessage(new TextMessage(ocppCodec.encodeCallError(call.getUniqueId(), OcppErrorCode.InternalError.name(), e.getMessage(), null)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, @NonNull CloseStatus status) {
        sessionRepository.remove(session.getId());
    }

    private String readChargePointId(WebSocketSession session) {
        Map<String, Object> attributes = session.getAttributes();
        Object value = attributes.get("chargePointId");
        return value == null ? null : String.valueOf(value);
    }
}
