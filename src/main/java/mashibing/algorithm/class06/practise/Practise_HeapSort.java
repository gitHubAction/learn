package mashibing.algorithm.class06.practise;

import java.util.Arrays;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/2/10 16:20
 */
public class Practise_HeapSort {

    public static void heapSort(int[] arr){
        if(arr == null || arr.length < 2){
            return;
        }
        // 构造堆
        for (int i = 0; i < arr.length; i++) {
            heapInsert(arr, i);
        }
        // 排序
        int heapSize = arr.length;
        swap(arr, 0, --heapSize);
        heapify(arr, 0, heapSize);
        while (heapSize > 0){
            heapify(arr, 0, heapSize);
            swap(arr, 0, --heapSize);
        }
    }

    private static void heapify(int[] arr, int i, int heapSize) {
        int left = i * 2 + 1;
        while(left < heapSize){
            int largest = left + 1 < heapSize && arr[left+1] > arr[left] ? left + 1 : left;
            largest = arr[i] > arr[largest] ? i : largest;
            if(i == largest){
                break;
            }
            swap(arr, i, largest);
            i = largest;
            left = i * 2 + 1;
        }
    }

    private static void heapInsert(int[] arr, int i) {
        while (arr[i] > arr[(i-1)/2]){
            swap(arr, i, (i-1)/2);
            i = (i-1)/2;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			heapSort(arr1);
			comparator(arr2);
			if (!isEqual(arr1, arr2)) {
				succeed = false;
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");

    }
}
