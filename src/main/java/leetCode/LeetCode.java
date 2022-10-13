package leetCode;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.text.Collator;
import java.util.*;

/**
 * @Author: zhangsh
 * @Date: 2019/7/19 11:53
 * @Version 1.0 链表
 * Description
 */
public class LeetCode {


    /**
     * 暴力解会栈溢出
     * @param n
     * @return
     */
    public static String nearestPalindromic(String n) {
        if(n == null || n == ""){
            return "";
        }
        long a = Long.valueOf(n);

        long left = process(a - 1, false);
        long right = process(a + 1,true);
        if(Math.abs(a - left) < Math.abs(a - right)){
            return left+"";
        }else if(Math.abs(a - left) == Math.abs(a - right)){
            return Math.min(left,right)+"";
        }else{
            return right+"";
        }
    }

    public static long process(long a, boolean flag){
        if(ishuiwen(a)){
            return a;
        }
        return process(flag ? a+1 : a-1, flag);
    }

    public static boolean ishuiwen(long a){
        String temp = a+"";
        int i = 0;
        int j = temp.length() -1;
        while(i < j){
            if(temp.charAt(i++) != temp.charAt(j--)){
                return false;
            }
        }
        return true;
    }

    /**
     * 1 如果n为1位数，则直接减1即为最小的且离n最近的回文数
     * 2 获取到n的一半，构造回文数（注意n长度的奇偶性）
     * 3 获取到n的一半-1，构造回文数
     * 4 获取到n的一半+1，构造回文数
     * 5 获取到n位数少一位的最大值
     * 6 获取到n位数大一位的最小值
     * 7 求上述5个数的最小且离n最近的数
     * @param n
     * @return java.lang.String
     * @author zhangshihao01
     * @date 2022/3/3 10:11
     */
    public static String nearestPalindromic1(String n){
        if(n.length() == 1){
            return String.valueOf(Long.parseLong(n) - 1);
        }
        int nLen = n.length();
        // 是否为奇数
        boolean odd = (nLen & 1) == 1;
        String half = n.substring(0, (nLen >> 1) + (odd ? 1 : 0));
        long a = getH(half, odd);
        long b = getH(String.valueOf(Long.parseLong(half) - 1), odd);
        long c = getH(String.valueOf(Long.parseLong(half) + 1), odd);
        long d = (long)Math.pow(10, nLen - 1) + 1;
        long e = (long)Math.pow(10, nLen) - 1;
        long nL = Long.parseLong(n);
        b = getMinAndDisMin(b, a, nL);
        b = getMinAndDisMin(b, c, nL);
        b = getMinAndDisMin(b, d, nL);
        b = getMinAndDisMin(b, e, nL);
        return String.valueOf(b);
    }

    private static long getH(String half, boolean odd) {
        StringBuilder res = new StringBuilder(half);
        int hlen = half.length()-1;
        hlen += odd ? -1 : 0;
        while(hlen >= 0){
            res.append(half.charAt(hlen--));
        }
        return Long.parseLong(res.toString());
    }

    private static long getMinAndDisMin(long res, long a, long nL) {
        if(a != nL){
            if(Math.abs(nL-a) < Math.abs(nL - res)){
                return a;
            }else if(Math.abs(nL-a) > Math.abs(nL - res)){
                return res;
            }else{
                return Math.min(res,a);
            }
        }
        return res;
    }


    /**
     * 给你一个整形数组nums，在数组中找出有三个数组成的最大乘积，并输出这个乘积
     * 实例1：
     *  输入 nums=[1,2,3]
     *  输出 6
     *
     * 实例2：
     *  输入：nums=[1,2,3,4]
     *  输出：24
     *
     * 示例3：
     *  输入：nums=[-1,-2,-3,4]
     *  输出：24
     * @param nums
     * @return
     * @author zhangshihao01
     * @date 2022/3/1 13:06
     */
    public static int findThreeMutilMax(int[] nums){
        if(nums == null || nums.length < 3){
            return 0;
        }
        Arrays.sort(nums);
        int a = nums[0] * nums[1] * nums[nums.length-1];
        int b = nums[nums.length-1] * nums[nums.length-2] * nums[nums.length-3];
        return Math.max(a,b);
    }


