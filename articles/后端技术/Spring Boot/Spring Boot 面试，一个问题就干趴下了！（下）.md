前些天栈长在**Java技术栈**微信公众号分享一篇文章：[Spring Boot 面试，一个问题就干趴下了！](https://mp.weixin.qq.com/s/Yd9yuAJLS2yWtSF09Xk1Gw)，看到大家的留言很精彩，特别是说"约定大于配置"的这两个玩家。

![](http://img.javastack.cn/20190402135751.png)

哈哈，上墙的朋友开不开森？

不错，约定优（大）于配置确实是 Spring Boot 整个框架的核心思想。

**那么怎么理解约定优于配置呢？**

百度百科定义：

> 约定优于配置（convention over configuration），也称作按约定编程，是一种软件设计范式，旨在减少软件开发人员需做决定的数量，获得简单的好处，而又不失灵活性。

总结就是两点：

1、约定一些推荐的默认配置；

2、开发人员只需要规定不符约定的部分；

这样做的好处就是，如果约定的默认配置符合我们的要求，省略即可，反之，再进行额外配置。

从 Spring Boot 中提供的默认的配置文件（application.properties/yml），再到默认值自动配置，都可以看出约定带来的便利，以及节省大量的配置。

来看下 Spring Boot 中一个自动配置的源码实例吧：

```
@Configuration
@ConditionalOnClass({ Servlet.class, StandardServletMultipartResolver.class,
		MultipartConfigElement.class })
@ConditionalOnProperty(prefix = "spring.servlet.multipart", name = "enabled", matchIfMissing = true)
@ConditionalOnWebApplication(type = Type.SERVLET)
@EnableConfigurationProperties(MultipartProperties.class)
public class MultipartAutoConfiguration {

	private final MultipartProperties multipartProperties;

	public MultipartAutoConfiguration(MultipartProperties multipartProperties) {
		this.multipartProperties = multipartProperties;
	}

	@Bean
	@ConditionalOnMissingBean
	public MultipartConfigElement multipartConfigElement() {
		return this.multipartProperties.createMultipartConfig();
	}

	@Bean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME)
	@ConditionalOnMissingBean(MultipartResolver.class)
	public StandardServletMultipartResolver multipartResolver() {
		StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
		multipartResolver.setResolveLazily(this.multipartProperties.isResolveLazily());
		return multipartResolver;
	}

}

@ConfigurationProperties(prefix = "spring.servlet.multipart", ignoreUnknownFields = false)
public class MultipartProperties {

	/**
	 * Whether to enable support of multipart uploads.
	 */
	private boolean enabled = true;

	/**
	 * Intermediate location of uploaded files.
	 */
	private String location;

	/**
	 * Max file size. Values can use the suffixes "MB" or "KB" to indicate megabytes or
	 * kilobytes, respectively.
	 */
	private String maxFileSize = "1MB";

	/**
	 * Max request size. Values can use the suffixes "MB" or "KB" to indicate megabytes or
	 * kilobytes, respectively.
	 */
	private String maxRequestSize = "10MB";

	/**
	 * Threshold after which files are written to disk. Values can use the suffixes "MB"
	 * or "KB" to indicate megabytes or kilobytes, respectively.
	 */
	private String fileSizeThreshold = "0";

	/**
	 * Whether to resolve the multipart request lazily at the time of file or parameter
	 * access.
	 */
	private boolean resolveLazily = false;

	// get/set/etc..

}
```

这是一个文件上传的自动配置类，约定了：

1、约定了配置参数以 `spring.servlet.multipart` 前缀开始；

2、约定了很多默认配置，如：默认上传文件大小为 1M；

3、约定了所有的参数配置类名都是 *Properties；

4、约定了所有的自动配置类名都是 *AutoConfiguration；

5、约定了所有自动配置类配置在：/META-INF/spring.factories；

等等……

这样我们做一个文件上传操作几乎不用写任何配置了，除非满足不了需求，如：现在文件上传 1M 太小了，再加一行自定义配置即可，我们也可以按约定编写其他自动配置。

如果还不能理解，再来看 Maven 怎么做的，Maven 简直把约定大于配置的思想体现淋漓尽致。

![](http://img.javastack.cn/20190402143208.png)

![](http://img.javastack.cn/20190402143236.png)

Maven规定了哪个目录放什么文件，哪个文件做什么用，Maven会自动去处理，不需要我们再额外配置，其实我们也没有额外配置的需要，至少栈长我现在还没有遇到过。如果这些目录都让你来通过配置文件来配置，而每个项目配置的又不一样，你会不会想要崩溃？

其实这也不是新技术，只是一种设计思想，早在 JDK 1.5 中添加的《[Java注解](https://mp.weixin.qq.com/s/FSrtDEwILSM-Q2ocnZdNbA)》就是很好的体现。

关于 “约定优于配置” 的思想，你还有什么好的想法，欢迎留言分享~

好了，今天的分享就到这里，关注Java技术栈微信公众号，在后台回复：boot，获取栈长整理的更多的 Spring Boot 教程，都是实战干货，以下仅为部分预览。

- Spring Boot 读取配置的几种方式
- Spring Boot 如何做参数校验？
- Spring Boot 最核心的 25 个注解！
- Spring Boot 2.x 启动全过程源码分析
- Spring Boot 2.x 新特性总结及迁移指南
- ……

本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "boot" 可获取更多 Spring Boot 教程，转载请原样保留本信息。

