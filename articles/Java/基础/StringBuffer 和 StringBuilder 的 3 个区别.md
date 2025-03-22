
StringBuffer 和 StringBuilder 它们都是可变的字符串，不过它们之间的区别是 Java 初中级面试出现几率十分高的一道题。这么简单的一道题，栈长在最近的面试过程中，却经常遇到很多求职者说反，搞不清使用场景的情况。

今天，栈长我带大家彻底搞懂 StringBuffer 和 StringBuilder 的几个区别和它们的应用场景，如果你是大牛，请略过，如果你是菜鸟，或者对这两个不是很清楚，可以学习一下，也可以为年底的面试加油冲刺。

先看看 StringBuffer 和 StringBuilder 的类结构吧：

![](http://img.javastack.cn/18-12-29/23490736.jpg)

其实很简单，就是继承了一个抽象的字符串父类：`AbstractStringBuilder`。下面我们再来看看它们的三个区别。

## 区别1：线程安全

StringBuffer：线程安全，StringBuilder：线程不安全。因为 StringBuffer 的所有公开方法都是 synchronized 修饰的，而 StringBuilder 并没有 synchronized 修饰。

**StringBuffer 代码片段：**

```
@Override
public synchronized StringBuffer append(String str) {
    toStringCache = null;
    super.append(str);
    return this;
}
```

## 区别2：缓冲区

**StringBuffer 代码片段：**

```
private transient char[] toStringCache;

@Override
public synchronized String toString() {
    if (toStringCache == null) {
        toStringCache = Arrays.copyOfRange(value, 0, count);
    }
    return new String(toStringCache, true);
}
```

**StringBuilder 代码片段：**

```
@Override
public String toString() {
    // Create a copy, don't share the array
    return new String(value, 0, count);
}
```

可以看出，StringBuffer 每次获取 toString 都会直接使用缓存区的 toStringCache 值来构造一个字符串。

而 StringBuilder 则每次都需要复制一次字符数组，再构造一个字符串。

所以，缓存冲这也是对 StringBuffer 的一个优化吧，不过 StringBuffer 的这个toString 方法仍然是同步的。

## 区别3：性能

既然 StringBuffer 是线程安全的，它的所有公开方法都是同步的，StringBuilder 是没有对方法加锁同步的，所以毫无疑问，StringBuilder 的性能要远大于 StringBuffer。

## 总结

所以，StringBuffer 适用于用在多线程操作同一个 StringBuffer 的场景，如果是单线程场合 StringBuilder 更适合。

以此送给正在面试或者即将去面试的 Java 程序猿们，如果对你有帮助，也欢迎分享给身边的朋友们，让大家少走弯路。

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "java" 可获取更多，转载请原样保留本信息。
