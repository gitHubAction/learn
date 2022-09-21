package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/7/8 17:44
 */
public class TencentMusic {

    public static long process(int[] arr, int k){
        int n = arr.length;
        long[][] dp  = new long[n][k+1];

        for(int i = 1; i <= n; i++){
            dp[i][1] = 1;
        }

        for(int i = 1; i <= k; i++){
            dp[1][i] = i;
        }

        for(int i = 2; i <= n; i++){
            for(int j = 2; j <= k; j++){
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }

        long res = 1;
        for(int i = 0, j = 0; i < n; i++){
            if(arr[i] == 0){
                j = i+1;
                while(j < n && arr[j] == 0){
                    j++;
                }
                int left = i - 1 >= 0 ? arr[i-1] : 1;
                int right = j < n ? arr[j] : k;
                res *= dp[j - i][right - left + 1];
                i = j;
            }
        }
        return res;
    }

}
