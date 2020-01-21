
上一篇：[Spring Boot 2.2.0 正式发布，支持 JDK 13！](https://mp.weixin.qq.com/s/3TF6ooiW3JUzSGeAiQ9m8g)

Spring Boot 2.2.0 没发布多久，Spring Boot 2.2.1 又发布了，这是一个很有意思，又有点 "坑" 的一个版本。

除了日常的 bug 修复、优化提升、依赖升级，这个版本又颠覆了一个 Spring Boot 2.2.0 配置。

如果你已经升级到了 Spring Boot 2.2.0，`@ConfigurationProperties` 注解是可以不需要定义 Bean 直接使用的。

`@ConfigurationProperties`如何使用，可以看这篇文章：
[Spring Boot读取配置的几种方式](https://mp.weixin.qq.com/s/aen2PIh0ut-BSHad-Bw7hg)

来看下使用区别，真是够折腾的几个版本。

**Spring Boot 2.2.0 之前：**

```
@Configuration
@ConfigurationProperties(prefix = "xxx")
public class XXXProperties
```

**Spring Boot 2.2.0：**

```
@ConfigurationProperties(prefix = "xxx")
public class XXXProperties
```

看见没，不需要定义 Bean，可以直接被自动扫描到。

而在 Spring Boot 2.2.1 中又作了调整：

**Spring Boot 2.2.1：**

![](http://img.javastack.cn/20191108112508.png)

`@ConfigurationProperties` 自动扫描支持被废弃，恢复到 2.2.0 之前的用法了，又加了一个新的配置方法：

```
@Configuration
@ConfigurationProperties(prefix = "xxx")
public class XXXProperties
```

或者是：

```
@ConfigurationProperties(prefix = "xxx")
public class XXXProperties

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableConfigurationProperties
public class Application 
```

这个版本加了一个 `@ConfigurationPropertiesScan` 注解，用来主动扫描没有定义 Bean 的 `@ConfigurationProperties`，相当于一个总开关，我觉得倒挺好的。

如果从 Spring Boot 2.2.0 过度到 Spring Boot 2.2.1，对于这个配置，如果不注意，就可能会有问题。

所以，千万别升级太快，否则是给自己挖坑……

未完，栈长将陆续分享 Spring Boot 最新技术教程，现在已经写了一堆存货了，关注微信公众号 "Java技术栈" ，公众号第一时间推送！

![](http://img.javastack.cn/wx_search_javastack.png)