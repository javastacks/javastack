
**今年 Dubbo 活了，并且被 Apache 收了。同时很不幸，Spring Cloud 下的 Netflix Eureka 组件项目居然宣布停止开发了。。**

已经从 Dubbo 迁移至 Spring Cloud 上的人，你们还好吗？

> 闭源：https://github.com/Netflix/eureka/wiki

![](http://img.javastack.cn/18-7-10/71721072.jpg)

**大概意思是：** Eureka 2.0 的开源工作已经停止，依赖于开源库里面的 Eureka 2.x 分支构建的项目或者相关代码，风险自负！

#### Eureka 是什么？

用 Spring Cloud 作为微服务框架的开发者应该都知道，Eureka 是其默认的也是推荐的服务注册中心组件。

既然首推 Eureka 作为服务注册中心组件也是因为 Netflix 优秀的各种套件，如 Zuul（服务网关组件）、Hystrix（熔断组件） 等都是 Spring Cloud 一站式解决方案。

**我们来看下 Eureka 和服务注册的关系图。**

![](http://img.javastack.cn/18-7-10/66188885.jpg)

#### Eureka 开源史末

Netflix 公司 2012 年将 Euerka 正式开源。

![](http://img.javastack.cn/18-7-10/27687996.jpg)

Eureka 1.x 最新版本 1.9.3，不知道是否会成为 Eureka 最后的开源版本。

![](http://img.javastack.cn/18-7-10/33405423.jpg)

本次 2.x 的的突然停止开发在其官网未到相关申明，希望不是闭源，是否开历史倒车，是否有其他阴谋，我们将持续跟进。

#### Spring Cloud 何去何从？

对于 Eureka 2.x 的停止开发，Spring Cloud 将何去何从？后续会不会替换默认的服务注册组件呢？不得而知，Spring Cloud 版本发布很快，已经快跟不上了。

Eureka 2.x 还未发布正式版本，Spring Cloud 还是在 1.x 上面开发的，最新版本依赖 1.9.2，所以虽然国内大多数公司也在用 Eureka，但暂时不会受影响。

![](http://img.javastack.cn/18-7-10/62658130.jpg)

1.x 相对稳定，也在持续更新，建议不要盲目升级或者切换到别的中间件。不过，随着 Eureka 2.x 的停更，后续还会不会有 3.x，如果没有，是否有必要迁移至 Consul、ZooKeeper、Etcd 等开源中间件上面去呢？

对于 Eureka 的闭源及带来的影响，你怎么看？欢迎留言讨论。

