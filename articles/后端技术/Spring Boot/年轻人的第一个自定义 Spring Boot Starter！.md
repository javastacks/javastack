陆陆续续，零零散散，栈长已经写了几十篇 Spring Boot 系列文章了，其中有介绍到 [Spring Boot Starters 启动器](https://mp.weixin.qq.com/s/9HJVGlplze5p0eBayvhFCA)，使用的、介绍的都是第三方的 Starters ，那如何开发一个自己的 Spring Boot Starter 呢？

下面带大家开发一个年轻人的第一个 Spring Boot Starter！

不知道 Starters 为何物的请进这个传送门===>
[Spring Boot Starters 启动器](https://mp.weixin.qq.com/s/9HJVGlplze5p0eBayvhFCA)，看完有了学习基础，我们再继续下面的自定义 Starter 实战！

## 一、自定义 Starter 必备组件

一个完整的 Spring Boot Starter 需要包含以下组件：

- 包含自动配置代码的自动配置模块；参考：[Spring Boot自动配置原理、实战](https://mp.weixin.qq.com/s/gs2zLSH6m9ijO0-pP2sr9Q)。
- Starter模块提供对自动模块的依赖关系，和相关依赖库，以及任何需要用到的依赖。简而言之，就是，添加一个 Starter 就应该提供使用该 Starter 所需的一切；

## 二、创建一个自定义Starter

怎么创建 Spring Boot 项目就不说了，之前也分享过，参考：[年轻人的第一个 Spring Boot 应用！](https://mp.weixin.qq.com/s/YNhoFtcvGuoY24fVQCPdmg)。

这个自定义 Starter 就实现一个根据属性的值是否配置Bean。

#### 1、创建自动配置类

```
package cn.javastack.springboot.starter.config;

import cn.javastack.springboot.starter.service.TestService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "javastack.starter", name = "enabled", havingValue = "true")
public class TestServiceAutoConfiguration {

    @Bean
    public TestService testService() {
        return new TestService();
    }

}
```

这个自动配置类很简单，就是根据是否有 `javastack.starter.enabled=true` 这个参数的值再配置一个Bean。 

`TestService`示例如下：

```
package cn.javastack.springboot.starter.service;

public class TestService {

    public String getServiceName() {
        return "Java技术栈";
    }

}
```

这个类就有一个方法 `getServiceName`，它就返回一个字符串：`Java技术栈`。

#### 2、允许自动配置

创建 `META-INF/spring.factories` 文件，添加这个允许自动配置的类。


```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
cn.javastack.springboot.starter.config.TestServiceAutoConfiguration
```

## 三、测试这个自定义Starter

上面的自定义 Starter 项目建好后，可以来测试一下它是否生效了。

一般是把它打成 jar 包上传到 Maven 仓库，供其他同事调用，这里我们本报打完包之后再引用它。

#### 1、添加依赖

新建一个 Spring Boot 测试项目，添加这个自定义 Starter 依赖，Maven 依赖如下：

```
<dependencies>
    <dependency>
        <groupId>cn.javastack</groupId>
        <artifactId>javastack-spring-boot-starter</artifactId>
        <version>1.0</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```

#### 2、添加测试方法

```
package cn.javastack.springboot.starter.sample;

import cn.javastack.springboot.starter.service.TestService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 微信公众号：Java技术栈
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner commandLineRunner(TestService testService) {
        return (args) -> {
            System.out.println(testService.getServiceName());
        };
    }

}
```

这个方法的作用是，项目启动后，调用 `TestService` 的实例方法，输出方法的值。

关于 `CommandLineRunner` 有不懂的可以看这篇文章：[Spring Boot Runner 启动器](https://mp.weixin.qq.com/s/WeO2kJLV6LKez56T5GG35Q)。

#### 3、开启配置

我们知道这个自定义 Starter 中需要有 `javastack.starter.enabled=true` 这个参数的值的，所以我们在 `application.yml` 配置文件中添加这个配置：

```
javastack:
  starter:
    enabled: true
```

#### 4、运行测试

运行 Application 类的 main 方法，最后会输出结果：Java技术栈。

当我们把配置改为：

```
javastack:
  starter:
    enabled: false
```

此时，运行报错，因为没有这个实例啊，自动配置类只有为 true 时才会配置。

## 四、总结

本章栈长简单演示了如何自定义一个 Spring Boot Starter，根据某个参数的值来决定是否自动配置，其实还可以根据是否有某个类、某个Bean……等，可以看下《[Spring Boot 最核心的 25 个注解](https://mp.weixin.qq.com/s/lOA9djEptJyZ2sm93nxr-Q)》这篇文章对应的 `ConditionOnXXX` 系列注解。

其实了解了 Spring Boot 自动配置的原理，自定义一个 Starter 并不难，你可以在这个实例基础上灵活扩展。

**本文完整的代码实例 Github 地址：**

> https://github.com/javastacks/spring-boot-best-practice

赶紧关注微信公众号：Java技术栈，已经更新一大堆教程了，后续这个教程会持续更新……

![](http://img.javastack.cn/wx_search_javastack.png)