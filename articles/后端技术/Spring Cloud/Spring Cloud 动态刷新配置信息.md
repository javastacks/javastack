有时候在配置中心有些参数是需要修改的，这时候如何不重启而达到实时生效的效果呢？

## 添加依赖


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


`spring-boot-starter-actuator`：这个模块的/refresh(POST请求)端点可以刷新配置，更多的使用参考Spring Boot系列文章。

## 生效前提

在需要刷新的Bean上添加@RefreshScope注解。

```
@RefreshScope
@RestController
public class TestController {

	@Value("${username}")
	private String username;
	
...
```

当配置更改时，标有@RefreshScope的Bean将得到特殊处理来生效配置。

## 扩展问题

如果项目少配置少的情况可以通过/refresh来手动刷新配置，如果项目比较复杂的情况呢这种肯定是行不通的，Spring Cloud Bus消息总线可以解决配置修改的真正的动态刷新。
