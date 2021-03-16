package cn.javastack.test.example.sort;

/**
 * 堆排序
 * 更多请关注公众号：Java技术栈
 */
public class HeapSort {

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

		heapSort(arr);

		// 排序后输出结果
		System.out.println("排序后为：");
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + "\t");
		}
	}

	private static void heapSort(int arr[]) {
		for (int i = 0; i < arr.length; i++) {
			heap(arr, arr.length - 1 - i);
			swap(arr, 0, arr.length - 1 - i);

			for (int k = 0; k < arr.length; k++) {
				System.out.print(arr[k] + "\t");
			}
			System.out.println();
		}
	}

	private static void heap(int[] data, int last) {
		for (int i = last / 2; i >= 0; i--) {
			int parent = i;
			while (2 * parent + 1 <= last) {
				int bigger = 2 * parent + 1;
				if (bigger < last) {
					if (data[bigger] < data[bigger + 1]) {
						bigger = bigger + 1;
					}
				}
				if (data[parent] < data[bigger]) {
					swap(data, parent, bigger);
					parent = bigger;
				} else {
					break;
				}
			}
		}
	}

	private static void swap(int[] data, int i, int j) {
		if (i == j) {
			return;
		}
		data[i] = data[i] + data[j];
		data[j] = data[i] - data[j];
		data[i] = data[i] - data[j];
	}

}