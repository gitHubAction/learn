package leetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:    NumDupDigitsAtMostN
 * Package:    leetCode
 * Datetime:    2022/5/12   10:16
 * Author:   zsh
 * Description: 1012. 至少有 1 位重复的数字
 *  给定正整数 n，返回在 [1, n] 范围内具有 至少 1 位 重复数字的正整数的个数。
 *  思路：递归
 */
public class NumDupDigitsAtMostN {
    public static void main(String[] args) {
        NumDupDigitsAtMostN n = new NumDupDigitsAtMostN();
        System.out.println(n.numDupDigitsAtMostN(7022376));
    }

    public int numDupDigitsAtMostN(int n) {
        int[] dp = new int[n+1];
        dp[0] = 0;
        for(int i = 1; i < dp.length; i++){
            if(isValid(i)){
                dp[i] = dp[i-1]+1;
            }else{
                dp[i] = dp[i-1];
            }
        }
        return dp[n];
    }

    private boolean isValid(int i) {
        if(i < 10)return false;
        Map<Integer,Integer> map = new HashMap<>();
        while(i != 0){
            Integer integer = Integer.valueOf(i % 10);
            if(map.containsKey(integer))return true;
            map.put(integer, 1);
            i /= 10;
        }
        return false;
    }
}
