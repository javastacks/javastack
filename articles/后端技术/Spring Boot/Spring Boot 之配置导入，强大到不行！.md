我们知道在 Spring Boot 中可以用一个 `@Configuration` 配置文件来配置所有 Bean 及其他配置（不会的看这篇文章：[Spring零配置之@Configuration注解详解](https://mp.weixin.qq.com/s/i8qHLkdtf4XzRyIRJ8R_7A)），但其实没必要这么做。

我们可以把相关的配置独立出来，放到多个 `@Configuration` 中，如以下参考：

- MainConfiguration：项目主要配置
- DataSoureceConfiguration：数据源配置
- RedisConfiguration：Redis配置
- MongoDBConfiguration：MongoDB配置

这时候 `@Import` 注解就能派上用场了，来看下它的源码：

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Import {

	/**
	 * {@link Configuration}, {@link ImportSelector}, {@link ImportBeanDefinitionRegistrar}
	 * or regular component classes to import.
	 */
	Class<?>[] value();

}
```

可以导入这三类注册文件：

- Configuration
- ImportSelector
- ImportBeanDefinitionRegistrar

第一个已经讲过了，其他两个后面会讲，关注Java技术栈微信公众号，获取第一时间推送。

先来看一个 `@Import` 示例，直接导入其他配置文件：

```
@Configuration
@Import({ RedisConfiguration.class })
public class MainConfiguration {
    // ...
}
```

当然，如果这些配置文件都在类扫描路径下，不用 `@Import` 导入注解，直接用 `@ComponentScan` 也能搞定，这个默认的 `@SpringBootApplication` 注解就包含了，不需要再重复加。

再者，`@Enable*` 注解也能派上用场：

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RedisConfiguration.class)
public @interface EnableRedis {
}
```

具体可以参考这篇文章：[Spring Enable*高级应用及原理](https://mp.weixin.qq.com/s/o73F_KbqH6biCwuN77eiuQ)，另外，关注Java技术栈微信公众号，在后台回复：spring，可以获取更多 Spring 干货。

**那么问题来了，老项目想用 Spring Boot 框架，但还有大量的 XML 配置文件，很难迁移到 `@Configuration` 配置文件里面来，怎么处理？**

栈长继续给你支招！

答案就是：`@ImportResource` 注解，来看下它的源码：

```
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ImportResource {

	@AliasFor("locations")
	String[] value() default {};

	@AliasFor("value")
	String[] locations() default {};

	Class<? extends BeanDefinitionReader> reader() default BeanDefinitionReader.class;

}
```

`@ImportResource` 可以指定要导入的 XML 配置文件的路径，还能指定自定义的 `BeanDefinitionReader`。

来一个示例，导入一个类路径下的 XML 配置文件：

```
@Configuration
@ImportResource("classpath:config/spring-mvc.xml")
public class MainConfiguration {
    // ...
}
```

获取所有 Spring Boot 示例代码，请关注微信公众号 "Java技术栈" 在后台回复关键字：bootcode

通过本文的介绍，可以看出来 Spring Boot 现在对原有的 Spring MVC 传统项目是支持的非常好的，不管是 Servlet，还是 XML  配置文件，都可以轻松集成。

上面说的这些注解在《[Spring Boot 最核心的 25 个注解](https://mp.weixin.qq.com/s/lOA9djEptJyZ2sm93nxr-Q)》文章中都有说明，后面会逐步分解开来，关注Java技术栈微信公众号，获取第一时间推送。

![](http://img.javastack.cn/wx_search_javastack.png)