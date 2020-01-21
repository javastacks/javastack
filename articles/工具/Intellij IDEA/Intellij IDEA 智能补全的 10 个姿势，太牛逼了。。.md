
一年多前，栈长那时候刚从 Eclipse 转型 IDEA 成功，前面转了好多次，都是失败史，都是泪。。后面我就在微信公众号 "Java技术栈" 写了这篇文章：[Intellij IDEA非常6的10个姿势！](https://mp.weixin.qq.com/s/NNPy3BRW851z2mjo6OPYXQ)，感叹 IDEA 智能提示的强大，也对 IDEA 略表尊敬。

关于智能提示，这次我再分享一篇关于自动补全方面的。

首先来看一下下面这张图，在方法里面有效位置后面输入一个.，最后面会列表所有可用的自动补全的关键字，这也就是今天要分享的内容。

![](http://img.javastack.cn/20190520171322.png)

下面再介绍几个它们的用法，其实很简单，跟上次一样，这次我同样还是录了动图，这样看得更直观，看起来更牛逼。。

## 1、快速打印输出

除了用 sout 开头快速生成，还能在后面快速生成。

![](http://img.javastack.cn/sout.gif)

## 2、快速定义局部变量

在字符串或者数字……后面输入 .var，回车，IDEA会自动推断并快速定义一个局部变量，不过它是 final 类型的。

![](http://img.javastack.cn/var.gif)

## 3、快速定义成员变量

在值后面输入.field，可以快速定义一个成员变量，如果当前方法是静态的，那生成的变量也是静态的。

![](http://img.javastack.cn/field.gif)

## 4、快速格式化字符串

在字符串后面输入.format，回车，IDEA会自动生成 String.format...语句，牛逼吧！

![](http://img.javastack.cn/format.gif)

## 5、快速判断（非）空

```
if (xx != null)
if (xx == null)
```

像上面这种判断空/非空的情况非常多吧，其实可以快速生成 if 判断语句块，非空：.notnull 或者 .nn，空：.null。

![](http://img.javastack.cn/null.gif)

## 6、快速取反判断

输入 .not 可以让布尔值快速取反，再输入 .if 可快速生成 if 判断语句块。

![](http://img.javastack.cn/notif.gif)

## 7、快速遍历集合

下面是几种 for 循环语句的快速生成演示，.for, .fori, .forr 都可以满足你的要求。

![](http://img.javastack.cn/for.gif)

## 8、快速返回值

在值后面输入.return，可以让当前值快速返回。

![](http://img.javastack.cn/return.gif)

## 9、快速生成同步锁

在对象后面输入.synchronized，可以快速生成该对象的同步锁语句块。

![](http://img.javastack.cn/synchronized.gif)

## 10、快速生成JDK8语句

下面演示的是快速生成 Lambda 以及 Optional 语句。

![](http://img.javastack.cn/jdk8.gif)

好了，今天栈长就介绍了 Intellij IDEA 如何更使用快速补全功能、涨姿势了吧。

关注Java技术栈微信公众号，栈长将继续分享 Intellij IDEA 的实战教程，公众号第一时间推送，持续关注。在公众号后台回复：idea，获取栈长整理的更多的 Intellij IDEA 教程，都是实战干货，以下仅为部分预览。

- Intellij IDEA 最常用配置详细图解
- Intellij IDEA 非常6的10个姿势
- Intellij IDEA 所有乱码解决方案
- Intellij IDEA 阅读源码的4个绝技
- Intellij IDEA Debug调试技巧
- ……

如果你喜欢 IDEA, 可加入我们的知识星球《[Java技术栈](https://mp.weixin.qq.com/s/tsSXINpS0Jkw0pKz5FiJhw)》，我会经常在上面分享 IDEA 的使用小技巧，对 IDEA 有什么不懂的也可以在上面向我和大家提问。

本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "idea" 可获取更多教程，转载请原样保留本信息。