package com.charging.ocpp.starter.websocket;

import com.charging.ocpp.core.enums.OcppVersion;
import com.charging.ocpp.core.exception.OcppErrorCode;
import com.charging.ocpp.core.exception.OcppException;
import com.charging.ocpp.core.handler.OcppActionHandler;
import com.charging.ocpp.core.handler.OcppHandlerRegistry;
import com.charging.ocpp.core.handler.OcppRequestContext;
import com.charging.ocpp.core.model.EmptyResponse;
import com.charging.ocpp.core.protocol.*;
import com.charging.ocpp.core.schema.OcppSchemaValidator;
import com.charging.ocpp.core.session.OcppConnection;
import com.charging.ocpp.core.session.OcppSessionRepository;
import com.charging.ocpp.starter.autoconfigure.OcppProperties;
import com.charging.ocpp.starter.service.OcppTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

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
    private final ObjectMapper objectMapper;

    public OcppWebSocketHandler(OcppCodec ocppCodec, OcppHandlerRegistry handlerRegistry, OcppSessionRepository sessionRepository,
                                OcppSchemaValidator schemaValidator, OcppTemplate ocppTemplate, OcppProperties properties,
                                ObjectMapper objectMapper) {
        this.ocppCodec = ocppCodec;
        this.handlerRegistry = handlerRegistry;
        this.sessionRepository = sessionRepository;
        this.schemaValidator = schemaValidator;
        this.ocppTemplate = ocppTemplate;
        this.properties = properties;
        this.objectMapper = OcppObjectMapperFactory.copyOf(objectMapper);
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        String chargePointId = readChargePointId(session);
        OcppVersion version = OcppVersion.fromSubProtocol(session.getAcceptedProtocol());
        if (chargePointId == null || version == null) {
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("缺少 chargePointId 或不支持的 OCPP 子协议"));
            return;
        }
        OcppConnection previous = sessionRepository.get(chargePointId);
        if (previous != null && previous.isOpen() && !session.getId().equals(previous.getSessionId())) {
            if (properties.getDuplicateConnectionPolicy() == OcppProperties.DuplicateConnectionPolicy.REJECT_NEW) {
                session.close(CloseStatus.POLICY_VIOLATION.withReason("chargePointId 已在线"));
                return;
            }
            if (properties.getDuplicateConnectionPolicy() == OcppProperties.DuplicateConnectionPolicy.CLOSE_OLD) {
                try {
                    previous.close();
                    ocppTemplate.cancelPendingRequestsForSession(previous.getSessionId(),
                            "chargePointId 重复连接，旧会话等待中的请求已取消");
                } catch (Exception e) {
                    log.warn("关闭重复 chargePointId 旧连接失败：chargePointId={}, sessionId={}",
                            chargePointId, previous.getSessionId(), e);
                }
            }
        }
        sessionRepository.save(new SpringOcppConnection(session, chargePointId, version));
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) throws Exception {
        SpringOcppConnection.markActivity(session);
        if (properties.getMaxTextMessageBytes() != null
                && payloadLengthBytes(message) > properties.getMaxTextMessageBytes()) {
            sendText(session, ocppCodec.encodeCallError("", OcppErrorCode.FormationViolation.name(),
                    "OCPP 消息超过最大长度限制", null));
            return;
        }

        OcppFrame frame;
        try {
            frame = ocppCodec.decode(message.getPayload());
        } catch (OcppException e) {
            sendText(session, ocppCodec.encodeCallError("", e.getErrorCode().name(), e.getMessage(), e.getDetails()));
            return;
        }

        if (frame instanceof OcppCall) {
            handleCall(session, (OcppCall) frame);
        } else if (frame instanceof OcppCallResult) {
            ocppTemplate.completeResult(session.getId(), (OcppCallResult) frame);
        } else if (frame instanceof OcppCallError) {
            ocppTemplate.completeError(session.getId(), (OcppCallError) frame);
        }
    }

    @Override
    protected void handlePongMessage(@NonNull WebSocketSession session, @NonNull PongMessage message) {
        // 充电桩对服务端 Ping 的应答，刷新入站活动时间用于连接活性判定。
        SpringOcppConnection.markActivity(session);
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
                    sendText(session, ocppCodec.encodeCallResult(call.getUniqueId(), new EmptyResponse()));
                    return;
                }
                throw new OcppException(OcppErrorCode.NotImplemented, "未找到 OCPP 处理器：" + version + "/" + call.getAction());
            }
            Object response = handler.handle(context, call.getPayload());
            if (response instanceof CompletionStage) {
                // 业务处理器选择异步返回时，不阻塞容器 IO 线程，在 Future 完成后再回 CALLRESULT/CALLERROR。
                ((CompletionStage<?>) response).whenComplete((value, error) ->
                        completeAsync(session, version, call.getAction(), call.getUniqueId(), value, error));
                return;
            }
            sendResult(session, version, call.getAction(), call.getUniqueId(), response);
        } catch (OcppException e) {
            sendText(session, ocppCodec.encodeCallError(call.getUniqueId(), e.getErrorCode().name(), e.getMessage(), e.getDetails()));
        } catch (Exception e) {
            sendText(session, ocppCodec.encodeCallError(call.getUniqueId(), OcppErrorCode.InternalError.name(), e.getMessage(), null));
        }
    }

    private void sendResult(WebSocketSession session, OcppVersion version, String action, String uniqueId, Object response) throws Exception {
        JsonNode responsePayload = toResponsePayload(response);
        schemaValidator.validate(version, action, false, responsePayload);
        sendText(session, ocppCodec.encodeCallResult(uniqueId, responsePayload));
    }

    private void completeAsync(WebSocketSession session, OcppVersion version, String action, String uniqueId,
                              Object value, Throwable error) {
        if (error != null) {
            Throwable cause = unwrap(error);
            if (cause instanceof OcppException) {
                OcppException e = (OcppException) cause;
                sendErrorSafely(session, uniqueId, e.getErrorCode().name(), e.getMessage(), e.getDetails());
            } else {
                sendErrorSafely(session, uniqueId, OcppErrorCode.InternalError.name(), cause.getMessage(), null);
            }
            return;
        }
        try {
            sendResult(session, version, action, uniqueId, value);
        } catch (OcppException e) {
            sendErrorSafely(session, uniqueId, e.getErrorCode().name(), e.getMessage(), e.getDetails());
        } catch (Exception e) {
            sendErrorSafely(session, uniqueId, OcppErrorCode.InternalError.name(), e.getMessage(), null);
        }
    }

    private void sendErrorSafely(WebSocketSession session, String uniqueId, String errorCode, String description, Object details) {
        try {
            sendText(session, ocppCodec.encodeCallError(uniqueId, errorCode, description, details));
        } catch (Exception e) {
            log.warn("发送 OCPP CALLERROR 失败：sessionId={}, uniqueId={}", session.getId(), uniqueId, e);
        }
    }

    private Throwable unwrap(Throwable error) {
        if ((error instanceof CompletionException || error instanceof ExecutionException) && error.getCause() != null) {
            return error.getCause();
        }
        return error;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, @NonNull CloseStatus status) {
        sessionRepository.remove(session.getId());
        ocppTemplate.cancelPendingRequestsForSession(session.getId(), "OCPP 会话已关闭，等待中的请求已取消");
    }

    private String readChargePointId(WebSocketSession session) {
        Map<String, Object> attributes = session.getAttributes();
        Object value = attributes.get("chargePointId");
        return value == null ? null : String.valueOf(value);
    }

    private JsonNode toResponsePayload(Object response) {
        if (response == null || response instanceof EmptyResponse) {
            return objectMapper.createObjectNode();
        }
        return objectMapper.valueToTree(response);
    }

    static int payloadLengthBytes(TextMessage message) {
        return message.getPayload().getBytes(StandardCharsets.UTF_8).length;
    }

    private void sendText(WebSocketSession session, String text) throws Exception {
        synchronized (SpringOcppConnection.sendLock(session)) {
            session.sendMessage(new TextMessage(text));
        }
    }
}
