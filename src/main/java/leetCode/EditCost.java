package leetCode;

import java.util.*;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/7/22 12:47
 */
public class EditCost {

    public void sortColors1(int[] nums){
        if(nums == null || nums.length == 0)return;
        int len = nums.length;
        int l = 0, r = len-1;
        for(int i = 0; i < len; i++){
            while(i <= r && nums[i] == 2){
                swap(nums,i,r--);
            }
            if(nums[i] == 0){
                swap(nums,i,l++);
            }
        }
    }

    public void swap(int[] nums, int i , int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public void sortColors(int[] nums) {
        if(nums == null || nums.length == 0)return;
        int l = 0,r = nums.length;
        int cnt0 = 0,cnt1= 0, cnt2 = 0;
        for(int i = 0; i < nums.length; i++){
            if(nums[i] == 0)cnt0++;
            if(nums[i] == 2)cnt2++;
        }
        Arrays.fill(nums,1);
        Arrays.fill(nums,0, cnt0-1,0);
        Arrays.fill(nums,nums.length-cnt2-1, nums.length-1, 2);
    }
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res = new ArrayList<>();
        if(strs == null || strs.length == 0)return res;
        Map<String,List<String>> table = new HashMap<>();
        for(String str: strs){
            List<String> t = table.getOrDefault(sort(str),new ArrayList<String>());
            t.add(str);
            table.put(sort(str),t);
        }
        return new ArrayList<>(table.values());
    }

    public String sort(String str){
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public static void main(String[] args) {
        EditCost ec =new EditCost();
        int i = ec.minDistance("horse", "ros");
        System.out.println(i);


        int[] ints = {2};
        ec.sortColors1(ints);
        System.out.println(ints);
    }


    public int minDistance(String word1, String word2) {
        if(word2 == null || word1 == null)return 0;

        char[] str1 = word1.toCharArray();
        char[] str2 = word2.toCharArray();
        int N = str1.length +1;
        int M = str2.length +1;
        int[][] dp = new int[N][M];
        dp[0][0] = 0;
        for(int i = 1; i < N; i++){
            dp[i][0] = i;
        }

        for(int j = 1; j < M; j++){
            dp[0][j] = j;
        }

        for(int i = 1; i < N; i++){
            for(int j = 1; j < M; j++){
                if(str1[i-1] == str2[j-1]){
                    dp[i][j] = dp[i-1][j-1];
                }else{
                    dp[i][j] = dp[i-1][j-1]+1;
                }
                dp[i][j] = Math.min(dp[i][j],dp[i-1][j]+1);
                dp[i][j] = Math.min(dp[i][j],dp[i][j-1]+1);
            }
        }
        return dp[N-1][M-1];
    }
}
