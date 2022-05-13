package leetCode;

/**
 * ClassName:    MinDeletionSize
 * Package:    leetCode
 * Datetime:    2022/5/12   10:07
 * Author:   zsh
 * Description: 944. 删列造序
 */
public class MinDeletionSize {

    public static void main(String[] args) {
        MinDeletionSize m = new MinDeletionSize();
        int i = m.minDeletionSize(new String[]{"cba", "daf", "ghi"});
        System.out.println(i);
    }


    public int minDeletionSize(String[] strs) {
        if(strs == null || strs.length == 0){
            return 0;
        }
        int res = 0;
        a:for(int i = 0; i < strs[0].length(); i++){
            for(int j = 1; j < strs.length; j++) {
                if (strs[j].charAt(i) - strs[j - 1].charAt(i) < 0) {
                    res++;
                    continue a;
                }
            }
        }
        return res;
    }
}
