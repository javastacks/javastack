> 作者 | Spring Cloud Alibaba 高级开发工程师洛夜
来自公众号阿里巴巴中间件投稿

前段时间 Hystrix 宣布不再维护之后（[Hystrix 停止开发。。。Spring Cloud 何去何从？](https://mp.weixin.qq.com/s/WPb8yUYPyvDCKJVb--W63Q)），Feign 作为一个跟 Hystrix 强依赖的组件，必然会有所担心后续的使用。

作为 Spring Cloud Alibaba 体系中的熔断器 Sentinel，Sentinel 目前整合了 Feign，本文对整合过程做一次总结，欢迎大家讨论和使用。

## Feign 是什么？

Feign 是一个 Java 实现的 Http 客户端，用于简化 Restful 调用。

Feign 跟 OkHttp、HttpClient 这种客户端实现理念不一样。Feign 强调接口的定义，接口中的一个方法对应一个 Http 请求，调用方法即发送一个 Http 请求；OkHttp 或 HttpClient 以过程式的方式发送 Http 请求。Feign 底层发送请求的实现可以跟 OkHttp 或 HttpClient 整合。

要想整合 Feign，首先要了解 Feign 的使用以及执行过程，然后看 Sentinel 如何整合进去。

## Feign 的使用

需要两个步骤：

#### 1、使用 `@EnableFeignClients` 注解开启 Feign 功能

```java
@SpringBootApplication
@EnableFeignClients // 开启 Feign 功能
public class MyApplication {
  ...
}
```

`@EnableFeignClients` 属性介绍：

value:String[] 包路径。比如 `org.my.pkg`，会扫描这个包路径下带有 `@FeignClient` 注解的类并处理；

basePackages:String[] 跟 value 属性作用一致；

basePackageClasses:Class<?>[] 跟 basePackages 作用一致，basePackages 是个 String 数组，而 basePackageClasses 是个 Class 数组，用于扫描这些类对应的 package；

defaultConfiguration:Class<?>[] 默认的配置类，对于所有的 Feign Client，这些配置类里的配置都会对它们生效，可以在配置类里构造 `feign.codec.Decoder`, `feign.codec.Encoder` 或 `feign.Contract` 等bean;

clients:Class<?>[] 表示 `@FeignClient`； 注解修饰的类集合，如果指定了该属性，那么扫描功能相关的属性就是失效。比如 value、basePackages 和 basePackageClasses；

#### 2、使用 `@FeignClient` 注解修饰接口，这样会基于跟接口生成代理类

```java
@FeignClient(name = "service-provider")
public interface EchoService {
  @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
  String echo(@PathVariable("str") String str);
}
```

只要确保这个被 `@FeignClient` 注解修饰到的接口能被 `@EnableFeignClients` 注解扫描到，就会基于 `java.lang.reflect.Proxy` 根据这个接口生成一个代理类。

生成代理类之后，会被注入到 `ApplicationContext` 中，直接 AutoWired 就能使用，使用的时候调用 `echo` 方法就相当于是发起一个 Restful 请求。

`@FeignClient` 属性介绍：

value:String 服务名。比如 `service-provider`, `http://service-provider`。比如 `EchoService` 中如果配置了 `value=service-provider`，那么调用 `echo` 方法的 url 为  `http://service-provider/echo`；如果配置了 `value=https://service-provider`，那么调用 `echo` 方法的 url 为 `https://service-provider/divide`

serviceId:String 该属性已过期，但还能用。作用跟 value 一致
name:String 跟 value 属性作用一致

qualifier:String 给 FeignClient 设置 `@Qualifier` 注解

url:String 绝对路径，用于替换服务名。优先级比服务名高。比如 `EchoService` 中如果配置了 `url=aaa`，那么调用 `echo` 方法的 url 为  `http://aaa/echo`；如果配置了 `url=https://aaa`，那么调用 `echo` 方法的 url 为 `https://aaa/divide`

decode404:boolean 默认是 false，表示对于一个 http status code 为 404 的请求是否需要进行 decode，默认不进行 decode，当成一个异常处理。设置为true之后，遇到 404 的 response 还是会解析 body

configuration:Class<?>[] 跟 `@EnableFeignClients`  注解的 `defaultConfiguration` 属性作用一致，但是这个对于单个 FeignClient 的配置，而 `@EnableFeignClients`  里的 `defaultConfiguration` 属性是作用域全局的，针对所有的 FeignClient

fallback:Class<?> 默认值是 `void.class`，表示 fallback 类，需要实现 FeignClient 对应的接口，当调用方法发生异常的时候会调用这个 Fallback 类对应的 FeignClient 接口方法。

如果配置了 fallback 属性，那么会把这个 Fallback 类包装在一个默认的 `FallbackFactory` 实现类 `FallbackFactory.Default` 上，而不使用 fallbackFactory 属性对应的 `FallbackFactory` 实现类

fallbackFactory:Class<?> 默认值是 `void.class`，表示生产 fallback 类的 Factory，可以实现 `feign.hystrix.FallbackFactory` 接口，`FallbackFactory` 内部会针对一个 `Throwable` 异常返回一个 Fallback 类进行 fallback 操作

path:String 请求路径。 在服务名或 url 与 requestPath 之间

primary:boolean 默认是 true，表示当前这个 FeignClient 生成的 bean 是否是 primary。 

**所以如果在 `ApplicationContext`中存在一个实现 `EchoService` 接口的 Bean，但是注入的时候并不会使用该Bean，因为 FeignClient 生成的 Bean 是 primary**

## Feign 的执行过程

了解了 Feign 的使用之后，接下来我们来看 Feign 构造一个 Client 的过程。

从 `@EnableFeignClients` 注解可以看到，入口在该注解上的 `FeignClientsRegistrar` 类上，整个链路是这样的：

![Feign.png](https://cdn.nlark.com/lark/0/2018/png/64647/1544585446790-8affc733-701e-4ff1-819b-c8b7980de337.png) 

从这个链路上我们可以得到几个信息：

1.`@FeignClient` 注解修饰的接口最终会被转换成 `FeignClientFactoryBean` 这个 `FactoryBean`，`FactoryBean `内部的 getObject 方法最终会返回一个 Proxy

2.在构造 Proxy 的过程中会根据 `org.springframework.cloud.openfeign.Targeter` 接口的 `target` 方法去构造。如果启动了hystrix开关(`feign.hystrix.enabled=true`)，会使用 `HystrixTargeter`，否则使用默认的 `DefaultTargeter`

3.`Targeter` 内部构造 Proxy 的过程中会使用 `feign.Feign.Builder` 去调用它的 `build` 方法构造 `feign.Feign` 实例(默认只有一个子类 `ReflectiveFeign`)。

如果启动了 hystrix 开关(`feign.hystrix.enabled=true`)，会使用 `feign.hystrix.HystrixFeign.Builder`，否则使用默认的`feign.Feign.Builder`

4.构造出 `feign.Feign` 实例之后，调用 `newInstance` 方法返回一个 Proxy

简单看下这个 `newInstance` 方法内部的逻辑：

```java
public <T> T newInstance(Target<T> target) {
    Map<String, MethodHandler> nameToHandler = targetToHandlersByName.apply(target);
    Map<Method, MethodHandler> methodToHandler = new LinkedHashMap<Method, MethodHandler>();
    List<DefaultMethodHandler> defaultMethodHandlers = new LinkedList<DefaultMethodHandler>();

    for (Method method : target.type().getMethods()) {
      if (method.getDeclaringClass() == Object.class) {
        continue;
      } else if(Util.isDefault(method)) {
        DefaultMethodHandler handler = new DefaultMethodHandler(method);
        defaultMethodHandlers.add(handler);
        methodToHandler.put(method, handler);
      } else {
        methodToHandler.put(method, nameToHandler.get(Feign.configKey(target.type(), method)));
      }
    }
    // 使用 InvocationHandlerFactory 根据接口的方法信息和 target 对象构造 InvocationHandler
    InvocationHandler handler = factory.create(target, methodToHandler);
    // 构造代理
    T proxy = (T) Proxy.newProxyInstance(target.type().getClassLoader(), new Class<?>[]{target.type()}, handler);

    for(DefaultMethodHandler defaultMethodHandler : defaultMethodHandlers) {
      defaultMethodHandler.bindTo(proxy);
    }
    return proxy;
  }
```

这里的 `InvocationHandlerFactory` 是通过构造 `Feign` 的时候传入的：

* 使用原生的 `DefaultTargeter`: 那么会使用 `feign.InvocationHandlerFactory.Default` 这个 factory，并且构造出来的 `InvocationHandler` 是 `feign.ReflectiveFeign.FeignInvocationHandler`

* 使用 hystrix 的 `HystrixTargeter`: 那么会在`feign.hystrix.HystrixFeign.Builder#build(feign.hystrix.FallbackFactory<?>)` 方法中调用父类的 `invocationHandlerFactory` 方法传入一个匿名的 `InvocationHandlerFactory` 实现类，该类内部构造出的 `InvocationHandler` 为 `HystrixInvocationHandler`

# Sentinel 整合 Feign

理解了 Feign 的执行过程之后，Sentinel 想要整合 Feign，可以参考 Hystrix 的实现：

1.❌ 实现 `Targeter` 接口 `SentinelTargeter`。 **很不幸，`Targeter` 这个接口属于包级别的接口，在外部包中无法使用，这个 `Targeter` 无法使用。没关系，我们可以沿用默认的` HystrixTargeter`(实际上会用 `DefaultTargeter`，下文 Note 有解释)**

2.✅  `FeignClientFactoryBean` 内部构造 `Targeter`、`feign.Feign.Builder` 的时候，都会从 `FeignContext` 中获取。所以我们沿用默认的 `DefaultTargeter` 的时候，内部使用的 `feign.Feign.Builder` 可控，而且这个 Builder 不是包级别的类，可在外部使用

* 创建 `SentinelFeign.Builder` 继承 `feign.Feign.Builder` ，用来构造 `Feign`

* `SentinelFeign.Builder` 内部需要获取 `FeignClientFactoryBean` 中的属性进行处理，比如获取 `fallback`, `name`, `fallbackFactory`。

**很不幸，`FeignClientFactoryBean` 这个类也是包级别的类。没关系，我们知道它存在在 `ApplicationContext` 中的 beanName， 拿到 bean 之后根据反射获取属性就行(该过程在初始化的时候进行，不会在调用的时候进行，所以不会影响性能)**

* `SentinelFeign.Builder` 调用 `build` 方法构造 `Feign` 的过程中，我们不需要实现一个新的 `Feign`，跟 hystrix 一样沿用 `ReflectiveFeign` 即可，在沿用的过程中调用父类 `feign.Feign.Builder` 的一些方法进行改造即可，比如 `invocationHandlerFactory` 方法设置 `InvocationHandlerFactory` ，`contract` 的调用
   
3.✅ 跟 hystrix 一样实现自定义的 `InvocationHandler` 接口 `SentinelInvocationHandler` 用来处理方法的调用 

4.✅ `SentinelInvocationHandler` 内部使用 Sentinel 进行保护，这个时候涉及到资源名的获取。`SentinelInvocationHandler` 内部的 `feign.Target` 能获取服务名信息，`feign.InvocationHandlerFactory.MethodHandler` 的实现类 `feign.SynchronousMethodHandler` 能拿到对应的请求路径信息。

**很不幸，`feign.SynchronousMethodHandler` 这个类也是包级别的类。没关系，我们可以自定义一个 `feign.Contract` 的实现类 `SentinelContractHolder` 在处理 `MethodMetadata` 的过程把这些 metadata 保存下来**(`feign.Contract` 这个接口在 Builder 构造 Feign 的过程中会对方法进行解析并验证)。

在 `SentinelFeign.Builder` 中调用 `contract` 进行设置，`SentinelContractHolder` 内部保存一个 `Contract` 使用委托方式不影响原先的 `Contract` 过程



Note:  `spring-cloud-starter-openfeign` 依赖内部包含了 `feign-hystrix`。所以是说默认使用 `HystrixTargeter` 这个 `Targeter` ，进入 `HystrixTargeter` 的 `target` 方法内部一看，发现有段逻辑这么写的：

```java
@Override
public <T> T target(FeignClientFactoryBean factory, Feign.Builder feign, FeignContext context,
                    Target.HardCodedTarget<T> target) {
  if (!(feign instanceof feign.hystrix.HystrixFeign.Builder)) {
    // 如果 Builder 不是 feign.hystrix.HystrixFeign.Builder，使用这个 Builder 进行处理
    // 我们默认构造了 SentinelFeign.Builder 这个 Builder，默认使用 feign-hystrix 依赖也没有什么问题
    return feign.target(target);
  }
  feign.hystrix.HystrixFeign.Builder builder = (feign.hystrix.HystrixFeign.Builder) feign;
  ...
}
```

在 `SentinelInvocationHandler` 内部我们对资源名的处理策略是: `http方法:protocol://服务名/请求路径跟参数`

比如这个 `TestService`:

```java
@FeignClient(name = "test-service")
public interface TestService {
  @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
  String echo(@PathVariable("str") String str);

  @RequestMapping(value = "/divide", method = RequestMethod.GET)
  String divide(@RequestParam("a") Integer a, @RequestParam("b") Integer b);
}
```

* `echo` 方法对应的资源名：`GET:http://test-service/echo/{str}`
* `divide` 方法对应的资源名：`GET:http://test-service/divide`



# 总结

1.Feign 的内部很多类都是 package 级别的，外部 package 无法引用某些类，这个时候只能想办法绕过去，比如使用反射

2.目前这种实现有风险，万一哪天 starter 内部使用的 Feign 相关类变成了 package 级别，那么会改造代码。所以把 Sentinel 的实现放到 Feign 里并给 Feign 官方提 pr 可能更加合适

3.Feign的处理流程还是比较清晰的，只要能够理解其设计原理，我们就能容易地整合进去


欢迎大家对整合方案进行讨论，并能给出不合理的地方，当然能提pr解决不合理的地方就更好了。

Sentinel Starter 整合 Feign 的代码目前已经在 github 仓库上，但是没未发版。预计月底发版，如果现在就想使用，可以在 pom 中引入 Spring SNAPSHOT 的 repository 或自行下载源码进行编译。

最后再附上一个使用 Nacos 做服务发现和 Sentinel 做限流的 Feign 例子。

> https://github.com/spring-cloud-incubator/spring-cloud-alibaba/tree/master/spring-cloud-alibaba-examples/nacos-example/nacos-discovery-example

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "cloud" 可获取更多，转载请原样保留本信息。