package cn.javastack.test.example;

/**
 * 输出 1000 以内所有的水仙花数
 * 更多请关注公众号：Java技术栈
 */
public class NarcissisticNumber {

    public static void main(String[] args) {
        int ge, shi, bai;
        int count = 0;
        for (int i = 100; i < 1000; i++) {
            ge = i % 10;
            shi = i / 10 % 10;
            bai = i / 100;
            int result = (int) (Math.pow(ge, 3) + Math.pow(shi, 3) + Math.pow(bai, 3));
            if (i == result) {
                System.out.print(i);
                System.out.print(" ");
                count++;
            }
        }
        System.out.println();
        System.out.println("1000 以内的水仙花数个数是: " + count);
    }

}


