
## Spring Boot日志框架

Spring Boot支持Java Util Logging,Log4j2,Lockback作为日志框架，如果你使用starters启动器，Spring Boot将使用Logback作为默认日志框架。无论使用哪种日志框架，Spring Boot都支持配置将日志输出到控制台或者文件中。

spring-boot-starter启动器包含spring-boot-starter-logging启动器并集成了slf4j日志抽象及Logback日志框架。

## 属性配置日志

Spring Boot支持属性配置日志参数，这个不是很灵活，不细讲。

参考配置：


```
# LOGGING
logging.config= # Location of the logging configuration file. For instance `classpath:logback.xml` for Logback
logging.exception-conversion-word=%wEx # Conversion word used when logging exceptions.
logging.file= # Log file name. For instance `myapp.log`
logging.level.*= # Log levels severity mapping. For instance `logging.level.org.springframework=DEBUG`
logging.path= # Location of the log file. For instance `/var/log`
logging.pattern.console= # Appender pattern for output to the console. Only supported with the default logback setup.
logging.pattern.file= # Appender pattern for output to the file. Only supported with the default logback setup.
logging.pattern.level= # Appender pattern for log level (default %5p). Only supported with the default logback setup.
logging.register-shutdown-hook=false # Register a shutdown hook for the logging system when it is initialized.
```

如：

```
logging.level.root=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate=ERROR
```

## 自定义日志文件

根据不同的日志框架，默认加载的日志配置文件的文件名，放在资源根目录下，其他的目录及文件名不能被加载。

Logging System | Customization
--- |---
Logback | logback-spring.xml, logback-spring.groovy, logback.xml or logback.groovy
Log4j2  | log4j2-spring.xml or log4j2.xml
JDK (Java Util Logging) | logging.properties

既然默认自带了Logback框架，Logback也是最优秀的日志框架，往资源目录下创建一个logback-spring.xml即可，下面是一个参考配置文件。

```
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="false">

	<springProperty scope="context" name="APP_NAME" source="spring.application.name"/>
	<springProperty scope="context" name="APP_PORT" source="server.port"/>
	<springProperty scope="context" name="DEFAULT_APP_PORT" source="spring.application.port"/>

	<property name="OS_NAME" value="${os.name}"/>
	<if condition='property("OS_NAME").contains("Windows")'>
		<then>
			<property name="LOG_PATH" value="${LOG_PATH:-E:/logs}" />
		</then>
		<else>
			<property name="LOG_PATH" value="${LOG_PATH:-/log}" />
		</else>
	</if>    	
    
	<property name="LOG_DIR" value="${APP_NAME:-system}" />
	<property name="APP_PORT" value="${APP_PORT:-${DEFAULT_APP_PORT:-0}}" />
	<if condition='!property("APP_PORT").equals("0")'>
		<then>
			<property name="LOG_DIR" value="${LOG_DIR}-${APP_PORT}" />
		</then>
	</if>

	
	<!-- 控制台输出 -->
	<appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
		<encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
			<!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度，%msg：日志消息，%n是换行符 -->
			<pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
		</encoder>
	</appender>
	
	<!-- 按照每天生成日志文件 -->
	<appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
		<filter class="ch.qos.logback.classic.filter.ThresholdFilter">
			<level>INFO</level>
		</filter>
		<rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
			<!--日志文件输出的文件名 -->
			<FileNamePattern>${LOG_PATH}/${LOG_DIR}/info.log.%d{yyyy-MM-dd}.log</FileNamePattern>
			
			<!--日志文件保留天数 -->
			<MaxHistory>30</MaxHistory>
		</rollingPolicy>
		
		<encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
			<!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符 -->
			<pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
		</encoder>
		
		<!--日志文件最大的大小 -->
		<triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
			<MaxFileSize>10MB</MaxFileSize>
		</triggeringPolicy>
		
	</appender>
	
	<!-- 按照每天生成日志文件 error级别 -->
	<appender name="FILE-ERROR" class="ch.qos.logback.core.rolling.RollingFileAppender">
		<filter class="ch.qos.logback.classic.filter.LevelFilter">
			<level>ERROR</level>
			<onMatch>ACCEPT</onMatch>
			<onMismatch>DENY</onMismatch>
		</filter>   
		<rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
			<!--日志文件输出的文件名 -->
			<FileNamePattern>${LOG_PATH}/${LOG_DIR}/error.log.%d{yyyy-MM-dd}.log</FileNamePattern>
			
			<!--日志文件保留天数 -->
			<MaxHistory>30</MaxHistory>
		</rollingPolicy>
		
		<encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
			<!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符 -->
			<pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
		</encoder>
		
		<!--日志文件最大的大小 -->
		<triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
			<MaxFileSize>10MB</MaxFileSize>
		</triggeringPolicy>
		
	</appender>

	<!--myibatis log configure -->
	<logger name="com.apache.ibatis" level="TRACE" />
	<logger name="java.sql.Connection" level="DEBUG" />
	<logger name="java.sql.Statement" level="DEBUG" />
	<logger name="java.sql.PreparedStatement" level="DEBUG" />

	<!-- 日志输出级别 -->
	<root level="INFO">
		<appender-ref ref="STDOUT" />
		<appender-ref ref="FILE" />
		<appender-ref ref="FILE-ERROR" />
	</root>
	
</configuration>
```

强烈推荐使用logback-spring.xml作为文件名，因为logback.xml加载太早。

日志初始化在ApplicationContext创建之前，所以@PropertySources加载的配置是读取不到的，系统环境变量、Spring Environment及application,bootstrap配置文件中的信息可以读取到。

读取系统环境属性：

```
<property name="LOG_PATH" value="${LOG_PATH:-E:/logs}" />
```

读取当前应用Environment中的属性：

```
<springProperty scope="context" name="fluentHost" source="myapp.fluentd.host"
        defaultValue="localhost"/>
```

Spring Boot也支持通过springProfile来加载不同profiles下的配置。

```
<springProfile name="staging">
    <!-- configuration to be enabled when the "staging" profile is active -->
</springProfile>

<springProfile name="dev, staging">
    <!-- configuration to be enabled when the "dev" or "staging" profiles are active -->
</springProfile>

<springProfile name="!production">
    <!-- configuration to be enabled when the "production" profile is not active -->
</springProfile>
```
