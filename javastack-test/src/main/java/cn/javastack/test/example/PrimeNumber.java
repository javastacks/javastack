package cn.javastack.test.example;

/**
 * 输出 100 以内所有的素数
 * 更多请关注公众号：Java技术栈
 */
public class PrimeNumber {

    public static void main(String[] args) {
        int count = 0;
        for (int i = 2; i < 100; i++) {
            boolean isPrime = true;
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                count++;
                System.out.print(i);
                System.out.print(" ");
            }
        }
        System.out.println();
        System.out.println("100 以内的素数个数是: " + count);
    }

}


