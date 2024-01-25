package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description 137
 * 137. 只出现一次的数字 II
 * 已解答
 * 中等
 * 相关标签
 * 相关企业
 * 给你一个整数数组 nums ，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次 。请你找出并返回那个只出现了一次的元素。
 *
 * 你必须设计并实现线性时间复杂度的算法且使用常数级空间来解决此问题。
 *
 *
 *
 * 示例 1：
 *
 * 输入：nums = [2,2,3,2]
 * 输出：3
 * 示例 2：
 *
 * 输入：nums = [0,1,0,1,0,1,99]
 * 输出：99
 *
 *
 * 提示：
 *
 * 1 <= nums.length <= 3 * 104
 * -231 <= nums[i] <= 231 - 1
 * nums 中，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次
 * @date 2024/1/25 10:38
 */
public class SingleNum2 {

    public static void main(String[] args) {

    }

    /**
     * 解题思路
     *      1：hash表
     *      2：位图:
     *          nums 中，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次,
     *          则除了这个数字外，其他数字的对应位的和为3的倍数，改数字每一位都是所有数字每一位的和的余数
     *      3：门电路
     */
    public static int singleNum(int[] nums) {
        int ans = 0;
        for(int k = 0; k < 32; k++){
            int sum = 0;
            for (int i : nums) {
                sum += ((i >> k) & 1);
            }
            if(sum % 3 != 0){
                ans |= (1 << k);
            }
        }
        return ans;
    }
}
