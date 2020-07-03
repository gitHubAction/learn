package mashibing.algorithm.class3;

import java.util.Arrays;
import java.util.Collections;

/**
 * ClassName:    Code01_mergeSort
 * Package:    mashibing.algorithm.class3
 * Datetime:    2020/7/2   15:48
 * Author:   zsh
 * Description: 归并排序
 */
public class Code01_mergeSort {
    //非递归归并排序
    public static void mergeSort3(int[] arr){
        if(arr == null || arr.length < 2)return;


        int mergeSize = 1;
        int len =arr.length;
        while (mergeSize < len){
            int L = 0;
            while(L < len){
                int mid = L + mergeSize - 1;
                if(mid >= len){
                    break;
                }
                //右边界取len-1和L + mergeSize 中最小的
                int R = Math.min(mid + mergeSize,len-1);
                //合并
                merge(arr,L,mid,R);

                L = R + 1;
            }
            //如果mergeSize 大于 数组长度的一半，直接退出，避免mergeSize越界
            if (mergeSize > len / 2) {
                break;
            }
            //mergeSize扩为原来的2倍
            mergeSize <<= 1;
        }
    }



    private static void mergeSort1(int[] arr1) {
        process(arr1,0,arr1.length-1);
    }


    public static void process(int[] arr,int l, int r){
        //base case
        if(l == r)return;
        int mid = l + ((r - l) >> 1);
        //左侧有序
        process(arr,l,mid);
        //右侧由于
        process(arr,mid+1,r);
        //将有序区间合并
        merge(arr,l,mid,r);
    }

    private static void merge(int[] arr, int l, int mid, int r) {
        int[] help = new int[r-l+1];
        int j = 0;
        int L = l;
        int M = mid + 1;
        while (L <= mid && M <= r){
            help[j++] = arr[L] < arr[M] ? arr[L++] : arr[M++];
        }
        while (L <= mid){
            help[j++] = arr[L++];
        }
        while (M <= r){
            help[j++] = arr[M++];
        }
        for (int k = 0; k < help.length; k++) {
            arr[l+k] = help[k];
        }
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

    // for test
    public static void main(String[] args) {
        int testTime = 100;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            mergeSort1(arr1);
            mergeSort3(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Oops!");
    }
}
