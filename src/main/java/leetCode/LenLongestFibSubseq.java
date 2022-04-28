package leetCode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description 剑指 Offer II 093. 最长斐波那契数列
 *
 * 如果序列 X_1, X_2, ..., X_n 满足下列条件，就说它是 斐波那契式 的：
 *
 * n >= 3
 * 对于所有 i + 2 <= n，都有 X_i + X_{i+1} = X_{i+2}
 * 给定一个严格递增的正整数数组形成序列 arr ，找到 arr 中最长的斐波那契式的子序列的长度。如果一个不存在，返回  0 。
 *
 * （回想一下，子序列是从原序列  arr 中派生出来的，它从 arr 中删掉任意数量的元素（也可以不删），而不改变其余元素的顺序。例如， [3, 5, 8] 是 [3, 4, 5, 6, 7, 8] 的一个子序列）
 *
 * @date 2022/4/26 14:55
 */
public class LenLongestFibSubseq {

    public int lenLongestFibSubseq(int[] arr){
        Set<Integer> table = new HashSet<>();
        for(int i : arr){
            table.add(i);
        }
        int res = 0;
        for(int i = 0; i < arr.length; i++){
            for(int j = i+1; j < arr.length; j++){
                int x = arr[i];
                int y = arr[j];
                int len = 2;
                while(table.contains(x+y)){
                    int t = x + y;
                    x = y;
                    y = t;
                    len++;
                }
                if(len >= 3){
                    res = Math.max(len, res);
                }
            }
        }
        return res;
    }
}
