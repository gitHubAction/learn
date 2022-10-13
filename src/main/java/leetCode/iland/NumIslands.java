package leetCode.iland;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description https://leetcode.cn/problems/number-of-islands/
 * 200. 岛屿数量
 * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 *
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 *
 * 此外，你可以假设该网格的四条边均被水包围。
 *
 *
 *
 * 示例 1：
 *
 * 输入：grid = [
 *   ["1","1","1","1","0"],
 *   ["1","1","0","1","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","0","0","0"]
 * ]
 * 输出：1
 * 示例 2：
 *
 * 输入：grid = [
 *   ["1","1","0","0","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","1","0","0"],
 *   ["0","0","0","1","1"]
 * ]
 * 输出：3
 * @date 2022/10/12 14:49
 */
public class NumIslands {

    /**
     * dfs遍历每个格子找岛，并将遍历到的格子的值改为2表示遍历过了
     * @param grid
     * @return
     */
    public int numIslands(char[][] grid){
        int res = 0;
        for(int r = 0; r < grid.length; r++){
            for(int c = 0; c < grid[0].length; c++){
                if(grid[r][c] == '1'){
                    dfs(r,c,grid);
                    res++;
                }
            }
        }
        return res;
    }

    private void dfs(int r, int c, char[][] grid) {
        // 如果不在给定的grid内，直接返回
        if(!isArea(r,c,grid))return;
        // 如果是海洋0或者被遍历过的陆地2，直接返回
        if(grid[r][c] != '1')return;
        // 是1
        grid[r][c] = '2';
        // 四个方向遍历
        dfs(r-1,c,grid);
        dfs(r+1,c,grid);
        dfs(r,c-1,grid);
        dfs(r,c+1,grid);
    }

    private boolean isArea(int r, int c, char[][] grid) {
        return r < 0 || c < 0 || r >= grid.length || c >= grid[0].length;
    }
}
