package leetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description 560. 和为 K 的子数组
 * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的子数组的个数 。
 * @date 2022/6/6 11:37
 */
public class SubarraySum {

    public static void main(String[] args) {
        int[] arr = {1,1,1};
        int res = subArraySum(arr, 2);
    }

    private static int subArraySum(int[] arr, int k) {
        int ans = 0, len = arr.length;
        Map<Integer,Integer> table = new HashMap<>();
        // 求前缀和
        int[] preSum = new int[len+1];
        table.put(0,1);
        for(int i = 1; i <= len; i++){
            preSum[i] = preSum[i-1] + arr[i-1];
            table.put(preSum[i], table.getOrDefault(preSum[i],0)+1);
        }
        for(int i = 1; i <= len; i++){
            for (int j = i; j <= len; j++) {
                if(preSum[j] - preSum[i-1] == k){
                    ans++;
                }
            }
        }
        return ans;
    }

    private static int subArraySum1(int[] arr, int k) {
        int ans = 0, len = arr.length;
        Map<Integer,Integer> table = new HashMap<>();
        // 求前缀和
        int[] preSum = new int[len+1];
        table.put(0,1);
        for(int i = 1; i <= len; i++){
            preSum[i] = preSum[i-1] + arr[i-1];
            ans += table.getOrDefault(preSum[i] - k, 0);
            table.put(preSum[i], table.getOrDefault(preSum[i],0)+1);
        }
        return ans;
    }
}
