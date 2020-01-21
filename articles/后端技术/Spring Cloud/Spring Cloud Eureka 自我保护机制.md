
## 自我保护背景

首先对Eureka注册中心需要了解的是Eureka各个节点都是平等的，没有ZK中角色的概念， 即使N-1个节点挂掉也不会影响其他节点的正常运行。

默认情况下，如果Eureka Server在一定时间内（默认90秒）没有接收到某个微服务实例的心跳，Eureka Server将会移除该实例。但是当网络分区故障发生时，微服务与Eureka Server之间无法正常通信，而微服务本身是正常运行的，此时不应该移除这个微服务，所以引入了自我保护机制。

## 自我保护机制

官方对于自我保护机制的定义：

> https://github.com/Netflix/eureka/wiki/Understanding-Eureka-Peer-to-Peer-Communication

自我保护模式正是一种针对网络异常波动的安全保护措施，使用自我保护模式能使Eureka集群更加的健壮、稳定的运行。

自我保护机制的工作机制是如果在15分钟内超过85%的客户端节点都没有正常的心跳，那么Eureka就认为客户端与注册中心出现了网络故障，Eureka Server自动进入自我保护机制，此时会出现以下几种情况：

1、Eureka Server不再从注册列表中移除因为长时间没收到心跳而应该过期的服务。\
2、Eureka Server仍然能够接受新服务的注册和查询请求，但是不会被同步到其它节点上，保证当前节点依然可用。\
3、当网络稳定时，当前Eureka Server新的注册信息会被同步到其它节点中。

因此Eureka Server可以很好的应对因网络故障导致部分节点失联的情况，而不会像ZK那样如果有一半不可用的情况会导致整个集群不可用而变成瘫痪。

## 自我保护开关

Eureka自我保护机制，通过配置`eureka.server.enable-self-preservation`来true打开/false禁用自我保护机制，默认打开状态，建议生产环境打开此配置。

## 开发环境配置

开发环境中如果要实现服务失效能自动移除，只需要修改以下配置。

#### 1、 注册中心关闭自我保护机制，修改检查失效服务的时间。

```
eureka:
  server: 
    enable-self-preservation: false
    eviction-interval-timer-in-ms: 3000
```

#### 2、 微服务修改减短服务心跳的时间。

```
# 默认90秒
lease-expiration-duration-in-seconds: 10
# 默认30秒
lease-renewal-interval-in-seconds: 3
```

以上配置建议在生产环境使用默认的时间配置。