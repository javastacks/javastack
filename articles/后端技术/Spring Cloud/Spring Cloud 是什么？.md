## 简介

Spring Cloud是一系列框架的有序集合。它利用Spring Boot的开发便利性巧妙地简化了分布式系统基础设施的开发，如服务发现注册、配置中心、消息总线、负载均衡、断路器、数据监控等，都可以用Spring Boot的开发风格做到一键启动和部署。

Spring并没有重复制造轮子，它只是将目前各家公司开发的比较成熟、经得起实际考验的服务框架组合起来，通过Spring Boot风格进行再封装屏蔽掉了复杂的配置和实现原理，最终给开发者留出了一套简单易懂、易部署和易维护的分布式系统开发工具包。

目前最新版本：Dalston.SR3

> 官网：http://projects.spring.io/spring-cloud/

## 特性
Spring Cloud专注于提供良好的开箱即用经验的典型用例和可扩展性机制覆盖。

- 分布式/版本化配置

- 服务注册和发现

- 路由

- service - to - service调用

- 负载均衡

- 断路器

- 分布式消息传递

## 子项目

Spring Cloud的子项目，大致可分成两类，一类是对现有成熟框架”Spring Boot化”的封装和抽象，也是数量最多的项目；第二类是开发了一部分分布式系统的基础设施的实现，如Spring Cloud Stream扮演的就是kafka, ActiveMQ这样的角色。对于我们想快速实践微服务的开发者来说，第一类子项目就已经足够使用，如：

- Spring Cloud Netflix

    是对Netflix开发的一套分布式服务框架的封装，包括服务的发现和注册，负载均衡、断路器、REST客户端、请求路由等。
　　
- Spring Cloud Config

    将配置信息外部化存储, 并能配合Spring Cloud Bus可以实现动态修改配置文件。
　　
- Spring Cloud Bus

    分布式消息队列，是对Kafka, MQ的封装。
　　
- Spring Cloud Security

    是对Spring Security的封装，能实现服务之间的认证调用和安全保护等，并能配合Netflix使用。
　　
- Spring Cloud Zookeeper

    对Zookeeper的封装，使之能配置其它Spring Cloud的子项目使用。
　　
- Spring Cloud Eureka

    是 Spring Cloud Netflix微服务套件中的一部分，它基于Netflix Eureka 做了二次分装，主要负责完成微服务架构中的服务治理功能。

## 前景

Spring Cloud对于中小型互联网公司来说是一种福音，因为这类公司往往没有实力或者没有足够的资金投入去开发自己的分布式系统基础设施，使用Spring Cloud一站式解决方案能在从容应对业务发展的同时大大减少开发成本。同时，随着近几年微服务架构和Docker容器概念的火爆，也会让Spring Cloud在未来越来越“云”化的软件开发风格中立有一席之地，尤其是在目前五花八门的分布式解决方案中提供了标准化的、全站式的技术方案，意义可能会堪比当前Servlet规范的诞生，有效推进服务端软件系统技术水平的进步。

## 和dubbo的对比

组件 | Dubbo | Spring Cloud
---|---|---
服务注册中心 | Zookeeper | Spring Cloud Netflix Eureka
服务调用方式 | RPC | REST API
服务网关 | 无 | Spring Cloud Netflix Zuul
断路器 | 不完善 | Spring Cloud Netflix Hystrix
分布式配置 | 无 | Spring Cloud Config
服务跟踪 | 无 | Spring Cloud Sleuth
消息总线 | 无 | Spring Cloud Bus
数据流 | 无 | Spring Cloud Stream
批量任务 | 无 | Spring Cloud Task
... | ... | ...
 
所以，对比看出其实没什么比较性，Dubbo只是Spring Cloud的一个子集而已，Spring Cloud是一系列的分布式的解决方案包。不过随着目前Dubbo官方的重新申明维护并得到重视，Dubbo生态圈也会逐渐强大。

