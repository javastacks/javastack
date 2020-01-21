
## 什么是Spring Boot?

Spring Boot是Spring开源组织下的子项目，是Spring组件一站式解决方案，主要是简化了使用Spring的难度，简省了繁重的配置，提供了各种启动器，开发者能快速上手。

官方网站：http://projects.spring.io/spring-boot/\
GitHub源码：https://github.com/spring-projects/spring-boot


## Spring Boot的优点

Features

> - Create stand-alone Spring applications
> - Embed Tomcat, Jetty or Undertow directly (no need to deploy WAR files)
> - Provide opinionated 'starter' POMs to simplify your Maven configuration
> - Automatically configure Spring whenever possible
> - Provide production-ready features such as metrics, health checks and externalized configuration
> - Absolutely no code generation and no requirement for XML configuration

- 独立运行

    Spring Boot而且内嵌了各种servlet容器，Tomcat、Jetty等，现在不再需要打成war包部署到容器中，Spring Boot只要打成一个可执行的jar包就能独立运行，所有的依赖包都在一个jar包内。
    
 - 简化配置

    spring-boot-starter-web启动器自动依赖其他组件，简少了maven的配置。
    

```
+- org.springframework.boot:spring-boot-starter-web:jar:1.5.6.RELEASE:compile
+- org.springframework.boot:spring-boot-starter-tomcat:jar:1.5.6.RELEASE:compile
|  +- org.apache.tomcat.embed:tomcat-embed-core:jar:8.5.16:compile
|  +- org.apache.tomcat.embed:tomcat-embed-el:jar:8.5.16:compile
|  \- org.apache.tomcat.embed:tomcat-embed-websocket:jar:8.5.16:compile
+- org.hibernate:hibernate-validator:jar:5.3.5.Final:compile
|  +- javax.validation:validation-api:jar:1.1.0.Final:compile
|  +- org.jboss.logging:jboss-logging:jar:3.3.1.Final:compile
|  \- com.fasterxml:classmate:jar:1.3.3:compile
\- org.springframework:spring-webmvc:jar:4.3.10.RELEASE:compile
```


- 自动配置

    Spring Boot能根据当前类路径下的类、jar包来自动配置bean，如添加一个spring-boot-starter-web启动器就能拥有web的功能，无需其他配置。
    
- 无代码生成和XML配置

    Spring Boot配置过程中无代码生成，也无需XML配置文件就能完成所有配置工作，这一切都是借助于条件注解完成的，这也是Spring4.x的核心功能之一。
    
- 应用监控

    Spring Boot提供一系列端点可以监控服务及应用，做健康检测。
    
## Spring Boot的缺点

Spring Boot虽然上手很容易，但如果你不了解其核心技术及流程，所以一旦遇到问题就很棘手，而且现在的解决方案也不是很多，需要一个完善的过程。