package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/4/26 14:17
 */
public class Fib {


    /**
     * 剑指 Offer 10- I. 斐波那契数列
     * @param n
     * @return
     */
    public int fib(int n){
        if(n == 0){
            return 0;
        }
        if(n == 1 || n == 2){
            return 1;
        }
        return (fib(n-1) + fib(n -2)) % (int)1e9+7;
    }


    public int fib2(int n){
        if(n == 0)return 0;
        if(n == 1)return 1;
        int[] dp = new int[n+1];
        dp[1] = dp[2] = 1;
        for(int i = 3; i <= n; i++){
            dp[i] = dp[i-1]+dp[i-2];
        }
        return dp[n];
    }
}
