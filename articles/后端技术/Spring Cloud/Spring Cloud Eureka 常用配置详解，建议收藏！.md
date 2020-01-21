前几天，栈长分享了 《[Spring Cloud Eureka 注册中心集群搭建，Greenwich 最新版！](https://mp.weixin.qq.com/s/uyoN8iB1rLOS9mLuvVebbg)》，今天来分享下 Spring Cloud Eureka 常用的一些参数配置及说明。

Spring Boot 的配置参考Java技术栈微信公众号往期 Spring Boot 系列文章，在公众号后台回复：boot。这篇只针对 Spring Cloud Eureka 常用到的配置进行解释。

Spring Cloud Eureka 主要分为下面三个模块的参数：

- **Eureka Server**
- **Eureka Client**
- **Eureka Instance**

## Eureka Server

Eureka Server 的配置参数格式：eureka.server.xxx。

#### enable-self-preservation

表示注册中心是否开启服务的自我保护能力。

什么是自我保护？看这篇文章：[SpringCloud Eureka自我保护机制](https://mp.weixin.qq.com/s/vwPstQ0R0s_PsEhZnALP9Q)，或者关注Java技术栈微信公众号，在后台回复：cloud。

#### renewal-percent-threshold

表示 Eureka Server 开启自我保护的系数，默认：0.85。

#### eviction-interval-timer-in-ms

表示 Eureka Server 清理无效节点的频率，默认 60000 毫秒（60 秒）。

更多 Eureka Server 参数配置可以看一下这个类：

> org.springframework.cloud.netflix.eureka.server.EurekaServerConfigBean

## Eureka Instance

Eureka Instance 的配置参数格式：eureka.instance.xxx。

#### instance-id

表示实例在注册中心注册的唯一ID。

#### prefer-ip-address

- true：实例以 IP 的形式注册
- false：实例以机器 HOSTNAME 形式注册

#### lease-expiration-duration-in-seconds

表示 Eureka Server 在接收到上一个心跳之后等待下一个心跳的秒数（默认 90 秒），若不能在指定时间内收到心跳，则移除此实例，并禁止此实例的流量。

- 此值设置太长，即使实例不存在，流量也能路由到该实例
- 此值设置太小，由于网络故障，实例会被取消流量

需要设置为至少高于 lease-renewal-interval-in-seconds 的值，不然会被误移除了。

#### lease-renewal-interval-in-seconds

表示 Eureka Client 向 Eureka Server 发送心跳的频率（默认 30 秒），如果在 lease-expiration-duration-in-seconds 指定的时间内未收到心跳，则移除该实例。

更多 Eureka Instance 参数配置可以看一下这个类：

> org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean

## Eureka Client

Eureka Client 的配置参数格式：eureka.client.xxx。

#### register-with-eureka

表示此实例是否注册到 Eureka Server 以供其他实例发现。在某些情况下，如果你不想自己的实例被发现，而只想发现其他实例，配置为 false 即可。

#### fetch-registry

表示客户端是否从 Eureka Server 获取实例注册信息。

#### serviceUrl.defaultZone

表示客户端需要注册的 Eureka Server 的地址。

更多 Eureka Client 参数配置可以看一下这个类：

> org.springframework.cloud.netflix.eureka.EurekaClientConfigBean

## 用到的其他参数

#### spring.application.name

表示应用名称，在注册中心中显示的服务注册名称。

#### spring.cloud.client.ip-address

获取客户端的 IP 地址。

上面讲的 Eureka 某些参数可以在 Eureka 控制台上面找到。

![](http://img.javastack.cn/20190423153640.png)

Eureka 控制台上面的其他参数都可以定制。

好了，今天的分享就到这里了，建议转发收藏，不再迷路。

后续会分享更多 Eureka 高级玩法，栈长正在拼命撰写中……关注Java技术栈微信公众号可获取及时推送。在公众号后台回复：cloud，获取栈长整理的更多的 Spring Cloud 教程，都是实战干货，以下仅为部分预览。

- Spring Cloud 配置中心高可用搭建
- Spring Cloud 多版本如何选择
- Spring Cloud 是什么，和 Dubbo 对比
- Spring Cloud 注册中心高可用搭建
- Spring Cloud Eureka 自我保护机制
- ……

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "cloud" 可获取更多 Spring Cloud 教程，转载请原样保留本信息。