    public static String complexNumberMultiply(String num1, String num2) {
        // 获取两个数的实部和虚部
        int[] arr1 = split(num1);
        int[] arr2 = split(num2);
        // 最后的虚部
        int b = arr1[0] * arr2[1] + arr1[1] * arr2[0];
        // 最后的实部
        int a = arr1[0] * arr2[0] + (arr1[1] * arr2[1] * -1);
        // 构造最后的字符串
        return a + "+" + (b) +"i";
    }

    public static int[] split(String num){
        String[] str = num.split("\\+");
        str[1] = str[1].substring(0,str[1].length()-1);
        return new int[]{Integer.valueOf(str[0]),Integer.valueOf(str[1])};
    }


    public static int[] findBall(int[][] grid){
        if(grid == null || grid.length < 1){
            return new int[]{-1};
        }
        int n = grid[0].length;
        int rows = grid.length;
        int[] res = new int[n];
        for(int i = 0; i < n; i++){
            System.out.println("第"+(i+1)+"个球");
            res[i] = dfs(grid,i,0,n,rows);
        }
        return res;
    }

    public static int dfs(int[][] grid, int ball, int curRow,int cols, int rows){
        // 左边界位置
        if(ball == 0 && grid[curRow][ball] == -1){
            return -1;
        }
        // 右边界位置
        if(ball == cols - 1 && grid[curRow][ball] == 1){
            return -1;
        }
        // 形成了V字形
        if(grid[curRow][ball] != grid[curRow][ball+grid[curRow][ball]]){
            return -1;
        }
        // 最后一行了
        if(curRow == rows -1){
            return ball + grid[curRow][ball];
        }
        // 没有到最后一行继续dfs
        return dfs(grid, ball+grid[curRow][ball], curRow+1, cols, rows);
    }



    public static boolean isPalindrome(int x) {
        if(x < 0){
            return false;
        }
        int res = 0;
        int temp = x;
        while(temp != 0){
            if(res < Integer.MIN_VALUE / 10 || res > Integer.MAX_VALUE / 10){
                return false;
            }
            int dig = temp % 10;
            temp /= 10;
            res = res * 10 + dig;
        }
        return res == x;
    }



    public static int reverse(int x){
        int rev = 0;
        while (x != 0) {
            if (rev < Integer.MIN_VALUE / 10 || rev > Integer.MAX_VALUE / 10) {
                return 0;
            }
            int digit = x % 10;
            x /= 10;
            rev = rev * 10 + digit;
        }
        return rev;
    }

