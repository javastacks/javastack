Spring Cloud Greenwich 正式版在 01/23/2019 这天正式发布了，下面我们来看下有哪些更新内容。

## 生命周期终止提醒

#### Spring Cloud Edgware 

Edgware 版本将于 08/01/2019 正式退役，具体可以参考官方宣布：

> https://spring.io/blog/2018/07/30/spring-cloud-edgware-eol-aug-1st-2019

#### Spring Cloud Finchley

Finchley 版本作为 Spring Boot 2.0.x 的主要版本，Finchley 的生命周期也会由 Spring Boot 2.0.x 版本的终止而终止。

#### Spring Cloud Greenwich

Greenwich 版本现在作为一个次要版本，它后续将继续支持 Spring Boot 2.x 的发布支持。

分不清这些版本的区别和意义？可以参考栈长之前写的关于版本的文章：
[Spring Cloud 多版本怎么选择？](https://mp.weixin.qq.com/s/IqlHFsIrFJ5vBG9-1gldJw)。

## Greenwich 重大更新

#### 兼容JDK

这个最新版本的发布最重要的一点是，所有的子项目都兼容 Java 11。

#### 新项目

**1、Spring Cloud GCP**

提供对 Google Cloud Platform 的集成。

**2、Spring Cloud Kubernetes**

提供对 Kubernetes 的集成。

#### Spring Cloud Netflix 进入维护模式

最近，Netflix 宣布 Netflix 进入维护模式：《[Hystrix 停止开发。。。Spring Cloud 何去何从？](https://mp.weixin.qq.com/s/WPb8yUYPyvDCKJVb--W63Q)》，Ribbon 自 2016 年以来也一直处于类似的状态，尽管它们已经进入维护模式，但它们在 Netflix 内部已经大规则部署应用。

另外，Hystrix Dashboard 和 Turbine 已经被 Atlas 取代，这两个项目最后一次的代码提交分别是 2 年前和 4 年前了。Zuul 1 和 Archaius 1 也已经被后续的版本取代，不再向后续版本兼容。

以下 Spring Cloud Netflix 模块及相应启动器将进入维护模式：

- spring-cloud-netflix-archaius
- spring-cloud-netflix-hystrix-contract
- spring-cloud-netflix-hystrix-dashboard
- spring-cloud-netflix-hystrix-stream
- spring-cloud-netflix-hystrix
- spring-cloud-netflix-ribbon
- spring-cloud-netflix-turbine-stream
- spring-cloud-netflix-turbine
- spring-cloud-netflix-zuul

这些并不包括 Eureka 或者 concurrency-limits 模块。

**什么是维护模式？**

这些个项目进入维护模式后，Spring Cloud 团队也不会往这些模块添加新功能了，但是还是会修复一些 bug 及安全漏洞，也会考虑和审查来自社区的小规模拉取请求。

Spring Cloud Greenwich 版本在这些维护模式的项目上至少会支持 1 年。

**有什么替换方案？**

官方推荐了以下替代方案，也许在不久的将来，或者下一个大版本这些替代方案会成为主流项目。

目前的 | 可替换
---|---
Hystrix	   |  Resilience4j
Hystrix Dashboard / Turbine   |  Micrometer + Monitoring System
Ribbon	   |  Spring Cloud Loadbalancer
Zuul 1	   |  Spring Cloud Gateway
Archaius 1 |  Spring Boot external config + Spring Cloud Config

这个版本除了以上重大更新，还有一些子项目的版本更新、问题修复等，这里就不详细分析了，大家有兴趣的可以去看官方发布博文：

> https://spring.io/blog/2019/01/23/spring-cloud-greenwich-release-is-now-available

如果你也在使用 Spring Cloud 搭建微服务，可以关注Java技术栈微信公众号，在后台回复关键字：spring，栈长整理了一系列 boot/ cloud 技术文章，都是干货。

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "spring" 可获取更多，转载请原样保留本信息。