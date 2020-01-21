前些天栈长在微信公众号Java技术栈分享了 Spring Cloud Eureka 最新版 实现注册中心的实战教程：[Spring Cloud Eureka 注册中心集群搭建，Greenwich 最新版！](https://mp.weixin.qq.com/s/uyoN8iB1rLOS9mLuvVebbg)，成功进入 Eureka 控制台页面。

但控制台首页默认是没有登录认证保护的，打开就能访问，而且你的微服务也能随意注册进去，这样是不安全的，本章栈长将加入登录认证功能，把你的 Eureka 注册中心保护起来。

本文基于最新的 [Spring Cloud Greenwich.SR1](https://mp.weixin.qq.com/s/V6W634Rqjm9SoKb04bGygA) 以及 Spring Boot 2.1.3 版本进行分享。

**1、加入 Spring Security 依赖**

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

**2、添加安全配置**

在 application.yml 配置文件中添加以下配置：

```
spring: 
  security:
    user:
      name: javastack
      password: javastack
```

配置用来登录认证的用户名和密码。

**3、修改defaultZone**

需要在 defaultZone 中添加用户名密码认证。

格式如下：

```
defaultZone: http://username:password@eureka:8761/eureka/
```

参考配置如下：

```
defaultZone: http://javastack:javastack@eureka1:8761/eureka/, http://javastack:javastack@eureka2:8762/eureka/
```

**4、禁用CSRF**

![](http://img.javastack.cn/20190328114313.png)

如上图所示，注册实例出现在 unavailable-replicas 里面。

这是因为加入了安全认证模块后，默认会开启 CSRF 跨站脚本攻击，需要禁用它，添加以下配置即可。

> 参考：https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-eureka-server.html#_securing_the_eureka_server

```
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/eureka/**");
        super.configure(http);
    }

}
```

这样配置后，打开 Eureka 控制台页面会先要跳到登录页面做登录认证才能访问，如下图所示。

![](http://img.javastack.cn/20190328145822.png)

好了，今天的分享就到这里了，后续会分享更多 Eureka 高级玩法，栈长正在拼命撰写中……关注Java技术栈微信公众号可获取及时推送。在公众号后台回复：cloud，获取栈长整理的更多的 Spring Cloud 教程，都是实战干货，以下仅为部分预览。

- Spring Cloud 最新 Finchley 版本踩坑
- Spring Cloud 多版本如何选择
- Spring Cloud 是什么，和 Dubbo 对比
- Spring Cloud 注册中心高可用搭建
- Spring Cloud Eureka 自我保护机制
- ……

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "cloud" 可获取更多 Spring Cloud 教程，转载请原样保留本信息。
