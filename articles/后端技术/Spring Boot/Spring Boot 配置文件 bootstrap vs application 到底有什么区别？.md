
用过 Spring Boot 的都知道在 Spring Boot 中有以下两种配置文件

- bootstrap (.yml 或者 .properties)
- application (.yml 或者 .properties)

为什么会有这两种配置文件呢？大家都清楚它们的区别和具体使用场景吗？

#### bootstrap/ application 的区别

特意去翻了下 Spring Boot 的官方文档，没有找到关于这两种文件的具体定义，然后再翻了下 Spring Cloud 的官方文档找到了它们的区别。

> https://cloud.spring.io/spring-cloud-static/Greenwich.SR1/single/spring-cloud.html#_the_bootstrap_application_context

认真阅读了下文档，原文大概意思是这样。

> Spring Cloud 构建于 Spring Boot 之上，在 Spring Boot 中有两种上下文，一种是 bootstrap, 另外一种是 application, bootstrap 是应用程序的父上下文，也就是说 bootstrap 加载优先于 applicaton。bootstrap 主要用于从额外的资源来加载配置信息，还可以在本地外部配置文件中解密属性。这两个上下文共用一个环境，它是任何Spring应用程序的外部属性的来源。bootstrap 里面的属性会优先加载，它们默认也不能被本地相同配置覆盖。

**因此，对比 application 配置文件，bootstrap 配置文件具有以下几个特性。**

- boostrap 由父 ApplicationContext 加载，比 applicaton 优先加载
- boostrap 里面的属性不能被覆盖

#### bootstrap/ application 的应用场景

application 配置文件这个容易理解，主要用于 Spring Boot 项目的自动化配置。

bootstrap 配置文件有以下几个应用场景。

- 使用 Spring Cloud Config 配置中心时，这时需要在 bootstrap 配置文件中添加连接到配置中心的配置属性来加载外部配置中心的配置信息；
- 一些固定的不能被覆盖的属性
- 一些加密/解密的场景；

以下这个截图是一个国外网友问了一个 Spring Cloud 工程师得到的回答。

![](http://img.javastack.cn/18-4-2/38651438.jpg)

做过 Spring Cloud 微服务的朋友应该对 bootstrap 的应用十分清楚，我们的微信公众号Java技术栈也有 Spring Cloud 的实战教程，在 Spring 专题中都能看到。

好了，今天的分享就到这里，关注Java技术栈微信公众号，在后台回复：boot，获取栈长整理的更多的 Spring Boot 教程，都是实战干货，以下仅为部分预览。

- Spring Boot 读取配置的几种方式
- Spring Boot 如何做参数校验？
- Spring Boot 最核心的 25 个注解！
- Spring Boot 2.x 启动全过程源码分析
- Spring Boot 2.x 新特性总结及迁移指南
- ……

本文原创首发于微信公众号：Java技术栈（id:javastack），转载请原样保留本信息。