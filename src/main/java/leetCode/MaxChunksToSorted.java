package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description https://leetcode.cn/problems/max-chunks-to-make-sorted/
 * 769. 最多能完成排序的块
 * 给定一个长度为 n 的整数数组 arr ，它表示在 [0, n - 1] 范围内的整数的排列。
 *
 * 我们将 arr 分割成若干 块 (即分区)，并对每个块单独排序。将它们连接起来后，使得连接的结果和按升序排序后的原数组相同。
 *
 * 返回数组能分成的最多块数量。
 *
 *
 *
 * 示例 1:
 *
 * 输入: arr = [4,3,2,1,0]
 * 输出: 1
 * 解释:
 * 将数组分成2块或者更多块，都无法得到所需的结果。
 * 例如，分成 [4, 3], [2, 1, 0] 的结果是 [3, 4, 0, 1, 2]，这不是有序的数组。
 * 示例 2:
 *
 * 输入: arr = [1,0,2,3,4]
 * 输出: 4
 * 解释:
 * 我们可以把它分成两块，例如 [1, 0], [2, 3, 4]。
 * 然而，分成 [1, 0], [2], [3], [4] 可以得到最多的块数。
 * @date 2022/10/13 14:51
 */
public class MaxChunksToSorted {


    // 对于区间[j,i]上，当且仅当 最小值min=j && 最大值 max=i 时进行排序后结果为  [min,max]
    public int maxChunksToSorted(int[] nums){
        int res = 0;
        int n = nums.length,min = n,max = -1,j = 0;
        for(int i = 0; i < n; i++){
            min = Math.min(nums[i],min);
            max = Math.max(nums[i],max);
            if(j == min && i == max){
                min = n;
                max = -1;
                j = i+1;
                res++;
            }
        }
        return res;
    }

    // 贪心算法，
    public int maxChunksToSorted1(int[] nums){
        int res = 0,max = -1;
        for(int i = 0; i < nums.length; i++){
            max = Math.max(nums[i],max);
            if(max == i){
                res++;
            }
        }
        return res;
    }
}
