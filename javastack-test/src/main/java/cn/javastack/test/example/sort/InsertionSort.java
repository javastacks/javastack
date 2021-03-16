package cn.javastack.test.example.sort;

/**
 * 插入排序
 * 更多请关注公众号：Java技术栈
 */
public class InsertionSort {
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

        insertionSort(arr);

        // 排序后输出结果
        System.out.println("排序后为：");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }
    }

    public static void insertionSort(int arr[]) {
        int i, j, t;
        for (i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                t = arr[i];
                for (j = i - 1; j >= 0 && t < arr[j]; j--) {
                    arr[j + 1] = arr[j];
                }

                // 插入到适当位置
                arr[j + 1] = t;

                for (int k = 0; k < arr.length; k++) {
                    System.out.print(arr[k] + "\t");
                }
                System.out.println();
            }
        }
    }

}