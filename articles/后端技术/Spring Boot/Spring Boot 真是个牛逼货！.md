现在 Spring Boot 非常火，各种技术文章，各种付费教程，多如牛毛，可能还有些不知道 Spring Boot 的，那它到底是什么呢？有什么用？今天给大家详细介绍一下。

## Spring Boot 的背景

了解 Spring Boot 必须先说说 Spring 框架！

在 Java 后端框架繁荣的今天，Spring 框架无疑是最最火热，也是必不可少的开源框架，更是稳坐 Java 后端框架的龙头老大。

用过 Spring 框架的都知道 Spring 能流行是因为它的两把利器：IOC 和 AOP，IOC 可以帮助我们管理对象的依赖关系，极大减少对象的耦合性，而 AOP 的切面编程功能可以更方面的使用动态代理来实现各种动态方法功能（如事务、缓存、日志等）。

而要集成 Spring 框架，必须要用到 XML 配置文件，或者注解式的 Java 代码配置。无论是使用 XML 或者代码配置方式，都需要对相关组件的配置有足够的了解，然后再编写大量冗长的配置代码。

然后又有多少开发人员能精通这些配置呢？如果我们只提供一些配置参数让框架能自动配置这些组件，那是不是 so easy?

基于简化 Spring 快速上手为目的，Spring Boot 框架诞生了！

## 什么是 Spring Boot?

Spring Boot是 Spring 开源组织下的一个子项目，也是 Spring 组件一站式解决方案，主要是为了简化使用 Spring 框架的难度，简省繁重的配置。

Spring Boot提供了各种组件的启动器（starters），开发者只要能配置好对应组件参数，Spring Boot 就会自动配置，让开发者能快速搭建依赖于 Spring 组件的 Java 项目。

Spring Boot不但能创建传统的 war 包应用，还能创建独立的不依赖于任何外部容器（如：tomcat）的独立应用，使用 `java -jar` 命令就能启动。同时，Spring Boot也提供了一个命令行工具来执行 Spring 的脚本。

Spring Boot 的官方网站：

> https://projects.spring.io/spring-boot/

Spring Boot 的源码：

> https://github.com/spring-projects/spring-boot

## Spring Boot 的设计目标

如果你不清楚 Spring 是干嘛用的，那再来看看设计它的目标和初衷是什么！

- 为 Spring 应用开发提供一个更快、更容易上手的入门体验；
- 提供一系列在大型项目中经常用到的公共的非功能性特性，如：内嵌入服务器、安全、度量指标、健康检测、外部化配置；
- 零代码配置生成及零 XML 配置；

## Spring Boot 为什么能这么火？

Spring Boot 为什么能这么火？是因为它有以下几个特色。

**1、独立运行**

Spring Boot内嵌了各种 Servlet 容器，Tomcat、Jetty等，现在不再需要打成 war 包部署到容器中，Spring Boot 只要打成一个可执行的jar包就能独立运行，所有的依赖包都在一个 jar 包内。

**2、简化 Maven 配置**
 
