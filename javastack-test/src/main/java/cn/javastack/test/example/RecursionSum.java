package cn.javastack.test.example;

/**
 * 递归求和
 * 更多请关注公众号：Java技术栈
 */
public class RecursionSum {

    public static void main(String[] args) {
        System.out.println("1-100 递归求和结果为：" + sum(100));
    }

    public static int sum(int num) {
        if (num == 1) {
            return 1;
        }
        return num + sum(num - 1);
    }

}
