package leetCode;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: zhangsh
 * @Date: 2019/7/19 11:53
 * @Version 1.0 链表
 * Description
 */
public class LeetCode {

    /**
     * //编写一个函数来查找字符串数组中的最长公共前缀。
     * //
     * // 如果不存在公共前缀，返回空字符串 ""。
     * //
     * // 示例 1:
     * //
     * // 输入: ["flower","flow","flight"]
     * //输出: "fl"
     * //
     * //
     * // 示例 2:
     * //
     * // 输入: ["dog","racecar","car"]
     * //输出: ""
     * //解释: 输入不存在公共前缀。
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        if(strs == null || strs.length == 0){
            return "";
        }

        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            // 获取最长公共子串
            prefix = commonprefix(prefix,strs[i]);
            if(prefix.length() == 0){
                break;
            }
        }
        return prefix;
    }

    public String commonprefix(String prefix,String str){
        int index = 0;
        int len = Math.min(prefix.length(),str.length());
        while (index < len && prefix.charAt(index) == str.charAt(index)){
            index++;
        }
        return prefix.substring(0,index);
    }

    /**
     * 字符串转换成整数
     *  注意的三个点
     *      1、''为char字节  ""为字符串String
     *      2、字节比较时用  == 比较 ，
     *      3、 int最大值加1后超出边界变回int下边界   所以用 1L + Integer.MAX_VALUE
     * @param s
     * @return
     */
    public static int myAtoi(String s) {
        if("".equals((s = s.trim()))){
            return 0;
        }
        if(Character.isLetter(s.charAt(0))){
            return 0;
        }
        long res = 0;
        //正负  '' 表示字节char   "" 表示String
        boolean neg = '-' == s.charAt(0);
        int i = '-' == s.charAt(0) || '+' ==  s.charAt(0) ? 1 : 0;
        while(i < s.length() && Character.isDigit(s.charAt(i))){
            // 注意字节值相加
            res = res*10 + (s.charAt(i++) - '0');
            // 正值
            if(!neg && res > Integer.MAX_VALUE){
                res = Integer.MAX_VALUE;
                break;
            }
            // 负值
            //注意int最大值加1 边界问题
            if(neg && res > 1L + Integer.MAX_VALUE){
                res = 1L + Integer.MAX_VALUE;
                break;
            }
        }
        return neg ? (int)-res : (int)res;
    }

    /**
     *罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
     *
     * 字符          数值
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
     *
     * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9
     * 表示为 IX。这个特殊的规则只适用于以下六种情况：
     *
     * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
     * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
     * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
     * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
     * @param num
     * @return
     */
    public String intToRoman(int num) {
        int[] numbers = {1,4,5,9,10,40,50,90,100,400,500,900,1000};
        String[] romans = {"I","IV","V","IX","X","XL","L","XC","C","CD","D","CM","M"};
        //贪心算法
        StringBuilder res = new StringBuilder();
        for (int i = numbers.length-1; i >= 0 && num >= 0; i--) {
            // Repeat while the current symbol still fits into num.
            while (numbers[i] <= num) {
                num -= numbers[i];
                res.append(romans[i]);
            }
        }
        return res.toString();
    }



    /**
     * 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)
     * 。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/container-with-most-water
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        /**
         * 解法一：暴力解法
         */
        int cap = 0;
        //双指针
        for (int i = 0; i < height.length; i++) {
            for (int j = i+1; j < height.length; j++) {
                int newCap = Math.min(height[i],height[j]) * (j - i);
                cap = Math.max(cap,newCap);
            }
        }


