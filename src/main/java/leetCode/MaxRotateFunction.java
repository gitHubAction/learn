package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description  396. 旋转函数
 * @date 2022/4/24 10:43
 */
public class MaxRotateFunction {
    public static void main(String[] args) {
        MaxRotateFunction fun = new MaxRotateFunction();
        int res = fun.maxRotateFunction(new int[]{4,3,2,6});
        System.out.println(res);
    }

    /**
     * 数组前缀和+滑动窗口
     *  构造出原数组nums两倍的数组tNums,则对于tNumss任意从[i,i+n)都为旋转数组
     *  pre = tNums[i] * 0  + tNums[i+1] * 1 + ... + tNums[i+n-1] * (n-1);
     *  滑动窗口大小为n，每向右移动一位，则结果 为：
     *  cur =                tNums[i+1] * 0 + tNums[i+2] * 1 + .... + tNums[i+n-1] * (n-2) + tNums[i+n] * (n-1);
     *  cur = cur - tNums[i] * 0 + tNums[i+n] * (n) - (tNums[i+1] + ... + tNums[i+n-1])
     * @param nums
     * @return int
     * @author zhangshihao01
     * @date 2022/4/24 10:56
     */
    private int maxRotateFunction(int[] nums) {
        if(nums.length == 1){
            return 0;
        }
        int n = nums.length;
        int[] sums = new int[2 * n];
        // 求前缀和
        for(int i = 1; i < sums.length; i++){
            sums[i] = sums[i - 1] + nums[(i - 1) % n];
        }
        // 初始窗口的大小
        int res = 0;
        for(int i = 0; i < n; i++){
            res += nums[i] * i;
        }
        // 窗口移动并更新res
        for(int i = n, cur = res; i < 2 * n; i++){
            cur += nums[i % n] * n;
            cur -= sums[i] - sums[i-n];
            res = Math.max(cur, res);
        }
        return res;
    }
}
