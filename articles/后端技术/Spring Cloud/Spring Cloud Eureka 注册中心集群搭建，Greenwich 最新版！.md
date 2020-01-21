Spring Cloud 的注册中心可以由 Eureka、Consul、Zookeeper、ETCD 等来实现，这里推荐使用 Spring Cloud Eureka 来实现注册中心，它基于 Netflix 的 Eureka 做了二次封装，完成分布式服务中服务治理的功能，微服务系统中的服务注册与发现都通过这个注册中心来进行管理。

今天栈长就来分享一个 Eureka 注册中心玩法，从 0 到分布式集群一步到位，单机版的咱就不玩了，没意义。

本文基于最新的 [Spring Cloud Greenwich.SR1](https://mp.weixin.qq.com/s/V6W634Rqjm9SoKb04bGygA) 以及 Spring Boot 2.1.3 版本进行分享。

## 快速构建一个 Eureka Server 项目

打开 Spring 的快速构建网址，如下图所示，选择对应的参数，最后选择 Eureka Server 依赖，生成项目示例代码即可。

> https://start.spring.io/

![](http://img.javastack.cn/微信截图_20190327180739.png)

![](http://img.javastack.cn/微信截图_20190327180726.png)

栈长这里是生成了一个 Maven 示例项目。

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.3.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>cn.javastack</groupId>
	<artifactId>spring-cloud--eureka-server</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>spring-cloud--eureka-server</name>
	<description>Demo project for Spring Cloud Eureka Server</description>

	<properties>
		<java.version>1.8</java.version>
		<spring-cloud.version>Greenwich.SR1</spring-cloud.version>
	</properties>

	<dependencies>
	
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

	</dependencies>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

主要是加入了 Eureka Server 和 Spring Test 依赖包，还有 Spring Boot 和 Spring Cloud 的基础依赖。

Maven就不多介绍了，不熟悉的，请关注Java技术栈微信公众号，在后台回复：Maven，即可获取栈长整理的一系列 Maven 系列教程文章。

## 开启 Eureka Server 功能

```
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

}
```

在启动类上加入 @EnableEurekaServer 注解，`@EnableEurekaServer`注解即开启注册中心服务器的功能。

Spring Boot就不多介绍了，不熟悉的，请关注Java技术栈微信公众号，在后台回复：Boot，即可获取栈长整理的一系列 Spring Boot 系列教程文章。

## 添加 Eureka Server 配置

在 application.yml 中加入如下配置：

```
spring:
  application:
    name: register-center

eureka:
  instance:
    prefer-ip-address: false
    instance-id: ${spring.cloud.client.ip-address}:${server.port}
    lease-expiration-duration-in-seconds: 30
    lease-renewal-interval-in-seconds: 5
  server:
    enable-self-preservation: true
    eviction-interval-timer-in-ms: 5000
  client:
    register-with-eureka: true
    fetch-registry: true
    serviceUrl:
      defaultZone: http://eureka1:8761/eureka/, http://eureka2:8762/eureka/


logging.level.com.netflix:
  eureka: OFF
  discovery: OFF

---
spring:
  profiles: rc1

server:
  port: 8761

eureka.instance.hostname: eureka1

---
spring:
  profiles: rc2

server:
  port: 8762

eureka.instance.hostname: eureka2
```

配置这里不细讲，下篇文章栈长单独分享这些参数的含义，关注微信公众号：Java技术栈，获取第一时间推送。

这里做了两台注册中心的高可用配置rc1,rc2，也可以做多台，既然是高可用，每个注册中心都向别的注册中心注册自己。

**注意不要用Localhost**

![](http://img.javastack.cn/20190328114313.png)

如上图所示，如果大家在实战中遇到集群不可用，出现在 unavailable-replicas 里面时，说明是你配置的问题。

如果 defaultZone 用了 localhost，prefer-ip-address 设置的是 false，则集群不行，不能用 localhost，要配置 hosts，并代替 localhost。

```
127.0.0.1 localhost eureka1 eureka2
```

## 启动 Eureka 注册中心

这样两个注册心的 Eureka Server 就搭好了，启动的时候使用不同的 Profile 来指定不同的端口。

```
spring-boot:run -Dspring-boot.run.profiles=rc1
spring-boot:run -Dspring-boot.run.profiles=rc2
```

按上方面命令启动两个 Eureka Server，然后再来验证一下注册情况，分别打开两个 Eureka Server 控制台页面。

> http://localhost:8761/
http://localhost:8762/

![](http://img.javastack.cn/20190328103743.png)

我们可以看到两个注册的注册中心实例了。

好了，今天的分享就到这里了，近期会分享更多 Eureka 高级玩法，栈长正在拼命撰写中……关注Java技术栈微信公众号可获取及时推送。在公众号后台回复：cloud，获取栈长整理的更多的 Spring Cloud 教程，都是实战干货，以下仅为部分预览。

- Spring Cloud 最新 Finchley 版本踩坑
- Spring Cloud 多版本如何选择
- Spring Cloud 是什么，和 Dubbo 对比
- Spring Cloud 配置中心高可用搭建
- Spring Cloud Eureka 自我保护机制
- ……

大家有什么问题，也可以点击[这个链接](https://mp.weixin.qq.com/s/iqCLAduVzDqt19L6D4FCUQ)加入Java技术栈知识星球，和大家共同讨论，也可以向栈长提问，快 2000 人已加入。

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "cloud" 可获取更多 Spring Cloud 教程，转载请原样保留本信息。
