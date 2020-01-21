
昨天栈长介绍了《[Java多线程可以分组，还能这样玩！](https://mp.weixin.qq.com/s/dlN1U36ILEckZlO_wR1TzA)》线程分组的妙用。今天，栈长会详细介绍 Java 中的多线程 start() 和 run() 两个方法，Java 老司机请跳过，新手或者对这两个不是很理解的可以继续往下看。

**首先要知道实现多线程最基本的两种方式：**

1、继承 `java.lang.Thread` 类；

2、实现 `java.lang.Runnable`接口；

其中 Thread 类也是实现了 Runnable 接口，而 Runnable 接口定义了唯一的一个 run() 方法，所以基于 Thread 和 Runnable 创建多线程都需要实现 run() 方法，是多线程真正运行的主方法。

```
@FunctionalInterface
public interface Runnable {
    public abstract void run();
}
```

而 start() 方法则是 Thread 类的方法，用来异步启动一个线程，然后主线程立刻返回。该启动的线程不会马上运行，会放到等待队列中等待 CPU 调度，只有线程真正被 CPU 调度时才会调用 run() 方法执行。

所以 start() 方法只是标识线程为就绪状态的一个附加方法，以下 start() 方法的源码，其中 start0() 是一个本地 native 方法。

```
public synchronized void start() {
    if (threadStatus != 0)
        throw new IllegalThreadStateException();

    group.add(this);

    boolean started = false;
    try {
        start0();
        started = true;
    } finally {
        try {
            if (!started) {
                group.threadStartFailed(this);
            }
        } catch (Throwable ignore) {
            /* do nothing. If start0 threw a Throwable then
              it will be passed up the call stack */
        }
    }
}
```

请注意，start() 方法被标识为 synchronized 的，即为了防止被多次启动的一个同步操作。

**那么你会问了，为什么要有两个方法，直接用一个 run() 方法不就行了吗！？** 还真不行，如果直接调用 run() 方法，那就等于调用了一个普通的同步方法，达不到多线程运行的异步执行，来看下面的例子。

```
/**
 * 微信公众号：Java技术栈
 */
public static void main(String[] args) {
	Thread thread = new Thread(() -> {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Java技术栈");
	});

	long start = System.currentTimeMillis();
	thread.start();
	System.out.println(System.currentTimeMillis() - start);

	start = System.currentTimeMillis();
	thread.run();
	System.out.println(System.currentTimeMillis() - start);
}

```

程序输出：

```
0
Java技术栈
3000
Java技术栈
```

从程序输出结果可以看出，启动 start 方法前后只用了 0 毫秒，而启动 run 方法则阻塞了 3000 毫秒等程序执行完再继续执行，这就是同步与异步的一个最重要的区别。

看完这篇，你应该对 start 和 run 方法有了一个大概的掌握吧，再也不怕面试官问你这两个的区别了吧！

动手转发给更多的朋友吧！

---

更多 Java 多线程技术文章请在Java技术栈微信公众号后台回复关键字：多线程。

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "多线程" 可获取更多，转载请原样保留本信息。
