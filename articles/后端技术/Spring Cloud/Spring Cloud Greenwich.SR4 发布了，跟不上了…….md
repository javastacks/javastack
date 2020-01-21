前几天 Spring Cloud Greenwich.SR4 发布了：

> https://spring.io/blog/2019/11/19/spring-cloud-greenwich-sr4-released

我们来看下都更新了什么。

在 Maven 仓库中已经可以用了：

```
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>Greenwich.SR4</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

#### 1、Spring Boot 

Spring Cloud Greenwich.SR4 是基于 Spring Boot 2.1.10.RELEASE 构建的，Spring Boot 版本越来越高了。

#### 2、Spring Cloud Openfeign

Spring Cloud Openfeign 的版本升级到了 OpenFeign 10.4.0。

#### 3、Spring Cloud Vault

Spring Cloud Vault 的依赖和文档变更和更新。

#### 4、Spring Cloud Gateway

Spring Cloud Gateway 增加了对 Spring Cloud LoadBalancer 的支持。

#### 5、还有其他模块的 bug 修复

- Spring Cloud Kubernetes
- Spring Cloud Commons
- Spring Cloud Openfeign
- Spring Cloud Contract
- Spring Cloud Vault
- Spring Cloud Netflix
- Spring Cloud Sleuth
- Spring Cloud Config
- Spring Cloud Gateway

升级推荐阅读：

- [Spring Cloud 升级最新 Greenwich 版本](https://mp.weixin.qq.com/s/76vRZseVvKJaBPGxIIIn5g)
- [Spring Cloud 升级最新 Finchley 版本](https://mp.weixin.qq.com/s/CvAmV4mjWHqNPkUoy0CwYw)

@ All 码农们：你们升级了吗？有遇到什么样的坑？欢迎留言！

关注微信公众号：Java技术栈，在公众号后台回复：cloud，获取栈长整理的 Spring Cloud 系列教程，都是实战干货。

- Spring Cloud 配置中心高可用搭建
- Spring Cloud 多版本如何选择
- Spring Cloud 是什么，和 Dubbo 对比
- Spring Cloud 注册中心高可用搭建
- Spring Cloud Eureka 自我保护机制
- ……

![](http://img.javastack.cn/wx_search_javastack.png)