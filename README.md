# OCPP Protocol Framework

基于 Spring Boot + JDK8 + Maven + Lombok + spring-websocket 的 OCPP 协议框架。框架把 WebSocket、OCPP-J 帧解析、请求应答关联、版本路由、会话管理和异常转换封装起来，业务系统只需要处理业务回调和主动下发命令。

## 模块

| 模块 | 说明 |
| --- | --- |
| `ocpp-core` | 不依赖 Spring 的协议核心，包括帧模型、编解码器、处理器注册表、会话仓储接口、典型 DTO、下发命令网关接口。 |
| `ocpp-spring-boot-starter` | Spring Boot Starter，提供自动配置、WebSocket 接入、注解扫描、官方 JSON Schema 校验、握手安全、报文大小限制、可选默认处理器和 `OcppTemplate`。 |
| `ocpp-demo-business` | 业务接入示例，可直接运行。 |
| `docs` | 中文开发使用手册、架构说明和生产化使用指南。 |

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

详细说明见 `docs/开发使用手册.md` 和 `docs/生产化使用指南.md`。

## 生产化能力

- `ocpp-core` 提供 `OcppActionMetadata` 和 `OcppActionDescriptor`，覆盖 OCPP 1.6J 的 28 个 Action 与 OCPP 2.0.1 的 64 个 Action，并维护 Action 到请求 DTO、响应 DTO、请求 Schema、响应 Schema 的结构化映射。每个 Action 的 Request/Response 都有独立模型类；暂未细化官方字段的模型通过 `additionalProperties` 承载扩展字段，并提供中文字段注释。
- starter 默认启用 `OfficialOcppSchemaValidator`，在业务 Handler 执行前校验协议版本、Action 合法性、Payload 对象形态以及对应 JSON Schema 约束。
- 平台主动下发命令时，`OcppTemplate` 会在发送前执行官方 Schema 校验，并在 Spring 容器关闭时取消等待中的 pending request，避免资源悬挂。
- WebSocket 握手支持 `allowed-origins`、`require-auth-token`、`auth-token-header`、`auth-token-query-parameter` 和 `charge-point-tokens`，可作为基础接入鉴权能力。
- WebSocket 文本帧支持 `max-text-message-bytes` 限制，防止异常大报文冲击内存。
- starter 内置默认处理器默认不注册，只有显式配置 `ocpp.enable-default-handlers=true` 时才会启用；生产项目应使用 `@OcppActionMapping` 或自定义 `OcppActionHandler` 实现真实业务动作。

## 生产建议

生产部署前请阅读 `docs/生产化使用指南.md`。尤其注意：默认 `InMemoryOcppSessionRepository` 仅适合单机开发，多实例部署需要替换为业务自己的会话仓储和跨节点命令路由方案。
