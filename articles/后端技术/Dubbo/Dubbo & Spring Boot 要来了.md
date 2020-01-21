![image](https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3636281043,3305223769&fm=27&gp=0.jpg)

Duboo和Spring Boot都是非常优秀的框架，现在它们要结合了。为了简化Dubbo开发集成，阿里Dubbo团队将发布基于Spring Boot的版本，可快速上手Dubbo的分布式开发，并提供了一些准生产环境特性（如安全、健康检查、配置外部化等……）。

如果你还不知道什么是Dubbo，可以去Dubbo官网（http://dubbo.io）花点时间了解下。

**下面来看看Dubbo & Spring Boot集成是如何做的！**

用过Dubbo的都知道，分布式场景中有两个重要的角色，一个是提供者，一个是消费者。

#### 如何实现提供者服务

**1、定义一个提供者接口：DemoService**

```
public interface DemoService {

    String sayHello(String name);

}
```

**2、实现一个提供者，并实现DemoService接口**

```
@Service(
        version = "1.0.0",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class DefaultDemoService implements DemoService {

    public String sayHello(String name) {
        return "Hello, " + name + " (from Spring Boot)";
    }

}
```

注意，提供者服务由注解@Service定义，其参数配置在application文件中，见下方定义。

**3、然后在appliation文件中添加配置**

```
# Spring boot application
spring.application.name = dubbo-provider-demo
server.port = 9090
management.port = 9091

# Base packages to scan Dubbo Components (e.g @Service , @Reference)
dubbo.scan.basePackages  = com.alibaba.boot.dubbo.demo.provider.service

# Dubbo Config properties
## ApplicationConfig Bean
dubbo.application.id = dubbo-provider-demo
dubbo.application.name = dubbo-provider-demo

## ProtocolConfig Bean
dubbo.protocol.id = dubbo
dubbo.protocol.name = dubbo
dubbo.protocol.port = 12345

## RegistryConfig Bean
dubbo.registry.id = my-registry
dubbo.registry.address = N/A
```

**4、最后再提供一个提供者服务启动类**


```
@SpringBootApplication
public class DubboProviderDemo {

    public static void main(String[] args) {

        SpringApplication.run(DubboProviderDemo.class,args);

    }

}
```

> 更多提供者示例代码可参考：https://github.com/dubbo/dubbo-spring-boot-project/tree/master/dubbo-spring-boot-samples/dubbo-spring-boot-sample-provider

#### 如何实现消费者服务

消费者服务用来消费提供者提供的服务，也就是消费者服务调用提供者的服务进行操作。

消费者需要注入提供者的接口对应的Spring Bean实例。

**1、定义一个消费者**

```
@RestController
public class DemoConsumerController {

    @Reference(version = "1.0.0",
            application = "${dubbo.application.id}",
            url = "dubbo://localhost:12345")
    private DemoService demoService;

    @RequestMapping("/sayHello")
    public String sayHello(@RequestParam String name) {
        return demoService.sayHello(name);
    }

}
```

@Reference注解用来注入提供者服务的Spring Bean实例，其参数配置依然在application文件中，见下方定义。

**2、然后在appliation文件中添加配置**

```
# Spring boot application
spring.application.name = dubbo-consumer-demo
server.port = 8080
management.port = 8081


# Dubbo Config properties
## ApplicationConfig Bean
dubbo.application.id = dubbo-consumer-demo
dubbo.application.name = dubbo-consumer-demo

## ProtocolConfig Bean
dubbo.protocol.id = dubbo
dubbo.protocol.name = dubbo
dubbo.protocol.port = 12345
```

**3、最后再提供一个消费者服务启动类**

```
@SpringBootApplication(scanBasePackages = "com.alibaba.boot.dubbo.demo.consumer.controller")
public class DubboConsumerDemo {

    public static void main(String[] args) {

        SpringApplication.run(DubboConsumerDemo.class,args);

    }

}
```

> 更多消费者示例代码可参考：https://github.com/dubbo/dubbo-spring-boot-project/blob/master/dubbo-spring-boot-samples/dubbo-spring-boot-sample-consumer

先启动提供者启动类，再启动消费者启动类，通过访问消费者控制层可调用提供者服务进行消费。

更多与Spring Boot集成的新特性可参考官方文档

> 项目地址：https://github.com/dubbo/dubbo-spring-boot-project

看完是不是觉得上手非常简单了，Spring Boot真是快速开发神器。不过，目前该项目还未正式发布，敬请期待，我们也将会持续关注，如果有更进一步消息会及时通知到大家。

