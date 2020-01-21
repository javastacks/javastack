
![](http://img.javastack.cn/18-6-20/82197726.jpg)

在 Spring 的官方博客上已经看到 Spring Cloud Finchley 在 06 月 19 日这一天正式发布了，我们在 Maven 中央仓库也看到了最新版的更新。

![](http://img.javastack.cn/18-6-20/96626148.jpg)

Finchley 正式版的发布貌似经历了相当长的时间，果然是闷声发大财，这次的重大发布主要带来了以下 4 项重大更新。

### 重大更新

##### 1、新增 Spring Cloud Gateway 组件

Spring Cloud Gateway 是一个基于 Spring Webflux 和响应式 Netty 的下一代 API 网关，用来替换 Spring Cloud Netflix Zuul。它提供了更加简单的动态路由，以及针对每个路由的过滤器（如地址重写、断路器、添加/删除请求头、限流和安全等）。

##### 2、新增 Spring Cloud Function 组件

**Spring Cloud Function 的主要功能如下：**

- 通过一系列函数推进业务逻辑的实现；
- 将业务逻辑的开发生命周期从任何特定运行目标中分离，以便相同的代码可以作为一个 Web 端点、一个流处理器或一个任务来运行；
- 支持一个跨 serverless providers 的统一编程模型，并拥有独立运行的能力（本地或 PaaS 平台）；
- 支持在 serverless providers 上面启用 Spring Boot 特性，如自动配置、依赖注入、指标等；

##### 3、兼容 Spring Boot 2.0.x

Finchley 版本是基于 Spring Boot 2.0.x 构建的，官方建议不要与 Spring Boot 1.5.x 及之前的版本一起工作。

##### 4、最低支持 JDK 1.8

JDK 门槛提高了，1.8 毕竟是现在的主流。

更多其他的更新细节请参考 Spring 的官方博客。

> https://spring.io/blog/2018/06/19/spring-cloud-finchley-release-is-available）

### 其他版本生命周期

新版本的发布预示着其他历史版本逐渐退出历史舞台，Spring 官方公布了其他版本的结束服务的截止时间。

- **Camden**

现在开始结束生命周期。

- **Dalston**

将于 2018 年 12 月结束生命周期。

- **Edgware**

伴随着 Spring Boot 1.5.x 的结束而结束生命周期。

### 最后

如果分不清这些版本的意义，请阅读我之前的一篇文章《[Spring Cloud 多版本怎么选择？帮你解惑！](https://mp.weixin.qq.com/s/IqlHFsIrFJ5vBG9-1gldJw)》，更多 Spring Cloud 的技术文章请查看公众号专题菜单中 Spring 技术教程汇总。

**@码农们 你们现在用的什么版本，对于升级这个版本有什么看法？欢迎留言！**
