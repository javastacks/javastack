
好久没有研究 Spring Cloud 了，也没有关注它的更新及新特性，上官网看了下，又增加了几个版本，有正式版有预览版，多达 6 个版本，实在让人蒙逼。

而我们的项目版本还仪停留在 Dalston SR1 版本。

#### Spring Cloud 的多版本

![](http://img.javastack.cn/18-4-2/44559010.jpg)

这么多不同的版本，它们都有什么区别，我们开发微服务到底使用哪一个版本呢？这些版本与 Spring Boot 版本又是一个怎样的依赖关系？带着这些问题，我们必须把它一一搞清楚，不然总会让你很头疼。。

首先 Spring Cloud 是一个由很多子项目组成的庞大项目，Spring Cloud 的主要项目如下：


```
Spring Cloud Config
Spring Cloud Netflix
Spring Cloud Bus
Spring Cloud Cluster
Spring Cloud Security
...

更多请参考官方说明：
https://projects.spring.io/spring-cloud/
```

然而，这些子项目都有不同的发布阶段，为了管理主项目和子项目的依赖关系，以及为了避免和子项目版本的冲突和误解，主项目版本命名并没有采用和子项目数字版本化的形式，而是采用了英文名称。

英文命名方式也比较有意思，Spring Cloud 采用了英国伦敦地铁站的名称来命名，并由地铁站名称字母A-Z依次类推的形式来发布迭代版本。

由上可知，Spring Cloud 的第一个版本 "Angel" 就不觉得奇怪了，接着 "Brixton" 就是第二个版本。当一个项目到达发布临界点或者解决了一个严重的BUG后就会发布一个 "service Release" 版本， 简称 SR（X）版本，x 代表一个递增数字。

**由此我们可以得出 "Finchley M9" 就是目前最新的开发版本，"Edgware SR3" 是最新稳定版本。**

#### Spring Cloud 版本依赖关系

下图揭示了主项目和子项目版本的依赖关系。

![](http://img.javastack.cn/18-4-2/50615593.jpg)

#### Spring Cloud & Spring Boot 依赖关系

![](http://img.javastack.cn/18-4-2/47489606.jpg)

- Finchley 是基于 Spring Boot 2.0.x 构建的，不支持 Spring Boot 1.5.x
- Dalston 和 Edgware 是基于 Spring Boot 1.5.x 构建的，不支持 Spring Boot 2.0.x
- Camden 构建于 Spring Boot 1.4.x，但依然能支持 Spring Boot 1.5.x

注意：Brixton 和 Angel 版本在2017年7月已经停止更新迭代了，我们就不关注它的版本依赖关系了。

Spring Cloud 正处理高速发展期，版本发布更新太快，项目更新版本还要考虑兼容性等各种问题，让人很头疼！后续我会慢慢揭开这些面纱。。

