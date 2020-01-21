传统的 Java 应用程序都需要打一个 war 包，并到到 Tomcat webapps 目录下运行。

Java 支持打 Jar 包，但没有提供一个标准的方式在一个 Jar 包内再加载嵌入别的 Jar 包，一般需要扩展类加载器来实现，即使这样，也做不到打成一个包运行，因为没有 Servlet 容器。

有了 Spring Boot 框架之后，这一切都变得简单，我们可以很方便的将应用程序打成一个可执行 Jar 包。

## 怎么打成 Jar 包运行？

#### 1、添加插件

```
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <executions>
        <execution>
            <goals>
                <goal>repackage</goal>
            </goals>
            <configuration>
                <classifier>exec-${env}</classifier>
            </configuration>
        </execution>
    </executions>
</plugin>
```

这里是添加了一个 Maven 打包插件，通过配置可以定制打成的 Jar 包的格式，如：javastack-exec-dev.jar。

如果你是用的 spring-boot-starter-parent 方式来使用 Spring Boot，那就不用写 executions 选项，因为它包括了 executions repackage 构建配置。

这个插件的更多用法参考：

> https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/maven-plugin/usage.html

#### 2、打成 Jar 包

使用 mvn package 命令或者 IDE 中的 Maven 插件都可以打包。Maven 不熟悉的请关注Java技术栈微信公众号，在后台回复：maven，干货栈长我都整理好了。

打完包之后，在项目 target 目录就可以看到打成的 Jar 包。

这个 Jar 包除了包括编译完的 class 文件，还内置了 Servlet 容器，并集成了所有需要依赖的其他 Jar 包库，我们来看下这个 Jar 包的目录结构。

```
├─BOOT-INF
│  ├─classes
│  │  └─cn
│  │      └─javastack
│  │          └─demo
│  └─lib
├─META-INF
│  └─maven
│      └─cn.javastack
│          └─demo
└─org
    └─springframework
        └─boot
            └─loader
                ├─archive
                ├─data
                ├─jar
                └─util
```

在文件 \META-INF\MANIFEST.MF 中找到应用程序启动类：

```
Manifest-Version: 1.0
Archiver-Version: Plexus Archiver
Built-By: javastack
Start-Class: cn.javastack.demo.Application
Spring-Boot-Classes: BOOT-INF/classes/
Spring-Boot-Lib: BOOT-INF/lib/
Spring-Boot-Version: 2.1.3.RELEASE
Created-By: Apache Maven 3.5.0
Build-Jdk: 1.8.0_151
Main-Class: org.springframework.boot.loader.JarLauncher
```

#### 3、运行 Jar 包

运行命令格式：

> $ java -jar xxx.jar

这个命令的更多用法请看这篇文章：[Java Jar包的压缩、解压使用指南](https://mp.weixin.qq.com/s/7KkE5NRCqZti66-NjAiAEQ)，或者关注Java技术栈微信公众号，在后台回复：java。

运行命令演示：

> $ java -jar javastack-exec-dev.jar

```
.   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::  (v2.1.3.RELEASE)
....... . . .
....... . . . (log output here)
....... . . .
........ Started Example in 2.536 seconds (JVM running for 2.864)
```

好了，跑起来了。更多运行 Spring Boot 的方法看这篇文章：[运行 Spring Boot 应用的 3 种方式！](https://mp.weixin.qq.com/s/lZsQnvlR38TLuV--D3F7Ag)。

好了，今天的分享就到这里，更多 Spring Boot 文章正在撰写中，关注Java技术栈微信公众号获取第一时间推送。

在公众号后台回复：boot，还能获取栈长整理的往期 Spring Boot 教程，都是实战干货，以下仅为部分预览。

- Spring Boot 读取配置的几种方式
- Spring Boot 如何做参数校验？
- Spring Boot 最核心的 25 个注解！
- Spring Boot 2.x 启动全过程源码分析
- Spring Boot 2.x 新特性总结及迁移指南
- ……

获取所有 Spring Boot 示例代码，请关注微信公众号 "Java技术栈" 在后台回复关键字：bootcode。

未完，栈长将陆续分享 Spring Boot 最新技术教程，现在已经写了一堆存货了，关注微信公众号 "Java技术栈" ，公众号第一时间推送！

![](http://img.javastack.cn/wx_search_javastack.png)