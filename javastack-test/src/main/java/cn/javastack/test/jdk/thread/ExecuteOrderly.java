package cn.javastack.test.jdk.thread;

/**
 * @author: 栈长
 * @from: 公众号Java技术栈
 */
public class ExecuteOrderly {

    /**
     * @author: 栈长
     * @from: 公众号Java技术栈
     */
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("T1 执行完成...");
        });

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("T2 执行完成...");
        });

        Thread t3 = new Thread(() -> {
            System.out.println("T3 执行完成...");
        });

        t1.start();
        t1.join();

        t2.start();
        t2.join();

        t3.start();
    }

}
