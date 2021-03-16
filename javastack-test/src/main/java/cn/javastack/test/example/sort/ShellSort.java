package cn.javastack.test.example.sort;

/**
 * 希尔排序
 * 更多请关注公众号：Java技术栈
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] arr = {28, 21, 3, 1, 6, 66, 5, 33, 2, 19};

        // 排序前输出
        System.out.println("排序前为：");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }

        // 输出排序过程
        System.out.println();
        System.out.println("正在排序...");

        shellSort(arr);

        // 排序后输出结果
        System.out.println("排序后为：");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }
    }

    public static void shellSort(int[] arr) {
        int j, temp;
        for (int incr = arr.length / 2; incr > 0; incr /= 2) {
            for (int i = incr; i < arr.length; i++) {
                temp = arr[i];
                for (j = i; j >= incr; j -= incr) {
                    if (temp < arr[j - incr]) {
                        arr[j] = arr[j - incr];
                    } else {
                        break;
                    }
                }
                arr[j] = temp;
                for (int k = 0; k < arr.length; k++) {
                    System.out.print(arr[k] + "\t");
                }
                System.out.println();
            }
        }
    }
}