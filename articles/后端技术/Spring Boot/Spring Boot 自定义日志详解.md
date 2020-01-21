
本节内容基于 Spring Boot 2.0.

## 你所需具备的基础

- [什么是 Spring Boot?](https://mp.weixin.qq.com/s/jWLcPxTg9bH3D9_7qbYbfw)
- [Spring Boot 核心配置文件详解](https://mp.weixin.qq.com/s/BzXNfBzq-2TOCbiHG3xcsQ)
- [Spring Boot 开启的 2 种方式](https://mp.weixin.qq.com/s/PYM_iV-u3dPMpP3MNz7Hig)
- [Spring Boot 自动配置原理、实战](https://mp.weixin.qq.com/s/gs2zLSH6m9ijO0-pP2sr9Q)
- [Spring Boot 2.x 启动全过程源码分析](https://mp.weixin.qq.com/s/iMPXjuKRKT5lMZ4oVSp4Ww)

更多请在Java技术栈微信公众号后台回复关键字：boot。

## Spring Boot 日志综合介绍

Spring Boot 内部代码使用的是 `commons-logging` 来记录日志的，但是底层日志实现框架是可以随意替换的。Spring Boot为 `Java Util Logging`, `Log4J2`, 和 `Logback` 日志框架提供了默认配置。

Spring Boot支持的日志框架默认配置如下。

```
# LOGGING
logging.config= # Location of the logging configuration file. For instance, `classpath:logback.xml` for Logback.
logging.exception-conversion-word=%wEx # Conversion word used when logging exceptions.
logging.file= # Log file name (for instance, `myapp.log`). Names can be an exact location or relative to the current directory.
logging.file.max-history=0 # Maximum of archive log files to keep. Only supported with the default logback setup.
logging.file.max-size=10MB # Maximum log file size. Only supported with the default logback setup.
logging.level.*= # Log levels severity mapping. For instance, `logging.level.org.springframework=DEBUG`.
logging.path= # Location of the log file. For instance, `/var/log`.
logging.pattern.console= # Appender pattern for output to the console. Supported only with the default Logback setup.
logging.pattern.dateformat=yyyy-MM-dd HH:mm:ss.SSS # Appender pattern for log date format. Supported only with the default Logback setup.
logging.pattern.file= # Appender pattern for output to a file. Supported only with the default Logback setup.
logging.pattern.level=%5p # Appender pattern for log level. Supported only with the default Logback setup.
logging.register-shutdown-hook=false # Register a shutdown hook for the logging system when it is initialized.
```

如果不配置以上任何参数，日志默认只会以 `INFO` 以上的级别打印在控制台，不会记录在日志文件中。

如果使用了任何 `Starters`，那 Spring Boot 默认会使用 `Logback` 日志框架记录日志，并为 `Logback` 提供了支持`Java Util Logging`, `Commons Logging`, `Log4J`, `SLF4J` 适合的桥接器以便能从这些日志门面中自由切换。即项目中不管使用哪个日志门面，Logback都能正常工作。

如下图，从 `spring-boot-starter-web` 依赖树中看出包含了默认日志框架 `Logback` 及其他的桥接器。

![](http://qianniu.javastack.cn/18-5-24/3396845.jpg)

## Spring Boot 日志实战

在配置文件 `application.properties` 添加以下配置。

```
# 日志级别
logging.level.root=DEBUG

# 输出到日志文件
logging.file=d:/logs/javastack.log

# 控制框架中的日志级别
logging.level.org.springframework=INFO
logging.level.sun=WARN
```

在 `Application` 启动类中添加以下测试代码。

```
private static final org.apache.commons.logging.Log logger1 = org.apache.commons.logging
			.LogFactory
			.getLog(SpringBootBestPracticeApplication.class);

private static final org.slf4j.Logger logger2 = org.slf4j.LoggerFactory
		.getLogger(SpringBootBestPracticeApplication.class);

private static final java.util.logging.Logger logger3 = java.util.logging.Logger
		.getLogger("SpringBootBestPracticeApplication");
		
@Bean
public CommandLineRunner loggerLineRunner() {
	return (args) -> {
		logger1.error("commons logging error...");

		logger1.info("commons logging info...");
		logger2.info("slf4j info...");
		logger2.info("java util logging info...");

		logger1.debug("commons logging debug...");
	};
}		
```


日志输出如下。


```
2018-05-24 17:16:21.645 ERROR 3132 --- [           main] c.j.s.SpringBootBestPracticeApplication  : commons logging error...
2018-05-24 17:16:21.645  INFO 3132 --- [           main] c.j.s.SpringBootBestPracticeApplication  : commons logging info...
2018-05-24 17:16:21.645  INFO 3132 --- [           main] c.j.s.SpringBootBestPracticeApplication  : slf4j info...
2018-05-24 17:16:21.645  INFO 3132 --- [           main] c.j.s.SpringBootBestPracticeApplication  : java util logging info...
2018-05-24 17:16:21.645 DEBUG 3132 --- [           main] c.j.s.SpringBootBestPracticeApplication  : commons logging debug...
```

程序中使用了三种不同的日志门面测试，和默认的 `Logback` 框架工作都十分正常，日志也正常输出到指定文件中了。

Spring Boot 默认提供配置的形式非常简单，只适合简单的日志应用，虽然说日志输出格式可以自定义，但日志文件如何按天滚动等其他更复杂的策略却不能配置，只能通过自定义引用日志文件的形式。

## Spring Boot 定制日志文件

简单的日志配置不能满足实际项目需求，那可以通过引用定制日志文件的形式达到目的。Spring Boot能根据类路径下的类库和配置文件自动配置对应的日志框架。


日志框架 | 配置文件
---|---
Logback | logback-spring.xml, logback-spring.groovy, logback.xml, or logback.groovy
Log4j2 | log4j2-spring.xml or log4j2.xml
JDK (Java Util Logging）| logging.properties

按对应类库在 classpath 下创建对应支持的日志配置文件就行，或者通过配置 `logging.config` 指定。

既然默认是支持 `Logback` 的，那现在只要在资源根目录下创建一个 `logback-spring.xml` 文件即可。`xx-spring` 这是 Spring Boot 推荐的命名方式，否则 Spring Boot 不能完全控制日志初始化，因为默认命名配置文件 `logback.xml` 加载较早不能获取到 `application.properties` 中的配置信息。

看到这里，相信你对 Spring Boot 的日志应该有了一个全面的了解。如何使用配置文件打印日志和传统项目一样，这里就不啰嗦了。

所有 Spring Boot 文章示例代码都在 Github 上面，大家可以 Star 关注一下。

> https://github.com/javastacks/spring-boot-best-practice

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "boot" 可获取更多，转载请原样保留本信息。