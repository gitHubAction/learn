package leetCode;

/**
 * ClassName:    OneEditAway
 * Package:    leetCode
 * Datetime:    2022/5/13   10:51
 * Author:   zsh
 * Description: 面试题 01.05. 一次编辑
 * 字符串有三种编辑操作:插入一个字符、删除一个字符或者替换一个字符。
 * 给定两个字符串，编写一个函数判定它们是否只需要一次(或者零次)编辑。
 * 示例 1:
 *
 * 输入:
 * first = "pale"
 * second = "ple"
 * 输出: True
 *  
 *
 * 示例 2:
 *
 * 输入:
 * first = "pales"
 * second = "pal"
 * 输出: False
 *
 */
public class OneEditAway {

    public boolean oneEditAway(String first, String second) {
        if(first.equals(second))return true;
        int fn = first.length();
        int sn = second.length();
        if(Math.abs(fn-sn) > 1)return false;
        // 人为保证first长度小于second
        if(fn > sn)return oneEditAway(second, first);

        // 双指针移动
        int i = 0, j = 0, times = 0;
        while(i < fn && j < sn && times <= 1){
            char f = first.charAt(i);
            char s = second.charAt(j);
            if(f == s){
                i++;j++;
            }else{
                if(fn == sn){
                    // 当前位只能通过交换来实现一致
                    i++;j++;
                    // 交换次数+1
                    times++;
                }else{
                    // 因为fn < sn,则只能将first对应位置新增一个second.charAt[j]来实现一致
                    j++;
                    // 次数+1
                    times++;
                }
            }
        }
        // 交换次数是否大于1
        return times <= 1;
    }
}
