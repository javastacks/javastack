Spring Boot 提供的自动配置非常强大，某些情况下，自动配置的功能可能不符合我们的需求，需要我们自定义配置，这个时候就需要排除/禁用 Spring Boot 某些类的自动化配置了。

比如：数据源、邮件，这些都是提供了自动配置的，我们需要排除 Spring Boot 的自动化配置，交给我们自己来自定义，该如何做呢？

今天栈长给你介绍 4 种排除方式，总有一种能帮到你！

#### 方法1

使用 `@SpringBootApplication` 注解的时候，使用 exclude 属性进行排除指定的类：

```
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MailSenderAutoConfiguration.class})
public class Application {
    // ...
}
```

自动配置类不在类路径下的时候，使用 excludeName 属性进行排除指定的类名全路径：

```
@SpringBootApplication(excludeName = {"org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration", "org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration"})
public class Application {
    // ...
}
```

这个注解集成了 `@EnableAutoConfiguration` 注解及其里面的参数，这个不用多解释了，具体看这篇文章：[Spring Boot 最核心的 3 个注解详解](https://mp.weixin.qq.com/s/kNvy_0jb4oJtYdaxryq5xg)。另外，关注Java技术栈微信公众号，在后台回复：boot，可以获取更多 Spring Boot 干货。

#### 方法2

单独使用 `@EnableAutoConfiguration` 注解的时候：

```
@...
@EnableAutoConfiguration
(exclude = {DataSourceAutoConfiguration.class, MailSenderAutoConfiguration.class})
public class Application {
    // ...
}
```

自动配置类不在类路径下的时候，使用 excludeName 属性进行排除指定的类名全路径：

```
@...
@EnableAutoConfiguration {"org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration", "org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration"})
public class Application {
    // ...
}
```

#### 方法3

使用 Spring Cloud 和 `@SpringCloudApplication` 注解的时候：

```
@...
@EnableAutoConfiguration
(exclude = {DataSourceAutoConfiguration.class, MailSenderAutoConfiguration.class})
@SpringCloudApplication
public class Application {
    // ...
}
```

Spring Cloud 必须建立在 Spring Boot 应用之上，所以这个不用多解释了。

#### 方法4

终极方案，不管是 Spring Boot 还是 Spring Cloud 都可以搞定，在配置文件中指定参数 `spring.autoconfigure.exclude` 进行排除：

```
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
    org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration
```

或者还可以这样写：

```
spring.autoconfigure.exclude[0]=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
spring.autoconfigure.exclude[1]=org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration
```

如果你用的是 yaml 配置文件，可以这么写：

```
spring:     
  autoconfigure:
    exclude:
      - org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
      - org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration
```

知道了这 4 种排除方法，我们使用 Spring Boot 的自动配置功能就游刃有余了，怎么样？都 get 到了不？建议转发+收藏，再置顶公众号，以后不迷路~

好了，今天的分享就到这里，更多 Spring Boot 文章正在撰写中，关注Java技术栈微信公众号获取第一时间推送。在公众号后台回复：boot，还能获取栈长整理的往期 Spring Boot 教程，都是实战干货，以下仅为部分预览。

- Spring Boot 读取配置的几种方式
- Spring Boot 如何做参数校验？
- Spring Boot 最核心的 25 个注解！
- Spring Boot 2.x 启动全过程源码分析
- Spring Boot 2.x 新特性总结及迁移指南
- ……

获取所有 Spring Boot 示例代码，请关注微信公众号 "Java技术栈" 在后台回复关键字：bootcode。

未完，栈长将陆续分享 Spring Boot 最新技术教程，现在已经写了一堆存货了，关注微信公众号 "Java技术栈" ，公众号第一时间推送！

![](http://img.javastack.cn/wx_search_javastack.png)