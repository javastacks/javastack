Spring Cloud可以增加HTTP Basic认证来增加服务连接的安全性。

## 1、加入security启动器

在maven配置文件中加入Spring Boot的security启动器。

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

这样，就开启对服务连接的安全保护，系统默认为生成一个用户名为"user"及一个随机密码，随机密码在服务启动的时候在日志中会打印出来。

## 2、自定义用户名密码

随机密码没什么实际意义，我们需要一个固定的连接用户名和密码。

在应用配置文件中加入以下配置即可。


```
security: 
  user: 
    name: admin
    password: admin123456
```

这样配置完后在连接这个服务的时候就会要求输入用户名和密码，如果认证失败会返回401错误。


```
{
    "timestamp": 1502689874556,
    "status": 401,
    "error": "Unauthorized",
    "message": "Bad credentials",
    "path": "/test/save"
}
```

## 3、安全连接

1、注册中心安全连接

username:password@ipaddress

2、Feign申明式服务安全连接


```
@FeignClient(name = "SERVICE", configuration = FeignAuthConfig.class)
public interface OrderService extends OrderAPI {

}
```

```
@Configuration
public class FeignAuthConfig {

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
    	return new BasicAuthRequestInterceptor("admin","admin123456");
    }
}
```
