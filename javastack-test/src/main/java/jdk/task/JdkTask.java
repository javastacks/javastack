package jdk.task;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class JdkTask {


    public static void main(String[] args) throws InterruptedException {
//        sleepTask();
//        timerTask();
        poolTask();
    }

    /**
     * 休眠实现定时任务
     * 来源公众号：Java技术栈
     */
    private static void sleepTask() {
        new Thread(() -> {
            while (true) {
                System.out.println("hi, 欢迎关注：Java技术栈");

                try {
                    // 每隔3秒执行一次
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * timer定时任务
     * 来源公众号：Java技术栈
     */
    private static void timerTask() throws InterruptedException {
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("hi, 欢迎关注：Java技术栈");
            }
        };

        // 第一次任务延迟时间
        long delay = 2000;

        // 任务执行频率
        long period = 3 * 1000;

        // 开始调度
//        timer.schedule(timerTask, delay, period);

        // 指定首次运行时间
        timer.schedule(timerTask, DateUtils.addSeconds(new Date(), 5), period);

        Thread.sleep(20000);

        // 终止并移除任务
        timer.cancel();
        timer.purge();
    }

    /**
     * 线程池定时任务
     * 来源公众号：Java技术栈
     */
    public static void poolTask(){
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(10);

        pool.scheduleAtFixedRate(() -> {
            System.out.println("hi, 欢迎关注：Java技术栈");
        }, 2000, 3000, TimeUnit.MILLISECONDS);

    }

}
