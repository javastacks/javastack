最近栈长面试了不少人，其中不乏说对 Spring Boot 非常熟悉的，然后当我问到一些 Spring Boot 核心功能和原理的时候，没人能说得上来，或者说不到点上，可以说一个问题就问趴下了！

这是我的问题：

**我看你上面写了熟悉 Spring Boot，那你能讲下为什么我们要用 Spring Boot 吗？**

下面我列几个最常见的三个回答：

**A：Spring Boot 最主要是不用 XML 配置，可以用 Java 来配置 bean，省去了许多配置文件。**

我又问：Spring 本身就可以用 Java 配置代替 XML 配置，和 Spring Boot 有什么关系呢？

然后对方就吱吱唔唔了……

**B：Spring Boot 我们用来做 Spring Cloud 微服务。**

我又问：微服务和 Spring Boot 有什么关系？不用 Spring Boot 行不行？

然后对方就吱吱唔唔了……

**C：Spring Boot 可以打 jar 包部署，内部集成了Tomcat。**

这个确实是 Spring Boot 的特色，但是我还是觉得没有答到关键点上。

然后我继续问，如果不考虑打 jar 包部署呢，然后就没然后了……

为什么我们要用 Spring Boot，显然上面三个求职者没有答到关键点上，Spring Boot 最重要的功能是：**自动配置**。

**为什么说是自动配置？**

Spring Boot 的开启注解是：@SpringBootApplication，其实它就是由下面三个注解组成的：

- @Configuration
- @ComponentScan
- @EnableAutoConfiguration

上面三个注解，前面两个都是 Spring 自带的，和 Spring Boot 无关，所以说上面的回答的不是在点上。具体请看这篇文章：[Spring Boot 最核心的 3 个注解详解](https://mp.weixin.qq.com/s/kNvy_0jb4oJtYdaxryq5xg)。

所以说 Spring Boot 最最核心的就是这个 `@EnableAutoConfiguration` 注解了，它能根据类路径下的 jar 包和配置动态加载配置和注入bean。

举个例子，比如我在 lib 下放一个 druid 连接池的 jar 包，然后在 application.yml 文件配置 druid 相关的参数，Spring Boot 就能够自动配置所有我们需要的东西，如果我把 jar 包拿掉或者把参数去掉，那 Spring Boot 就不会自动配置。

这样我们就能把许多功能做成公共的自动配置的启动器（starters），其实 druid 连接池就是这么做的，它提供了针对 Spring Boot 的启动器：druid-spring-boot-starter。

有了这个自动配置的启动器，我们就能非常简单的使用它，

先添加 jar 包依赖：

```
<dependency>
   <groupId>com.alibaba</groupId>
   <artifactId>druid-spring-boot-starter</artifactId>
   <version>1.1.10</version>
</dependency>
```

再添加相关参数：

```
spring.datasource.url= 
spring.datasource.username=
spring.datasource.password=
……
```

如果是传统的项目，我们要自己手动写一大堆的配置，而且还不灵活，有了这个启动器，我们就可以做到简单集成。具体大家可以看 druid-spring-boot-starter 是怎么实现的，也可以参考之前写的文章：[Spring Boot自动配置原理、实战](https://mp.weixin.qq.com/s/gs2zLSH6m9ijO0-pP2sr9Q)。

所以，这才是 Spring Boot 的核心，这才是我们为什么使用 Spring Boot 的原因。如果答不到这个关键点，那真没有掌握到 Spring Boot 的核心所在。

好了，今天的分享就到这里，关注Java技术栈微信公众号，在后台回复：boot，获取栈长整理的更多的 Spring Boot 教程，都是实战干货，以下仅为部分预览。

- Spring Boot 读取配置的几种方式
- Spring Boot 如何做参数校验？
- Spring Boot 最核心的 25 个注解！
- Spring Boot 2.x 启动全过程源码分析
- Spring Boot 2.x 新特性总结及迁移指南
- ……

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "boot" 可获取更多 Spring Boot 教程，转载请原样保留本信息。

