去年将 Spring Cloud 升级到了 Finchley 版本：
[Spring Cloud 升级最新 Finchley 版本，踩了所有的坑！](https://mp.weixin.qq.com/s/CvAmV4mjWHqNPkUoy0CwYw)

这个大版本栈长是踩了非常多的坑啊，帮助了不少小伙伴。

Spring Cloud Greenwich 在 01/23/2019 这天正式发布了：
[Spring Cloud Greenwich 正式发布，Hystrix 即将寿终正寝！](https://mp.weixin.qq.com/s/V6W634Rqjm9SoKb04bGygA)。

Greenwich发布也快 1 年了，栈长迟迟没有升级，距离上个大版本升级一年多了，所以栈长最近将 Spring Cloud 升级到了 Greenwich，几乎没踩多少坑，但也有坑，这里再记录分享一下，希望对你有用。

## 依赖升级

**升级前 => 升级后**

Spring Cloud Finchley.RELEASE => Spring Cloud Greenwich.SR3

Spring Boot 2.0.2.RELEASE => Spring Boot 2.1.7.RELEASE

**为什么不直接升级到 Spring Boot 2.2.x？**

Spring Cloud 和 Spring Boot 兼容性请见正文表格：

Spring Cloud Train | Spring Boot Version
---|---
Hoxton | 2.2.x
Greenwich | 2.1.x
Finchley | 2.0.x
Edgware | 1.5.x
Dalston | 1.5.x

如果用超出版本的，兼容性会有问题。

分不清这些版本的区别和意义？可以参考栈长之前写的关于版本的文章：
[Spring Cloud 多版本怎么选择？](https://mp.weixin.qq.com/s/IqlHFsIrFJ5vBG9-1gldJw)。

有兴趣的可以关注栈长的微信公众号：**Java技术栈**，Spring Boot、Spring Cloud 干货教程及时推送。

## Feign踩坑

升级后，应用启动正常，但调用 Feign 服务的时候报了个这个异常：

> The bean 'SERVICE-XXX.FeignClientSpecification', defined in null, could not be registered. A bean with that name has already been defined in null and overriding is disabled.

同时日志中也给出了解决文案：

> Action:
> 
> Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true

没错，就是在配置文件中配置上这个参数：

> spring.main.allow-bean-definition-overriding=true

原因就是使用 `@FeignClient` 的时候定义了多个相同 `name` 的接口。

```
@FeignClient(name = "xxx", configuration = XXXConfig.class, fallbackFactory =
        XXXServiceFallback.class)
public interface XXXService extends IXXXService {

}
```

**那为什么升级之后是好好的呢？**

那是因为在 Spring Boot 2.1.0 之后把默认值改成了false。

![](http://img.javastack.cn/20191107153501.png)

![](http://img.javastack.cn/20191107164525.png)

而在 Spring Boot 2.1.0 之前这个值都是 true，也没有这个参数可以修改这个配置。

![](http://img.javastack.cn/20191107152500.png)

**那这个参数到底有什么用？**

正常情况下，Spring容器里面只可能有一个唯一名字的 Bean 的，如果名字相同的情况下，就要看这个参数决定了，即是否允许 Bean 覆盖，不允许情况下会抛出异常，如果允许，则谁覆盖谁要看 Spring 容器 Bean 的初始化的顺序了。

所以，配置这个参数为 true 后，多个 `@FeignClient` 注解相同名字的 Bean 的 `configuration` 参数就会被覆盖了。

如果项目中有多个 `configuration`，那会受影响，我们没有多个这样的配置，所以暂且先配置这个参数解决问题。解决之后，就能正常使用 Feign 了，正常访问微服务。

这难道是 Feign 的坑吗？不可能把所有东西都写在一个接口服务里面啊！暂时也没找到好的办法，后续再研究下吧。

未完，栈长将陆续分享 Spring Cloud 最新技术教程，现在已经写了一堆存货了，关注微信公众号 "Java技术栈" ，公众号第一时间推送！

@ All 码农们：你们升级了吗？有遇到什么样的坑？欢迎留言！

![](http://img.javastack.cn/wx_search_javastack.png)