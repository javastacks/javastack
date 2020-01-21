最近发现 Spring Boot 本地不能 Debug 调试了，原来 Spring Boot 升级后，对应插件的命令参数都变了，故本文做一个升级。

**背景：**

Spring Boot 项目在使用 Spring Boot Maven 插件执行启动命令 `spring-boot:run` 的时候，如果设置的断点进不去，要进行以下的设置。

**官方解决方案：**

> By default, the run goal runs your application in a forked process. If you need to debug it, you should add the necessary JVM arguments to enable remote debugging. The following configuration suspend the process until a debugger has joined on port 5005:

直接看怎么做吧！

## 1、添加 JVM 参数

在插件 `spring-boot-maven-plugin` 里面加上 `jvmArguments` 配置。

```
<project>
  ...
  <build>
    ...
    <plugins>
      ...
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <version>2.2.0.RELEASE</version>
        <configuration>
          <jvmArguments>
            -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005
          </jvmArguments>
        </configuration>
        ...
      </plugin>
      ...
    </plugins>
    ...
  </build>
  ...
</project>
```

或者在命令行指定：

```
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
```

![](http://img.javastack.cn/20191030171505.png)

最新配置可以参考官方说明：

> https://docs.spring.io/spring-boot/docs/current/maven-plugin/examples/run-debug.html

## 2、添加一个Remote

在开发工具里面新增一个 Remote 配置：

![](http://img.javastack.cn/20191030170633.png)

只需要确定 Host、Port 参数即可。

- Host：地址

localhost：本地启动地址；

- Port：端口

5005：上面命令行指定的端口；

## 3、开始调试

先启动加了 `jvmArguments` 参数的 Spring Boot 项目：

![](http://img.javastack.cn/20191030174448.png)

程序停在监听端口：5005，再 debug 启动Remote：

![](http://img.javastack.cn/20191030174710.png)

再回到项目，开始启动输出日志，然后就可以进行断点调试了。

这就是远程调试了，也能帮你 debug 远程 Spring Boot 应用，但在本地调试要操作两次，略显麻烦。

未完，栈长将陆续分享 Spring Boot 最新技术教程，现在已经写了一堆存货了，关注微信公众号 "Java技术栈" ，公众号第一时间推送！

