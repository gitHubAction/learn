package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description 713 乘积小于K的子数组
 * @date 2022/5/5 11:43
 */
public class NumSubarrayProductLessThanK {

    public static void main(String[] args) {
//        [10,9,10,4,3,8,3,3,6,2,10,10,9,3]
        // 1 2 3    6
//19
        NumSubarrayProductLessThanK k = new NumSubarrayProductLessThanK();
        System.out.println(k.numSubarrayProductLessThanK(new int[]{10,9,10,4,3,8,3,3,6,2,10,10,9,3}, 19));
    }

    public int numSubarrayProductLessThanKByWindow(int[] nums, int k){
        if(k <= 1)return 0;
        int res = 0;
        for(int l = 0,r = 0, cur = 1; r < nums.length; r++){
            cur *= nums[r];
            while (cur >= k)cur/= nums[l++];
            res += r - l + 1;
        }
        return res;
    }

    /**
     * 模拟枚举所有结果
     * @param nums
     * @param k
     * @return
     */
    public int numSubarrayProductLessThanK(int[] nums, int k){
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            res += process(nums, i, k);
        }
        return res;
    }

    private int process(int[] nums, int i, int k) {
        int res = 0;
        int j = i;
        int t = 1;
        while(t < k && j < nums.length){
            t *= nums[j++];
            if(t >= k){
                break;
            }
            res++;
        }
        return res;
    }
}
