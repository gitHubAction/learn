import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: zhangsh
 * @Date: 2019/7/19 11:53
 * @Version 1.0 链表
 * Description
 */
public class LeeCode {
    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        int len = nums.length;
        for(int i = 0 ; i< len ; i++){
            for(int j = len-1; j > 0 ; j--){
                int sum = nums[i] + nums[j];
                if(i != j && target == sum){
                    result[0] =  i;
                    result[1] =  j;
                }
            }
        }
        return result;
    }


    public static int[] bubleSort(int[] nums){
        if (nums.length < 1)return null;
        for(int i = 0;i< nums.length ; i++){
            for (int j = i+1; j < nums.length; j++){
                if(nums[i] > nums[j]){
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
            }
        }
        return nums;
    }


    /**
     * Z字形转换
     *
     * 解题思路：
     *      方向变量
     * @param s
     * @param num
     * @return
     */
    public static List convertZ(String s,int num){
        //初始化行数集合
        List<StringBuilder> strz = new ArrayList<>();
        for (int i = 0; i< Math.min(s.length(),num);i++) {
            strz.add(new StringBuilder());
        }

        int curRow = 0;
        Boolean goDown = false;
        for(Character c: s.toCharArray()){
            strz.get(curRow).append(c);
            if(curRow == num-1 || curRow == 0) goDown = !goDown;
            curRow += goDown ? 1:-1;
        }
        return strz;
    }

    public static void main(String[] args) {

//        sortedSquares(new int[]{-4,-1,0,3,10});
        /*int[] ints = new LeeCode().twoSum(new int[]{3,3}, 6);
        for (int i:ints) {
            System.out.println(i);
        }*/

        /*Integer a = null;
        System.out.println(a == null);
        System.out.println(maxSub("acdefasc"));



        int[] ints1 = selectionSort(new int[]{4, 3, 5, 2, 1, 10, 9, 2, 6, 7, 8, 9, 5});
        for (int i:ints1) {
            System.out.print(i+",");
        }

        int[] ints = mergeSort(new int[]{4, 3});
//        mergeSort(new int[]{4, 3, 5, 2, 1, 10, 9, 2, 6, 7, 8, 9, 5});
        for (int i:ints) {
            System.out.print(i+"、");
        }

        BigDecimal bd = new BigDecimal("80.88");
        System.out.println(bd.divide(new BigDecimal(2)));*/

     /*   int[] arrs = {9, 3, 7, 6, 10, 23, 12};
        int[] ints = quickSort(arrs, 0, arrs.length - 1);
        for (int i = 0; i < arrs.length; i++) {
            System.out.println(arrs[i]);
        }*/
//        System.out.println(largestPerimeter(new int[]{2,1,2}));


        String str1 = new StringBuilder("计算机").append("win7").toString();
        System.out.println(str1.intern() == str1);


        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);

    }


    /**
     * 冒泡排序：
     *
     * @param arr
     * @return
     */
    public static int[] bubleSort1(int[] arr){
        if(arr.length == 0)return arr;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length-1-i; j++) {
                if(arr[j+1]<arr[j]){
                    int temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }


    /**
     * 选择排序：
     * 将数组R[n]分为有序区R[0,1,...i-1]和无序区R[i,...n]
     *      第i(i=1,2,3....n-1)趟排序有序区为空，
     *      先找出无序区中最小的元素,然后有序区中最小的元素进行交换，
     *      使得有序区的元素变为R[1..i]无序区的元素变为R[i+1,...n]
     *      进行n-1趟排序
     *
     * @param arr
     * @return
     */
    public static int[] selectionSort(int[] arr){
        if(arr.length == 0)return arr;
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            //找出无序数组中的最小值的下标
            for (int j = i+1; j < arr.length; j++) {
                if(arr[minIndex] > arr[j]){
                    minIndex = j;
                }
            }
            //将无序区的比有序区的最小值进行交换
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }


    /**
     * 插入排序：
     *
     * @param arr
     * @return
     */
    public static int[] insertionSort(int[] arr){
        if(arr.length == 0) return arr;
        for (int i = 0; i < arr.length-1; i++) {
            //当前元素
            int cur = arr[i+1];
            //有序元素区的最后一个元素
            int preIndex = i;
            //循环比较有序区的元素与当前元素的大小
            while (preIndex>=0 && cur < arr[preIndex]  ){
                //前一个元素后移
                arr[preIndex+1] = arr[preIndex];
                //与有序去的下一个元素比较 比较元素索引
                preIndex--;
            }
            arr[preIndex+1] = cur;
        }
        return arr;
    }


    /**
     * 归并排序
     * @param arr
     * @return
     */
    public static int[] mergeSort(int[] arr){
        if(arr.length < 2 )return arr;
        int mid = arr.length /2;
        int [] left = Arrays.copyOfRange(arr,0,mid);
        int [] right = Arrays.copyOfRange(arr,mid,arr.length);

        return merge(mergeSort(left),mergeSort(right));
    }

    public static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        for (int index = 0, i = 0, j = 0; index < result.length; index++) {
            if (i >= left.length)
                result[index] = right[j++];
            else if (j >= right.length)
                result[index] = left[i++];
            else if (left[i] > right[j])
                result[index] = right[j++];
            else
                result[index] = left[i++];
        }
        return result;
    }


    /**
     * 快速排序
     * @param arr
     * @return
     */
    public static int[] quickSort(int[] arr, int left, int right){
        if(arr == null || arr.length == 0)return arr;
        //声明一个数来存 基准数
        int pivot;
        //一直可以排序
        if (left < right) {
            //从小到大
            pivot = partition(arr, left, right);
            //一直递归处理左边的一堆数
            quickSort(arr, left, pivot - 1);
            //一直递归处理右边的一堆数
            quickSort(arr, pivot + 1, right);
        }
        return arr;
    }

    private static int partition(int[] arr, int left, int right) {
        //去除一个数作为基准数
        int key = arr[left];
        while (left < right){
            //右边先往左边走
            while (left < right && arr[right] >= key) {
                //往左挪一步
                right--;
            }
            //交换位置
            arr[left] = arr[right];
            //左边往右边走
            while (left < right && arr[left] <= key){
                left++;
            }
            //交换两个位置
            arr[right] = arr[left];
        }

        //左右指针碰撞之后交换基准数给往右走的
        arr[left] = key;
        return left;
    }


    /**
     * 判断一个字符串中不含有重复字符的最长子串的长度
     *
     * 解题思路：
     *      窗口法
     * @param s
     * @return
     */
    public static int maxSub(String s){
        int n = s.length();
        int i = 0;//窗口其实位置
        int j = 0;//窗口末位位置
        int result = 0;//结果
        Set window = new HashSet();
        while (i< n && j < n){
            if(window.contains(s.charAt(j))){
                window.remove(s.charAt(i));
                i++;
            }else{
                window.add(s.charAt(j));
                j++;
                result = Math.max(result,j-i);
            }
        }
        System.out.println(window);
        return result;
    }



    public static String strWithout3a3b(int A, int B) {
        StringBuilder sb = new StringBuilder();
        while(A >0 && B>0){
            if(A > B){
                sb.append("aab");
                A -= 2;
                B -= 1;
            }else if(A < B){
                sb.append("bba");
                B -= 2;
                A -= 1;
            }else{
                for(int i = 0 ; i < A ; i++){
                    sb.append("ab");
                }
                A = 0;
                B = 0;
            }
        }
        if(A == 0){
            for(int i = 0 ; i < B ; i++){
                sb.append("b");
            }
        }else{
            for(int i = 0 ; i < A ; i++){
                sb.append("a");
            }
        }
        return sb.toString();
    }


    public static int largestPerimeter(int[] A) {
        /*for(int i = 0;i < A.length;i++){
            for(int j = i ; j < A.length; j++){
                if(A[j] > A[i]){
                    int temp = A[i];
                    A[i] = A[j];
                    A[j] = temp;
                }
            }
        }
        System.out.println(A);
        for(int i = 0 ; i <= A.length-3 ; i++){
            if(A[i] + A[i+1] > A[i+2]){
                return A[i] + A[i+1] + A[i+2];
            }
        }
        return 0;*/
        Arrays.sort(A);
        for (int i = A.length - 3; i >= 0; --i)
            if (A[i] + A[i+1] > A[i+2])
                return A[i] + A[i+1] + A[i+2];
        return 0;
    }


    public static int[] sortedSquares(int[] A) {
        for(int i = 0; i < A.length ; i++){
            for(int j = i ; j < A.length; j++){
                if(A[i] * A[i]> A[j] * A[j]){
                    int temp = A[i];
                    A[i] = A[j];
                    A[j] = temp;
                }
            }
        }
        for(int i = 0; i < A.length ; i++){
            A[i] *= A[i];
        }
        return A;
    }
}
