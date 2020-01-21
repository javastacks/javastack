![](http://qianniu.javastack.cn/18-11-29/72621668.jpg)

栈长得到消息，Hystrix 停止开发了。。。

大家如果有对 Hystrix 不清楚的，请看下这篇文章：[分布式服务防雪崩熔断器，Hystrix理论+实战](https://mp.weixin.qq.com/s/w9lK_lwfaTbq5aU4byugaA)。

来看下 Hystrix 停止开发官宣：

> https://github.com/Netflix/Hystrix

![](http://qianniu.javastack.cn/18-11-29/66626010.jpg)

**文中大概的意思是：**

Hystrix 不再继续开发了，目前的稳定版本 1.5.18 已经足够满足现有应用对 Hystrix 的需求。

停止开发，意味着：

- 不再主动修复bugs
- 不再接受合并请求
- 不再发布新版本

即使停止开发，但不影响现有的项目，大家可以继续使用 Hystrix，没有问题的。但新项目还是推荐大家使用开源容错组件：Resilience4j。

> Resilience4j 是一个轻量级的容错组件，其灵感来自于 Hystrix，主要为 Java 8 和函数式编程设计的.

看到这里，栈长表示学不动了。。。

![](http://qianniu.javastack.cn/18-11-29/10737668.jpg)

同时，它们的重心不再是预先配置达到限流的目的，而转移到了应用程序本身的实时性能上。

这些年来，Hystrix 为 Netflix 和各大互联网公司提供了良好的服务，停止开发并不意味着 Hystrix 的理念不再有价值，反而激发了许多更优秀的项目。

## Spring Cloud 何去何从？

为什么这么说？因为 Spring Cloud 默认使用 Hystrix 作为其服务默认的熔断组件，Hystrix 的停止开发必然会对 Spring Cloud 造成影响。。。

上面说了，Hystrix 官方推荐替代的开源组件：Resilience4j，这个栈长也没有用过，查了下，资料也比较稀少。

那除了 Resilience4j 这个，还有没有别的替代品呢？

前些天已经告诉大家《[Spring Cloud for Alibaba 来了](https://mp.weixin.qq.com/s/69ecH-MN5ZOnvSnSiWMu-w)》，这其中就会包含另外一个阿里开源的组件：Sentinel，它是一个轻量级的高可用防护的流量管理框架。

Sentinel 开源地址：
> https://github.com/alibaba/Sentinel

但现在 Spring Cloud Alibaba 项目还在孵化当中，2019 年才转正，所以现在集成 Spring Cloud 用 Sentinel 还为时过早。

后面 Spring Cloud 会使用什么组件作为其默认的熔断组件还不好说，但我们必须提前做好准备，知道了这些替代品，就不会慌了。

Spring Cloud 也是醉了，Eureka 2.x 难产，现在 Hystrix 又停止开发，大家怎么看？

> 本文原创首发于微信公众号：Java技术栈（id:javastack），转载请原样保留本信息。