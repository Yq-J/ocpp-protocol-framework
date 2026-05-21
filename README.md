# OCPP Protocol Framework

基于 Spring Boot + JDK8 + Maven + Lombok + spring-websocket 的 OCPP 协议框架。框架把 WebSocket、OCPP-J 帧解析、请求应答关联、版本路由、会话管理和异常转换封装起来，业务系统只需要处理业务回调和主动下发命令。

## 模块

| 模块 | 说明 |
| --- | --- |
| `ocpp-core` | 不依赖 Spring 的协议核心，包括帧模型、编解码器、处理器注册表、会话仓储接口、典型 DTO。 |
| `ocpp-spring-boot-starter` | Spring Boot Starter，提供自动配置、WebSocket 接入、注解扫描、默认处理器和 `OcppTemplate`。 |
| `ocpp-demo-business` | 业务接入示例，可直接运行。 |
| `docs` | 中文开发使用手册和架构说明。 |

## 构建

```bash
mvn clean install
```

## 启动示例

```bash
cd ocpp-demo-business
mvn spring-boot:run
```

默认接入地址：

```text
ws://localhost:8080/ocpp/{chargePointId}
```

WebSocket 子协议：

- OCPP 1.6：`ocpp1.6`
- OCPP 2.0.1：`ocpp2.0.1`

## 业务系统引入

```xml
<dependency>
    <groupId>com.charging.ocpp</groupId>
    <artifactId>ocpp-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

详细说明见 `docs/开发使用手册.md`。


## Lombok 与 spring-websocket

- DTO、配置类、示例业务类中已使用 Lombok 常用注解，例如 `@Data`、`@Builder`、`@NoArgsConstructor`、`@AllArgsConstructor`、`@Accessors(chain = true)`、`@Slf4j`、`@RequiredArgsConstructor`。
- WebSocket 传输层显式依赖并使用 `org.springframework:spring-websocket`，核心入口为 `TextWebSocketHandler` 和 `WebSocketConfigurer`。
- 注释均为中文，关键类包含更详细的设计说明，作者为 `JYq`。
