## 概念

我们知道 start() 方法是启动线程，让线程变成就绪状态等待 CPU 调度后执行。

那 yield() 方法是干什么用的呢？来看下源码。

```
/**
 * A hint to the scheduler that the current thread is willing to yield
 * its current use of a processor. The scheduler is free to ignore this
 * hint.
 *
 * <p> Yield is a heuristic attempt to improve relative progression
 * between threads that would otherwise over-utilise a CPU. Its use
 * should be combined with detailed profiling and benchmarking to
 * ensure that it actually has the desired effect.
 *
 * <p> It is rarely appropriate to use this method. It may be useful
 * for debugging or testing purposes, where it may help to reproduce
 * bugs due to race conditions. It may also be useful when designing
 * concurrency control constructs such as the ones in the
 * {@link java.util.concurrent.locks} package.
 */
public static native void yield();
```

yield 即 "谦让"，也是 Thread 类的方法。它让掉当前线程 CPU 的时间片，使正在运行中的线程重新变成就绪状态，并重新竞争 CPU 的调度权。它可能会获取到，也有可能被其他线程获取到。

## 实战

下面是一个使用示例。

```
/**
 * 微信公众号：Java技术栈
 */
public static void main(String[] args) {
	Runnable runnable = () -> {
		for (int i = 0; i <= 100; i++) {
			System.out.println(Thread.currentThread().getName() + "-----" + i);
			if (i % 20 == 0) {
				Thread.yield();
			}
		}
	};
	new Thread(runnable, "栈长").start();
    new Thread(runnable, "小蜜").start();
}
```

这个示例每当执行完 20 个之后就让出 CPU，每次谦让后就会马上获取到调度权继续执行。

**运行以上程序，可以有以下两种结果。**

结果1：栈长让出了 CPU 资源，小蜜成功上位。

```
栈长-----29
栈长-----30
小蜜-----26
栈长-----31
```

结果2：栈长让出了 CPU 资源，栈长继续运行。

```
栈长-----28
栈长-----29
栈长-----30
栈长-----31
```

而如果我们把两个线程加上线程优先级，那输出的结果又不一样。

```
thread1.setPriority(Thread.MIN_PRIORITY);
thread2.setPriority(Thread.MAX_PRIORITY);
```

因为给小蜜加了最高优先权，栈长加了最低优先权，即使栈长先启动，那小蜜还是有很大的概率比栈长先会输出完的，大家可以试一下。

## yield 和 sleep 的异同

1）yield, sleep 都能暂停当前线程，sleep 可以指定具体休眠的时间，而 yield 则依赖 CPU 的时间片划分。

2）yield, sleep 两个在暂停过程中，如已经持有锁，则都不会释放锁资源。

3）yield 不能被中断，而 sleep 则可以接受中断。

## 总结 

栈长没用过 yield，感觉没什么鸟用。

如果一定要用它的话，一句话解释就是：yield 方法可以很好的控制多线程，如执行某项复杂的任务时，如果担心占用资源过多，可以在完成某个重要的工作后使用 yield 方法让掉当前 CPU 的调度权，等下次获取到再继续执行，这样不但能完成自己的重要工作，也能给其他线程一些运行的机会，避免一个线程长时间占有 CPU 资源。

更多多线程教程请在Java技术栈微信公众号后台回复关键字：多线程。

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "多线程" 可获取更多，转载请原样保留本信息。

