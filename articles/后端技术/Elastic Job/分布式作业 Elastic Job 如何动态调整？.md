
前面分享了两篇分布式作业调度框架 Elastic Job 的介绍及应用实战。

- [ElasticJob－分布式作业调度神器](https://mp.weixin.qq.com/s/1W1zI28riaWN9k3HHBL9Kw)

- [分布式作业 Elastic Job 快速上手指南！](https://mp.weixin.qq.com/s/QCssypUN4ayVfVcpjZRg7A)

Elastic Job 提供了简单易用的运维平台，方便用户监控、动态修改作业参数、作业操作及查询作业。

#### 设计理念

运维平台和elastic-job-lite并无直接关系，是通过读取作业注册中心数据展现作业状态，或更新注册中心数据修改全局配置。

控制台只能控制作业本身是否运行，但不能控制作业进程的启动，因为控制台和作业本身服务器是完全分离的，控制台并不能控制作业服务器。

#### 功能列表

- 登录安全控制

- 注册中心、事件追踪数据源管理

- 快捷修改作业设置

- 作业和服务器维度状态查看

- 操作作业禁用\启用、停止和删除等生命周期

- 事件追踪查询

#### 不支持项

- 添加作业 

作业在首次运行时将自动添加，Elastic-Job-Lite 以 `jar` 方式启动，并无作业分发功能。如需完全通过运维平台发布作业，请使用 `Elastic-Job-Cloud`。

#### 运维平台搭建

**1、在官网下载最新稳定的源码包，地址如下。**

> https://github.com/elasticjob/elastic-job-lite

这里我们下载了最新的 `2.1.5` 的最新发布包。

**2、编译下载后的源码包**

下载后，随便解压到哪个目录，然后执行 `mvn install` 编译。

```
cd d:/elastic-job-lite-2.1.5
mvn install
```

![](http://img.javastack.cn/18-3-19/70978286.jpg)

**3、启动运维平台**

在编译目录 `d:\elastic-job-lite-2.1.5\elastic-job-lite\elastic-job-lite-console\target` 找到编译后的包： `elastic-job-lite-console-2.1.5.tar.gz` ，然后解压到 `elastic-job-lite-console-2.1.5` ，并执行 `bin` 目录下的 `start.bat` 即可启动，Linux下为 `start.sh`。

![](http://img.javastack.cn/18-3-19/67592187.jpg)

`8899` 为默认端口号，可通过启动脚本输入 `-p` 自定义端口号。

**4、访问运维平台**

Elastic-Job 提供了两种账户：管理员及访客。管理员拥有全部操作权限，访客仅拥有察看权限。默认管理员用户名和密码是 `root/root`，访客用户名和密码是 `guest/guest`，可通过 `conf\auth.properties` 修改管理员及访客用户名及密码。

```
root.username=root
root.password=root
guest.username=guest
guest.password=guest
```

打开浏览器访问 `http://localhost:8899/` 输出用户名和密码即可访问控制台。

![](http://img.javastack.cn/18-3-19/91428457.jpg)

**5、连接到注册中心**

运维平台搭建完后，需要添加目标注册中心，再进行连接。

![](http://img.javastack.cn/18-3-19/89674953.jpg)

**6、作业操作**

我们可以对作业配置进行修改、查看详情、失效、终止、手动触发作业等操作，但终止作业后，需要重启工程才能重新启动作业，控制台不能手动启动。

![](http://img.javastack.cn/18-3-19/94019933.jpg)

好了，这节的 Elastic-Job 运维平台搭建使用指南就到这里了，更多的内容大家可以去摸索，有问题可以去Java技术栈知识星球提问，星主会认真回答每个粉丝的提问。

