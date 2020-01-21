
前面的文章，栈长和大家分享过多线程创建的3种方式《[实现 Java 多线程的 3 种方式](https://mp.weixin.qq.com/s/39Sb60J0DC2mNN6DYvpFtg)》。

但如果线程很多的情况下，你知道如何对它们进行分组吗？

和 Dubbo 的服务分组一样，Java 可以对相同性质的线程进行分组。

来看下线程类 Thread 的所有构造方法。

![](http://qianniu.javastack.cn/18-6-3/9061041.jpg)

如图所示，带有 ThreadGroup 的所有线程构造方法都可以定义线程组的。

线程组使用 java.lang.ThreadGroup 类定义，它有两个构造方法，第二个构造方法允许线程组有父类线程组，也就是说一个线程组可以多个子线程组。

```
java.lang.ThreadGroup#ThreadGroup(java.lang.String)
java.lang.ThreadGroup#ThreadGroup(java.lang.ThreadGroup, java.lang.String)
```

![](http://qianniu.javastack.cn/18-6-4/38415499.jpg)

线程组中比较有用的几个方法。
> // 获取当前线程组内的运行线程数
> java.lang.ThreadGroup#activeCount
> 
> // 中断线程组内的所有线程
> java.lang.ThreadGroup#interrupt
> 
> // 使用 System.out 打印出所有线程信息
> java.lang.ThreadGroup#list()

我们来简单使用下线程组吧！

```
/**
 * 微信公众号：Java技术栈
 */
public static void main(String[] args) {
	Runnable runnable = () -> {
		System.out.println("Java技术栈线程线程组名称：" + Thread.currentThread().getThreadGroup());
		System.out.println("Java技术栈线程线程名称：" + Thread.currentThread().getName());
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	};

	ThreadGroup userGroup = new ThreadGroup("user");
    userGroup.setMaxPriority(Thread.MIN_PRIORITY);
    
	Thread userTask1 = new Thread(userGroup, runnable, "user-task1");
	Thread userTask2 = new Thread(userGroup, runnable, "user-task2");

	userTask1.start();
	userTask2.start();

	System.out.println("Java技术栈线程线程组活跃线程数：" + userGroup.activeCount());
	userGroup.list();

}
```

程序输出以下结果。

```
Java技术栈线程线程组名称：java.lang.ThreadGroup[name=user,maxpri=1]
Java技术栈线程线程名称：user-task1
Java技术栈线程线程组活跃线程数：2
Java技术栈线程线程组名称：java.lang.ThreadGroup[name=user,maxpri=1]
Java技术栈线程线程名称：user-task2
java.lang.ThreadGroup[name=user,maxpri=1]
    Thread[user-task1,1,user]
    Thread[user-task2,1,user]
```

根据示例代码和程序输出结果应该对线程组有了一个大概的了解吧。

线程组还能统一设置组内所有线程的最高优先级，线程单独设置的优先级不会高于线程组设置的最大优先级。

另外，线程组中有一个 stop 方法用来终止组内所有的线程，但这个方法和 Thread 中的 stop 方法一样会带来很多问题，所以它们两个现在都已经被废弃了，官方也是不建议使用了，建议使用线程中断功能进行优雅终止线程。

---

更多 Java 多线程技术文章请在微信公众号后台回复关键字：多线程。

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "java" 可获取更多，转载请原样保留本信息。