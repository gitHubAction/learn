package leetCode;

import java.util.Arrays;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description 473. 火柴拼正方形
 * 你将得到一个整数数组 matchsticks ，其中 matchsticks[i] 是第 i 个火柴棒的长度。
 * 你要用 所有的火柴棍 拼成一个正方形。你 不能折断 任何一根火柴棒，但你可以把它们连在一起，而且每根火柴棒必须 使用一次 。
 *
 * 如果你能使这个正方形，则返回 true ，否则返回 false 。
 * @date 2022/6/14 10:02
 */
public class MakeSquare {

    public static void main(String[] args) {
        MakeSquare ms = new MakeSquare();
        System.out.println(ms.makesquare(new int[]{3,3,3,3,4}));
    }

    public boolean makesquare(int[] matchsticks) {
        int sum = 0;
        for(int a : matchsticks){
            sum+= a;
        }
        if(sum % 4 != 0){
            return false;
        }
        Arrays.sort(matchsticks);
        return process(matchsticks.length - 1, matchsticks, new int[4], sum / 4);
    }

    private boolean process(int index, int[] matchsticks, int[] edges, int edge) {
        // base case 所有的火柴都尝试摆放完毕
        if(index < 0){
            return true;
        }
        // 往4个边摆放火柴
        for(int i = 0; i < edges.length; i++){
            edges[i] += matchsticks[index];
            if(edges[i] <= edge && process(index-1, matchsticks, edges, edge)){
                return true;
            }
            edges[i] -= matchsticks[index];
        }
        return false;
    }

}
