package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description 942. 增减字符串匹配
 * 由范围 [0,n] 内所有整数组成的 n + 1 个整数的排列序列可以表示为长度为 n 的字符串 s ，其中:
 *
 * 如果 perm[i] < perm[i + 1] ，那么 s[i] == 'I' 
 * 如果 perm[i] > perm[i + 1] ，那么 s[i] == 'D' 
 * 给定一个字符串 s ，重构排列 perm 并返回它。如果有多个有效排列perm，则返回其中 任何一个
 *
 * @date 2022/5/10 8:53
 */
public class DiStringMatch {


    /**
     *
     * @param s
     * @return
     */
    public int[] diStringMatch(String s) {
        int n = s.length();
        int min = 0, max = n, idx = 0;
        int[] res = new int[n+1];
        for(int i = 0; i < n; i++){
            res[idx++] = s.charAt(i) == 'I' ? min++ : max--;
        }
        res[idx] = min;
        return res;
    }
}
