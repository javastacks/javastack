
在Spring Boot实现代码热部署是一件很简单的事情，代码的修改可以自动部署并重新热启动项目。


## 引用devtools依赖

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

这样，当修改一个java类时就会热更新。

## 自定义配置热部署

以下配置用于自定义配置热部署，可以不设置。

```
# 热部署开关，false即不启用热部署
spring.devtools.restart.enabled: true

# 指定热部署的目录
#spring.devtools.restart.additional-paths: src/main/java

# 指定目录不更新
spring.devtools.restart.exclude: test/**
```

## Intellij Idea修改

如果是idea，需要改以下两个地方：

1、勾上自动编译或者手动重新编译

> File > Settings > Compiler-Build Project automatically

2、注册

> ctrl + shift + alt + / > Registry > 勾选Compiler autoMake allow when app running

## 注意事项

1、生产环境devtools将被禁用，如java -jar方式或者自定义的类加载器等都会识别为生产环境。

2、打包应用默认不会包含devtools，除非你禁用SpringBoot Maven插件的`excludeDevtools`属性。

3、Thymeleaf无需配置`spring.thymeleaf.cache: false`，devtools默认会自动设置，[点击](https://github.com/spring-projects/spring-boot/blob/v1.5.7.RELEASE/spring-boot-devtools/src/main/java/org/springframework/boot/devtools/env/DevToolsPropertyDefaultsPostProcessor.java)参考完整属性。

下面是devtools自动配置的部分源码：

```
@Order(Ordered.LOWEST_PRECEDENCE)
public class DevToolsPropertyDefaultsPostProcessor implements EnvironmentPostProcessor {

	private static final Map<String, Object> PROPERTIES;

	static {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("spring.thymeleaf.cache", "false");
		properties.put("spring.freemarker.cache", "false");
		properties.put("spring.groovy.template.cache", "false");
		properties.put("spring.mustache.cache", "false");
		properties.put("server.session.persistent", "true");
		properties.put("spring.h2.console.enabled", "true");
		properties.put("spring.resources.cache-period", "0");
		properties.put("spring.resources.chain.cache", "false");
		properties.put("spring.template.provider.cache", "false");
		properties.put("spring.mvc.log-resolved-exception", "true");
		properties.put("server.jsp-servlet.init-parameters.development", "true");
		PROPERTIES = Collections.unmodifiableMap(properties);
	}
```

4、devtools会在windows资源管理器占用java进程，在开发工具里面杀不掉，只能手动kill掉，不然重启会选成端口重复绑定报错。

关于boot-devtools更多详细用法，[点击](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-devtools.html)参考官方文档。