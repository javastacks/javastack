
#### Fork/Join是什么？

Fork/Join框架是Java7提供的并行执行任务框架，思想是将大任务分解成小任务，然后小任务又可以继续分解，然后每个小任务分别计算出结果再合并起来，最后将汇总的结果作为大任务结果。其思想和MapReduce的思想非常类似。对于任务的分割，要求各个子任务之间相互独立，能够并行独立地执行任务，互相之间不影响。

**Fork/Join的运行流程图如下：**

![image](https://res.infoq.com/articles/fork-join-introduction/zh/resources/21.png)

我们可以通过Fork/Join单词字面上的意思去理解这个框架。Fork是叉子分叉的意思，即将大任务分解成并行的小任务，Join是连接结合的意思，即将所有并行的小任务的执行结果汇总起来。

![image](http://java.boot.by/ocpjp7-upgrade/images/040501.gif)

#### 工作窃取算法

ForkJoin采用了工作窃取（work-stealing）算法，若一个工作线程的任务队列为空没有任务执行时，便从其他工作线程中获取任务主动执行。为了实现工作窃取，在工作线程中维护了双端队列，窃取任务线程从队尾获取任务，被窃取任务线程从队头获取任务。这种机制充分利用线程进行并行计算，减少了线程竞争。但是当队列中只存在一个任务了时，两个线程去取反而会造成资源浪费。

**工作窃取的运行流程图如下：**

![image](https://res.infoq.com/articles/fork-join-introduction/zh/resources/image3.png)

#### Fork/Join核心类

Fork/Join框架主要由子任务、任务调度两部分组成，类层次图如下。

![image](http://img.blog.csdn.net/20160720172854109)

- **ForkJoinPool**

ForkJoinPool是ForkJoin框架中的任务调度器，和ThreadPoolExecutor一样实现了自己的线程池，提供了三种调度子任务的方法：

1. execute：异步执行指定任务，无返回结果；
1. invoke、invokeAll：异步执行指定任务，等待完成才返回结果；
1. submit：异步执行指定任务，并立即返回一个Future对象；

- **ForkJoinTask**

Fork/Join框架中的实际的执行任务类，有以下两种实现，一般继承这两种实现类即可。

1. RecursiveAction：用于无结果返回的子任务；
1. RecursiveTask：用于有结果返回的子任务；

#### Fork/Join框架实战

下面实现一个Fork/Join小例子，从1+2+...10亿，每个任务只能处理1000个数相加，超过1000个的自动分解成小任务并行处理；并展示了通过不使用Fork/Join和使用时的时间损耗对比。

```
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTask extends RecursiveTask<Long> {

	private static final long MAX = 1000000000L;
	private static final long THRESHOLD = 1000L;
	private long start;
	private long end;

	public ForkJoinTask(long start, long end) {
		this.start = start;
		this.end = end;
	}

	public static void main(String[] args) {
		test();
		System.out.println("--------------------");
		testForkJoin();
	}

	private static void test() {
		System.out.println("test");
		long start = System.currentTimeMillis();
		Long sum = 0L;
		for (long i = 0L; i <= MAX; i++) {
			sum += i;
		}
		System.out.println(sum);
		System.out.println(System.currentTimeMillis() - start + "ms");
	}

	private static void testForkJoin() {
		System.out.println("testForkJoin");
		long start = System.currentTimeMillis();
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		Long sum = forkJoinPool.invoke(new ForkJoinTask(1, MAX));
		System.out.println(sum);
		System.out.println(System.currentTimeMillis() - start + "ms");
	}

	@Override
	protected Long compute() {
		long sum = 0;
		if (end - start <= THRESHOLD) {
			for (long i = start; i <= end; i++) {
				sum += i;
			}
			return sum;
		} else {
			long mid = (start + end) / 2;

			ForkJoinTask task1 = new ForkJoinTask(start, mid);
			task1.fork();

			ForkJoinTask task2 = new ForkJoinTask(mid + 1, end);
			task2.fork();

			return task1.join() + task2.join();
		}
	}

}
```

这里需要计算结果，所以任务继承的是RecursiveTask类。ForkJoinTask需要实现compute方法，在这个方法里首先需要判断任务是否小于等于阈值1000，如果是就直接执行任务。否则分割成两个子任务，每个子任务在调用fork方法时，又会进入compute方法，看看当前子任务是否需要继续分割成孙任务，如果不需要继续分割，则执行当前子任务并返回结果。使用join方法会阻塞并等待子任务执行完并得到其结果。

程序输出：

```
test
500000000500000000
4992ms
--------------------
testForkJoin
500000000500000000
508ms
```
从结果看出，并行的时间损耗明显要少于串行的，这就是并行任务的好处。

尽管如此，在使用Fork/Join时也得注意，不要盲目使用。

1. 如果任务拆解的很深，系统内的线程数量堆积，导致系统性能性能严重下降；
1. 如果函数的调用栈很深，会导致栈内存溢出；