        /**
         * 解法二：双指针
         */
        int head = 0,tail = height.length-1;
        cap = 0;
        while (head < tail){
            int newCap = Math.min(height[head],height[tail])*(tail-head);
            cap = Math.max(cap,newCap);
            //哪个指针的值小移动哪个指针
            int i = height[head] < height[tail] ? head++ : tail--;
            //超简洁写法
            /*cap = height[head] < height[tail] ?
                    Math.max(cap,(tail-head)*height[head++]) :
                    Math.max(cap,(tail-head)*height[tail--]);*/
        }
        return cap;
    }


    /**
     * 窗口法求
     * @param target
     * @return
     */
    public static int[][] findContinuousSequence1(int target) {
        int left = 1,right = 1,sum = 0;
        List<int[]> res = new ArrayList<>();

        while (left <= target/2){
            if(sum>target){
                //左边界右移
                sum -= left++;
            }else if(sum < target){
                //右边界右移
                sum += right++;
            }else{
                //添加结果
                int[] temp = new int[right-left];
                for(int i = left;i<right;i++){
                    temp[i-left] = i;
                }
                res.add(temp);
                //左边界右移做下一轮循环
                sum -= left++;
            }
        }
        return res.toArray(new int[res.size()][]);
    }

    public static int[][] findContinuousSequence(int target) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int a = 1; a < target; a++) {
            for (int n = 2; n < target; n++) {
                if(2*a*n + n * n -n == 2*target)map.put(a,n);
                if(2*a*n + n*n -n > 2*target)break;
            }
        }

        int size = map.keySet().size();
        int[][] result = new int[size][];
        int i = 0;
        for (int a : map.keySet()) {
            int[] temp = new int[map.get(a)];
            for (int j = 0; j < map.get(a); j++) {
                temp[j] = a+j;
            }
            result[i++] = temp;
        }
        return result;
    }


    public static int orangesRotting(int[][] grid){
        //腐烂的橘子
        Queue<int[]> queue = new LinkedList<int[]>();
        //未腐烂的橘子的个数
        int good = 0;
        //寻找所有的腐烂的橘子
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == 2)queue.add(new int[]{i,j});
                if(grid[i][j] == 1)good++;
            }
        }
        int minitues = 0;
        while (queue.size()>0 && good>0){
            minitues++;
            for (int i = 0; i < queue.size(); i++) {
                //去除腐烂的橘子
                int[] poll = queue.poll();
                //腐烂上下左右
                //上
                if(poll[1] - 1 >= 0 && grid[poll[0]][poll[1]-1] == 1){
                    grid[poll[0]][poll[1]-1] = 2;
                    good--;
                    queue.add(new int[]{poll[0],poll[1]-1});
                }
                //下
                if(poll[1] + 1 < grid.length && grid[poll[0]][poll[1]+1] == 1){
                    grid[poll[0]][poll[1]+1] = 2;
                    good--;
                    queue.add(new int[]{poll[0],poll[1]+1});
                }
                //左
                if(poll[0] - 1 >= 0 && grid[poll[0]-1][poll[1]] == 1){
                    grid[poll[0]-1][poll[1]] = 2;
                    good--;
                    queue.add(new int[]{poll[0]-1,poll[1]});
                }
                //右
                if(poll[0] + 1 < grid[0].length && grid[poll[0]+1][poll[1]] == 1){
                    grid[poll[0]+1][poll[1]] = 2;
                    good--;
                    queue.add(new int[]{poll[0]+1,poll[1]});
                }
            }
        }

        if(good > 0) return  -1;
        return minitues;

    }


    /**
     * 寻找两个数组的中位数
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int result[] = new int[nums1.length+nums2.length];
        //归并排序
        for (int index = 0,i = 0 , j = 0; index < result.length; index++) {
            //先保证两个数组不越界
            if(i >= nums1.length){
                result[index] = nums2[j++];
            }else if(j >= nums2.length){
                result[index] = nums1[i++];
            }else if(nums1[i] > nums2[j]){
                //按照从小到大的顺序
                result[index] = nums2[j++];
            }else {
                result[index] = nums1[i++];
            }
        }
        return result.length%2 != 0 ?
                result[result.length/2] :
                (double)(result[result.length/2 -1] + result[result.length/2])/2;
    }


    class ListNode{
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    /**
     * 两数相加
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode tempNode = new ListNode(0);
        ListNode cur = tempNode;
        int jinwei = 0;
        while (l1 != null || l2 != null){
            int p = l1 != null ? l1.val : 0;
            int q = l2 != null ? l2.val : 0;
            int sum = p + q + jinwei;
            //取商
            jinwei = sum/10;
            //取余数
            int yushu = sum%10;
            cur.next = new ListNode(yushu);
            cur = cur.next;
            if(l1!= null)l1 = l1.next;
            if(l2!= null)l2 = l2.next;
        }

        if(jinwei > 0){
            cur.next = new ListNode(jinwei);
        }
        return tempNode.next;
    }


    /**
     * 分糖果
     * @param candies
     * @param num_people
     * @return
     */
    public static int[] distributeCandies(int candies, int num_people) {
        int[] result = new int[num_people];
        int first = 1;//记录改轮要分几个糖果
        //糖果大于0才分配
        while (candies > 0){
            //遍历小朋友分糖果
            for (int i = 0; i < result.length; i++) {
                //糖果数是否够分
                if(candies - first > 0){
                    //减掉分掉的糖果
                    candies-=first;
                    //对应小朋友手中的糖果数
                    result[i] = result[i]+first++;
                }else {
                    //不够分，直接把所有糖果给改小朋友
                    result[i] = result[i]+(candies);
                    //糖果数置0
                    candies = 0;
                    //跳出循环
                    break;
                }
            }
        }
        return result;
    }


    /**
     * 两数之和等于目标数
     * @param nums
     * @param target
     * @return
     */
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
    public static String convertZ(String s,int num){
        if(num == 1) return s;
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
        return strz.toString();
    }

    public static void main(String[] args) {
//        findContinuousSequence1(9);
        int a = myAtoi("   -42");
        System.out.println(a);
//        int[][] arr = {{2,1,1},{1,1,0},{0,1,1}};
//        System.out.println(orangesRotting(arr));
//        findMedianSortedArrays(new int[]{1,3,4},new int[]{1,3,4,5,7});

//        distributeCandies(60,4);
//        System.out.println(maxSub("abccabdc"));
//        sortedSquares(new int[]{-4,-1,0,3,10});
        /*int[] ints = new LeetCode().twoSum(new int[]{3,3}, 6);
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
//                result[index] = right[j++];
                result[index] = left[i++];
            else
                result[index] = right[j++];
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
        int i = 0;//窗口起始位置
        int j = 0;//窗口末位位置
        int result = 0;//结果
        Set window = new HashSet();
        while (i< n && j < n){
            if(window.contains(s.charAt(j))){
                //删除掉窗口的内容，直到重复的字符串位置
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
