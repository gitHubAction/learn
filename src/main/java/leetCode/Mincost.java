package leetCode;

import java.util.Arrays;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/4/15 18:16
 */
public class Mincost {

    public static void main(String[] args) {
        System.out.println("TXT_BTN_0".substring("TXT_BTN_0".lastIndexOf("_")+1));
        System.out.println("TXT_BTN_0".substring(0,"TXT_BTN_0".lastIndexOf("_")));
        System.out.println("recogin".lastIndexOf("_"));


        int mincost = mincost(new int[][]{{1, 2, 3, 4}, {3, 4, 2, 5}, {4, 5, 2, 6}});
        System.out.println(mincost);
    }
    /**
     * 粉刷房子的最小花费I   只能刷三种颜色
     * 动态规划
     * @param costs
     */
    public static int mincost(int[][] costs){
        int[][] dp = new int[costs.length][costs[0].length];
        for(int[] t : dp){
            Arrays.fill(t, Integer.MAX_VALUE);
        }
        for(int i = 0; i < costs[0].length; i++){
            dp[0][i] = costs[0][i];
        }

        for(int i = 1; i < costs.length; i++){
            for(int j = 0; j < costs[i].length; j++){
                for(int k = 0; k < costs[i].length; k++){
                    // 相邻房子不能选一样
                    if(j != k){
                        dp[i][j] = Math.min(dp[i][j],dp[i-1][k]+costs[i][j]);
                    }
                }
            }
        }
        int res = Integer.MAX_VALUE;
        for(int i = 0;  i < costs[0].length; i++){
            res = Math.min(res, dp[costs.length-1][i]);
        }
        return res;
    }
}
