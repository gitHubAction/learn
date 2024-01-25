package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description leetcode 260
 * 260. 只出现一次的数字 III
 * 已解答
 * 中等
 * 相关标签
 * 相关企业
 * 给你一个整数数组 nums，其中恰好有两个元素只出现一次，其余所有元素均出现两次。 找出只出现一次的那两个元素。你可以按 任意顺序 返回答案。
 *
 * 你必须设计并实现线性时间复杂度的算法且仅使用常量额外空间来解决此问题。
 *
 *
 *
 * 示例 1：
 *
 * 输入：nums = [1,2,1,3,2,5]
 * 输出：[3,5]
 * 解释：[5, 3] 也是有效的答案。
 * 示例 2：
 *
 * 输入：nums = [-1,0]
 * 输出：[-1,0]
 * 示例 3：
 *
 * 输入：nums = [0,1]
 * 输出：[1,0]
 *
 *
 * 提示：
 *
 * 2 <= nums.length <= 3 * 104
 * -231 <= nums[i] <= 231 - 1
 * 除两个只出现一次的整数外，nums 中的其他数字都出现两次
 * @date 2024/1/25 10:17
 */
public class SingleNum {
    public static void main(String[] args) {
        singleNum(new int[]{1,1,2,2,3,3,4,5});
    }

    public static int[] singleNum(int[] nums){
        int sum = 0,k = -1;
        int[] ans = new int[2];
        // 异或去除其他重复两次的数字，剩余的两个数字的和
        for (int i: nums) {
            sum ^= i;
        }
        // 找到这两个数字不相同的位
        for (int i = 31; i >= 0; i--) {
            if((sum >> i & 1) == 1){
                k = i;
                break;
            }
        }
        for (int i:nums) {
            if((i >> k & 1) ==1) {
                ans[0]^=i;
            }
            else{
              ans[1] ^= i;
            }
        }
        return ans;
    }
}
