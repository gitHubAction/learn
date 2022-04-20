package mashibing.algorithm.class4;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/2/21 10:13
 */
public class Practis_MergeSort {
    public static void main(String[] args) {
        int[] a = new int[]{1,-1,32,-12,12,32,43,4,3,23,21};
        mergeSort(a);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    public static void mergeSort2(int[] a){
        if(a == null || a.length < 2){
            return;
        }
        int mergeSize = 1;
        int len = a.length;
        while (mergeSize < len){
            int l = 0;
            while (l < len){
                if(mergeSize > len - l){
                    break;
                }
                int m = l + mergeSize -1;
                int r = m + Math.min(mergeSize, len-m-1);
                merge(a, l, m, r);
                l = r + 1;
            }

            if(mergeSize > len / 2){
                break;
            }
            mergeSize <<= 1;
        }
    }

    public static void mergeSort(int[] a) {
        if(a == null || a.length < 2){
            return;
        }
        // 做递归
        process(a, 0, a.length-1);
    }

    private static void process(int[] a, int l, int r) {
        if(l == r){
            return;
        }
        int m = l + ((r - l) >> 1);
        process(a, l, m);
        process(a, m+1, r);
        // 归并
        merge(a, l, m, r);
    }

    private static void merge(int[] a, int l, int m, int r) {
        int[] help = new int[r-l+1];
        int i = 0;
        int p1 = l;
        int p2 = m + 1;
        while (p1 <= m && p2 <= r){
            help[i++] =  a[p1] <= a[p2] ? a[p1++] : a[p2++];
        }
        while (p1 <= m){
            help[i++] = a[p1++];
        }
        while (p2 <= r){
            help[i++] = a[p2++];
        }
        for (int j = 0; j < help.length; j++) {
            a[l+j] = help[j];
        }
    }

}
