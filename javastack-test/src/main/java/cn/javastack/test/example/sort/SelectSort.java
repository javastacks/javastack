package cn.javastack.test.example.sort;

/**
 * 选择排序
 * 更多请关注公众号：Java技术栈
 */
public class SelectSort {

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

        selectSort(arr);

        // 排序后输出结果
        System.out.println("排序后为：");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }
	}

    private static void selectSort(int[] arr) {
        int temp;
        for (int j = 0; j < arr.length - 1; j++) {
            int min = arr[j];
            int minIndex = j;
            for (int k = j + 1; k < arr.length; k++) {
                if (min > arr[k]) {
                    min = arr[k];
                    minIndex = k;
                }
            }
            temp = arr[j];
            arr[j] = arr[minIndex];
            arr[minIndex] = temp;

            for (int k = 0; k < arr.length; k++) {
                System.out.print(arr[k] + "\t");
            }
            System.out.println();
        }
    }

}