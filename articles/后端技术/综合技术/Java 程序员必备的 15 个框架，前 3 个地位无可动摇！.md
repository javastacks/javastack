Java 程序员方向太多，且不说移动开发、大数据、区块链、人工智能这些，大部分 Java 程序员都是 Java Web/后端开发。那作为一名 Java Web 开发程序员必须需要熟悉哪些框架呢？

今天，栈长我给大家列举了一些通用的、必须掌握的框架，学会这些，20K+ 不是问题。

## 1.Spring

毫无疑问，Spring 框架现在是 Java 后端框架家族里面最强大的一个，其拥有 IOC 和 AOP 两大利器，大大简化了软件开发复杂性。并且，Spring 现在能与所有主流开发框架集成，可谓是一个万能框架，Spring 让 JAVA 开发变得更多简单。

**官网：**

> https://spring.io/projects/spring-framework

**源码：**

> https://github.com/spring-projects/spring-framework

**推荐：**

[Java 必看的 Spring 知识汇总](https://mp.weixin.qq.com/s/jV5Z3earaNk0g0h008mPbg)

更多请在Java技术栈微信公众号后台回复关键字：spring。

## 2.Spring MVC

Spring MVC 是一个 MVC 开源框架，用来代替 Struts。它是 Spring 项目里面的一个重要组成部分，能与 Spring IOC 容器紧密结合，以及拥有松耦合、方便配置、代码分离等特点，让 JAVA 程序员开发 WEB 项目变得更加容易。

**官网：**

> https://spring.io/projects/spring-framework

**源码：**

> https://github.com/spring-projects/spring-framework

**推荐：**

[从 0 开始手写一个 Spring MVC 框架](https://mp.weixin.qq.com/s/36F_fFbGKkRL20DJgX4ahg)

更多请在Java技术栈微信公众号后台回复关键字：mvc。

## 3.Spring Boot

Spring Boot 是 Spring 开源组织下的一个子项目，也是 Spring 组件一站式解决方案，主要是为了简化使用 Spring 框架的难度，简省繁重的配置。

Spring Boot提供了各种组件的启动器（starters），开发者只要能配置好对应组件参数，Spring Boot 就会自动配置，让开发者能快速搭建依赖于 Spring 组件的 Java 项目。

**官网：**

> https://spring.io/projects/spring-boot

**源码：**

> https://github.com/spring-projects/spring-boot

**推荐：**

- [Spring Boot 核心配置文件详解](https://mp.weixin.qq.com/s/BzXNfBzq-2TOCbiHG3xcsQ)
- [Spring Boot 2.x 启动全过程源码分析](https://mp.weixin.qq.com/s/iMPXjuKRKT5lMZ4oVSp4Ww)
- [20 道 Spring Boot 面试题答案](https://mp.weixin.qq.com/s/GmTayi-Izb_lJqNz-zVhxg)

更多请在Java技术栈微信公众号后台回复关键字：boot。

## 4.Spring Cloud

Spring Cloud 是一系列框架的有序集合，是目前最火热的微服务框架首选，它利用Spring Boot 的开发便利性巧妙地简化了分布式系统基础设施的开发，如服务发现注册、配置中心、消息总线、负载均衡、断路器、数据监控等，都可以用 Spring Boot 的开发风格做到一键启动和部署。

**官网：**

> http://projects.spring.io/spring-cloud/

**源码：**

> https://github.com/spring-cloud

**推荐：**

- [Spring Cloud 最新 Finchley 版本踩坑](https://mp.weixin.qq.com/s/CvAmV4mjWHqNPkUoy0CwYw)
- [微服务为什么选Spring Cloud？](https://mp.weixin.qq.com/s/jQnxdrNdGDLvo8dUxOc2eQ)
- [Spring Cloud 多版本怎么选择？](https://mp.weixin.qq.com/s/IqlHFsIrFJ5vBG9-1gldJw)

更多请在Java技术栈微信公众号后台回复关键字：cloud。

## 5.Mybatis/ iBatis

iBatis 曾是开源软件组 Apache 推出的一种轻量级的对象关系映射持久层（ORM）框架，随着开发团队转投Google Code 旗下，ibatis 3.x 正式更名为 Mybatis，即：iBatis 2.x, MyBatis 3.x。

**官网：**

> http://www.mybatis.org/mybatis-3/

**源码：**

> https://github.com/mybatis

**推荐：**

[Mybatis 传递多个参数的 4 种方式](https://mp.weixin.qq.com/s/8p5UTWHObodgIrgXjcNhIg)

更多请关注Java技术栈微信公众号，在后台回复：mybatis。

## 6.Hibernate

Hibernate 是一个开放源代码的对象关系映射框架，它对 JDBC 进行了非常轻量级的对象封装，它将 POJO 与数据库表建立映射关系，是一个全自动的 orm 框架。Hibernate 可以自动生成 SQL 语句，自动执行，使得 Java 程序员可以随心所欲的使用对象编程思维来操作数据库。

**官网：**

> http://hibernate.org/

**源码：**

> https://github.com/hibernate

## 7.Dubbo

Dubbo是阿里巴巴开源的基于 Java 的高性能 RPC 分布式服务框架，现已成为 Apache 基金会孵化项目。使用 Dubbo 可以将核心业务抽取出来，作为独立的服务，逐渐形成稳定的服务中心，可用于提高业务复用灵活扩展，使前端应用能更快速的响应多变的市场需求。

**官网：**

> http://dubbo.apache.org

**源码：**

> https://github.com/apache/incubator-dubbo

**推荐：**

- [Dubbo 架构设计详解](https://mp.weixin.qq.com/s/q8S3Ihas0KXVMfbdNjau0w)
- [史上最全 40 道 Dubbo 面试题及答案](https://mp.weixin.qq.com/s/PdWRHgm83XwPYP08KnkIsw)

更多请在Java技术栈微信公众号后台回复关键字：dubbo。

## 8.Netty

Netty 是由 JBOSS 提供的一个开源的、异步的、基于事件驱动的网络通信框架，用 Netty 可以快速开发高性能、高可靠性的网络服务器和客户端程序，Netty 简化了网络应用的编程开发过程，使开发网络编程变得异常简单。

**官网：**

> https://netty.io/

**源码：**

> https://github.com/netty/netty

## 9.Shiro

Apache Shiro是一个强大而灵活的开源安全框架，它干净利落地处理身份认证，授权，企业会话管理和加密。

**官网：**

> http://shiro.apache.org/

**源码：**

> https://github.com/apache/shiro

## 10.Ehcache

EhCache 是一个纯Java的进程内缓存框架，具有快速、精干等特点，是 Hibernate 中默认的CacheProvider。它使用的是 JVM 的堆内存，超过内存可以设置缓存到磁盘，企业版的可以使用 JVM 堆外的物理内存。

**官网：**

> http://www.ehcache.org/

**源码：**

> https://github.com/ehcache/ehcache3

**推荐：**

[Ehcache介绍及整合Spring实现高速缓存](https://mp.weixin.qq.com/s/FqstzO4u_kwH79B2YD83Aw)

更多请在Java技术栈微信公众号后台回复关键字：ehcache。

## 11.Quartz

Quartz 是一个基于 Java 的广泛使用的开源的任务调度框架，做过定时任务的没有没用过这个框架的吧？

**官网：**

> http://www.quartz-scheduler.org/

**源码：**

> https://github.com/quartz-scheduler/quartz

## 12.Velocity

Velocity 是一个基于 Java 的模板引擎，简单而强大的模板语言为各种 Web 框架提供模板服务，来适配 MVC 模型。

**官网：**

> http://velocity.apache.org/

**源码：**

> https://github.com/apache/velocity-engine

## 13.jQuery

jQuery是一个快速、简洁的 JavaScript 框架，它封装 JavaScript 常用的功能代码，提供一种简便的 JavaScript 设计模式，极大地简化了 JavaScript 编程。

虽然哥好久没做 Web 开发了，但哥也不曾忘记，也还记得一些常用的写法，如：

```
$("#wx").html("javastack");
```

**官网：**

> http://jquery.com/

**源码：**

> http://jquery.com/download/

## 14.JUnit

JUnit 是一个 Java 语言的单元测试框架，绝大多数 Java 的开发环境都已经集成了 JUnit 作为其单元测试的工具。

**官网：**

> https://junit.org

**源码：**

> https://github.com/junit-team/

## 15.Log4j

Log4j 是 Apache 的一个开源日志框架，通过 Log4j 我们可以将程序中的日志信息输出到控制台、文件等来记录日志。作为一个最老牌的日志框架，它现在的主流版本是 Log4j2。Log4j2是重新架构的一款日志框架，抛弃了之前 Log4j 的不足，以及吸取了优秀日志框架 Logback 的设计。

**官网：**

> https://logging.apache.org/log4j/2.x/

**源码：**

> https://logging.apache.org/log4j/2.x/source-repository.html

如果上面的大部分没用过，甚至都没听说过，那就怀疑你是不是个假程序员了，要加油了。

这些都是 Java 程序员必备的开发框架，有些不一定是首选的选择，但这些一定是 Java 程序员必备的。。

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "java" 可获取更多，转载请原样保留本信息。