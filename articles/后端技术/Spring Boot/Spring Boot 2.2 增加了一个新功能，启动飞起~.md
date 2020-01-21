前几天栈长分享了一个好玩的框架：[一个比Spring Boot快44倍的Java框架！](https://mp.weixin.qq.com/s/bQsfgYQ01CEk3PQlvc356A)，是不是感觉 Spring Boot 略慢？今天讲一下 Spring Boot 添加的这个新特性，可以大大提升 Spring Boot 的启动速度。

最近，Spring团队宣布在 Spring Boot 2.2+ 中添加了一个重要功能：延迟加载，目前这个版本暂时还是快照版，不过我们可以先了解下怎么使用这个延迟加载功能。

#### 延迟加载是什么意思？

有点经验的程序员应该都知道，在 Spring 框架中早已经支持延迟加载功能的，简单来说就是一个类的实例化，不需要 Spring 容器启动的时候就开始实例化，而是在第一次需要它的时候再实例化，这样大大提升了程序启动速度，也在一定程序上节省了系统资源。

#### 怎么开启延迟加载？

在传统 Spring 项目中我们是这么做的：

```
<bean id="testBean" calss="cn.javastack.TestBean" lazy-init="true" />
```

以上 bean 配置是不是很熟悉？

没错，加了 `lazy-init="true"` 表示延迟加载，默认不加为false，表示容器启动时立即加载。

在 Spring 3.0+ 之后也可以这么做：

```
@Lazy
public TestBean testBean() {
	return new TestBean();
}
```

@Lazy：默认值为true，表示延迟加载；

#### Spring Boot如何开启？

由上面的例子我们可以知道，在任何 Spring Boot 版本中其实是支持 Bean 的延迟加载的，但这样是需要我们手工去配置的，这样会比较麻烦。

在 Spring Boot 2.2+ 中，延期加载将变得更加简单，有几下几种配置方式：

- 参数：spring.main.lazy-initialization
- 类：SpringApplication
- 类：SpringApplicationBuilder

通过以上几种方式设置成：true，容器中的 Bean 就将配置成延迟加载。

Spring Boot 项目在 IDE 中再配合 DevTools 工具，可以使本发开发环境启动变得更快，400ms就可以启动起来了，大大提高了开发效率。

#### 延迟加载有没有缺点？

延迟加载确实可以大大减少应用程序的启动时间，还能节省系统资源，那么问题来了，你可能会问，为什么不默认开启它呢？为什么还要额外提供一个配置？

听栈长道来，延迟加载确实有很多好处，但也会造成一些在启动的时候就能发现而要等到延迟加载才发现的问题，如：内存不足啊、类找不到啊、又或者是配置错误引发的系列问题。

还有一个问题就是，因为第一次请求的时候才去实例化，可能造成第一个请求变慢，响应延迟，体验不是很好。这样一来，对负载均衡和自动伸缩方面也会有不利影响。

#### 结束语

正如我们在上面所分析到的，延迟加载确实可以显着改善启动时间，但也有一些明显的缺点，所以我们一定小心谨慎的启用它。或者我们可以对项目进行评估下，延迟加载真的对我们的项目有这么重要或者急迫么？

等正式版 Spring Boot 2.2 发布，栈长给再出一个实战文章，欢迎关注栈长的微信公众号：Java技术栈，不要走开。

好了，今天的分享就到这里，关注Java技术栈微信公众号，在后台回复：boot，获取栈长整理的更多的 Spring Boot 教程，都是实战干货，以下仅为部分预览。

- Spring Boot 读取配置的几种方式
- Spring Boot 如何做参数校验？
- Spring Boot 最核心的 25 个注解！
- Spring Boot 2.x 启动全过程源码分析
- Spring Boot 2.x 新特性总结及迁移指南
- ……

最后，你们是怎么应用延迟加载功能的，欢迎留言分享~

本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "boot" 可获取更多 Spring Boot 教程，转载请原样保留本信息。

![](http://img.javastack.cn/wx_search_javastack.png)