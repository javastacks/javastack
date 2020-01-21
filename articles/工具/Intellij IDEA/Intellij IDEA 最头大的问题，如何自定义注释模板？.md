想栈长我当初从 Eclipse 转用 IDEA 真是纠结，放弃然后尝试了N次，不过现在已经算是转型成功了，可以完全脱离 Eclipse 撸码了，虽然说我现在真的撸得非常少了。。

说到 IDEA 的痛点，我觉得注释配置就不是很人性化。Eclipse 可以很方便的设置和导入公共的自定义注释模板、格式化模板，但在 IDEA 中确实有点蹩脚，配置起来甚至有点高级，还需要用到 Grovvy 脚本。。

废话不说了，来看下如何解决 Intellij IDEA 最头大的问题：自定义注释模板。。

**IDEA 中有以下两种配置模板。**

- File and Code Templates
- Live Templates

下面介绍下这两种的使用方法。

## File and Code Templates

File and Code Templates 用来配置文件和代码模板，即文件在创建的时候自动会按文件模板生成代码注释。

下面来演示一下如何自动生成 Java 类注释。

依次打开下面这个菜单：

> Editor > File and Code Templates > files

![](http://img.javastack.cn/18-10-23/68361689.jpg)

里面自带了很多文件的注释模板，只不过要自己设置，这个 class 里面的内容就是自带的，里面有一个 `#parse` 的代码。

```
#parse("File Header.java")
```

这个 File Header.java 哪里来的呢？

依次打开下面这个菜单：

> Editor > File and Code Templates > files > Includes

![](http://img.javastack.cn/18-10-23/41018185.jpg)

其实这个 Includes 就是前面 Files 模板里面 parse 引用的片段而已。File Header 这里默认是空的，然后在这个片段里面编辑模板即可，支持一些预定义的变量，如：DATE、TIME、USER 等，上图给的是配置示例。

![](http://img.javastack.cn/18-10-23/9061457.jpg)

从上图也可以看到，这里的模板配置用的是 Apache Velocity 模板语言，其中的 #parse 就是 Velocity 模板语言的关键字。

所以，这里的 Java 类自动生成类注释就配置完那个 File Header 就行了，配置完后，随便创建一个类就会自动带上注释。

其他的文件配置方式不再撰述。

## Live Templates

Live Templates 用来配置动态模板，可以在指定位置使用缩写字母自动生成注释。下面演示两个创建示例，手动生成类注释、方法注释。

上面演示的是创建类的时候自动添加注释，如果想手动添加类注释，怎么加？

首先打开 Live Templates 菜单：

> Editor > Live Tempaltes

![](http://img.javastack.cn/18-10-23/56817313.jpg)

上面截图的已经配置好了 cc 和 mc，代表我输入 cc 和 mc 后会自动带出来类注释、方法注释。

右上角有个 + 号，点击选择 Live Template 添加一个动态模板，依次配置下面的内容。

- **abbreviation：** 模板缩写
- **Description：** 模板描述
- **Template Text：** 模板内容

Template Text 里面美元符号包起来的是变量，需要点击 `Edit variable` 按钮来设置这些变量。

![](http://img.javastack.cn/18-10-23/85720861.jpg)

另外，就是设置模板使用的场景，如以下所示，点击 Define 链接，设置类模板只能在 Java 语言下使用。

![](http://img.javastack.cn/18-10-23/35553716.jpg)

方法注释和类注释配置一致，配置示例如下：

![](http://img.javastack.cn/18-10-23/59877084.jpg)

![](http://img.javastack.cn/18-10-23/24832650.jpg)

唯一不同的是，方法里面的 params 变量需要用以下 Groovy 表达式来配置，比较麻烦。。

```
groovyScript("def result=''; def params=\"${_1}\".replaceAll('[\\\\[|\\\\]|\\\\s]', '').split(',').toList(); for(i = 0; i < params.size(); i++) {result+=' * @param ' + params[i] + ((i < params.size() - 1) ? '\\n' : '')}; return result", methodParameters())
```

而且方法注释这里还有一个坑，使用 mc 时不能在方法上面，要在方法里面使用，不然 @param 带出来的是 null, 而不是具体的参数名称。

好了，今天就介绍了 Intellij IDEA 如何配置文件代码模板、类和方法注释，更多的就去研究这两个菜单吧。

关注Java技术栈微信公众号，栈长将继续分享 Intellij IDEA 的实战教程，公众号第一时间推送，持续关注。在公众号后台回复：idea，获取栈长整理的更多的 Intellij IDEA 教程，都是实战干货，以下仅为部分预览。

- Intellij IDEA 最常用配置详细图解
- Intellij IDEA 非常6的 10 个姿势
- Intellij IDEA 所有乱码解决方案
- Intellij IDEA 阅读源码的 4 个绝技
- Intellij IDEA Debug 调试技巧
- ……

如果你喜欢 IDEA, 可加入我们的知识星球《[Java技术栈](https://mp.weixin.qq.com/s/iqCLAduVzDqt19L6D4FCUQ)》，我会经常在上面分享 IDEA 的使用小技巧，对 IDEA 有什么不懂的也可以在上面向我和大家提问。

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "idea" 可获取更多教程，转载请原样保留本信息。