
前阵子，Oracle 发布了一个黑科技 "GraalVM"，号称是一个全新的通用全栈虚拟机，并具有高性能、跨语言交互等逆天特性，真有这么神奇？

### GraalVM 简介

GraalVM 是一个跨语言的通用虚拟机，不仅支持了 Java、Scala、Groovy、Kotlin 等基于 JVM 的语言，以及 C、C++ 等基于 LLVM 的语言，还支持其他像 JavaScript、Ruby、Python 和 R 语言等。

**GraalVM 有以下几个特性。**

- 更加高效快速的运行代码
- 能与大多数编程语言直接交互
- 使用 Graal SDK 嵌入多语言
- 创建预编译的原生镜像
- 提供一系列工具来监视、调试和配置所有代码

> 官网：http://www.graalvm.org/

### GraalVM 有什么用？

#### 1、支持多种语言组合编程

来看下面这段代码，来自官网。

```
const express = require('express');
const app = express();
app.listen(3000);
app.get('/', function(req, res) {
  var text = 'Hello World!';
  const BigInteger = Java.type(
    'java.math.BigInteger');
  text += BigInteger.valueOf(2)
    .pow(100).toString(16);
  text += Polyglot.eval(
    'R', 'runif(100)')[0];
  res.send(text);
})
```

它同时使用了 Node.js、Java、R 三种语言，是不是很奇葩？

GraalVM 消除了各种编程语言之间的隔离性，那这种多编程语言结合使用会不会有性能影响？官方的说明是：零开销的互操作，这样，我们就可以为应用程序选择最佳的编程语言组合。

#### 2、原生镜像加速

来看这段代码，同样来自官网。

```
$ javac HelloWorld.java
$ time java HelloWorld
user 0.070s
$ native-image HelloWorld
$ time ./helloworld
user 0.005s
```

GraalVM 可以预编译成原生镜像，从而极大提速了启动时间，并能减少 JVM 应用的内存占用。

#### 4、可嵌入式运行环境

GraalVM 可以被嵌入到各种应用程序中，既可以独立运行，也可以在已经内置好的 OpenJDK、Node.js、Oracle、MySQL 等环境中运行。

结合上面的特性，我们来看下 GraalVM 的架构图。

![](http://img.javastack.cn/18-7-25/26341155.jpg)


### GraalVM 版本介绍

如下图所示，GraalVM 提供了社区版和企业版两个版本。

![](http://img.javastack.cn/18-7-25/16994384.jpg)

从特性来看，上述所说的高性能、内存优化貌似都在企业版中？？企业版或者可能只是在社区版上又增加了额外的提升吧！

> 社区版下载：github.com/oracle/graal/releases

### 总结

GraalVM 这玩意看起来很不错，可以说这就是一个全栈开发平台，不仅支持主流编程语言，还能组合在一起编程，根据不同任务来选择最佳的语言。另外。它还能提高更高的执行效率，以及占用更少的内存。

至于应用场景，是否可应用于生产，现在还真不好说。这么一个变态级的产品，我们期待它有更好的发展吧。

@ 程序猿：你们觉得 GraalVM 前景如何，或者有什么实际用途？欢迎留言！

