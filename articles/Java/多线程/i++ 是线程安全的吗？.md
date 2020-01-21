
![](http://img.javastack.cn/18-6-8/12258861.jpg)

**i++ 是线程安全的吗？**

相信很多中高级的 Java 面试者都遇到过这个问题，很多对这个不是很清楚的肯定是一脸蒙逼。内心肯定还在质疑，i++ 居然还有线程安全问题？只能说自己了解的不够多，自己的水平有限。

先来看下面的示例来验证下 i++ 到底是不是线程安全的。

**1000个线程，每个线程对共享变量 count 进行 1000 次 ++ 操作。**

```
static int count = 0;

static CountDownLatch cdl = new CountDownLatch(1000);

/**
* 微信公众号：Java技术栈
*/
public static void main(String[] args) throws Exception {
	CountRunnable countRunnable = new CountRunnable();
	for (int i = 0; i < 1000; i++) {
		new Thread(countRunnable).start();
	}
	cdl.await();
	System.out.println(count);
}

static class CountRunnable implements Runnable {

	private void count() {
		for (int i = 0; i < 1000; i++) {
			count++;
		}
	}

	@Override
	public void run() {
		count();
		cdl.countDown();
	}

}
```

上面的例子我们期望的结果应该是 1000000，但运行 N 遍，你会发现总是不为 1000000，至少你现在知道了 i++ 操作它不是线程安全的了。

先来看 JMM 模型中对共享变量的读写原理吧。

![](http://img.javastack.cn/18-6-8/60972585.jpg)

每个线程都有自己的工作内存，每个线程需要对共享变量操作时必须先把共享变量从主内存 load 到自己的工作内存，等完成对共享变量的操作时再 save 到主内存。

问题就出在这了，如果一个线程运算完后还没刷到主内存，此时这个共享变量的值被另外一个线程从主内存读取到了，这个时候读取的数据就是脏数据了，它会覆盖其他线程计算完的值。。。

**这也是经典的内存不可见问题，那么把 count 加上 volatile 让内存可见是否能解决这个问题呢？** 答案是：不能。因为 volatile 只能保证可见性，不能保证原子性。多个线程同时读取这个共享变量的值，就算保证其他线程修改的可见性，也不能保证线程之间读取到同样的值然后相互覆盖对方的值的情况。

关于多线程的几种关键概念请翻阅《[多线程之原子性、可见性、有序性详解](https://mp.weixin.qq.com/s/DWaxnysIQ8NSWN1NME_HvA)》这篇文章。

#### 解决方案

说了这么多，对于 i++ 这种线程不安全问题有没有其他解决方案呢？当然有，请参考以下几种解决方案。

1、对 i++ 操作的方法加同步锁，同时只能有一个线程执行 i++ 操作；

2、使用支持原子性操作的类，如 `java.util.concurrent.atomic.AtomicInteger`，它使用的是 CAS 算法，效率优于第 1 种；

如果对你有帮助，点个赞分享下给个鼓励吧！