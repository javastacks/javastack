
本文通过config server连接git仓库来实现配置中心，除了git还可以使用svn或者系统本地目录都行。

## 引入依赖

```
<dependencies>
    <dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-config-server</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-eureka</artifactId>
	</dependency>
</dependencies>
```

spring-cloud-config-server这个就是配置中心server的依赖。

配置中心做到高可用本身也需要向注册中心注册自己的实例，所以需求引用spring-cloud-starter-eureka依赖。

## 添加启动类，开启Config Server功能


```
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class ConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigApplication.class, args);
	}

}
```

`@EnableConfigServer`：即开启配置服务器的功能。

`@EnableDiscoveryClient`：开启自动注册客户端，默认情况下，ServiceRegistry实现将自动注册正在运行的服务。如注册中心使用是Eureka，这里也可以使用的@EnableEurekaClient注解。

## 添加Config配置

```
spring: 
  application:
    name: config-center
  profiles:
    active: config-center1
  cloud: 
    config:
      server:
        git:
          uri: ${git.uri}
          searchPaths: ${git.searchPaths}
          username: ${git.username}
          password: ${git.password}
          basedir: ${git.basedir}
          clone-on-start: true
          force-pull: true
          
eureka:
  instance: 
    prefer-ip-address: true  
    instance-id: ${spring.cloud.client.ipAddress}:${server.port}
    lease-expiration-duration-in-seconds: ${lease-expiration-duration-in-seconds}
    lease-renewal-interval-in-seconds: ${lease-renewal-interval-in-seconds}
  client:
    serviceUrl:
      defaultZone: ${register-center.urls}
    
---
spring:
  profiles: config-center1
  
server: 
  port: ${config-center1.server.port}

---
spring: 
  profiles: config-center2
  
server: 
  port: ${config-center2.server.port}
```

这里配置了两台Config Server，都注册到了两台注册中心上。

## Maven filter配置

```
#git
git.uri=http://gitlab.example.com/test/config.git
git.username=root
git.password=root
git.searchPaths=config-center
git.basedir=f:/config/config-center/git
```


## Spring Cloud Git配置详解

**`spring.cloud.config.server.git.uri`**：git仓库地址。

**`spring.cloud.config.server.git.searchPaths`**：git仓库搜索目录。

**`spring.cloud.config.server.git.username`**：连接git的用户名。

**`spring.cloud.config.server.git.password`**：连接git的用户名密码。

**`spring.cloud.config.server.git.basedir`**：配置中心在本地缓存配置的目录。

**`spring.cloud.config.server.git.clone-on-start`**：配置为true表示启动时就克隆配置缓存到本地。

**`spring.cloud.config.server.git.force-pull`**：配置为true表示如果本地副本是脏的，将使Spring Cloud Config Server强制从远程存储库拉取配置。


## 启动配置中心

分别启动以下配置中心，使用不同的Profile指定端口。


```
spring-boot:run -Drun.profiles=config-center1 -P dev
spring-boot:run -Drun.profiles=config-center2 -P dev
```
