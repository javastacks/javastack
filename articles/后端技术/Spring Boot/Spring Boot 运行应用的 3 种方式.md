今天介绍 3 种运行 Spring Boot 应用的方式，看大家用过几种？

## 你所需具备的基础

- [什么是 Spring Boot?](https://mp.weixin.qq.com/s/jWLcPxTg9bH3D9_7qbYbfw)
- [Spring Boot 核心配置文件详解](https://mp.weixin.qq.com/s/BzXNfBzq-2TOCbiHG3xcsQ)
- [Spring Boot 开启的 2 种方式](https://mp.weixin.qq.com/s/PYM_iV-u3dPMpP3MNz7Hig)
- [Spring Boot 自动配置原理、实战](https://mp.weixin.qq.com/s/gs2zLSH6m9ijO0-pP2sr9Q)
- [Spring Boot 2.x 启动全过程源码分析](https://mp.weixin.qq.com/s/iMPXjuKRKT5lMZ4oVSp4Ww)

更多请在Java技术栈微信公众号后台回复关键字：boot。

## 运行 Spring Boot 应用的 3 种方式

#### 1、在 IDE 中运行

在 Eclipse、IDEA 中直接运行，又有以下两种方式。

- **jar 包方式**

Spring Boot 默认采用 jar 包内嵌 Tomcat、Jetty 等 Server 的方式，并需要提供一个含有 main 方法的主类。这个时候，直接在 IDE 中运行这个 main 方法就能启动 Spring Boot 应用了。

![](http://img.javastack.cn/18-8-24/89912145.jpg)

- **war 包方式**

如果你的应用改装成了 war 包方式部署，这个时候就需要在 IDE 中配置 Server，然后在 Server 中加入你的 Spring Boot 项目，最后运行这个 Server 即可。

如何改装成 war 包方式运行请看这篇文章《[Spring Boot 发布 jar 包转为 war 包秘籍](https://mp.weixin.qq.com/s/RQAPIwQJ2jMmlcM76LJhSQ)》

#### 2、打包运行

当你的 Spring Boot 准备提测或者上线，都需要打成 jar 包或者 war 包运行，war 包方式这里不说直接丢到 Server 里面运行即可，这里介绍直接运行 jar 包的方式。

> $ java -jar javastack-0.0.1-SNAPSHOT.jar

这种方式也支持开启远程调试，如：

> $ java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -jar javastack-0.0.1-SNAPSHOT.jar

#### 3、用插件运行

可以在 IDE 或者命令行中使用 Maven 和 Gradle 插件来运行 Spring Boot 应用。

1）Maven Plugin

> $ mvn spring-boot:run

更多详情请访问以下官方链接。

> https://docs.spring.io/spring-boot/docs/current/maven-plugin/

2）Gradle Plugin

> $ gradle bootRun

更多详情请访问以下官方链接。

> https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/

最后来一张 Maven 的截图。

![](http://img.javastack.cn/18-8-24/27826270.jpg)

#### 总结

在 IDE 中推荐使用插件的方式来运行，因为可以集成更多的插件化的功能，如：热部署、区别不同环境配置等。

在非 IDE 中请使用 `java -jar` 的方式，或者直接打包在 Server 中运行！

如果有收获，欢迎点赞转发！

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "boot" 可获取更多，转载请原样保留本信息。