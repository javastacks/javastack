最近面试一些 Java 开发者，他们其中有些在公司实际用过 Spring Boot, 有些是自己兴趣爱好在业余自己学习过。然而，当我问他们 Spring Boot 最核心的 3 个注解是什么，令我失望的是鲜有人能答上来，这样你能说你对 Spring Boot 很了解吗？这可能还会给你减分！

## 你所需具备的基础

- [什么是 Spring Boot?](https://mp.weixin.qq.com/s/jWLcPxTg9bH3D9_7qbYbfw)
- [Spring Boot 核心配置文件详解](https://mp.weixin.qq.com/s/BzXNfBzq-2TOCbiHG3xcsQ)
- [Spring Boot 开启的 2 种方式](https://mp.weixin.qq.com/s/PYM_iV-u3dPMpP3MNz7Hig)
- [Spring Boot 自动配置原理、实战](https://mp.weixin.qq.com/s/gs2zLSH6m9ijO0-pP2sr9Q)
- [Spring Boot 2.x 启动全过程源码分析](https://mp.weixin.qq.com/s/iMPXjuKRKT5lMZ4oVSp4Ww)

更多请在Java技术栈微信公众号后台回复关键字：boot。

## Spring Boot 核心注解讲解

Spring Boot 最大的特点是无需 XML 配置文件，能自动扫描包路径装载并注入对象，并能做到根据 classpath 下的 jar 包自动配置。

所以 Spring Boot 最核心的 3 个注解就是：

#### 1、@Configuration

> org.springframework.context.annotation.Configuration

这是 Spring 3.0 添加的一个注解，用来代替 applicationContext.xml 配置文件，所有这个配置文件里面能做到的事情都可以通过这个注解所在类来进行注册。

下面几个相关注解也是非常重要的！

**@Bean**

用来代替 XML 配置文件里面的 `<bean ...>` 配置。

**@ImportResource**

如果有些通过类的注册方式配置不了的，可以通过这个注解引入额外的 XML 配置文件，有些老的配置文件无法通过 `@Configuration` 方式配置的非常管用。

**@Import**

用来引入额外的一个或者多个 `@Configuration` 修饰的配置文件类。

**@SpringBootConfiguration**

这个注解就是 `@Configuration` 注解的变体，只是用来修饰是 Spring Boot 配置而已，或者可利于 Spring Boot 后续的扩展，源码如下。

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
public @interface SpringBootConfiguration {

}
```

#### 2、@ComponentScan

> org.springframework.context.annotation.ComponentScan

这是 Spring 3.1 添加的一个注解，用来代替配置文件中的 `component-scan` 配置，开启组件扫描，即自动扫描包路径下的 `@Component` 注解进行注册 bean 实例到 context 中。

另外，`@ComponentScans` 是可重复注解，即可以配置多个，用来配置注册不同的子包。

关于重复注解，大家可以看这篇文章《[JAVA元注解@interface详解](https://mp.weixin.qq.com/s/FSrtDEwILSM-Q2ocnZdNbA)》。

#### 3、@EnableAutoConfiguration

> org.springframework.boot.autoconfigure.EnableAutoConfiguration

看全路径就知道，这是自 Spring Boot 诞生时添加的注解，用来提供自动配置，上面的两个都是 `spring-context` 包下的，不属于 Spring Boot，所以 Spring 3.0 之后的去 XML 配置方式已经为 Spring Boot 埋下了伏笔！
    
自动配置更多详细使用及实战可以看这篇文章《[Spring Boot自动配置原理、实战](https://mp.weixin.qq.com/s/gs2zLSH6m9ijO0-pP2sr9Q)》。

#### 最后的彩蛋

Spring Boot 最核心的 3 个注解介绍完了，大家可能会有疑问，为什么最主要的注解 `@SpringBootApplication` 不在其中？

留给大家的彩蛋，其实这个 `@SpringBootApplication` 注解就包含了以上 3 个主要注解，平时没有自定义配置的需求，则使用 `@SpringBootApplication` 注解完全就可以了！

让我们来看下 `@SpringBootApplication` 注解的源码，一切明了！

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
		@Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {

    ...

}
```

![](http://img.javastack.cn/18-8-27/31801590.jpg)

看完涨姿势了吗？

点赞转发一下吧，以后碾压面试官！

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "boot" 可获取更多，转载请原样保留本信息。