![](http://img.javastack.cn/18-5-3/97561296.jpg)

如上图所示，现在只要依赖 `spring-boot-starter-web` 启动器包，它包含所有 web 开发所有的依赖，就能拥有 Spring Web 的能力，极大简少了 maven 对依赖的配置。

使用 Maven 命令：`mvn dependency:tree` 也可以看到完整的依赖树：

```
[INFO] +- org.springframework.boot:spring-boot-starter-web:jar:2.0.1.RELEASE:compile
[INFO] |  +- org.springframework.boot:spring-boot-starter:jar:2.0.1.RELEASE:compile
[INFO] |  |  +- org.springframework.boot:spring-boot:jar:2.0.1.RELEASE:compile
[INFO] |  |  +- org.springframework.boot:spring-boot-autoconfigure:jar:2.0.1.RELEASE:compile
[INFO] |  |  +- org.springframework.boot:spring-boot-starter-logging:jar:2.0.1.RELEASE:compile
[INFO] |  |  |  +- ch.qos.logback:logback-classic:jar:1.2.3:compile
[INFO] |  |  |  |  \- ch.qos.logback:logback-core:jar:1.2.3:compile
[INFO] |  |  |  +- org.apache.logging.log4j:log4j-to-slf4j:jar:2.10.0:compile
[INFO] |  |  |  |  \- org.apache.logging.log4j:log4j-api:jar:2.10.0:compile
[INFO] |  |  |  \- org.slf4j:jul-to-slf4j:jar:1.7.25:compile
[INFO] |  |  +- javax.annotation:javax.annotation-api:jar:1.3.2:compile
[INFO] |  |  \- org.yaml:snakeyaml:jar:1.19:runtime
[INFO] |  +- org.springframework.boot:spring-boot-starter-json:jar:2.0.1.RELEASE:compile
[INFO] |  |  +- com.fasterxml.jackson.core:jackson-databind:jar:2.9.5:compile
[INFO] |  |  |  +- com.fasterxml.jackson.core:jackson-annotations:jar:2.9.0:compile
[INFO] |  |  |  \- com.fasterxml.jackson.core:jackson-core:jar:2.9.5:compile
[INFO] |  |  +- com.fasterxml.jackson.datatype:jackson-datatype-jdk8:jar:2.9.5:compile
[INFO] |  |  +- com.fasterxml.jackson.datatype:jackson-datatype-jsr310:jar:2.9.5:compile
[INFO] |  |  \- com.fasterxml.jackson.module:jackson-module-parameter-names:jar:2.9.5:compile
[INFO] |  +- org.springframework.boot:spring-boot-starter-tomcat:jar:2.0.1.RELEASE:compile
[INFO] |  |  +- org.apache.tomcat.embed:tomcat-embed-core:jar:8.5.29:compile
[INFO] |  |  +- org.apache.tomcat.embed:tomcat-embed-el:jar:8.5.29:compile
[INFO] |  |  \- org.apache.tomcat.embed:tomcat-embed-websocket:jar:8.5.29:compile
[INFO] |  +- org.hibernate.validator:hibernate-validator:jar:6.0.9.Final:compile
[INFO] |  |  +- javax.validation:validation-api:jar:2.0.1.Final:compile
[INFO] |  |  +- org.jboss.logging:jboss-logging:jar:3.3.2.Final:compile
[INFO] |  |  \- com.fasterxml:classmate:jar:1.3.4:compile
[INFO] |  +- org.springframework:spring-web:jar:5.0.5.RELEASE:compile
[INFO] |  |  \- org.springframework:spring-beans:jar:5.0.5.RELEASE:compile
[INFO] |  \- org.springframework:spring-webmvc:jar:5.0.5.RELEASE:compile
[INFO] |     +- org.springframework:spring-aop:jar:5.0.5.RELEASE:compile
[INFO] |     +- org.springframework:spring-context:jar:5.0.5.RELEASE:compile
[INFO] |     \- org.springframework:spring-expression:jar:5.0.5.RELEASE:compile
```

**3、自动配置**

Spring Boot能根据当前类路径下的类或者 jar 包里面来的类来自动配置 Spring Bean，如添加一个 `spring-boot-starter-web` 启动器就能拥有 web 的功能，无需其他配置。也可以在配置文件中添加相关配置来自定义装配，这个后面的课程会讲到，请关注后续内容。
    
**4、无代码生成和XML配置**

Spring Boot配置过程中无代码生成，也无需XML配置文件就能完成所有配置工作，这一切都是借助于条件注解完成的，这也是 Spring 4+ 的核心功能之一。
    
**5、应用监控**

Spring Boot提供一系列端点可以监控服务及应用，能对Spring 应用做健康检测。
    
## Spring Boot 的缺点

说了这么多 Spring Boot 的优点和特色，虽然上手很容易，但也不是没有缺点。任何框架都有其优缺点，Spring Boot 也不例外，我大概可以总结有以下几个缺点。

1、需要非常了解 Spring Boot 的核心技术原理，不然一旦遇到问题就很棘手。因为什么东西都集成了，自动配置化。这样，需要对配置信息非常熟悉，要找起问题来不是很容易。

2、从原始 Spring 项目很难平滑迁移至 Spring Boot 框架上来，因为有些历史老旧的 XML 配置无法通过 Java 来配置，还需要额外的 XML 文件就不是很完美。再比如，之前是独立的 Tomcat，什么参数都在线上配置好了，你改为内置的 Tomcat 就会遇到很多问题。

相对于优点来说，这些缺点也都不算什么。总之，Spring Boot 是值得我们任何一个 Java 开发者尝试摸索的。

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "boot" 可获取更多，转载请原样保留本信息。

