package leetCode;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description 51. N 皇后
 * 按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线上的棋子。
 *
 * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 *
 * 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
 *
 * 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 * @date 2022/6/6 14:53
 */
public class SolveNQueens {

    public static void main(String[] args) {

        List<List<String>> ans = solveNQueens(15);
        ans.forEach(System.out::println);
    }

    private static List<List<String>> solveNQueens(int i) {
        List<List<String>> ans = new ArrayList<>();
        if(i > 31 || i < 1)return ans;
        int allLoc = i == 31 ? -1 : (1 << i)-1;
        int[] queues = new int[i];
        process(ans, queues, 0, allLoc, 0, 0, 0);
        return ans;
    }

    private static void process(List<List<String>> ans,int[] queues, int curRow, int allLoc, int curLimit, int leftLimit, int rightLimit) {
        if(allLoc == curLimit){
            ans.add(generator(queues));
            return;
        }
        int curLoc = allLoc & (~(curLimit | leftLimit | rightLimit));
        while(curLoc != 0){
            int cur = curLoc & ((~curLoc) + 1);
            // 当前行的皇后摆放的位置
            // 1(0001) 2(0010) 4(0100) 8(1000) 16(0001 0000)
            queues[curRow] =  Integer.bitCount(cur-1);
            // 去掉当前行摆放的位置
            curLoc = curLoc ^ cur;
            // 递归下一行
            process(ans,
                    queues,
                    curRow+1,
                    allLoc,
                    curLimit | cur,
                    (leftLimit | cur) << 1,
                    (rightLimit | cur) >> 1
                    );
            queues[curRow]  = -1;
        }
    }

    private static List<String> generator(int[] queues) {
        List<String> ans = new ArrayList<>();
        for (int row = 0; row < queues.length; row++) {
            char[] rowStr = new char[queues.length];
            Arrays.fill(rowStr, '.');
            rowStr[queues[row]] = 'Q';
            ans.add(new String(rowStr));
        }
        return ans;
    }
}
