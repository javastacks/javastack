最近栈长看到一个框架，官方号称可以比 Spring Boot 快 44 倍，居然这么牛逼，有这么神奇吗？今天带大家来认识一下。

这个框架名叫：**light-4j**。

> 官网简介：A fast, lightweight and more productive microservices framework

很简单，翻译过来就是：一个快速、轻量级和更高效的微服务框架。

#### 为什么叫light-4j？

全称应该是：Light for Java，意味着轻量级，并以闪电般的速度来使用 Java 编程。

#### 这个框架有什么用？

**1、降低成本**

为什么说它能降低成本，因为它速度非常快，占用内存也非常小。

重点来了，它比类似 Spring Boot 这种内嵌 Tomcat 式的主流微服务框架平台要快 44 倍，并且只需要用其 1/5 内存，听起来是不是很牛逼，确实是节约了不少内存空间。

这里有一份 benchmark 的测试报告，它与 Spring Boot 及其他微服务平台作了一个比较：

![](http://img.javastack.cn/20190322142231.png)

> 详细报告：https://github.com/networknt/microservices-framework-benchmark

很强大，性能与 Go 语言并肩，并且拥有更低的平均延迟。

这里还有一份与其他 web 框架的对比：

> 详细报告：https://www.techempower.com/benchmarks/#section=data-r15&hw=ph&test=plaintext

性能表现非常靠前，吊打 Spring 等各种框架！

**2、丰富的特性**

- 带有启动/关闭钩子和各种中间件的插件架构
- 分布式OAuth2 JWT安全验证作为框架的一部分
- 基于OpenAPI规范进行请求和响应验证
- 收集测量指标并支持服务和客户端在控制台显示
- 全局运行时异常处理，如API异常及其他受检查异常
- 在日志输出前加密敏感数据，如：信用卡、SIN号等
- 为请求参数、请求头、BODY清除跨站攻击脚本
- 重要信息或整个请求/响应的审计
- 请求体支持各种类型的content-type
- 配置标准化响应码及响应消息
- 支持外部配置化Docker环境所有模块
- 来自其他域名的跨域处理
- 支持对外提供的服务限速处理
- 服务发现与注册支持直连、Consul和Zookeeper
- 客户端侧发现和负载平衡，消除代理层
- 与Light-OAuth2紧密集成并支持可跟踪性

栈长先介绍到这，大家感兴趣的可以去 Github 捣鼓……

> Github地址：https://github.com/networknt/light-4j

![](http://img.javastack.cn/20190322135924.png)

#### 栈长有话说

看完你可能觉得呵呵了，有人用吗？

这个栈长我目前没有可靠数据，但这个框架的性能表现和内存消耗真的非常惊人，以及它的各种功能特性都值得借鉴。

至于比 Spring Boot 框架要快 44 倍，这个大家也不用太纠结，Spring 发展到今天，经过国外各种大神的打磨，可以说是非常精湛。

Spring 日益宠大的同时，其内部依赖集成了太多东西，在性能这方面没其他框架强，确实能够理解，但 Spring 的生态圈是没有任何框架可以比拟的。在追求性能的同时，它肯定也会牺牲很多东西，所以，我觉得一个生态繁荣的技术平台比追求性能更重要。

最后，你们有公司用过这个框架吗？你对这个框架怎么看，欢迎留言讨论~

关注Java技术栈微信公众号，在后台回复：boot，获取栈长整理的更多的 Spring Boot 技术文章，都是实战干货，以下仅为部分预览。

- Spring Boot 2.x 启动全过程源码分析
- Spring Boot 自定义日志详解
- Spring Boot 核心配置文件详解
- Spring Boot 最核心的 25 个注解！
- Spring Boot 集成Mybatis实现双数据源
- ……

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "工具" 可获取更多，转载请原样保留本信息。
