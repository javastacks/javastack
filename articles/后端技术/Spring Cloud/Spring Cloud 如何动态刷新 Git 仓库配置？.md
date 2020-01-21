有时候在配置中心有些参数是需要修改的，这时候如何不重启而达到实时生效的效果呢？

**本文基于以下讲解：**

- Spring Cloud Greenwich.SR3
- Spring Boot 2.1.7.RELEASE
- 基于 Git 的配置中心仓库

#### 添加 actuator 依赖

在引用配置中心的项目中添加以下 `actuator` 依赖：

```
<dependencies>
    ...
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-actuator</artifactId>
	</dependency>	
	...
</dependencies>
```

`spring-boot-starter-actuator`：这个模块的 `/actuator/refresh` (POST请求)端点可以刷新配置，更多的使用参考 Spring Boot 系列文章。

#### 暴露 refresh 端点

actuator 默认是不暴露 refresh 端点的，需要我们主动暴露，在引用配置中心的项目中添加以下配置：

```
management:
  endpoints:
    web:
      exposure:
        include: refresh,info,health
```

#### 添加刷新范围

引用了配置中心的项目，在需要刷新的 `Bean` 上添加 `@RefreshScope` 注解。

**示例1：**

```
@RefreshScope
@RestController
public class TestController {

	@Value("${username}")
	private String username;
	
...

}
```

**示例2：**

```
@RefreshScope
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {

...

}
```

当配置更改时，标有 `@RefreshScope` 的 `Bean` 将得到特殊处理来生效配置，不然改了配置不会刷新的。`@RefreshScope` 的原理可以参考这篇文章：[Spring Cloud @RefreshScope 原理是什么？](https://mp.weixin.qq.com/s/DcnNLTkPA8j4zpk4Dkq4fQ)，很详细。

#### 手动刷新配置

修改配置后，我们可以通过 post 到 `/actuator/refresh` 即可手动刷新配置。

如下图所示：

![](http://img.javastack.cn/20191209171607.png)

如果参数有变更，刷新成功的话，会返回一个含有参数名的变更数组。

#### 自动刷新配置

如果你使用了 Gitlab 或者 Github 仓库，可以配置 `Webhooks` 来做到自动更新，当参数变更时，能做到自动通知。

Gitlab配置如下图所示：

![](http://img.javastack.cn/20191209165252.png)

Github也差不多的，可以配置一个 URL（用于变更通知）和一个 Secret Token（用于请求验证）。

但这种方式仅限于单台，如果有多台或者多个系统，该如何通知，恐怕需要加一个公共接口来绕一圈了。

如果需要请求头认证的，可以使用这种方式：

> https://user:password@ip:port/xxxx

#### 扩展问题

如果使用配置中心项目少的情况，我们是可以通过上面的方式进行配置动态刷新，如果项目比较复杂的情况呢？

上面的方式肯定都是行不通的，Spring Cloud Bus 消息总线可以解决配置修改的真正的动态刷新，请看下回分解。关注微信公众号：Java技术栈，第一时间推送。在公众号后台回复：cloud，还能获取栈长整理的 Spring Cloud 系列教程，都是实战干货。

![](http://img.javastack.cn/wx_search_javastack.png)


