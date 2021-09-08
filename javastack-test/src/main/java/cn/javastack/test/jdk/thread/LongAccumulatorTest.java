package cn.javastack.test.jdk.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAccumulator;

/**
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class LongAccumulatorTest {

    /**
     * 线程池大小
     */
    private static final int MAX_POOL_SIZE = 10;

    /**
     * 单线线程循环累加次数
     */
    private static final int MAX_LOOP_SIZE = 10;

    private static LongAccumulator longAccumulatorAdd = new LongAccumulator((left, right) -> left + right, 0);
    private static LongAccumulator longAccumulatorMax = new LongAccumulator(Long::max, 50);

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    public static void main(String[] args) throws InterruptedException {
        // 和 LongAdder 一致
        add(longAccumulatorAdd, 1);

        // 每次累加2
        add(longAccumulatorAdd, 2);

        // 求最大值
        max(longAccumulatorMax);
    }

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void add(LongAccumulator longAccumulator, long number) throws InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService es = Executors.newFixedThreadPool(MAX_POOL_SIZE);
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            es.execute(() -> {
                for (int j = 0; j < MAX_LOOP_SIZE; j++) {
                    longAccumulator.accumulate(number);
                }
            });
        }
        es.shutdown();
        es.awaitTermination(5, TimeUnit.MINUTES);
        System.out.printf("LongAccumulator %s*%s +%s 结果：%s，耗时：%sms.\n",
                MAX_POOL_SIZE,
                MAX_LOOP_SIZE,
                number,
                longAccumulator.get(),
                (System.currentTimeMillis() - start));
        longAccumulator.reset();
    }

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    private static void max(LongAccumulator longAccumulator) throws InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService es = Executors.newFixedThreadPool(MAX_POOL_SIZE);
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            int finalI = i;
            es.execute(() -> {
                longAccumulator.accumulate(finalI * 10);
            });
        }
        es.shutdown();
        es.awaitTermination(5, TimeUnit.MINUTES);
        System.out.printf("LongAccumulator 求最大值结果：%s，耗时：%sms.\n",
                longAccumulator.get(),
                (System.currentTimeMillis() - start));
        longAccumulator.reset();
    }

}
