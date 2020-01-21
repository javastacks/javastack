前些天栈长在Java技术栈微信公众号分享过 Spring Cloud Eureka 的系列文章：

- [Spring Cloud Eureka 自我保护机制
](https://mp.weixin.qq.com/s/vwPstQ0R0s_PsEhZnALP9Q)
- [Spring Cloud Eureka 常用配置详解](https://mp.weixin.qq.com/s/5lUJE_pHZJcvk_gHMhoX2Q)

其中，可能大家关于自我机制的具体保护逻辑还不是特别清楚，今天栈长就具体分析和实战一下，自我保护机制到底是怎么工作的。

现在我们把保护机制开启：

![](http://img.javastack.cn/20190514114125.png)

关注右上角的两个重要参数：

参数 | 说明
---|---
Renews threshold  | Eureka Server 期望每分钟收到客户端实例的总心跳数
Renews (last min) | Eureka Server 最后一分钟收到的总心跳数

我这里显示的数值如下：

```
Renews threshold	6
Renews (last min)	8
```

这个 6 和 8 分别是怎么算出来的？

先来看这两个参数的默认设置，摘自《[Spring Cloud Eureka 常用配置详解](https://mp.weixin.qq.com/s/5lUJE_pHZJcvk_gHMhoX2Q)》一文：


> eureka.server.renewal-percent-threshold：
表示 Eureka Server 开启自我保护的系数，默认：0.85。
 
> eureka.instance.lease-renewal-interval-in-seconds：
表示 Eureka Client 向 Eureka Server 发送心跳的频率（默认 30 秒），如果在 lease-expiration-duration-in-seconds 指定的时间内未收到心跳，则移除该实例。


这里有 4 个注册实例，保护系数：0.85，心跳频率：30秒（每分钟两次），计算公式如下：

```
Renews threshold = 4 * 2 * 0.85 = 6.8（取整为：6）
Renews (last min) = 4 * 2 = 8
```

现在删除一个配置中心实例测试一下：

![](http://img.javastack.cn/20190514113747.png)

出现警告：

> EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.

说明现在 Eureka Server 已经进行保护模式了，并且我删除的那个实例并不会从注册列表中移除，保护机制生效成功。

由此可知：Eureka Server 在一分钟内如果没有收到 6 个以上的心跳，即：Renews threshold >= Renews (last min)，如果保护机制已开启的情况下，则会开启保护机制。

为什么移除一个实例后，Renews threshold 还是 6 呢，算出来应该是 5，这是因为 Eureka Server 还没刷新这个值，默认 15 分钟刷新一次，可以通过设计以下值进行调整：

> eureka.server.renewal-threshold-update-interval-ms=900000

了解了心跳策略和保护机制后，对注册中心的日常维护就会有很多帮助。

好了，今天的分享就到这里了，建议转发收藏，不再迷路。

后续会分享更多 Eureka 高级玩法，栈长正在拼命撰写中……关注Java技术栈微信公众号可获取及时推送。在公众号后台回复：cloud，获取栈长整理的更多的 Spring Cloud 教程，都是实战干货，以下仅为部分预览。

- Spring Cloud 配置中心高可用搭建
- Spring Cloud 多版本如何选择
- Spring Cloud 是什么，和 Dubbo 对比
- Spring Cloud 注册中心高可用搭建
- Spring Cloud Eureka 自我保护机制
- ……

本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "cloud" 可获取更多 Spring Cloud 教程，转载请原样保留本信息。
