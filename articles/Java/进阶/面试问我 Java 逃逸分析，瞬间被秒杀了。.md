记得几年前有一次栈长去面试，问到了这么一个问题:

> Java中的对象都是在堆中分配吗？说明为什么！

当时我被问得一脸蒙逼，瞬间被秒杀得体无完肤，当时我压根就不知道他在考什么知识点，难道对象不是在堆中分配吗？最后就没然后了，回去等通知了。。

这个面试题很经典，我最近也分享到了知识星球上面：

![](http://img.javastack.cn/20190527134034.png)

回答很精彩，大家可以加入一起搞技术，我现在将答案总结一下给大家。

## 什么是逃逸分析？

关于 Java 逃逸分析的定义：

逃逸分析（Escape Analysis）简单来讲就是，Java Hotspot 虚拟机可以分析新创建对象的使用范围，并决定是否在 Java 堆上分配内存的一项技术。

逃逸分析的 JVM 参数如下：

- 开启逃逸分析：-XX:+DoEscapeAnalysis
- 关闭逃逸分析：-XX:-DoEscapeAnalysis
- 显示分析结果：-XX:+PrintEscapeAnalysis

逃逸分析技术在 Java SE 6u23+ 开始支持，并默认设置为启用状态，可以不用额外加这个参数。

## 逃逸分析算法

Java Hotspot 编译器实现下面论文中描述的逃逸算法：

```
[Choi99] Jong-Deok Choi, Manish Gupta, Mauricio Seffano,
          Vugranam C. Sreedhar, Sam Midkiff,
          "Escape Analysis for Java", Procedings of ACM SIGPLAN
          OOPSLA  Conference, November 1, 1999
```

根据 Jong-Deok Choi, Manish Gupta, Mauricio Seffano,Vugranam C. Sreedhar, Sam Midkiff 等大牛在论文《**Escape Analysis for Java**》中描述的算法进行逃逸分析的。

该算法引入了连通图，用连通图来构建对象和对象引用之间的可达性关系，并在次基础上，提出一种组合数据流分析法。

由于算法是上下文相关和流敏感的，并且模拟了对象任意层次的嵌套关系，所以分析精度较高，只是运行时间和内存消耗相对较大。 

## 对象逃逸状态

我们了解了 Java 中的逃逸分析技术，再来了解下一个对象的逃逸状态。

#### 1、全局逃逸（GlobalEscape）

即一个对象的作用范围逃出了当前方法或者当前线程，有以下几种场景：

- 对象是一个静态变量
- 对象是一个已经发生逃逸的对象
- 对象作为当前方法的返回值

#### 2、参数逃逸（ArgEscape）

即一个对象被作为方法参数传递或者被参数引用，但在调用过程中不会发生全局逃逸，这个状态是通过被调方法的字节码确定的。

#### 3、没有逃逸

即方法中的对象没有发生逃逸。

## 逃逸分析优化

针对上面第三点，当一个对象没有逃逸时，可以得到以下几个虚拟机的优化。

**1) 锁消除**

我们知道线程同步锁是非常牺牲性能的，当编译器确定当前对象只有当前线程使用，那么就会移除该对象的同步锁。

例如，StringBuffer 和 Vector 都是用 synchronized 修饰线程安全的，但大部分情况下，它们都只是在当前线程中用到，这样编译器就会优化移除掉这些锁操作。

锁消除的 JVM 参数如下：

- 开启锁消除：-XX:+EliminateLocks
- 关闭锁消除：-XX:-EliminateLocks

锁消除在 JDK8 中都是默认开启的，并且锁消除都要建立在逃逸分析的基础上。

**2) 标量替换**

首先要明白标量和聚合量，基础类型和对象的引用可以理解为标量，它们不能被进一步分解。而能被进一步分解的量就是聚合量，比如：对象。

对象是聚合量，它又可以被进一步分解成标量，将其成员变量分解为分散的变量，这就叫做标量替换。

这样，如果一个对象没有发生逃逸，那压根就不用创建它，只会在栈或者寄存器上创建它用到的成员标量，节省了内存空间，也提升了应用程序性能。

标量替换的 JVM 参数如下：

- 开启标量替换：-XX:+EliminateAllocations
- 关闭标量替换：-XX:-EliminateAllocations
- 显示标量替换详情：-XX:+PrintEliminateAllocations

标量替换同样在 JDK8 中都是默认开启的，并且都要建立在逃逸分析的基础上。

**3) 栈上分配**

当对象没有发生逃逸时，该对象就可以通过标量替换分解成成员标量分配在栈内存中，和方法的生命周期一致，随着栈帧出栈时销毁，减少了 GC 压力，提高了应用程序性能。

## 总结

逃逸分析讲完了，总结了不少时间，我们也应该大概知道逃逸分析是为了优化 JVM 内存和提升程序性能的。

我们知道这点后，在平时开发过程中就要可尽可能的控制变量的作用范围了，变量范围越小越好，让虚拟机尽可能有优化的空间。

简单举一个例子吧，如：

```
return sb;
```

可以改为：

```
return sb.toString();
```

这是一种优化案例，把 StringBuilder 变量控制在了当前方法之内，没有逃出当前方法作用域。

大家还有没有别的优化经验，欢迎分享~

参考资料：

 - https://docs.oracle.com/javase/8/docs/technotes/guides/vm/performance-enhancements-7.html#escapeAnalysis
 - https://blog.csdn.net/rickiyeat/article/details/76802085
 - https://blog.csdn.net/baichoufei90/article/details/85180478
 
关注Java技术栈微信公众号，栈长将继续分享 Java 干货教程，公众号第一时间推送，持续关注。在公众号后台回复：java，获取栈长整理的更多的 Java 教程，都是实战干货，以下仅为部分预览。

- 你真的搞懂 transient 关键字了吗？
- 面试常考：Synchronized 有几种用法？
- Java 11 已发布，String 还能这样玩！
- Java 中的 String 真的是不可变吗？
- sleep( ) 和 wait( ) 的这 5 个区别
- ……

本文原创首发于微信公众号：Java技术栈（id:javastack），转载请原样保留本信息。
 
