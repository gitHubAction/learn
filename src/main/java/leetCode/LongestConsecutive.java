package leetCode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description 128. 最长连续序列
 * https://leetcode.cn/problems/longest-consecutive-sequence/
 *
 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 *
 * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
 *示例 1：
 *
 * 输入：nums = [100,4,200,1,3,2]
 * 输出：4
 * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
 * 示例 2：
 *
 * 输入：nums = [0,3,7,2,5,8,4,6,0,1]
 * 输出：9
 * @date 2022/10/11 14:57
 */
public class LongestConsecutive {

    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (Integer i: nums) {
            set.add(i);
        }
        int res = 0;
        for (Integer num : nums) {
            if(set.remove(num)){
                // 沿cur往左找
                int tem = 1;
                int cur = num;
                while(set.remove(cur-1))cur--;
                tem += (num - cur);
                // 沿cur往右找
                cur = num;
                while(set.remove(cur+1))cur++;
                tem += (cur - num);
                res = Math.max(tem,res);
            }
        }
        return res;
    }
}
