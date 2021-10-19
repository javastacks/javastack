package cn.javastack.test.example;

/**
 * 只出现过一次的数
 * 更多请关注公众号：Java技术栈
 */
public class OneNumber {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 39, 2, 39, 2, 1, 2, 9, 3, 33, 13, 33};
        for (int i = 0; i < arr.length; i++) {
            int num = 0;
            for (int j = 0; j < arr.length; j++) {
                if (arr[i] == arr[j]) {
                    num++;
                }
            }
            if (num == 1) {
                System.out.println("这个数只出现了一次：" + arr[i]);
            }
        }
    }

}
