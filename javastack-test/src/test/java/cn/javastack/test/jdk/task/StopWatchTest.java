package cn.javastack.test.jdk.task;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;

/**
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class StopWatchTest {

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void jdkWasteTime() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread.sleep(3000);
        System.out.printf("耗时：%dms.", System.currentTimeMillis() - start);
    }

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void commonsLang3WasteTime() throws InterruptedException {
        StopWatch sw = StopWatch.createStarted();

        Thread.sleep(1000);
        System.out.printf("首次耗时：%dms.\n", sw.getTime()); // 1002ms

        sw.suspend();
        Thread.sleep(1000);
        System.out.printf("暂停耗时：%dms.\n", sw.getTime()); // 1000ms

        sw.resume();
        Thread.sleep(1000);
        System.out.printf("恢复耗时：%dms.\n", sw.getTime()); // 2001ms

        Thread.sleep(1000);
        sw.stop();
        Thread.sleep(1000);
        System.out.printf("总耗时：%dms.\n", sw.getTime()); // 3009ms

        sw.reset();
        sw.start();
        Thread.sleep(1000);
        System.out.printf("重置耗时：%dms.\n", sw.getTime()); // 1000ms
    }

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    @Test
    public void springWasteTime() throws InterruptedException {
        org.springframework.util.StopWatch sw = new org.springframework.util.StopWatch("公众号Java技术栈：测试耗时");

        sw.start("任务1");
        Thread.sleep(1000);
        sw.stop();
        System.out.printf("任务1耗时：%d%s.\n", sw.getLastTaskTimeMillis(), "ms");

        Thread.sleep(1000);

        sw.start("任务2");
        Thread.sleep(1100);
        sw.stop();
        System.out.printf("任务2耗时：%d%s.\n", sw.getLastTaskTimeMillis(), "ms");

        sw.start("任务3");
        Thread.sleep(1200);
        sw.stop();
        System.out.printf("任务3耗时：%d%s.\n", sw.getLastTaskTimeMillis(), "ms");

        System.out.println(sw.prettyPrint());
    }

}
