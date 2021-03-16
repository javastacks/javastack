package cn.javastack.test.example.sort;

/**
 * 快速排序
 * 更多请关注公众号：Java技术栈
 */
public class QuickSort {

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
        quickSort(arr, 0, arr.length - 1);

        // 排序后输出结果
        System.out.println("排序后为：");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }
    }

    public static void quickSort(int arr[], int low, int high) {
        int pivot, pos, i, t;
        if (low < high) {
            pos = low;
            pivot = arr[pos];
            for (i = low + 1; i <= high; i++) {
                if (arr[i] < pivot) {
                    pos++;
                    t = arr[pos];
                    arr[pos] = arr[i];
                    arr[i] = t;
                }
            }
            t = arr[low];
            arr[low] = arr[pos];
            arr[pos] = t;

            for (int k = 0; k < arr.length; k++) {
                System.out.print(arr[k] + "\t");
            }
            System.out.println();

            // 分而治之
            // 排序左半部分
            quickSort(arr, low, pos - 1);

            // 排序右半部分
            quickSort(arr, pos + 1, high);
        }
    }

}