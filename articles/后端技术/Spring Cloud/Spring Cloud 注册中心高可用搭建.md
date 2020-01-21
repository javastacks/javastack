

Spring Cloud的注册中心可以由Eureka、Consul、Zookeeper、ETCD等来实现，这里推荐使用Spring Cloud Eureka来实现注册中心，它基于Netfilix的Eureka做了二次封装，完成分布式服务中服务治理的功能，微服务系统中的服务注册与发现都通过这个注册中心来进行管理。

## 引入Eureka Server依赖

之前的文章基础上加入Spring Cloud的依赖，现在再加入注册中心Eureka Server的依赖。


```
<dependencies>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-eureka-server</artifactId>
	</dependency>
</dependencies>
```

	
## 添加启动类，开启Eureka Server功能

在包根目录下加入启动类：

```
@EnableEurekaServer
@SpringBootApplication
public class RegisterApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(RegisterApplication.class).bannerMode(Banner.Mode.LOG)
				.run(args);
	}
}
```

`@EnableEurekaServer`注解即开启注册中心服务器的功能。

## 添加Eureka配置

在application.yml中加入如下配置：


```
spring: 
  application: 
    name: register-center
  profiles: 
    active: register-center1
    
eureka:
  instance: 
    prefer-ip-address: true
    instance-id: ${spring.cloud.client.ipAddress}:${server.port}
    lease-expiration-duration-in-seconds: ${lease-expiration-duration-in-seconds}
    lease-renewal-interval-in-seconds: ${lease-renewal-interval-in-seconds}
  server: 
    enable-self-preservation: ${enable-self-preservation}  
    eviction-interval-timer-in-ms: ${eviction-interval-timer-in-ms}
  client:
    register-with-eureka: true
    fetch-registry: true
    serviceUrl: 
      defaultZone: ${register-center.urls}
      
---  
spring: 
  profiles: register-center1
      
server: 
  port: ${register-center1.server.port}
      
---
spring: 
  profiles: register-center2
  
server: 
  port: ${register-center2.server.port}
```

这里做了两台注册中心的高可用配置register-center1,register-center2，也可以做多台，既然是高可用，每个注册中心都向别的注册中心注册自己。

## Maven filter配置

${}里面的配置由maven resource filter来打包进行控制，不同的环境使用不同的配置文件。

如filter-dev.properties的配置参考如下：

```
#url
register-center1.server.ip=192.168.1.22
register-center2.server.ip=192.168.1.23
register-center.urls=http://${register-center1.server.ip}:${register-center1.server.port}/eureka/,http://${register-center2.server.ip}:${register-center2.server.port}/eureka/

#port
register-center1.server.port=7001
register-center2.server.port=7002
      
#config
enable-self-preservation=false
eviction-interval-timer-in-ms=5000
lease-expiration-duration-in-seconds=20
lease-renewal-interval-in-seconds=6
```
    
## Spring Cloud配置详解

Spring Boot的配置参考Spring Boot系列文章，这里只对Spring Cloud用到的配置解释。

**`spring.application.name`**：配置应用名称，在注册中心中显示的服务注册名称。

**`spring.cloud.client.ipAddress`**：获取客户端的IP地址。

**`eureka.instance.prefer-ip-address`**：配置为true为喜欢IP，即连接注册中心使用IP地址形式，也可以使用HOSTNAME，但生产环境不推荐。


**`eureka.instance.instance-id`**：配置在注册中心注册的唯一实例ID。


**`eureka.instance.lease-expiration-duration-in-seconds`**：指示eureka服务器在接收到最后一个心跳之后等待的时间（秒），然后才能从此视图中删除此实例，并禁止此实例的流量。将此值设置得太长可能意味着流量可以路由到实例，即使实例不存在。设置此值太小可能意味着，由于临时网络故障，实例可能会被取消流量。此值将设置为至少高于lease-renewal-interval-in-seconds中指定的值。

**`eureka.instance.lease-renewal-interval-in-seconds`**：指示eureka客户端需要向eureka服务器发送心跳以指示它仍然存在的频率（以秒为单位）。如果在lease-expiration-duration-in-seconds中指定的时间段内未收到心跳线，则eureka服务器将从其视图中删除该实例，因此不允许此实例的流量。请注意，如果该实例实现HealthCheckCallback，然后决定使其本身不可用，则该实例仍然可能无法访问流量。

**`eureka.server.enable-self-preservation`**：配置注册中心是否开启服务的自我保护功能。

**`eureka.server.eviction-interval-timer-in-ms`**：配置注册中心清理无效节点的时间间隔，默认60000毫秒，即60秒。


**`eureka.client.register-with-eureka`**：配置为true指示此实例将其信息注册到eureka服务器以供其他人发现。在某些情况下，您不希望发现实例，而您只想发现其他实例配置为false。

**`eureka.client.fetch-registry`**：指示该客户端是否应从eureka服务器获取eureka注册表信息。。

**`eureka.client.serviceUrl.defaultZone`**：Eureka服务器地址。


## 启动注册中心

这样一个两个注册心的Eureka Server就搭好了，启动的时候使用不同的Profile来指定不同的端口。

```
spring-boot:run -Drun.profiles=register-center1  -P dev
spring-boot:run -Drun.profiles=register-center2  -P dev
```
