package mashibing.algorithm.class1;

/**
 * ClassName:    BSExit
 * Package:    mashibing.algorithm
 * Datetime:    2020/6/22   16:53
 * Author:   zsh
 * Description: 二分查找
 */
public class Code03_BSExit {


    public static void main(String[] args) {
        int[] arr = new int[]{1,3,4,6,7,8,9,22,33};
        System.out.println(exit(arr,5));
        System.out.println(nearestIndex(arr,5));
    }


    /**
     * 二分查找
     * @param arr
     * @param num
     * @return
     */
    public static boolean exit(int[] arr,int num){
        if(arr == null || arr.length == 0)return false;
        int L = 0;
        int R = arr.length-1;
        int mid = 0;

        while (L < R){
            mid = L + ((R - L) >> 1);//（L+R）/2
            if(arr[mid] == num){
                return true;
            }else if(arr[mid] > num){
                R = mid -1;
            }else {
                L = mid +1;
            }
        }
        return arr[R] == num;
    }



    public static int nearestIndex(int[] arr,int num){
        if(arr == null || arr.length == 0)return -1;
        int L = 0;
        int R = arr.length-1;
        int mid = 0;
        int index = -1;

        while (L <= R){
            mid = L +( (R-L)>>1 );
            if(arr[mid] >= num){
                R = mid -1;
                index = mid;
            }else {
                L = mid +1;
            }
        }
        return index;
    }
}
