package mashibing.algorithm.class5;

import mashibing.algorithm.class05.Code02_PartitionAndQuickSort;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description 快速排序的思维是  将待排序的数组分为<arr[i]、=arr[i]、<arr[i]三部分，然后递归左部分进行分区和右部分进行分区
 * @date 2022/2/17 9:26
 */
public class Practis_PartitionAndQuickSort {
    public static void main(String[] args) {
        boolean flag = true;
        for (int i = 0; i < 10000; i++) {
            int[] arr1 = generateRandomArray(100, 500);
            int[] arr2 = copyArray(arr1);
            quickSort(arr1);
            if(!isEqual(arr1, arr2)){
                flag = false;
                for (int j = 0; j < arr1.length; j++) {
                    System.out.print(arr1[j]+" "+ arr2[j]);
                    System.out.println();
                }
                break;
            }
        }
        System.out.println(flag ? "nice" : "funck");
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


    private static void swap(int[] arr, int index, int i) {
        int temp = arr[index];
        arr[index]= arr[i];
        arr[i] = temp;
    }

    public static void quickSort(int[] arr){
        if(arr == null || arr.length < 2){
            return;
        }
        process(arr, 0, arr.length-1);
    }

    private static void process(int[] arr, int L, int R) {
        if(L > R){
            return;
        }
        int M  = partition(arr, L , R);
        process(arr, L, M-1);
        process(arr, M+1, R);
    }

    private static int partition(int[] arr, int L, int R) {
        if(L>R){
            return -1;
        }
        if(L==R){
            return L;
        }
        // 取数组最右的元素作为参照
        int less = L - 1;
        int index = L;
        while (index < R){
            if(arr[index] <= arr[R]){
                swap(arr, index, ++less);
            }
            index++;
        }
        // 最后将arr[R]与小于区+1 位置的互换
        swap(arr, ++less, R);
        return less;
    }
}
