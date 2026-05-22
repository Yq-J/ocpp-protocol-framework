# OCPP 框架开发与部署手册（prod_v2.0）

## 1. 分支策略

- 生产优化分支：`prod_v2.0`
- 从稳定基线分支切出，所有高负荷相关优化在该分支完成。

## 2. 本地开发

## 2.1 环境要求

- JDK 8+
- Maven 3.8+
- Linux/macOS（推荐）

## 2.2 构建

```bash
mvn -q -DskipTests package
```

## 2.3 核心配置项

在 `application.yml` 增加：

```yaml
ocpp:
  path: /ocpp/{chargePointId}
  connection-timeout-seconds: 60
  allow-unknown-actions: false
  inbound-worker-threads: 32
  inbound-queue-capacity: 200000
  pending-cleanup-interval-millis: 1000
  high-load-log-sampling-enabled: true
  max-inbound-messages-per-second: 50000
  redis-global-guard-enabled: true
```

参数说明：

- `inbound-worker-threads`：异步处理线程数。
- `inbound-queue-capacity`：入站缓冲队列深度。
- `pending-cleanup-interval-millis`：Future 超时回收周期。
- `high-load-log-sampling-enabled`：高负荷日志降采样开关。
- `max-inbound-messages-per-second`：入站消息每秒阈值，超过后快速拒绝。
- `redis-global-guard-enabled`：是否启用 Redis 集群级限流保护。

## 3. 压测与验收

## 3.1 压测目标

- 在线连接数：100,000
- 关键动作：BootNotification / Heartbeat / MeterValues
- 验收指标：
  - 连接稳定率 > 99.9%
  - P99 处理延迟满足业务 SLA
  - 无 OOM / 无长时间 Full GC

## 3.2 建议步骤

1. 1万连接预热，验证线程、队列、水位与日志。
2. 梯度提升到 3万/5万/10万连接。
3. 在 10万连接下进行突发流量冲击，观察拒绝保护是否生效。

## 4. 生产部署

## 4.1 单节点启动

```bash
java -Xms8g -Xmx8g -XX:+UseG1GC -jar ocpp-demo-business/target/ocpp-demo-business.jar
```

## 4.2 多节点部署建议

- 采用无状态应用部署（会话按长连接驻留实例）。
- 使用 L4/L7 负载均衡并配置合理空闲超时。
- 对接监控系统采集：
  - JVM 堆使用率
  - 线程池活跃线程与队列长度
  - OCPP 错误码分布

## 5. 运维排障

### 5.1 常见现象：系统繁忙返回增多

排查顺序：

1. 查看 `inboundExecutor` 队列是否持续满载。
2. 增加 `inbound-worker-threads` 或扩容实例。
3. 检查上游是否存在突发重放/风暴流量。

### 5.2 常见现象：命令超时偏高

1. 检查桩端响应时延。
2. 适当调大 `connection-timeout-seconds`。
3. 观察 `pending-cleanup-interval-millis` 是否过大导致超时回收滞后。
