前几天在写《[HashMap 和 Hashtable 的 6 个区别](https://mp.weixin.qq.com/s/EGqKMndXiJDIMeRQwxBd_w)》这篇文章的时候，差点把 Hashtable 写成了 HashTable，后来看源码证实了是：Hashtable，小写的 "t"able，不符合驼峰命名规则。

> 什么是驼峰命名规则，来看下面的关键字：
> 
> - HashMap
> - ArrayList
> - ConcurrentHashMap
> 
> 简单来说就是，标识符的每个单词首字母必须大写，看起来像是驼峰的形状。

当时就很好奇，Hashtable 为什么不是 HashTable 呢？作为一名初级的 Java 程序员都应该知道的基本的驼峰命名规则，为什么 JDK 代码里面还有这种不规范的命名呢？

我想大家应该都比较好奇，我特意去查了下资料，没有查到官方的说法，不过在 stackoverflow 上看到了同样的这个问题。

原贴如下：

> https://stackoverflow.com/questions/12506706/why-is-the-t-in-hash-tablehashtable-in-java-not-capitalized

![](http://qianniu.javastack.cn/18-12-6/56236009.jpg)

最佳答案是：

> Hashtable was created in Java v1. The consistent naming conventions for collections were established later, in Java2, when the other classes were published as part of the brand new Java Collection Framework.
> 
> Which btw made Hashtable obsolete, so it should not be used in new code.
> 
> Hope that helps.

意思就是：

> Hashtable 是在 Java 1.0 的时候创建的，而集合的统一规范命名是在后来的 Java 2 开始约定的，当时其他一部分集合类的发布构成了新的集合框架。
> 
> 顺便说一下，这样就使得 Hashtable 过时了，所以不应该在新代码中继续使用它。

栈长看了下，Hashtable 确实是 JDK1.0 添加的，最早的一个集合类，这样也说得过去。那为什么不在后面的 JDK 版本中修复它呢？可能是为了考虑兼容使用 JDK 老版本的系统吧。所以就将错就错封存在了 JDK，直到现在 JDK 11 了也还没有修复或者考虑删除它。

另外，关于《[HashMap 和 Hashtable 的 6 个区别](https://mp.weixin.qq.com/s/EGqKMndXiJDIMeRQwxBd_w)》，有人留言说可以使用 currenthashtable。

![](http://qianniu.javastack.cn/18-12-6/79250944.jpg)

栈长又去证实了下，没有 currenthashtable 和 concurrenthashtable 这个类，所有 concurrent* 开头的并发类和接口都在这里了。

![](http://qianniu.javastack.cn/18-12-6/49796932.jpg)

好了，关于 Hashtable 的迷惑就此全都解开了。

所有 Java JVM、多线程、集合、新特性等系列文章请在微信公众号 “Java技术栈” 后台回复关键字：java，后续也会不断更新。

有收获？转发给更多的人吧！

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "java" 可获取更多，转载请原样保留本信息。