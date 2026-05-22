package com.charging.ocpp.core.annotation;

import com.charging.ocpp.core.enums.OcppVersion;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * OCPP 动作映射注解。
 * <p>
 * 作者：JYq。
 * 业务方法使用该注解声明自己处理哪个协议版本、哪个 Action。框架启动时会扫描所有 Spring Bean，
 * 将被标注的方法注册到 OcppHandlerRegistry。运行时收到充电桩 CALL 消息后，框架使用 version + action 找到目标方法，
 * 并自动完成 payload 反序列化、方法调用、返回值序列化和异常转换。
 * </p>
 * <p>
 * 该注解是业务层与协议层解耦的核心入口。业务代码不需要知道底层是 spring-websocket，也不需要直接处理 OCPP-J 数组。
 * </p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OcppActionMapping {
    /*
     * 1. 这是一个方法级注解，业务方法标上它之后，starter 会在启动时扫描并注册成 OCPP Action 处理器。
     * 2. version 表示该方法处理哪个协议版本，例如 OCPP_16 或 OCPP_201；action 表示协议动作名，例如 BootNotification。
     * 3. 运行时收到 CALL 帧后，框架会用 version + action 作为路由键找到目标方法，因此同名 action 在不同协议版本下可以分别处理。
     * 4. 该注解让业务代码不用直接依赖 WebSocketSession、JSON 数组帧和注册表细节，只需要接收 DTO 或 JsonNode。
     */
    OcppVersion version();
    String action();
}
