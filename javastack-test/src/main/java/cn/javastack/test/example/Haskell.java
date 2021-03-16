package cn.javastack.test.example;

/**
 * 九九乘法表
 * 更多请关注公众号：Java技术栈
 */
public class Haskell {

    public static void main(String[] args) {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + "*" + i + "=" + (i * j) + "\t");
            }
            System.out.println();
        }
    }

}

