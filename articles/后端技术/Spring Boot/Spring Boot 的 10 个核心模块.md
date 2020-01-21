学习 Spring Boot 必须得了解它的核心模块，和 Spring 框架一样，Spring Boot 也是一个庞大的项目，也是由许多核心子模块组成的。

## 你所需具备的基础

- [告诉你，Spring Boot 真是个牛逼货！](https://mp.weixin.qq.com/s/jsvvBQYs6DKBEFo3Qz3YDA)
- [Spring Boot 核心配置文件详解](https://mp.weixin.qq.com/s/BzXNfBzq-2TOCbiHG3xcsQ)
- [Spring Boot 开启的 2 种方式](https://mp.weixin.qq.com/s/PYM_iV-u3dPMpP3MNz7Hig)
- [Spring Boot 自动配置原理、实战](https://mp.weixin.qq.com/s/gs2zLSH6m9ijO0-pP2sr9Q)
- [Spring Boot 2.x 启动全过程源码分析](https://mp.weixin.qq.com/s/iMPXjuKRKT5lMZ4oVSp4Ww)

更多请在Java技术栈微信公众号后台回复关键字：boot。

## Spring Boot 的核心模块

下面我们大概来了解一下 Spring Boot 的核心模块。

**1、spring-boot**

这是 Spring Boot 的主模块，也是支持其他模块的核心模块，主要包含以下几点：

1) 提供了一个启动 Spring 应用的主类，并提供了一个相当方便的静态方法，它的主要是作用是负责创建和刷新 Spring 容器的上下文；

2) 内嵌式的并可自由选择搭配的 WEB 应用容器，如：`Tomcat`, `Jetty`, `Undertow`等；

3) 对配置外部化的支持；

4) 提供一个很方便的 Spring 容器上下文初始化器，包括合理记录日志默认参数的支持。

**2、spring-boot-autoconfigure**

Spring Boot能根据类路径下的内容自动一些公共大型应用，提供的 `@EnableAutoConfiguration` 注解就能启用 Spring 功能的自动配置。

自动配置功能可以推断用户可能需要加载哪些 Spring Bean, 如：如果类路径下有 `HicariCP` 这个连接池的包，此时并未提供任何有效连接池的配置，那么 Spring Boot 就知道你可能需要一个连接池，并做相应配置。如果用户配置了其他连接池，那么 Spring Boot 会放弃自动配置。

**3、spring-boot-starters**

Starters，我们叫它启动器好了，它是包括一系列依赖的描述符。简单的说就是，它可以一站式的帮你打包 Spring 及相关技术应用，而不需要你到处找依赖和示例配置代码，它都帮你做好了。

例如，第一章我们在介绍 Spring Boot 的时候就说了 `spring-boot-starter-web` 这个启动器，你只要引用了这个启动器应用，就会自动配置 WEB 应用的能力。

`spring-boot-starters` 这个启动器这主要提供了 `spring-boot`, `spring-context`, `spring-beans` 这三个 Spring 模块而已。

**4、spring-boot-cli**

这是 Spring Boot 的命令行工具，用于编译和运行 `Groovy` 源程序，可以十分简单的编写并运行一个应用程序。它也能监控你的文件，一旦有变动就会自动重新编译和重新启动应用程序。

**5、spring-boot-actuator**

这是 Spring Boot 提供的执行端点，你可以更好的监控及和你的应用程序交互。这个模块提供了像健康端点、环境端点、Spring Bean端点等。

**6、spring-boot-actuator-autoconfigure**

这个原理同上，为 Spring Boot 执行端点提供自动配置。


**7、spring-boot-test**

Spring Boot测试模块，为应用测试提供了许多非常有用的核心功能。


**8、spring-boot-test-autoconfigure**

这个原理同上，为 Spring Boot 测试模块提供自动配置。


**9、spring-boot-loader**

这个模块可以用来构建一个单独可执行的 jar 包，使用 `java -jar` 就能直接运行。一般不会直接使用这个来打包，使用 Spring Boot 提供的 Maven 或者 Gradle 插件就行了。

**10、spring-boot-devtools**

开发者工具模块，主要为 Spring Boot 开发阶段提供一些特性，如修改了代码自动重启应用等。这个模块的功能是可选的，只限于本地开发阶段，当打成整包运行时这些功能会被禁用。

大概的核心模块就是这些，里面更多的细节请阅读后续的更多文章。更多 Spring Boot 文章请在Java技术栈微信公众号后台回复关键字：boot。

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "boot" 可获取更多，转载请原样保留本信息。