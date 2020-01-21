
Spring Boot 返回 XML 数据，前提必须已经搭建了 Spring Boot 项目，所以这一块代码就不贴了，可以点击查看之前分享的 [Spring Boot 返回 JSON 数据，一分钟搞定！](https://mp.weixin.qq.com/s/cFztjzQttMwBQJqAowUZ2A)。

## 你所需具备的基础

- [什么是 Spring Boot?](https://mp.weixin.qq.com/s/jWLcPxTg9bH3D9_7qbYbfw)
- [Spring Boot 核心配置文件详解](https://mp.weixin.qq.com/s/BzXNfBzq-2TOCbiHG3xcsQ)
- [Spring Boot 开启的 2 种方式](https://mp.weixin.qq.com/s/PYM_iV-u3dPMpP3MNz7Hig)
- [Spring Boot 自动配置原理、实战](https://mp.weixin.qq.com/s/gs2zLSH6m9ijO0-pP2sr9Q)
- [Spring Boot 2.x 启动全过程源码分析](https://mp.weixin.qq.com/s/iMPXjuKRKT5lMZ4oVSp4Ww)

更多请在Java技术栈微信公众号后台回复关键字：boot。

## 如何返回 XML 数据？

那么如何返回 XML 格式数据呢？其实很简单！

### 加入 XML 工具依赖

```
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
</dependency>
```

不用带版本号，因为在 `spring-webmvc` 包中已经定义好了，只是没有把依赖传递过来而已，如下面源码所示。

```
<dependency>
  <groupId>com.fasterxml.jackson.dataformat</groupId>
  <artifactId>jackson-dataformat-xml</artifactId>
  <version>2.9.5</version>
  <scope>compile</scope>
  <exclusions>
    <exclusion>
      <artifactId>jcl-over-slf4j</artifactId>
      <groupId>org.slf4j</groupId>
    </exclusion>
  </exclusions>
  <optional>true</optional>
</dependency>
```

可以看到 `<optional>true</optional>`，所以，只要手动显示加下这个依赖就行了，关于这个标签可以点击[这里](https://mp.weixin.qq.com/s/6WIX8WzoJvcBPZDI9AxQPw)查看这篇文章。

### 返回 XML 数据格式定义

##### 1）定义返回方式

在 Controller 类上面用 `@RestController` 定义或者在方法上面用 `@ResponseBody` 定义，表明是在 Body 区域输出数据。

##### 2）定义返回类型

此时 `Content-Type` 默认为 `application/xhtml+xml;charset=UTF-8` 格式，可以手动改变下类型：`application/xml;charset=UTF-8`。

```
@RequestMapping(value = "/test" produces = MediaType.APPLICATION_XML_VALUE)
```

表明是用 `application/xml` 格式输出数据。

##### 3）定义输出格式

控制层方法直接返回对象，对象会自动转换为 XML 格式，不过是默认的标签，可以通过以下标签进行自定义 XML 格式。

```
@JacksonXmlRootElement(localName = "response")
public class UserXmlVO {

	@JacksonXmlProperty(localName = "user_name")
	private String name;

	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "order_info")
	private List<OrderInfoVO> orderList;
	
	// get set 略
	
}
```

上面演示了几个常用的注解。

**@JacksonXmlRootElement：** 用在类上，用来自定义根节点名称；

**@JacksonXmlProperty：** 用在属性上，用来自定义子节点名称；

**@JacksonXmlElementWrapper：** 用在属性上，可以用来嵌套包装一层父节点，或者禁用此属性参与 XML 转换。

更多注解可以查看这个包：

![](http://qianniu.javastack.cn/18-8-16/89032800.jpg)

### 如何手动完成对象XML 的互转？

`jackson-dataformat-xml` 这个包里面有一个 `com.fasterxml.jackson.dataformat.xml.XmlMapper` 类，该类继承父类 `com.fasterxml.jackson.databind.ObjectMapper`，可以利用它来完成互转操作。

下面是几个参考方法：

```
com.fasterxml.jackson.dataformat.xml.XmlMapper#readValue
com.fasterxml.jackson.dataformat.xml.XmlMapper#writeValue
com.fasterxml.jackson.databind.ObjectMapper#writeValueAsString
```

更多相关的使用及原理可以查看这个包。

好了，Spring Boot 返回 XML 格式数据就是这么简单，有什么不懂的可以点击阅读原文加入星球和大家一起学习讨论。

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "boot" 可获取更多，转载请原样保留本信息。
