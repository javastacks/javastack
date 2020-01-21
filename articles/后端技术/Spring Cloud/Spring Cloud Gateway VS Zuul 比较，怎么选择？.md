Spring Cloud Gateway 是 [Spring Cloud Finchley](https://mp.weixin.qq.com/s/3MmPgia6ghxwfj2Xcm6tyg) 版推出来的新组件，用来代替服务网关：Zuul。

那 Spring Cloud Gateway 和 Zuul 都有哪些区别呢，咱们来比较一下。

**1、开源组织**

Spring Cloud Gateway 是 Spring Cloud 微服务平台的一个子项目，属于 Spring 开源社区，依赖名叫：spring-cloud-starter-gateway。

> https://spring.io/projects/spring-cloud-gateway

Zuul 是 Netflix 公司的开源项目，Spring Cloud 在 Netflix 项目中也已经集成了 Zuul，依赖名叫：spring-cloud-starter-netflix-zuul。

> https://github.com/Netflix/zuul

**2、底层实现**

> https://stackoverflow.com/questions/47092048/how-is-spring-cloud-gateway-different-from-zuul

据 Spring Cloud Gateway 原作者的解释：

Zuul构建于 Servlet 2.5，兼容 3.x，使用的是阻塞式的 API，不支持长连接，比如 websockets。另外

Spring Cloud Gateway构建于 Spring 5+，基于 Spring Boot 2.x 响应式的、非阻塞式的 API。同时，它支持 websockets，和 Spring 框架紧密集成，开发体验相对来说十分不错。

**3、性能表现**

这个没什么好比的，要比就和 Zuul 2.x 比，Zuul 2.x 在底层上有了很大的改变，使用了异步无阻塞式的 API，性能改善明显，不过现在 Spring Cloud 也没集成 Zuul 2.x，所以就没什么好比的。


**如何选择？**

本文说的 Zuul 指 Zuul 1.x，Netflix 早就发布了最新的 Zuul 2.x，但 Spring Cloud 貌似没有整合计划，栈长看了下目前最新的包，整合的还是 Zuul 1.x。

据了解，正是因为 Zuul 2.x 的不断跳票，Spring Cloud 才釜底抽薪推出了自己的服务网关：Spring Cloud Gateway，栈长看了下，使用起来比 Zuul 更简单，配置更方便，所以说选 Spring Cloud Gateway 没错，毕竟是 Spring Cloud 亲儿子，不会始乱终弃。

关注Java技术栈微信公众号，栈长将继续分享 Spring Cloud Gateway 的实战教程，公众号第一时间推送，持续关注。在公众号后台回复：cloud，获取栈长整理的更多的 Spring Cloud 教程，都是实战干货，以下仅为部分预览。

- Spring Cloud 最新 Finchley 版本踩坑
- Spring Cloud 多版本如何选择
- Spring Cloud 是什么，和 Dubbo 对比
- Spring Cloud 注册中心高可用搭建
- Spring Cloud Eureka 自我保护机制
- ……

本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "cloud" 可获取更多 Spring Cloud 教程，转载请原样保留本信息。



