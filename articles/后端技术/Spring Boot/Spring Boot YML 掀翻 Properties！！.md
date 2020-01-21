.properties 配置文件大家应该都很熟悉，键值对嘛，.yml 配置文件栈长也是从 Spring Boot 开始了解到的。

那么，这两种格式的配置文件到底有哪些区别呢？哪个更好？能不能替换代替？今天，栈长就来解开这些谜团，看 YML 能不能掀翻Properties。。。

**.properties格式：**

```
spring.application.name=register-center
spring.security.user.name=javastack
spring.security.user.password=javastack
```

**.yml格式：**

```
spring:
  application:
    name: register-center
  security:
    user:
      name: javastack
      password: javastack
```

如上所示，.properties 是键值对形式，.yml 是树状结构的，配置更方便，可以直接写中文，阅读也可友好。

这还不算什么，yml最强大的是能节省很多配置，如下所示：

```
---
spring:
  profiles: rc1

server:
  port: 8761

eureka.instance.hostname: eureka1

---
spring:
  profiles: rc2

server:
  port: 8762

eureka.instance.hostname: eureka2

---
spring:
  profiles: rc3

server:
  port: 8763

eureka.instance.hostname: eureka3
```

以上配置，yml一个配置文件就能搞定，而用 properties 则需要三个文件，具体你可以看下Java技术栈微信公众号发过的《[Spring Boot Profile不同环境配置](https://mp.weixin.qq.com/s/K0kdQwoo2t5FDsTUJttSAA)》这篇文章就知道 yml 的厉害了。

但在 .yml 配置文件中需要注意以下几点：

- 键冒号后面需要带一个空格
- 缩进只能用空格，不能用tab
- @PropertySource注解不能加载yml文件

不考虑 @PropertySource 注解，栈长现在用 yml 完全可以代替 properties 配置文件，能用 yml 就不会用 properties 了，可以看这篇文章：[Spring Boot读取配置的几种方式](https://mp.weixin.qq.com/s/aen2PIh0ut-BSHad-Bw7hg)。

当然这只是在 Spring Boot 中能完全替代，某些框架还是需要用 properties 的，如：某些日志配置文件、第三方的配置文件等。

你还知道其他的 yml 的更多玩法不，欢迎留言分享~

好了，今天的分享就到这里，关注Java技术栈微信公众号，在后台回复：boot，获取栈长整理的更多的 Spring Boot 教程，都是实战干货，以下仅为部分预览。

- Spring Boot 读取配置的几种方式
- Spring Boot 如何做参数校验？
- Spring Boot 最核心的 25 个注解！
- Spring Boot 2.x 启动全过程源码分析
- Spring Boot 2.x 新特性总结及迁移指南
- ……

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "boot" 可获取更多 Spring Boot 教程，转载请原样保留本信息。

