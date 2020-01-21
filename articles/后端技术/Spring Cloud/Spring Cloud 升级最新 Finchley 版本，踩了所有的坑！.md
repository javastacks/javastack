
Spring Boot 2.x 已经发布了很久，现在 Spring Cloud 也发布了 基于 Spring Boot 2.x 的 Finchley 版本，现在一起为项目做一次整体框架升级。

**升级前 => 升级后**

Spring Boot 1.5.x => Spring Boot 2.0.2

Spring Cloud Edgware SR4 => Spring Cloud Finchley.RELEASE

### Eureka Server

**Eureka Server 依赖更新**

升级前：

```
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-eureka-server</artifactId>
</dependency>
```

升级后：

```
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

### Eureka Client

因为配置中心需要作为服务注册到注册中心，所以需要升级 Eureka Client，其他依赖没有变动。

**Eureka Client 依赖更新**

升级前：

```
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-eureka</artifactId>
</dependency>
```

升级后：
		
```
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

### Spring Cloud

**注册中心里面的客户端实例IP显示不正确**

因为 Spring Cloud 获取服务客户端 IP 地址配置变更了。

升级前：

```
${spring.cloud.client.ipAddress}
```

升级后：

```
${spring.cloud.client.ip-address}
```

### Spring Security

一般注册中心、配置中心都会使用安全加密，就会依赖 `spring-boot-starter-security` 组件，升级后有几下两个问题。

**1、用户名和密码无法登录**

因为 Spring Security 的参数进行了变更。

升级前：

```
security:
  user:
    name:
    password:
```

升级后：


```
spring:
  security:
     user:
       name: 
       password:
```

**2、注册中心没有注册实例**

如图所示，没有注册实例，两个注册中心无法互相注册。

![](http://qianniu.javastack.cn/18-8-1/42415130.jpg)
		
因为 Spring Security 默认开启了所有 CSRF 攻击防御，需要禁用 /eureka 的防御。

在 Application 入口类增加忽略配置：

```
@EnableWebSecurity
static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().ignoringAntMatchers("/eureka/**");
		super.configure(http);
	}
}
```

**3、配置中心无法加解密**

升级后发现访问配置中心无法读取到配置，也无法加解密配置信息，访问配置中心链接直接跳转到了登录页面。

![](http://qianniu.javastack.cn/18-8-3/35230831.jpg)

现在想变回之前的 basic auth 认证方式，找源码发现是自动配置跳到了登录页面，现在重写一下。

> 自动配置源码：
> org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)

```
protected void configure(HttpSecurity http) throws Exception {
	logger.debug("Using default configure(HttpSecurity). If subclassed this will potentially override subclass configure(HttpSecurity).");

	http
		.authorizeRequests()
			.anyRequest().authenticated()
			.and()
		.formLogin().and()
		.httpBasic();
}
```

重写之后：


```
@EnableWebSecurity
static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().ignoringAntMatchers("/**").and().authorizeRequests().anyRequest()
				.authenticated().and().httpBasic();
	}

}
```

其实就是把 `formLogin()` 干掉了，又回到之前的 basic auth 认证方式，如下图所示。

![](http://qianniu.javastack.cn/18-8-3/81574886.jpg)

现在我们又可以使用以下命令加解密了。

> 如解密：
> curl http://xx.xx.xx.xx:7100/decrypt -d secret -u user:password

恢复 basic auth 之后，之前的服务需要加密连接配置中心的又正常运行了。

### Maven

升级到 Spring Boot 2.x 之后发现 Spring Boot 的 Maven 启动插件不好用了，主要是 Profile 不能自由切换。

升级前：

```
spring-boot:run -Drun.profiles=profile1
```

升级后：

```
spring-boot:run -Dspring-boot.run.profiles=profile1
```

> 具体的请参考：
> https://docs.spring.io/spring-boot/docs/current/maven-plugin/run-mojo.html

### 总结

以上都是踩完所有的坑总结出来的解决方案，实际解决问题的过程远要复杂的多。版本变化有点大，本次已成功升级了 Spring Cloud 基础依赖，及注册中心（Eureka Server）、配置中心（Config Server）。

其他像 Gateway 代替了 Zuul, 及其他组件再慢慢升级，Spring Cloud 的快速发展令升级变得非常蛋疼，本文记录了升级过程中踩过的所有的坑。。。

坑死了，已经保证编译、运行正常，其他还有什么坑不知道，刚升级完 Finchley 这个正式版本，Spring Cloud 刚刚又发布了 Finchley.SR1，感觉 Spring Cloud 变成了学不动系列了。。。

**@ All 码农们：你们升级了吗？有遇到什么样的坑？欢迎留言！**

