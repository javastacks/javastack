Java 13 都快要来了，12必须跟栈长学起！

[Java 13 即将发布，新特性必须抢先看！](https://mp.weixin.qq.com/s/Gg6KKz7vhDRpzeMR8CG4DA)

栈长之前在Java技术栈微信公众号分享过《[Java 11 已发布，String 还能这样玩！](https://mp.weixin.qq.com/s/OZKvkG5OhMXf4lTklZQzGQ)》这篇文章，介绍了 Java 11 的 String 新玩法，让大家耳目一新。

Java 12 已经发布数月了：[Java 12 正式发布，8大新特性！](https://mp.weixin.qq.com/s/IXOoSTq6qi4ZH63gCaKa3A)，今天我再来分享下在 Java 12 中关于 String 的三个骚操作，绝逼有用。

更多关于 Java 的资讯、干货教程、以及好消息，请关注微信公众号：Java技术栈，第一时间推送。

坐稳了，准备起飞！

## 1、transform

transform：即字符串转换，来看下 transform 的实现源码：

```
public <R> R transform(Function<? super String, ? extends R> f) {
    return f.apply(this);
}
```

传入一个函数式接口 Function，接受一个值，返回一个值，参考：[Java 8 新特性之函数式接口](https://mp.weixin.qq.com/s/nC3f17zZlXC08XNJWA-FBw)。

废话少说，直接上手就是干：

```
private static void testTransform() {
    System.out.println("======test java 12 transform======");
    List<String> list1 = List.of("Java", " Python", " C++ ");
    List<String> list2 = new ArrayList<>();

    list1.forEach(element ->
            list2.add(element.transform(String::strip)
                    .transform(String::toUpperCase)
                    .transform((e) -> "Hi," + e))
    );

    list2.forEach(System.out::println);
}
```

结果输出：

```
======test java 12 transform======
Hi,JAVA
Hi,PYTHON
Hi,C++
```

示例是对一个字符串连续转换了三遍，代码很简单，大家都能领会的。

## 2、indent

直接看示例吧：

```
private static void testIndent() {
    System.out.println("======test java 12 indent======");
    String result = "Java\n Python\nC++".indent(3);
    System.out.println(result);
}
```

结果输出：

```
======test java 12 indent======
   Java
    Python
   C++
```

换行符 \n 后前缩进 N 个空格，为 0 或负数不缩进。

以下是 indent 的核心源码：

```
private String indent(int n, boolean removeBlanks) {
    Stream<String> stream = removeBlanks ? lines(Integer.MAX_VALUE, Integer.MAX_VALUE)
                                         : lines();
    if (n > 0) {
        final String spaces = " ".repeat(n);
        stream = stream.map(s -> spaces + s);
    } else if (n == Integer.MIN_VALUE) {
        stream = stream.map(s -> s.stripLeading());
    } else if (n < 0) {
        stream = stream.map(s -> s.substring(Math.min(-n, s.indexOfNonWhitespace())));
    }
    return stream.collect(Collectors.joining("\n", "", "\n"));
}
```

其实就是调用了 lines() 方法来创建一个 Stream，然后再往前拼接指定数量的空格。

参考：《[Java 11 已发布，String 还能这样玩！](https://mp.weixin.qq.com/s/OZKvkG5OhMXf4lTklZQzGQ)》这篇文章对 lines() 的介绍。

## 3、describeConstable

```
private static void testDescribeConstable() {
    System.out.println("======test java 12 describeConstable======");
    String name = "Java技术栈";
    Optional<String> optional = name.describeConstable();
    System.out.println(optional.get());
}
```

结果输出：

```
======test java 12 describeConstable======
Java技术栈
```

Java 12, String 实现了 Constable 接口：

> java.lang.constant.Constable

这个接口就有一个方法，源码如下：

```
public interface Constable {

    Optional<? extends ConstantDesc> describeConstable();
    
}
```

Java 12 String 的实现源码：

```
@Override
public Optional<String> describeConstable() {
    return Optional.of(this);
}
```

很简单，其实就是调用 Optional.of 方法返回一个 Optional 类型，Optional不懂的可以参考这篇文章：[Java 8 新特性之 Optional](https://mp.weixin.qq.com/s/uXw4eTZqLfj871FlciPh6Q)。

好了，今天的分享就到这，收藏转发一下吧，多学习了解，日后必定有用！

历史 Java 新特性干货分享：

![](http://img.javastack.cn/20190613135450.png)
![](http://img.javastack.cn/20190613135537.png)

获取上面这份 Java 8~12 系列新特性干货文章，请在微信搜索关注微信公众号：Java技术栈，在公众号后台回复：java。

Java 12 新特性继续更新中……

本文原创首发于微信公众号：Java技术栈（id:javastack），转载请原样保留本信息。
 