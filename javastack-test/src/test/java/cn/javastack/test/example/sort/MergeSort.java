package cn.javastack.test.example.sort;


/**
 * 归并排序
 * 更多请关注公众号：Java技术栈
 */
public class MergeSort {

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

        mergeSort(arr, 0, arr.length - 1);

        // 排序后输出结果
        System.out.println("排序后为：");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }
    }

    private static void mergeSort(int[] arr, int start, int end) {
        if (start < end) {
            int m = (start + end) / 2;

            mergeSort(arr, start, m);
            mergeSort(arr, m + 1, end);

            merge(arr, start, m, end);

            for (int k = 0; k < arr.length; k++) {
                System.out.print(arr[k] + "\t");
            }
            System.out.println();
        }
    }

    public static void merge(int[] arr, int start, int m, int end) {
        int length = end - start + 1;
        int temp[] = new int[length];

        int i = start;
        int j = m + 1;
        int k = 0;

        while (i <= m && j <= end) {
            if (arr[i] < arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= m) {
            temp[k++] = arr[i++];
        }
        while (j <= end) {
            temp[k++] = arr[j++];
        }

        k = 0;
        for (int t = start; t <= end; t++) {
            arr[t] = temp[k++];
        }
    }

}