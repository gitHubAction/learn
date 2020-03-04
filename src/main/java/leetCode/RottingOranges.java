package leetCode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: zhangsh
 * @Date: 2020/3/4 16:22
 * @Version 1.0
 * Description
 * 在给定的网格中，每个单元格可以有以下三个值之一：
 *
 *      值 0 代表空单元格；
 *      值 1 代表新鲜橘子；
 *      值 2 代表腐烂的橘子。
 *      每分钟，任何与腐烂的橘子（在 4 个正方向上）相邻的新鲜橘子都会腐烂。
 *
 *      返回直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回 -1。
 *
 *      来源：力扣（LeetCode）
 *      链接：https://leetcode-cn.com/problems/rotting-oranges
 *      著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class RottingOranges {


    public static void main(String[] args) {
//        int[][] arr = {{2,1,1},{1,1,0},{0,1,1}};
        int[][] arr = {{2,2,2,1,1}};
        System.out.println(orangesRotting(arr));
    }

    public static int orangesRotting(int[][] grid) {
        if(grid== null || grid.length == 0) return -1;
        //基于BFS(广度优先)算法
        //先找出所有的腐烂得橘子(入队)
        //然后做循环遍历计算时间，如果遍历完所有的腐烂的橘子之后，还有未腐烂的橘子，则返回-1
        Queue<int[]> badOra = new LinkedList<>();
        int freshCnt = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == 1){
                    freshCnt++;//记录新鲜橘子的数量
                }
                if(grid[i][j] == 2){
                    badOra.add(new int[]{i,j});//记录腐烂的橘子的位置
                }
            }
        }
        int min = 0;//记录总共需要多少次轮询
        while ( freshCnt > 0 && !badOra.isEmpty()){
            min++;
            int size = badOra.size();//先记录队列的长度
            for (int i = 0; i < size; i++) {
                int[] badO = badOra.poll();
                //进行上下左右循环腐烂
                int x = badO[0];
                int y = badO[1];
                //腐烂左边
                if(x-1 >= 0 && grid[x-1][y] == 1){
                    grid[x-1][y] = 2;
                    badOra.add(new int[]{x-1,y});
                    freshCnt--;
                }
                //腐烂右边
                if(x+1 < grid.length && grid[x+1][y] == 1){
                    grid[x+1][y] = 2;
                    badOra.add(new int[]{x+1,y});
                    freshCnt--;
                }
                //腐烂上边
                if(y-1 >= 0 && grid[x][y-1] == 1){
                    grid[x][y-1] = 2;
                    badOra.add(new int[]{x,y-1});
                    freshCnt--;
                }
                //腐烂下边
                if(y+1 < grid[0].length && grid[x][y+1] == 1){
                    grid[x][y+1] = 2;
                    badOra.add(new int[]{x,y+1});
                    freshCnt--;
                }
            }
        }

        if(freshCnt > 0) return -1;
        return min;
    }

}
