
之前写过一篇 Java 线程池的使用介绍文章《[线程池全面解析](https://mp.weixin.qq.com/s/L2KKLlmOKJUQKfLdFa-1FA)》，全面介绍了什么是线程池、线程池核心类、线程池工作流程、线程池分类、拒绝策略、及如何提交与关闭线程池等。

但在实际开发过程中，在线程池使用过程中可能会遇到各方面的故障，如线程池阻塞，无法提交新任务等。

如果你想监控某一个线程池的执行状态，线程池执行类 `ThreadPoolExecutor ` 也给出了相关的 API, 能实时获取线程池的当前活动线程数、正在排队中的线程数、已经执行完成的线程数、总线程数等。

> 总线程数 = 排队线程数 + 活动线程数 +  执行完成的线程数。

下面给出一个线程池使用示例，及教你获取线程池状态。


```
private static ExecutorService es = new ThreadPoolExecutor(50, 100, 0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(100000));

public static void main(String[] args) throws Exception {
	for (int i = 0; i < 100000; i++) {
		es.execute(() -> {
			System.out.print(1);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	ThreadPoolExecutor tpe = ((ThreadPoolExecutor) es);

	while (true) {
		System.out.println();

		int queueSize = tpe.getQueue().size();
		System.out.println("当前排队线程数：" + queueSize);

		int activeCount = tpe.getActiveCount();
		System.out.println("当前活动线程数：" + activeCount);

		long completedTaskCount = tpe.getCompletedTaskCount();
		System.out.println("执行完成线程数：" + completedTaskCount);

		long taskCount = tpe.getTaskCount();
		System.out.println("总线程数：" + taskCount);

		Thread.sleep(3000);
	}

}
```

线程池提交了 100000 个任务，但同时只有 50 个线程在执行工作，我们每陋 3 秒来获取当前线程池的运行状态。

第一次程序输出：

```
当前排队线程数：99950
当前活动线程数：50
执行完成线程数：0
总线程数（排队线程数 + 活动线程数 +  执行完成线程数）：100000
```

第二次程序输出：

```
当前排队线程数：99800
当前活动线程数：50
执行完成线程数：150
总线程数（排队线程数 + 活动线程数 +  执行完成线程数）：100000
```

活动线程数和总线程数是不变的，排队中的线程数和执行完成的线程数不断在变化，直到所有任务执行完毕，最后输出：

```
当前排队线程数：0
当前活动线程数：0
执行完成线程数：100000
总线程数（排队线程数 + 活动线程数 +  执行完成线程数）：100000
```

这样，你了解了这些 API 的使用方法，你想监控线程池的状态就非常方便了。

大家有什么问题在下边留言，想深入交流学习的也可以点击左下方的阅读原文链接加入 Java 技术知识星球。目前知识星球 5.1劳动节-5.4 青年节优惠活动最后一天，错过今天再等半年……

