package leetCode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * 139. 单词拆分
 * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。请你判断是否可以利用字典中出现的单词拼接出 s 。
 *
 * 注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
 *
 *
 *
 * 示例 1：
 *
 * 输入: s = "leetcode", wordDict = ["leet", "code"]
 * 输出: true
 * 解释: 返回 true 因为 "leetcode" 可以由 "leet" 和 "code" 拼接成。
 * 示例 2：
 *
 * 输入: s = "applepenapple", wordDict = ["apple", "pen"]
 * 输出: true
 * 解释: 返回 true 因为 "applepenapple" 可以由 "apple" "pen" "apple" 拼接成。
 *      注意，你可以重复使用字典中的单词。
 * 示例 3：
 *
 * 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * 输出: false
 * @date 2022/10/11 15:54
 */
public class WordBreak {


    public static void main(String[] args) {
        WordBreak wb = new WordBreak();

        wb.wordBreak("catsandog", Arrays.asList("cats", "dog", "sand", "and", "cat"));
        System.out.println(wb.wordBreakDFS("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab"
                , Arrays.asList("a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa")));


        wb.wordbreakdfs("a",Arrays.asList("aaaaaaaaaa","aaa"));
    }

    public boolean wordbreakdfs(String s, List<String> wordDict){
        boolean[] mem = new boolean[s.length()];
        return breakdfs(0,s,wordDict,mem);
    }

    private boolean breakdfs(int i, String s, List<String> wordDict, boolean[] mem) {
        for (String word : wordDict) {
            int next = i + word.length();
            if(next > s.length() || mem[next]){
                continue;
            }
            if(word.equals(s.substring(i,next))){
                if(next == s.length() || breakdfs(next,s,wordDict,mem)){
                    return true;
                }
                mem[next] = true;
            }
        }
        return false;
    }

    public boolean wordBreakDFS(String s, List<String> wordDict){
        Boolean[] mem = new Boolean[s.length()];
        return dfs(0,s,wordDict,mem);
    }

    public boolean dfs(int start, String s, List<String> wordDict, Boolean[] mem){
        if(start == s.length())return true;
        if(mem[start] != null)return mem[start];
        for(int i = start+1; i <= s.length(); i++){
            String prefix = s.substring(start,i);
            if(wordDict.contains(prefix) && dfs(i,s, wordDict,mem)){
                mem[start] = true;
                return true;
            }
        }
        mem[start] = false;
        return false;
    }

    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length()+1];
        // dp[i] 长度为i（0，i-1）的子串是否能拆成单词表中的单词
        // 边界条件
        dp[0] = true;
        Set<String> set = new HashSet<>(wordDict);
        for(int i = 1; i <= s.length(); i++){
            for(int j = 0; j < i; j++){
                if(dp[j] && set.contains(s.substring(j,i))){
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }
}
