## 什么是Thymeleaf

Thymeleaf是一款用于渲染XML、XHTML、HTML5内容的模板引擎。类似Velocity，FreeMaker模板引擎，它也可以轻易的与Spring MVC等Web框架进行集成作为Web应用的模板引擎。

Thymeleaf也是Spring Boot首要支持的模板引擎，并且在最新的Spring Boot版本中已经不再支持Velocity了。

> 官网：http://www.thymeleaf.org/

## 引入依赖

需要引入Spring Boot的Thymeleaf启动器依赖。

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

引入该依赖后会自动引入web依赖，不需要再单独引入web依赖。

## 自动配置说明

下面是Thymeleaf的自动配置相关类。

Thymeleaf的自动配置类：

> org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration

Thymeleaf的自动配置参数类：

> org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties

查看参数源码：

```
private static final Charset DEFAULT_ENCODING = Charset.forName("UTF-8");

private static final MimeType DEFAULT_CONTENT_TYPE = MimeType.valueOf("text/html");

public static final String DEFAULT_PREFIX = "classpath:/templates/";

public static final String DEFAULT_SUFFIX = ".html";
```

默认的编码是：UTF-8

默认的类型是：text/html

默认的模板文件目录是：classpath:/templates/

默认的模板文件后缀是：.html

这些参数都可以通过在application配置文件中指定`spring.thymeleaf.xx`进行更改，更多可参考该参数类。

## 实战

知道了自动配置的原理，所以我们可以知道怎么做了。

一、在resources目录下创建templates目录。

二、在templates目录下创建.html模板文件。

三、使用模板：

1、模板文件头部使用`<html xmlns:th="http://www.thymeleaf.org">`定义。

2、html标签上使用`th:`开头标识作为前缀。

3、通过`@{}`引入web静态文件。

```
<link rel="stylesheet" th:href="@{/css/jquery.min.css}"/>
```

4、访问数据

访问springmvc中的model数据：`${user.name}`，访问更多不同对象的数据请[点击](http://www.thymeleaf.org/doc/articles/springmvcaccessdata.html)参考官方定义。