    public static double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        if((nums1 == null || nums1.length == 0) && (nums2 == null || nums2.length == 0)){
            return 0;
        }
        // 合并数组
        int[] help = new int[nums1.length + nums2.length];
        int p1 = 0;
        int p2 = 0;
        int i = 0;
        while(p1 < nums1.length && p2 < nums2.length){
            help[i++] = nums1[p1] <= nums2[p2] ? nums1[p1++] : nums2[p2++];
        }
        while(p1 < nums1.length){
            help[i++] = nums1[p1++];
        }
        while(p2 < nums2.length){
            help[i++] = nums2[p2++];
        }
        // 求合并后的中位数
        int mid = help.length >> 1;
        if(help.length % 2 == 0){
            return (help[mid] + help[mid-1])/2.0;
        }else{
            return help[mid];
        }
    }




    /*（一）渔民小渔买了一个新的鱼群探测器，可以探测到海底的地形以及鱼群分布。

    探测器返回的数据为一个字符串，格式例如 "[[1,2,...],3,...]"。其中的方括号嵌套层数代表深度，数字代表鱼群数量。

    小渔想知道海底中不同的深度分别分布的鱼群数量之和，你能帮帮他吗？

    注意：返回结果需包含所有在 fish 中出现的深度，即使在该深度鱼群数量为零或者为空



    示例 1:

    输入: fish = "[[1,1],2,[1,1]]"
    输出: [2,4]
    解释: 在深度为 1 的地方有两个鱼群，在深度为 2 的地方有四个鱼群（四个 1 ），返回 [2, 4]
    示例 2:

    输入: fish = "[1,[4,[6]]]"
    输出: [1,4,6]
    解释: 在深度为 1 的地方有一个鱼群，在深度为 2 的地方有四个鱼群，在深度为 3 的地方有六个鱼群，返回 [1, 4, 6]
    示例 3:

    输入: fish = "[[[1,[]],0]]"
    输出: [0,0,1,0]
    解释: 在深度为 1 和 4 的地方没有鱼群，在深度为 2 的地方有 0 个鱼群，在深度为 3 的地方有 1 个鱼群，返回 [0,0,1,0]


    提示：

            2 <= fish.length <= 3*10^3
    fish[i] 仅由数字、逗号、方括号组成 且每个数字均 0 <= digit <= 10^6
    题目保证输入数据的方括号符合匹配规则*/
    public static Integer[] scan(String fish){
        if(StringUtils.isEmpty(fish) || "[]".equals(fish)){
            return new Integer[]{0};
        }
        Map<String, Integer> map = new TreeMap<>();
        // 利用栈结构
        Stack<Character> stack = new Stack<>();
        int i = 0;
        // 深度
        int dig = 0;
        while (i < fish.length()){
            char c = fish.charAt(i++);
            if(',' == c){
                continue;
            }
            if('[' == c){
                stack.push(c);
                map.putIfAbsent(""+(dig++),0);
            }
            if(']' == c){
                // 将 '[' 弹出
                stack.pop();
                // 深度-1
                dig--;
                continue;
            }
            // 数值
            if(Character.isDigit(c)){
                map.put((dig-1)+"",map.get((dig-1)+"") + (c - '0'));
            }
        }
        return map.values().toArray(new Integer[map.values().size()]);
    }




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


    static class ListNode{
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
        ListNode(){}
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


    public static String reverseOnlyLetters(String s) {
        int p1 = 0;
        int p2 = s.length() -1;
        char[] res = s.toCharArray();
        while(p1 != p2){
            // p1位置是不是英文字母
            // p2位置是不是英文字母
            // 如果p1不是，p1++;p2不位置不动
            if(Character.isLetter(res[p1]) && Character.isLetter(res[p2])){
                // 互换
                swap(res, p1++, p2--);
                continue;
            }
            if(Character.isLetter(res[p1]) && !Character.isLetter(res[p2])){
                p2--;
                continue;
            }
            if(!Character.isLetter(res[p1]) && Character.isLetter(res[p2])){
                p1++;
                continue;
            }
            if(!Character.isLetter(res[p1]) && !Character.isLetter(res[p2])){
                p1++;
                p2--;
                continue;
            }
        }
        return new String(res);
    }

    public static void swap(char[] arr, int i, int j){
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static String reversePrefix(String word, char ch) {
        if(!word.contains(ch+"")){
            return word;
        }
        Stack<Character> stack = new Stack<>();
        int i = 0;
        for(char t : word.toCharArray()){
            i++;
            stack.push(t);
            if(t == ch){
                break;
            }
        }
        StringBuilder res = new StringBuilder();
        while(!stack.isEmpty()){
            res.append(stack.pop());
        }
        res.append(word.substring(i));
        return res.toString();
    }

    public static int countGoodRectangles(int[][] rectangles) {
        if(rectangles == null || rectangles.length < 1){
            return 0;
        }
        Map<String,Integer> map = new HashMap<>();
        Integer maxLen = Math.min(rectangles[0][0], rectangles[0][1]);
        for(int i = 0; i < rectangles.length; i++){
            Integer t = Math.min(rectangles[i][0], rectangles[i][1]);
            maxLen = Math.max(t, maxLen);
            if(map.keySet().contains(t+"")){
                map.put(t+"", map.get(t+"")+1);
            }else {
                map.put(t+"", 1);
            }
        }
        return map.get(maxLen+"") == null ? 0 :  map.get(maxLen+"");
    }


    public static String longestNiceSubstring(String s) {
        if (s.length() < 2) {
            return "";
        }
        Set<Character> cache = new HashSet<>();
        for (char ch : s.toCharArray()) {
            cache.add(ch);
        }

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (cache.contains(Character.toUpperCase(ch))
                    && cache.contains(Character.toLowerCase(ch))) {
                continue;
            }
            String s1 = longestNiceSubstring(s.substring(0, i));
            String s2 = longestNiceSubstring(s.substring(i + 1));
            return s1.length() >= s2.length() ? s1 : s2;
        }
        return s;
    }

    public static int threeSumClosest(int[] nums, int target) {
        if(nums == null || nums.length < 1){
            return -1;
        }
        // 排序
        Arrays.sort(nums);
        int dis = Integer.MAX_VALUE;
        int res = 0;
        // 暴力找到所有的结果 求最接近的
        for(int i = 0; i < nums.length; i++){
            for(int j = i+1; j < nums.length; j++){
                for(int k = j+1; k < nums.length; k++){
                    int temp = nums[i] + nums[j] + nums[k];
                    int tempDis = Math.abs(temp- target);
                    if(Math.min(tempDis, dis) == tempDis){
                        dis = tempDis;
                        res = temp;
                    }
                }

            }
        }
        return res;
    }

    public static long subArrayRanges(int[] nums) {
        if(nums == null || nums.length < 1){
            return 0L;
        }
        long res = 0L;
        // 枚举所有子数组并累加范围和
        int childLen = 2;

        while(childLen <= nums.length){
            int p = 0;
            int p1 = p + childLen - 1;
            while(p1 < nums.length){
                res+= sum(nums, p++ , p1++);
            }
            childLen++;
        }
        return res;
    }

    public static long sum(int[] nums, int p, int p1){
        // p -- p1 上最大减最小的和
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for(int i = p; i <= p1; i++){
            min = Math.min(nums[i],min);
            max = Math.max(nums[i],max);
        }
        return (max - min);
    }


    public static long minimumTime(int[] time, int totalTrips) {
        // 二分查找  下届为花费最小的时间1，上届为完成一趟旅途话费时间最长的公交车完成totalTrips的时间
        Arrays.sort(time);
        long left = 0;
        // 记录当前最大完成旅途的时间
        long right = 1L*  time[0] * totalTrips ;
        // 在最小时间和最大时间之间搜索符合条件的时间
        while (left < right ){
            long mid = left + (right - left) /2;
            // 记录当前完成旅途的车
            long trips = 0;
            // 遍历每个车次需要完成的时间
            for(int t : time){
                if(mid < t){
                    break;
                }
                // 记录当前时间能完成的趟数
                trips += mid / t;
            }
            // 如果当前完成的车次已经到达了完成的次数则缩小范围 搜索前面时间范围
            if(trips >= totalTrips){
                right = mid;
            } else {
                // 反之搜索后面时间范围
                left = mid + 1;
            }
        }
        return left;
    }

    public static int[] platesBetweenCandles(String s, int[][] queries) {
        if(s == null || s == null){
            return new int[queries.length];
        }
        if(!s.contains("|") || !s.contains("*")){
            return new int[queries.length];
        }
        int[] res = new int[queries.length];
        for(int i = 0; i < queries.length; i++){
            res[i] = func(queries[i], s);
        }
        return res;
    }

    public static int func(int[] query, String s){
        int p = query[0];
        int p1 = query[1];
        String t = s.substring(p,p1+1);
        if(!t.contains("|") || !t.contains("*")){
            return 0;
        }
        int res = 0;
        while(p < p1){
            if(s.charAt(p) == '|' && s.charAt(p1) == '|'){
                for(int i = p+1; i < p1; i++){
                    if(s.charAt(i) == '*'){
                        res++;
                    }
                }
                break;
            }
            if(s.charAt(p) == '*'){
                p++;
            }
            if(s.charAt(p1) == '*'){
                p1--;
            }
        }
        return res;
    }


    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode res = new ListNode();
        ListNode resT = res;
        // 指针
        ListNode temp = head;
        Stack<ListNode> stack = new Stack();
        int t = k;
        while(temp != null){
            stack.push(temp);
            temp = temp.next;
            t--;
            if(t == 0){
                t = k;
                while(!stack.isEmpty()){
                    resT.next = stack.pop();
                    resT = resT.next;
                }
                resT.next = null;
            }
        }
        // 最后的可能不够k个
        while(stack.size() > 1){
            stack.pop();
        }
        if(!stack.isEmpty()){
            resT.next = stack.pop();
        }
        return res.next;
    }

    public static String[] findRestaurant(String[] list1, String[] list2) {
        if(list1 == null || list1.length < 1){
            return new String[1];
        }
        if(list1 == null || list1.length < 1){
            return new String[1];
        }
        Integer minSum = Integer.MAX_VALUE;
        Map<Integer,List<String>> map = new HashMap<>();
        for(int i = 0; i < list1.length; i++){
            for(int j = 0; j < list2.length; j++){
                if(list1[i] == list2[j]){
                    List<String> temp = map.get(i+j);
                    if(temp == null || temp.size() == 0){
                        temp = new ArrayList<>();
                    }
                    temp.add(list1[i]);
                    map.put(j+i, temp);
                    minSum = Math.min(j+i, minSum);
                }
            }
        }
        return map.get(minSum) == null ? null : map.get(minSum).toArray(new String[map.get(minSum).size()]);
    }

    public static List<String> letterCombinations(String digits) {
        if(digits.length() == 0){
            return new ArrayList<String>();
        }
        Map<String,List<String>> temp = new HashMap<>();
        temp.put("2",Arrays.asList("a","b","c"));
        temp.put("3",Arrays.asList("d","e","f"));
        temp.put("4",Arrays.asList("g","h","i"));
        temp.put("5",Arrays.asList("j","k","l"));
        temp.put("6",Arrays.asList("m","n","o"));
        temp.put("7",Arrays.asList("p","q","r","s"));
        temp.put("8",Arrays.asList("t","u","v"));
        temp.put("9",Arrays.asList("w","x","y","z"));

        List<String> res = new ArrayList<>();
        // dfs
        dfs(digits, 0, new StringBuilder(), res, temp);
        return res;
    }

    public static void dfs(String digits,int index, StringBuilder sb, List<String> res, Map<String,List<String>> map){
        if(index > digits.length() - 1){
            res.add(sb.toString());
            return;
        }
        String a = digits.charAt(index)+"";
        List<String> t = map.get(a);
        for(int i = 0; i < t.size(); i++){
            sb.append(t.get(i));
            dfs(digits, index+1, sb, res, map);
            // 删除刚添加的
            sb.deleteCharAt(sb.length()-1);
        }
    }


    public static boolean isValid(String s) {
        if(s == null || s == "" || s.length() < 2){
            return false;
        }
        // 奇数长度直接返回false
        if(s.length() % 2 != 0){
            return false;
        }
        int l = 0, r = s.length()-1;
        Stack<Character> stack = new Stack<>();
        while(l <= r){
            if(stack.isEmpty()){
                stack.push(s.charAt(l++));
            }else{
                if(func(stack.peek(), s.charAt(l))){
                    stack.pop();
                    l++;
                }else{
                    stack.push(s.charAt(l));
                    l++;
                }
            }
        }
        return stack.isEmpty();
    }

    public static boolean func(Character a, Character b){
        if(a == '(' && b == ')'){
            return true;
        }
        if(a == '[' && b == ']'){
            return true;
        }
        if(a == '{' && b == '}'){
            return true;
        }
        return false;
    }


    public static int longestValidParentheses(String s) {
        if(s == "" || s == null){
            return 0;
        }
        Stack<Character> stack = new Stack();
        Map<Character,Character> map = new HashMap<>();
        map.put(')','(');
        for(int i = 0; i < s.length(); i++){
            char t = s.charAt(i);
            if(map.containsKey(t)){
                if(stack.isEmpty() || stack.peek() != map.get(t)){
                    continue;
                }
                stack.push(t);
            }else{
                if(stack.isEmpty() || stack.peek() != map.get(s.charAt(i-1))){
                    stack.push(t);
                }
            }
        }
        return stack.size();
    }


    public static int maxSubArray(int[] nums) {
        int pre = 0, maxAns = nums[0];
        for (int x : nums) {
            pre = Math.max(pre + x, x);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }
    public static int findKthNumber(int n, int k) {
        Integer[] t = new Integer[n+1];
        for(int i = 0; i <= n; i++){
            t[i] = i;
        }
        Arrays.sort(t, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return String.valueOf(o1).compareTo(String.valueOf(o2));
            }
        });
        int i = 1;
        int res = 0;
        while(i <= k){
            res = t[i++];
        }
        return res;
    }

    public static int search(int[] nums, int target) {
        // 先找到k位置
        int l = 0, r = nums.length-1;
        while(l <= r){
            int mid = l + ((r - l) >> 1);
            if(nums[mid] == target){
                return mid;
            }
            // 往左找还是往右找
            if(nums[mid] > target){
                r = mid - 1;
            }else{
                l = mid + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
        for (Map.Entry e: entries
             ) {
            System.out.println(e.getKey());
        }
        System.out.println((false || true) && (false || true));
//        int[] test1 = {1,3};
//        int ress = search(test1, 1);
//        System.out.println(ress);
//
//        System.out.println(findKthNumber(1,1));
//
//        AllOne obj = new AllOne();
//        obj.inc("a");
//        obj.inc("b");
//        obj.inc("b");
//        obj.inc("c");
//        obj.inc("c");
//        obj.inc("c");
//        obj.dec("b");
//        obj.dec("b");
//        System.out.println(obj.getMinKey());
//        obj.dec("a");
//        System.out.println(obj.getMaxKey());
//        System.out.println(obj.getMinKey());
//        int[] sub = new int[]{-2,1,-3,4,-1,2,1,-5,4};
//        int i = maxSubArray(sub);
//        System.out.println(i);
//        int s = longestValidParentheses(")()())");
//        System.out.println(s);
//
//        boolean valid = isValid("({[]})");
//        System.out.println(valid);
////        List<String> strings = letterCombinations("999");
////        System.out.println(strings);
//        System.out.println("()".contains("()"));
//        System.out.println("()())(".contains("()"));
//
////        String[] testa = {"Shogun","Tapioca Express","Burger King","KFC"};
////        String[] testB = {"Piatti","The Grill at Torrey Pines","Hungry Hunter Steakhouse","Shogun"};
////        String[] restaurant = findRestaurant(testa, testB);
////        System.out.println(restaurant);
//        System.out.println(3 ^ 0);
//        System.out.println(3 ^ 1);
//
//        Queue<Node> q = new LinkedList<>();
//        q.poll();
//
//        Node a = new Node(1);
//        Node b = new Node(2);
//        Node c = new Node(3);
//        Node d = new Node(4);
//        Node e = new Node(5);
//        a.next = b;
//        ArrayList<Integer> integers = Lists.newArrayList(1);
//        integers.remove(integers.size()-1);
//        int[][] arr = new int[1][1];
////        a.next.next = c;
////        a.next.next.next =d;
////        a.next.next.next.next = e;
//        Node listNode = reverseKGroup(a, 2);
//        System.out.println(listNode);
//        ArrayList<String> a1 = Lists.newArrayList("a");
//        a1.toArray(new String[a1.size()]);
//
//
////        ArrayList<String> strings = Lists.newArrayList("a", "b");
////        List<String> b1 = new ArrayList<>();
////        System.out.println(strings.containsAll(b1));
////        String s = "|||||*|||*|||*||||*||||**|*|||**|**||**|||*|||*||***||*|*||";
////        int[][] arr = {{6,57}};
////        System.out.println(platesBetweenCandles(s, arr)[0]);
////        System.out.println(s.contains("|"));
//
//        long right =1L * 10000000 * 10000;
//        long left = 10000000 * 10000 * 1L;
//        System.out.println(right);
//        System.out.println(left);
//        System.out.println(right == left);
////        int[] t = {1,2,3};
////        long l = minimumTime(t, 5);
////        System.out.println(l);
////        long l = subArrayRanges(t);
////        System.out.println(l);
////        System.out.println(3<<0);
////        System.out.println(nearestPalindromic1("123"));
////        System.out.println(nearestPalindromic("123"));
////        Solution1.Node b = new Solution1.Node(1);
////        Solution1.Node[] arr = new Solution1.Node[]{null,b};
////        System.out.println(Solution1.mergeKLists(arr));
//
//
//
//
////        int[] a3 = {1,1,-1};
////        System.out.println(threeSumClosest(a3,2));
////
////        int[] a1 = {-5,-1,-2,-4,4};
////        System.out.println(findThreeMutilMax(a1));
////        Solution s = new Solution();
////        int[][] a = {{1,0,7},{2,0,6},{3,4,5},{0,3,0},{9,0,20}};
////        System.out.println(s.getMaximumGold(a));
////        int[][] a = {{5,8},{3,9},{5,12},{16,5}};
////        System.out.println(countGoodRectangles(a));
////        System.out.println(reversePrefix("abcd",'z'));
////        System.out.println(longestNiceSubstring("abASs"));
////        System.out.println(complexNumberMultiply("1+-1i",
////                "1+-1i"));
////        int[][] a = {{1,1,1,1,1,1},{-1,-1,-1,-1,-1,-1},{1,1,1,1,1,1},{-1,-1,-1,-1,-1,-1}};
////        int[][] a = {{1,1,1,-1,-1},{1,1,1,-1,-1},{-1,-1,-1,1,1},{1,1,1,1,-1},{-1,-1,-1,-1,-1}};
////        int[] ball = findBall(a);
////        System.out.println(ball);
////        System.out.println('A' - 'a');
//        System.out.println(reverseOnlyLetters("ab-cd"));
////
////        System.out.println(isPalindrome(121));
////        System.out.println(reverse(-2147483412));
////        System.out.println(findMedianSortedArrays1(new int[]{1,2}, new int[]{3,4}));
//////        findContinuousSequence1(9);
//////        int a = myAtoi("   -42");
//////        System.out.println(a);
//////        int[][] arr = {{2,1,1},{1,1,0},{0,1,1}};
//////        System.out.println(orangesRotting(arr));
//////        findMedianSortedArrays(new int[]{1,3,4},new int[]{1,3,4,5,7});
////
//////        distributeCandies(60,4);
//////        System.out.println(maxSub("abccabdc"));
//////        sortedSquares(new int[]{-4,-1,0,3,10});
////        /*int[] ints = new LeetCode().twoSum(new int[]{3,3}, 6);
////        for (int i:ints) {
////            System.out.println(i);
////        }*/
////
////        /*Integer a = null;
////        System.out.println(a == null);
////        System.out.println(maxSub("acdefasc"));
////
////
////
////        int[] ints1 = selectionSort(new int[]{4, 3, 5, 2, 1, 10, 9, 2, 6, 7, 8, 9, 5});
////        for (int i:ints1) {
////            System.out.print(i+",");
////        }
////
////        int[] ints = mergeSort(new int[]{4, 3});
//////        mergeSort(new int[]{4, 3, 5, 2, 1, 10, 9, 2, 6, 7, 8, 9, 5});
////        for (int i:ints) {
////            System.out.print(i+"、");
////        }
////
////        BigDecimal bd = new BigDecimal("80.88");
////        System.out.println(bd.divide(new BigDecimal(2)));*/
////
////     /*   int[] arrs = {9, 3, 7, 6, 10, 23, 12};
////        int[] ints = quickSort(arrs, 0, arrs.length - 1);
////        for (int i = 0; i < arrs.length; i++) {
////            System.out.println(arrs[i]);
////        }*/
//////        System.out.println(largestPerimeter(new int[]{2,1,2}));
////
////        String[] jobParam = new String[3];
////        for (int i = 0; i < jobParam.length; i++) {
////            System.out.println(jobParam[i]);
////        }
////
////        System.out.println(DateUtil.format(DateUtil.parse("2020-11-26","yyyy-MM-dd"),DatePattern.NORM_DATETIME_PATTERN));
////
////
////        Date endDate = new Date();
////        DateTime startDate = DateUtil.offsetMonth(endDate, -12);
////        String startDateStr = DateUtil.format(startDate, DatePattern.NORM_DATETIME_PATTERN);
////        String endDateStr = DateUtil.format(endDate, DatePattern.NORM_DATETIME_PATTERN);
////        System.out.println(String.format("startDate [{%s}]  endDate [{%s}]", startDateStr, endDateStr));
//////        Integer[] scan = scan("[[1,1],2,[1,1]]");
//////        System.out.println(scan.toString());
////        String a = "1,2, ";
////        String[] split = a.split(",");
////        Integer.valueOf(split[2]);
////        System.out.println(split.length);
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
