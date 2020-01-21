## 参数配置容器

server.xx开头的是所有servlet容器通用的配置，server.tomcat.xx开头的是tomcat特有的参数，其它类似。

所有参数绑定配置类：org.springframework.boot.autoconfigure.web.ServerProperties


## 代码配置容器

除了利用上面的参数来自动配置servlet容器，还可以通过代码的方式。可以直接实现EmbeddedServletContainerCustomizer这个接口，ServerProperties也是实现这个接口的。

```
@ConfigurationProperties(prefix = "server", ignoreUnknownFields = true)
public class ServerProperties
		implements EmbeddedServletContainerCustomizer, EnvironmentAware, Ordered {
...
```


当然如果是Tomcat、Jetty、Undertow也可以使用下面对应的特定的容器工厂类。


```
// Jetty
org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory

// Tomcat
org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory

// Undertow
org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory
```


## 替换Tomcat

> spring-boot-starter-web brings Tomcat with spring-boot-starter-tomcat, but spring-boot-starter-jetty and spring-boot-starter-undertow can be used instead.

spring-boot-starter-web自动携带了tomcat依赖，但也可以替换成jetty和undertow，下面是一个替换jetty的示例。

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <!-- Exclude the Tomcat dependency -->
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!-- Use Jetty instead -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
```
