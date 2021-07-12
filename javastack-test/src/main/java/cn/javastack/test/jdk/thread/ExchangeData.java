package cn.javastack.test.jdk.thread;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class ExchangeData {

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    public static void main(String[] args) throws InterruptedException {
        test4();
    }

    private static void test1() {
        Exchanger exchanger = new Exchanger();

        new Thread(() -> {
            try {
                Object data = "-公众号Java技术栈AAA";
                System.out.println(Thread.currentThread().getName() + data);

                // 开始交换数据
                data = exchanger.exchange(data);
                System.out.println(Thread.currentThread().getName() + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Object data = "-公众号Java技术栈BBB";
                System.out.println(Thread.currentThread().getName() + data);

                // 开始交换数据
                data = exchanger.exchange(data);
                System.out.println(Thread.currentThread().getName() + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void test2() {
        Exchanger exchanger = new Exchanger();

        new Thread(() -> {
            try {
                Object data = "-公众号Java技术栈AAA";
                System.out.println(Thread.currentThread().getName() + data);

                // 开始交换数据
                data = exchanger.exchange(data, 3000L, TimeUnit.MILLISECONDS);
                System.out.println(Thread.currentThread().getName() + data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void test3() throws InterruptedException {
        Exchanger exchanger = new Exchanger();

        Thread thread = new Thread(() -> {
            try {
                Object data = "-公众号Java技术栈AAA";
                System.out.println(Thread.currentThread().getName() + data);

                // 开始交换数据
                data = exchanger.exchange(data);
                System.out.println(Thread.currentThread().getName() + data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();

        // 线程中断
        Thread.sleep(3000L);
        thread.interrupt();
    }

    private static void test4() {
        Exchanger exchanger = new Exchanger();

        for (int i = 1; i <= 10; i++) {
            Integer data = i;
            new Thread(() -> {
                try {
                    Object exchange = exchanger.exchange(data);
                    System.out.println(Thread.currentThread().getName() + "-" + exchange);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Java技术栈" + i).start();
        }
    }

}
