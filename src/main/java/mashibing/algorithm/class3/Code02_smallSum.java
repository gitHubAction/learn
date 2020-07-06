package mashibing.algorithm.class3;

/**
 * ClassName:    Code02_smallSum
 * Package:    mashibing.algorithm.class3
 * Datetime:    2020/7/6   11:47
 * Author:   zsh
 * Description:  获取数组中左边小于每个元素的和
 * [1,3,4,2,5]
 * 1 左边小于1的 0
 * 3 左边小于3的 1
 * 4 左边小于4的 1 3
 * 2 左边小于2的 1
 * 5 左边小于5的 1 3 4 2
 *
 *
 * 思路：归并排序
 *
 * 递归的时间复杂度计算公式  T(N) = a * T(N/b) + O(N ^ d)
 *                        logb(a) == d           O(N^d * logN)
 *                        logb(a) > d            O(N ^ logb(a))
 *                        logb(a) < d            O(N ^ d)
 *
 */
public class Code02_smallSum {


    public static void main(String[] args) {
        int[] ints = {1, 3, 4, 2, 5};
        int i = smallSum(ints);
        System.out.println(i);
    }

    public static int smallSum(int[] arr){
        if(arr == null || arr.length < 2)return 0;
        return process(arr,0,arr.length-1);
    }

    private static int process(int[] arr, int L, int R) {
        if(L == R) return 0;//base case
        int mid = L + ((R - L)>>1);
        int left = process(arr,L,mid);
        int right = process(arr,mid+1,R);
        int merge = merge(arr,L,mid,R);
        return left + right + merge;
    }

    private static int merge(int[] arr, int L, int mid, int R) {
        int res = 0;
        int[] help = new int[R - L + 1];
        int helpI = 0;
        int p1 = L ;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= R){
            res += arr[p1] < arr[p2] ? (R - p2 + 1)* arr[p1] : 0;
            help[helpI++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= mid){
            help[helpI++] = arr[p1++];
        }
        while (p2 <= R){
            help[helpI++] = arr[p2++];
        }
        for (int i = 0; i < help.length; i++) {
            arr[i+L] = help[i];
        }
        return res;
    }
}
