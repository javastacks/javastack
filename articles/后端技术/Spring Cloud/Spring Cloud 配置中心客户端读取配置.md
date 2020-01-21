
微服务连接配置中心来实现外部配置的读取。

## 引入依赖


```
<dependencies>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-eureka</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-config</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-aop</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.retry</groupId>
		<artifactId>spring-retry</artifactId>
	</dependency>
</dependencies>
```


`spring-cloud-starter-config`：配置中心客户端的依赖。

`spring-boot-starter-aop`,`spring-retry`：这两个是连接配置中心快速失败和重试需要用到的依赖。

## 增加启动类


```
@EnableDiscoveryClient
@SpringBootApplication
public class ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

}
```

## 添加配置

在`bootstrap.yml`中添加如下配置，必须是bootstrap，application中不行。

```
spring: 
  application: 
    name: config-client
  cloud:
    config:
      #username: 
      #password: 
      name: ${git.application}
      profile: ${git.profile}
      label: ${git.label}
      fail-fast: true
      retry:
        initial-interval: 2000
        max-attempts: 5
      discovery: 
        enabled: true
        service-id: config-center 
      
eureka:
  client: 
    serviceUrl:
      defaultZone: ${register-center.urls}
```

可以看出配置比较简单，下面也不再详述。

`application.yml`配置文件参考如下：


```
spring: 
  profiles: 
    active: config-client1
    
eureka:
  instance:
    prefer-ip-address: true  
    instance-id: ${spring.cloud.client.ipAddress}:${server.port}
    lease-expiration-duration-in-seconds: ${lease-expiration-duration-in-seconds}
    lease-renewal-interval-in-seconds: ${lease-renewal-interval-in-seconds}

---
spring: 
  profiles: config-client1
      
server: 
  port: ${config-client1.server.port}
      
---
spring: 
  profiles: config-client2
  
server: 
  port: ${config-client2.server.port}
```

## Maven filter配置

```
... 

#git
git.application=application
git.profile=dev
git.label=master

...
```

## 读取配置


```
@RestController
public class TestController {

	@Value("${username}")
	private String username;
	
...
```

使用Value就能读取配置中心的配置，当然也可以通过其他方式获取SpringCloud中的配置，参考之前SpringBoot系列文章。

## 启动服务

通过指定Profile启动两台微服务，它们可以读取配置中心的内容。

```
spring-boot:run -Drun.profiles=config-client1 -P dev
spring-boot:run -Drun.profiles=config-client2 -P dev
```
