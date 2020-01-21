## 启动类

在包根目录下添加启动类，必须包含main方法，再添加Spring Boot启动方法：

```
SpringApplication.run(SampleController.class, args);
```
或者流式API


```
new SpringApplicationBuilder().run(args);
```

下面是一个典型的Spring Boot工程包布局，Application启动类位于根目录下
```
com
 +- example
     +- myproject
         +- Application.java
         |
         +- domain
         |   +- Customer.java
         |   +- CustomerRepository.java
         |
         +- service
         |   +- CustomerService.java
         |
         +- web
             +- CustomerController.java
```


## 核心注解

启动类上面的注解是@SpringBootApplication，它也是Spring Boot的核心注解，主要组合包含了以下3个注解：

@SpringBootConfiguration：组合了@Configuration注解，实现配置文件的功能。\
@EnableAutoConfiguration：打开自动配置的功能，也可以关闭某个自动配置的选项，如关闭数据源自动配置功能：
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })。\
@ComponentScan：Spring组件扫描。

## 配置文件

Spring Boot有两种类型的配置文件，application和bootstrap文件。Spring Boot会自动加载classpath目前下的这两个文件，文件格式为properties或者yml格式。

*.properties文件大家都知道是key=value的形式\
*.yml是key: value的形式

*.yml加载的属性是有顺序的，但不支持@PropertySource注解来导入配置，一般推荐用yml文件，看下来更加形象。

### application配置文件

application配置文件是应用级别的，是当前应用的配置文件。

### bootstrap配置文件

bootstrap配置文件是系统级别的，用来加载外部配置，如配置中心的配置信息，也可以用来定义系统不会变化的属性。bootstatp文件的加载先于application文件。