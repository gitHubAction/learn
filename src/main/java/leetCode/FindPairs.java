package leetCode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description 532. 数组中的 k-diff 数对
 * 给定一个整数数组和一个整数 k，你需要在数组里找到 不同的 k-diff 数对，并返回不同的 k-diff 数对 的数目。
 *
 * 这里将 k-diff 数对定义为一个整数对 (nums[i], nums[j])，并满足下述全部条件：
 *
 * 0 <= i < j < nums.length
 * |nums[i] - nums[j]| == k
 * 注意，|val| 表示 val 的绝对值。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [3, 1, 4, 1, 5], k = 2
 * 输出：2
 * 解释：数组中有两个 2-diff 数对, (1, 3) 和 (3, 5)。
 * 尽管数组中有两个1，但我们只应返回不同的数对的数量
 * @date 2022/6/16 14:06
 */
public class FindPairs {

    public static void main(String[] args) {
        FindPairs fp = new FindPairs();
        int pairs = fp.findPairs(new int[]{1, 2, 1, 3, 4, 21, 2, 3}, 0);
        System.out.println(pairs);
    }

    public int findPairs(int[] nums, int k) {
        // 记录数对
        Set<Integer> res = new HashSet<>();
        // 防止重复记录已经计算过的数
        Set<Integer> numSet = new HashSet<>();
        for(int i = 0; i < nums.length; i++){
            if(numSet.contains(nums[i] - k)){
                res.add(nums[i]-k);
            }
            if(numSet.contains(nums[i] + k)){
                res.add(nums[i]);
            }
            numSet.add(nums[i]);
        }
        return res.size();
    }


    // 排序+双指针
    public int findPairsByPoint(int[] nums, int k){
        Arrays.sort(nums);
        int ans = 0;
        for(int i = 0, j = 1; i < nums.length-1 && j < nums.length; i++){
            if(i > 0 && nums[i] == nums[i-1]){
                continue;
            }
            while(j <= i){
                j++;
            }
            while (j < nums.length && nums[i] + k < nums[j]){
                j++;
            }
            if(j < nums.length && nums[i] + k == nums[j]){
                ans++;
            }
        }
        return ans;
    }
}
