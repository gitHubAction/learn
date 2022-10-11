package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description 96. 不同的二叉搜索树
 * https://leetcode.cn/problems/unique-binary-search-trees/
 *
 * 给你一个整数 n ，求恰由 n 个节点组成且节点值从 1 到 n 互不相同的
 * 二叉搜索树 有多少种？返回满足题意的二叉搜索树的种数。
 *
 * @date 2022/10/11 14:01
 */
public class NumTrees {

    // 对于节点n,以i作为头结点，左树节点个数为 i-1, 右树为 n-i
    // G(n) = f(0) + f(1) + f(2) + ..+ f(i) +.. + f(n);
    // f(i) 表示以i为头结点，总共有多少种结果
    // f(i) = G(i-1) * G(n-i);
    // f(0) = G(0) = 0;
    // G(n) = G(0) * G(n-1) + G(1) * G(n-2) + G(2) * G(n-3) + .... + G(n-1) * G(0)
    public int numTrees(int n){
        int[] dp = new int[n+1];
        dp[0] = dp[1] = 1;
        for(int i = 2; i < n+1; i++){
            for(int j = 1; j < i+1; j++){
                dp[i] += dp[j-1] * dp[i-j];
            }
        }
        return dp[n];
    }
}
