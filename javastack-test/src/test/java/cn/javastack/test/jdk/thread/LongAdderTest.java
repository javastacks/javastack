package cn.javastack.test.jdk.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class LongAdderTest {

    /**
     * 线程池大小
     */
    private static final int MAX_POOL_SIZE = 10;

    /**
     * 单线线程循环累加次数
     */
    private static final int MAX_LOOP_SIZE = 10;

    private static AtomicLong atomicLong = new AtomicLong();

    private static LongAdder longAdder = new LongAdder();

    public static void main(String[] args) throws InterruptedException {
        atomicLongTest();
        System.out.println("------------------公众号：Java技术栈------------------");
        longAdderTest();
    }

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void atomicLongTest() throws InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService es = Executors.newFixedThreadPool(MAX_POOL_SIZE);
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            es.execute(() -> {
                for (int j = 0; j < MAX_LOOP_SIZE; j++) {
                    atomicLong.incrementAndGet();
                }
            });
        }
        es.shutdown();
        es.awaitTermination(5, TimeUnit.MINUTES);
        System.out.printf("AtomicLong %s*%s 结果：%s，耗时：%sms.\n",
                MAX_POOL_SIZE,
                MAX_LOOP_SIZE,
                atomicLong.get(),
                (System.currentTimeMillis() - start));
    }

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void longAdderTest() throws InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService es = Executors.newFixedThreadPool(MAX_POOL_SIZE);
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            es.execute(() -> {
                for (int j = 0; j < MAX_LOOP_SIZE; j++) {
                    longAdder.increment();
                }
            });
        }
        es.shutdown();
        es.awaitTermination(5, TimeUnit.MINUTES);
        System.out.printf("LongAdder %s*%s 结果：%s，耗时：%sms.\n",
                MAX_POOL_SIZE,
                MAX_LOOP_SIZE,
                longAdder.sum(),
                (System.currentTimeMillis() - start));
    }

}