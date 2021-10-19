package cn.javastack.test.example.sort;

/**
 * 二分排序
 * 更多请关注公众号：Java技术栈
 */
public class BinarySort {

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

		binarySort(arr);

		// 排序后输出结果
		System.out.println("排序后为：");
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + "\t");
		}
	}

	private static void binarySort(int[] arr){
		for (int i = 1; i < arr.length; i++) {
			int index = binaryFind(0, i - 1, arr[i], arr);
			insert(index, i, arr[i], arr);

			for (int k = 0; k < arr.length; k++) {
				System.out.print(arr[k] + "\t");
			}
			System.out.println();
		}

	}

	private static int binaryFind(int leftIndex, int rightIndex, int val, int[] arr) {
		int midIndex = ((rightIndex + leftIndex) / 2);
		int midVal = arr[midIndex];

		if (rightIndex >= leftIndex) {
			if (midVal > val) {
				return binaryFind(leftIndex, midIndex - 1, val, arr);
			} else if (midVal < val) {
				return binaryFind(midIndex + 1, rightIndex, val, arr);
			} else if (midVal == val) {
				return midIndex + 1;
			}
		} else {
			return leftIndex;
		}

		return -1;
	}

	private static void insert(int index, int last, int value, int[] arr) {
		int i = last - 1;
		while (index <= i) {
			arr[i + 1] = arr[i];
			i--;
		}
		arr[i + 1] = value;
	}

}