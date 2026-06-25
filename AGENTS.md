# 全局回复语言规则

- 默认使用中文回复用户。
- 即使用户用英文提问，也应使用中文回复。
- 只有当用户明确要求使用英文回复，例如“请用英文回答”“reply in English”“English only”时，才使用英文。
- 代码、命令、类名、方法名、配置项、错误日志、提交信息标题等技术内容可保留英文原文。
- 解释代码、分析问题、给出操作步骤时使用中文。

Java 开发规范
1. 命名规范
类命名
使用 PascalCase（大驼峰）：UserController, OrderService
接口实现类：ServiceImpl 后缀，如 OrderChargingServiceImpl
方法命名
使用 camelCase（小驼峰）
getter/setter：getXxx(), setXxx()
布尔值：isXxx(), hasXxx(), canXxx()
业务方法：动词 + 名词，如 getUserById(), createOrder()
变量命名
使用 camelCase
有意义的名词：userName, orderList
常量：全大写 + 下划线，如 MAX_RETRY_COUNT
2. 代码格式
缩进
使用 4 个空格缩进（不使用 Tab）
每行不超过 120 个字符
空格
运算符两侧加空格：a + b
关键字后加空格：if (condition)
方法参数逗号后加空格
括号
左括号不换行：if (condition) {
右括号单独一行
3. 注释规范
类注释

/**
 * 订单充电服务实现类
 *
 * @author JYq
 * @since 2024-01-01
 */

方法注释

/**
 * 根据 ID 查询订单详情
 *
 * @param id 订单 ID
 * @return 订单详情
 * @throws BusinessException 当订单不存在时
 */
行内注释
使用 // 单行注释
复杂逻辑必须添加注释
4. 异常处理
原则
不捕获 Exception 或 Throwable
自定义业务异常：BusinessException
记录异常日志：log.error()
示例
try {
    // 业务逻辑
} catch (BusinessException e) {
    log.error("业务异常：{}", e.getMessage());
    throw e;
} catch (Exception e) {
    log.error("系统异常", e);
    throw new BusinessException("系统错误");
}
5. 日志规范
使用 SLF4J
private static final Logger log = LoggerFactory.getLogger(ClassName.class);
日志级别
debug: 调试信息
info: 一般信息
warn: 警告信息
error: 错误信息
日志格式
log.info("用户登录成功，userId={}", userId);
log.error("订单创建失败", e);
6. 数据库规范
SQL 编写
关键字大写：SELECT, FROM, WHERE
表名/字段名小写 + 下划线：order_charging
禁止使用 SELECT *
MyBatis XML
<select id="selectById" resultType="OrderCharging">
    SELECT id, order_no, user_id
    FROM order_charging
    WHERE id = #{id}
</select>
7. 事务规范
注解方式
@Transactional(rollbackFor = Exception.class)
public void createOrder(Order order) {
    // 业务逻辑
}
原则
事务方法尽量简短
不在事务中做 RPC 调用
读写分离场景注意事务传播
8. 安全规范
输入验证
所有外部输入必须验证
使用 @Validated 注解
SQL 注入防护
必须使用参数化查询
禁止字符串拼接 SQL
XSS 防护
输出到前端时进行 HTML 转义
9. 性能规范
集合处理
指定初始容量：new ArrayList<>(size)
使用增强 for 循环
字符串操作
频繁拼接使用 StringBuilder
常量在前："ok".equals(str)
缓存使用
热点数据必须缓存
设置合理的过期时间
10. 版本控制
Git 提交规范
feat: 新功能
fix: 修复 bug
docs: 文档更新
style: 代码格式调整
refactor: 重构代码
test: 测试相关
chore: 构建/工具变动
提交示例
feat(order): 添加订单导出功能
fix(charging): 修复 OCMF 数据解析空指针异常
