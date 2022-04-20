package leetCode;

import java.security.SecureRandom;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description  5. 最长回文子串
 * @date 2022/4/13 10:35
 */
public class LongestPalindrome {

    public static void main(String[] args) {
        System.out.println(longestPalidrome("bb") == longestPalidrome1("bb"));
        System.out.println(longestPalidrome1("bb"));
    }

    /**
     *  动态规划
     * @param s
     * @return
     */
    public static String longestPalidrome(String s){
        if(s.length() < 2){
            return s;
        }
        int start = 0;
        int maxLen = 1;
        int length = s.length();
        // 动态规划数组 标识i到j上是否为回文串  i到i位置必为回文串
        boolean[][] dp = new boolean[length][length];
        for(int i = 0; i < length; i++){
            dp[i][i] = true;
        }

        for(int L = 2; L <= length; L++){
            for(int l = 0; l < length; l++){
                // 右边界r
                int r = l + L - 1;
                // 右边界是否越界
                if(r >= length)break;
                if(s.charAt(l) == s.charAt(r)){
                    if(r - l < 3){
                        dp[l][r] = true;
                    }else{
                        dp[l][r] = dp[l+1][r-1];
                    }
                }
                // 更新start和maxLen
                if(dp[l][r] && r-l+1 > maxLen){
                    start = l;
                    maxLen = r-l+1;
                }
            }
        }
        return s.substring(start, start+maxLen);
    }

    /**
     * 中心扩展动态规划
     * @param s
     * @return
     */
    public static String longestPalidrome1(String s){
        if(s.length() < 2){
            return s;
        }
        int l = 0,r = 0;
        for(int i = 0; i<s.length(); i++){
            // 以i位置为中心扩展的最大长度
            int len1 = expand(s,i,i);
            // 以 i和i+1 为中心扩展的长度
            int len2 = expand(s,i,i+1);
            int maxLen = Math.max(len1,len2);
            if(maxLen > r - l){
                // 重新计算左右边界
                l = i - (maxLen - 1) / 2;
                r = i + maxLen / 2;
            }
        }
        return s.substring(l,r+1);
    }

    private static int expand(String s, int i, int i1) {
        while(i >= 0 && i1 < s.length() && s.charAt(i) == s.charAt(i1)){
            i--;
            i1++;
        }
        return i1 - i - 1;
    }
}
