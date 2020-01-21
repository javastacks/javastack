
这一篇文章主要讲解 Spring Boot 2.x 与 1.5.x 的区别，2.x 主要更新了什么东西，以便对 Spring Boot 2.x 有一个详细的了解。

本文讲的 1.x 指的是 `1.5.10`, 2.x 指的是 `2.0.0`。

![](http://img.javastack.cn/18-7-2/81467719.jpg)

- **配置变更**

在 2.x 中废除了一些 1.x 中的配置，并增加了许多新配置，详细请查看以下链接中的变更表格。

> https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Configuration-Changelog

- **依赖 JDK 版本升级**

2.x 至少需要 JDK 8 的支持，2.x 里面的许多方法应用了 JDK 8 的许多高级新特性，所以你要升级到 2.0 版本，先确认你的应用必须兼容 JDK 8。

另外，2.x 开始了对 JDK 9 的支持。

- **第三方类库升级**

2.x 对第三方类库升级了所有能升级的稳定版本，一些值得关注的类库升级我给列出来了。

1) Spring Framework 5+

2) Tomcat 8.5+

3) Flyway 5+

4) Hibernate 5.2+

5) Thymeleaf 3+

- **响应式 Spring 编程支持**

2.x 通过启动器和自动配置全面支持 Spring 的响应式编程，响应式编程是完全异步和非阻塞的，它是基于事件驱动模型，而不是传统的线程模型。就连 Spring Boot 内部也对一些功能点进行了有必要的响应式升级，最值得注意的是对内嵌式容器的支持。

**对响应式编程支持又包括以下几个技术模块。**

1) Spring WebFlux & WebFlux.fn 支持

2) 响应式 Spring Data 支持

3) 响应式 Spring Security 支持

4) 内嵌式的 Netty 服务器支持

- **HTTP/2 支持**

提供对HTTP/2 的支持，如：Tomcat, Undertow, Jetty，这个得依赖具体选择的应用服务器和应用环境。

- **配置属性绑定**

在 1.x 中，配置绑定是通过注解 `@ConfigurationProperties` 来注入到 Spring 环境变量中的。

在 2.x 中，配置绑定功能有了些的改造，在调整了 1.x 中许多不一致地方之外，还提供了独立于注解之外的 API 来装配配置属性。并增加了属性来源，这样你就能知道这些属性是从哪个配置文件中加载进来的。

- **Gradle 插件**

Spring Boot的 Gradle 插件全面重写了，并且最小支持 Gradle 4+ 以便提供一些重要的特性提升。

- **Kotlin**

2.x 开始提供对 Kotlin 1.2 的支持，并且提供了一个 `runApplication` 函数来运行 Spring Boot 应用。

- **Actuator加强**

在 2.x 中，对执行器端点进行了许多改进，所有的 HTTP 执行端点现在都暴露在 `/actuator` 路径下，并对 JSON 结果集也做了改善。

- **Data 支持**

上面有说到对响应式 Spring Data 的支持，除此之外，其他 Data 模块也做了许多更新和提升，具体体现在以下几个地方。

1) 2.x 默认使用 `HikariCP` 连接池；

2) 更加合理化的优化了数据库初始化逻辑；

3) `spring.jdbc.template` 自动配置现在可以通过 `spring.jdbc.template` 属性定制；

4) 提供了新配置 `spring.jdbc.template` 方便分页和排序；

5) 对数据库 `spring.jdbc.template` 自动化配置支持；

6) 可以高级定制 `MongoDB` 客户端；

7) 可以通过 `spring.cache.redis.*` 来配置 `Redis` 缓存默认值。

- **Web加强**

除了上面说了 2.x 对响应式框架的支持，还包括以下几个 web 开发改进。

1) 使用内嵌式容器时，context path 会和端口一起记录并打印出来；

2) 所有支持的容器都支持过滤器的初始化；

3) Thymeleaf 开始支持 `javax.time` 类型；

4) 提供了一个 `spring-boot-starter-json` 启动器对 JSON 读写的支持。


- **Quartz支持**

2.x 提供了一个 `spring-boot-starter-quartz` 启动器对定时任务框架 Quartz 的支持；

- **测试加强**

在 2.x 中，对测试模块有了一些调整。

- **其他一些改进**

- **动态启动图案支持**

如下图所示，是不是很酷。。

![](http://img.javastack.cn/18-5-8/32572123.jpg)

## 迁移指南

从 1.5.x 可以顺利升级到 2.x，如果你的应用还停留在 1.5.x 之前的版本，建议先升级到 1.5.x，而不是直接升级到 2.x，这样的升级风险最小。

详细的迁移说明有点多，这里就不一一介绍了，具体请阅读官方迁移文档：

> https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Migration-Guide

