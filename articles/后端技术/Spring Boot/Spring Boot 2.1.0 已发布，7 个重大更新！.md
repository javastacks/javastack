
距离《[重磅：Spring Boot 2.0 正式发布！](https://mp.weixin.qq.com/s/VMIHit6kB9MVYhxX2Qx2DA)》已经过去大半年了，而 Spring Boot 2.1.0 在 10 月底就发布了，我们来看下 Spring Boot 2.1.0 都更新了什么，每一个 Java 技术人都值得关注。

栈长其实早就看到了更新了，现在才有时间来更新下。

## 1、第三方类库升级

- Hibernate 5.3
- Micrometer 1.1
- Reactor Californium
- Spring Data Lovelace
- Spring Framework 5.1
- Tomcat 9
- Undertow 2

主要就更新了这些类库，其实远不止这些。

## 2、性能提升

#### 1）应用程序性能

改进性能作为 Spring Boot 团队持续努力的一部分，性能提升在 Spring Boot 2.1 中取得了一些重大进展。

现在 Spring Boot 2.1+ 应用程序启动速度变得更快，并且消耗的内存更少，这在内存非常稀缺的应用环境中尤其重要。

#### 2）异步引导 Hibernate

在 Spring Boot 2.1 中还采用了 Spring 框架和 Spring Data JPA 框架对 Hibernate 异步引导的支持。

如果你使用了 Spring Data JPA 框架，并设置了下面的参数，那么 Hibernate 将在一个独立的线程中启动，而其他应用程序的启动是同时并行的。

> spring.data.jpa.repositories.bootstrap-mode=deferred

## 3、支持 Java 11

随着 Spring 5.1 对 Java 11 的支持，Spring Boot 2.1 现在也可以支持 Java 11 了，同时还可以与 Java 8 兼容。

## 4、支持参数数据大小

当你在 `application.properties` 配置文件配置参数时，如果一个参数的值需要表示成字节数或者其他的单位，你可以为参数的值指定单位（如：10MB），就像在 Spring Boot 2.0 中对 `Duration` 的支持一样，它可以转换成为：`org.springframework.util.unit.DataSize` 类。

所有支持的单位可以看这个类：

> org.springframework.util.unit.DataUnit

## 5、执行端点

#### 1）添加了两个新端点

Spring Boot 2.1 中添加了两个新的执行端点。

- **/actuator/caches**：提供应用程序的缓存管理信息。
- **/actuator/integrationgraph**：提供对 Spring 集成的组件图形化展现。

#### 2）健康端点加强

健康端点允许访问一个独立的组件健康状态，如：`/actuator/health/db` 将只执行 `db` 的健康端点。

## 6、度量

度量这块，除了升级到 Micrometer 1.1，还增加了用于导出到 AppOptics, Humio 和 KariosDB 的自动配置。

度量标准覆盖率也得到了改进，包括：

- Hibernate metrics
- Spring Framework’s WebClient
- Kafka consumer metrics
- Log4j2 metrics
- Jetty server thread pool metrics
- Server-side Jersey HTTP request metrics

## 7、其他更新

在版本更新说明中，还有大量的其他更改和提升，你也可以找到计划在下一个版本要删除的类和方法的列表。

## 最后

 Spring Boot 团队想借此机会，再次感谢所有的用户和贡献者，现在已经有超过 500 多人向 Spring Boot 提交代码，并且有超过 19,000 多次提交。
 
 如果你有更好的想法想要贡献给 Spring Boot，可以去 Spring Boot 官方仓库进行代码提交。
 
 > 更新说明：https://spring.io/blog/2018/10/30/spring-boot-2-1-0
 
栈长现在还在用 2.0，你们用的 Spring Boot 什么版本呢？你们期待 Spring Boot 添加什么样的功能？欢迎留言！
 
关注Java技术栈，获取更多干货推送！

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "boot" 可获取更多，转载请原样保留本信息。

