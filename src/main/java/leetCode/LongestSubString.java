package leetCode;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: zhangsh
 * @Date: 2020/3/5 10:05
 * @Version 1.0
 * Description 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 */
public class LongestSubString {

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbbhiuytghj"));
    }

    public static int lengthOfLongestSubstring(String s) {
        //窗口法
        int start = 0,end = 0;
        Set window = new HashSet();
        int result = 0;
        while (start < s.length() && end < s.length()){
            if(window.contains(s.charAt(end))){
                window.remove(s.charAt(start++));
            }else{
                window.add(s.charAt(end++));
                result = Math.max(result,end-start);
            }
        }
        return result;
    }
}
