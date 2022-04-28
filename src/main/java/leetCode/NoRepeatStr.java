package leetCode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/4/21 10:52
 */
public class NoRepeatStr {

    public static void main(String[] args) {
        NoRepeatStr noRepeatStr = new NoRepeatStr();
        int i = noRepeatStr.lengthOfLongestSubstring("pwwkew");
    }

    // 滑动窗口
    public int lengthOfLongestSubstring(String s) {
        if(s == null || s.length() < 1)return 0;
        if(s.length()==1)return 1;
        // 窗口
        int start = 0,end = 1,result = 0;
        Set window = new HashSet();
        window.add(s.charAt(0));
        while(end < s.length()){
            if(window.contains(s.charAt(end))){
                //移动窗口左边界
                window.remove(s.charAt(start++));
            }else{
                // 移动窗口右边界
                window.add(s.charAt(end++));
                result = Math.max(result , end - start);
            }
        }
        return result;
    }
}
