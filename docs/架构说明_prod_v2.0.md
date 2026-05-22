# OCPP 协议框架高负荷架构说明（prod_v2.0）

## 1. 目标与边界

本版本目标是让框架具备“十万桩体在线”场景下的稳定承载能力，重点优化协议层吞吐与内存稳定性，不引入业务语义耦合。

- **优化范围**：WebSocket 入站处理、异步请求超时清理、配置化容量治理。
- **不在范围**：计费、订单、站点管理、数据库分片等业务能力。

## 2. 关键瓶颈分析

原有实现中，`handleTextMessage` 在 WebSocket I/O 线程中直接完成解码、schema 校验、handler 调用与响应发送，在高并发下会出现：

1. I/O 线程被业务处理阻塞，导致连接读写抖动。
2. 峰值消息瞬间涌入时缺少显式背压机制。
3. PendingRequest 超时清理粒度较粗（秒级），大量并发 Future 的超时释放不够及时。

## 3. 新架构设计

### 3.1 入站异步卸载

新增 `inboundExecutor` 固定线程池 + 有界队列：

- I/O 线程仅负责接收消息并投递任务。
- 工作线程负责 decode / validate / route / encode / send。
- 当队列满时触发拒绝策略，快速返回“系统繁忙”，保护 JVM 免于无限排队。

### 3.2 容量治理与背压

通过配置实现“可观测、可调优”的容量控制：

- `ocpp.inbound-worker-threads`：工作线程数。
- `ocpp.inbound-queue-capacity`：入站队列深度。
- 当消息超过系统承载上限时，拒绝新任务并返回标准 OCPP 错误帧，优先保障系统整体可用。

### 3.3 Redis 全局限流（多实例）

为解决“多节点下单机限流不一致”的问题，新增可选 Redis 全局限流保护：

- 开关：`ocpp.redis-global-guard-enabled=true`
- 阈值：`ocpp.max-inbound-messages-per-second`
- 机制：按秒写入 `ocpp:inbound:qps:{epochSecond}` 计数键并设置短 TTL。

这样可在集群统一削峰，避免某一节点被突发流量击穿。

### 3.4 PendingRequest 精细化清理

- 将超时扫描周期调整为毫秒级可配置。
- 使用守护线程执行轻量遍历，及时释放超时 Future，降低内存滞留。

## 4. 并发模型

```text
[WebSocket I/O Thread]
   -> decode task submit
      -> [OCPP Inbound Worker Pool]
          -> OcppCodec decode
          -> Schema validate
          -> Handler invoke
          -> OcppCodec encode
          -> WebSocket send
```

另有独立 `ocpp-pending-cleaner` 周期清理 `pendingRequests`。

## 5. 运行参数建议（十万在线起步）

> 以下为基线建议，需结合压测结果调优。

- 8C16G 单节点：
  - `ocpp.inbound-worker-threads=32`
  - `ocpp.inbound-queue-capacity=200000`
  - `ocpp.pending-cleanup-interval-millis=1000`
- JVM：
  - 建议 G1GC，固定 `-Xms` / `-Xmx`，避免动态扩缩堆。
- 日志：
  - 高负荷下建议开启日志降采样。

## 6. 横向扩展建议

1. 按 chargePointId 哈希将连接分散到多实例。
2. 四层负载均衡保持长连接转发稳定。
3. 中心系统主动下发命令可通过外部路由层定位实例（后续可演进）。

## 7. 风险与后续演进

- 当前仍是单机内存会话仓库，跨实例命令路由需结合注册中心/消息总线增强。
- 极端流量下仍需依赖上游网关限流与熔断配合。
