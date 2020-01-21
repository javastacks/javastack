线程休眠是 Java 开发经常会用到的一个手段，就是让当前线程睡一会儿，睡醒之后再继续运行。

咱大多数程序员，多线程虽然学得不好，但线程休眠，无人不知，无人不晓，也都会用，不就是用 Thread.sleep 方法嘛！而且还将它用到那么绝，之前不是还有人写过休眠排序算法和休眠取时间的算法，再来回味下这么脑洞大开的两个算法：

- [休眠排序算法](https://mp.weixin.qq.com/s/Or3q3souk1GGVNB2qvEY2Q)
- [休眠取时间算法](https://mp.weixin.qq.com/s/Or3q3souk1GGVNB2qvEY2Q)

笑过之后，当然，这不是咋今天要讲的主题，栈长今天要讲的是如何更优雅的让线程休眠。

**来看下面的休眠程序：**

```
Thread.sleep(87000000);
```

你知道休眠多久吗？

醉了……

**再把上面的稍微改装下：**

```
Thread.sleep(24 * 60 * 60 * 1000 + 10 * 60 * 1000);
```

现在你估计大概能知道休眠多久了，但还是很茫然，很无助，不写注释，谁知道休眠多久？单位还是毫秒。。

其实就是休眠 24 小时 10 分钟，何必整这么麻烦呢？

**优雅又简单的方式来了：**

```
TimeUnit.DAYS.sleep(1);
TimeUnit.MINUTES.sleep(10);

或者 

TimeUnit.HOURS.sleep(24);
TimeUnit.MINUTES.sleep(10);
```

使用 `java.util.concurrent.TimeUnit` 类就可以优雅的搞定，不需要过多的单位运算及修饰，是不是很优雅，很简单？

上面演示了 HOURS、MINUTES，还有更多的枚举可以用。

**来看下 TimeUnit 的详细方法和枚举值：**

![](http://img.javastack.cn/TimeUnit.png)

其实 TimeUnit 还可以用来做时间单位转换，TimeUnit 提供了各种丰富的时间单位转换方法。

我们随便来看一个枚举值：

```
MINUTES {
    public long toNanos(long d)   { return x(d, C4/C0, MAX/(C4/C0)); }
    public long toMicros(long d)  { return x(d, C4/C1, MAX/(C4/C1)); }
    public long toMillis(long d)  { return x(d, C4/C2, MAX/(C4/C2)); }
    public long toSeconds(long d) { return x(d, C4/C3, MAX/(C4/C3)); }
    public long toMinutes(long d) { return d; }
    public long toHours(long d)   { return d/(C5/C4); }
    public long toDays(long d)    { return d/(C6/C4); }
    public long convert(long d, TimeUnit u) { return u.toMinutes(d); }
    int excessNanos(long d, long m) { return 0; }
}
```

可以很方便的把分钟转换成各种单位的值。

**再来看一下 TimeUnit 休眠的原理：**

```
public void sleep(long timeout) throws InterruptedException {
    if (timeout > 0) {
        long ms = toMillis(timeout);
        int ns = excessNanos(timeout, ms);
        Thread.sleep(ms, ns);
    }
}
```

其实 TimeUnit 的休眠就是调用了 Thread.sleep 休眠方法，哈哈，只是把 Thread.sleep 封装了，这样，用起来很简单方便，也提高了可读性。

大家也可以关注微信公众号：Java技术栈，栈长将继续分享更多 Java 多线程系列干货，在公众号后台回复：多线程，可以获取栈长已经整理好的历史 Java 多线程系列干货文章。

觉得有用，转发分享下朋友圈给更多的人看吧，另外，给个好看，谢谢老板~

> 本文原创首发于微信公众号：Java技术栈（id:javastack），转载请原样保留本信息。
