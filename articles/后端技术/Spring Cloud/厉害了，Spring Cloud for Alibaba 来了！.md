最近，Spring Cloud 发布了 Spring Cloud Alibaba 首个预览版本：Spring Cloud for Alibaba 0.2.0.

大家都好奇，这和阿里巴巴有什么关系？莫非是给阿里巴巴定制了一个 Spring Cloud ?

其实也差不多，栈长带大家来看下这到底是个什么鬼？

## Spring Cloud for Alibaba 是什么鬼？

> 官宣：
https://spring.io/blog/2018/10/30/spring-cloud-for-alibaba-0-2-0-released

官方定义如下：

> The Spring Cloud Alibaba project, consisting of Alibaba’s open-source components and several Alibaba Cloud products, aims to implement and expose well known Spring Framework patterns and abstractions to bring the benefits of Spring Boot and Spring Cloud to Java developers using Alibaba products.

栈长翻译：

> Spring Cloud for Alibaba，它是由一些阿里巴巴的开源组件和云产品组成的。这个项目的目的是为了让大家所熟知的 Spring 框架，其优秀的设计模式和抽象理念，以给使用阿里巴巴产品的 Java 开发者带来使用 Spring Boot 和 Spring Cloud 的更多便利。

注意：这个项目是由阿里巴巴维护的开源社区项目。

## 为什么首个版本为 0.2.0 ？

现在 Spring Boot 有两条线，即 Spring Boot 1.x 和 Spring Boot 2.x，所以 0.2.0 即是和 Spring Boot 2.x 兼容的，0.1.0 则是和 Spring Boot 1.x 兼容的。

这样就好理解了。。

## 项目组成部分

上面说的阿里巴巴开源组件，它的项目前缀是：spring-cloud-alibaba，它有几下几个特性。

- 服务发现
- 配置管理
- 安全高可用性

上面说的阿里巴巴云产品，它的项目前缀是：spring-cloud-alicloud，它有几下几个特性。

- 应用命名服务
- 应用配置管理
- 对象存储服务

## 如何使用？

Spring Cloud for Alibaba 0.1.0.RELEASE 和 0.2.0.RELEASE 两个包都已经提交到了 Maven 中央仓库了。

![](http://qianniu.javastack.cn/18-11-21/7555236.jpg)

Spring Cloud for Alibaba 0.2.0.RELEASE 的 Maven 依赖如下：

```
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-alibaba-dependencies</artifactId>
            <version>0.2.0.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

## 后续计划

**1、RocketMQ**

RocketMQ 是阿里开源的基于 Java 的消息队列中间件，目前 Spring Cloud 集成的消息队列只有 Kafka 和 RabbitMQ, 后续 Spring Cloud Stream 和 Spring Cloud Bus 会集成 RocketMQ, 这个牛逼了，期待。。

**2、AliCloud SchedulerX**

阿里云SchedulerX，它是阿里巴巴中间件团队开发的一款分布式任务调度工具，支持循环任务和在指定时间触发任务。 

**3、AliCloud SLS**

阿里云SLS，一站式日志管理服务，是一款阿里巴巴集团行之有效的工具，经历了一次又一次的大数据挑战。它能在不污染任何代码的情况下，快速收集、使用、交付、查询和分析日志数据。

**4、Spring Cloud Release Train**

Spring Cloud Alibaba 项目将于 2019 年从孵化器毕业，到时会正式加入 Spring Cloud 正式版本轨道上来。

## 项目源码

> https://github.com/spring-cloud-incubator/spring-cloud-alibaba

![](http://qianniu.javastack.cn/18-11-21/59298333.jpg)

都 1700+ Star 了。。

## 栈长有话说

其实 Spring Cloud for Alibaba 项目就是为了阿里的项目能很好的结合融入 Spring Boot & Cloud 使用，这个项目目前由阿里维护。

对同时使用 Spring Boot & Cloud 和阿里巴巴项目的人来说无疑带来了巨大的便利，一方面能结合 Spring 无缝接入，另一方面还能使用阿里巴巴的组件，也带来了更多的可选择性。

在感受到 Dubbo 加入 Apache 孵化器后的蜕变《[惊艳，Dubbo域名已改，也不再局限于Java！！](https://mp.weixin.qq.com/s/ZkFh851uwLJwT2cxYNuFWg)》，同时，现在更多优秀的阿里产品融入开源社区，相信 Java 开发环境会越来越好，Java 也会越来越强大！

最后，在Java技术栈微信公众号后台回复：cloud，可获取栈长整理的一系列 Spring Cloud 教程，目前大量教程还在撰写中……

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "cloud" 可获取更多，转载请原样保留本信息。