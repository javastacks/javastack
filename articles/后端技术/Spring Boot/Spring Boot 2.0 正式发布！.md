
![image](http://img.javastack.cn/18-3-1/6871468.jpg)

#### Spring Boot 2.0 正式发布！

2018/03/01最新消息，传得沸沸扬扬的Spring Boot 2.0 正式发布了。

小编去看了下Spring Boot的官网，正式版本已经释放出来了！！！

![](http://img.javastack.cn/18-3-1/48186693.jpg)

**这个版本更是历经了17个月，超过了215个人共同开发，总共超过6800次+提交完成的。这个版本是继4年前Spring Boot 1.0 之后的第一个主要版本，也是第一个正式支持Spring Framework 5.0的发行版本。**

我们也从Github上Spring Boot发布版本列表中看到了Spring Boot 2.0 的正式发布版本，版本号：v2.0.0.RELEASE。

> Spring Boot版本发布列表：https://github.com/spring-projects/spring-boot/releases/

![](http://img.javastack.cn/18-3-1/85348337.jpg)

#### Spring Boot 2.0 新特性

说了这么多，Spring Boot 2.0 和 1.0 比都有哪些变动和新特性呢？

- JDK最低要求1.8+，并支持1.9；
- 支持Spring webflux/webflux.fn响应式的web编程；
- 提供Spring Data Cassandra, MongoDB, Couchbase和Redis的响应式自动配置及starter POMs；
- 支持嵌入式的Netty；
- HTTP/2的支持：Tomcat, Undertow and Jetty；
- 全新的体系结构，支持Spring MVC、WebFlux和Jersey；
- 增强了Micrometer集成，以Atlas, Datadog, Ganglia, Graphite, Influx, JMX, New Relic, Prometheus, SignalFx, StatsD and Wavefront为基础的度量指标；
- Quartz调度支持；
- 极大简化了安全自动配置；

> 更多详细的新特性请看官方说明： https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Release-Notes

需要注意的是，许多配置属性在 Spring Boot 2.0 中已经被重命名或被删除，为了方便从 1.x 升级，Spring Boot 发布了一个新的 spring-boot-properties-migrator 模块。只要将其作为依赖添加到项目中，它不仅会分析应用程序的环境并在启动时打印诊断信息，而且还会在运行时阶段为项目临时将属性迁移至新的配置方式。

> 升级文档：https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Migration-Guide

还不知道Spring Boot技术或者还不够深入了解的，我们准备了一系列的Spring Boot实战教程，可以点击左下方的阅读原文进行深入学习。

