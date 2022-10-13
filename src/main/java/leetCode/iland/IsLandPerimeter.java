package leetCode.iland;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description  https://leetcode.cn/problems/island-perimeter/
 * 463. 岛屿的周长
 * 给定一个 row x col 的二维网格地图 grid ，其中：grid[i][j] = 1 表示陆地， grid[i][j] = 0 表示水域。
 *
 * 网格中的格子 水平和垂直 方向相连（对角线方向不相连）。整个网格被水完全包围，但其中恰好有一个岛屿（或者说，一个或多个表示陆地的格子相连组成的岛屿）。
 *
 * 岛屿中没有“湖”（“湖” 指水域在岛屿内部且不和岛屿周围的水相连）。格子是边长为 1 的正方形。网格为长方形，且宽度和高度均不超过 100 。计算这个岛屿的周长。
 *
 *
 *
 * 示例 1：
 *     https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/10/12/island.png
 *
 *
 * 输入：grid = [[0,1,0,0],[1,1,1,0],[0,1,0,0],[1,1,0,0]]
 * 输出：16
 * 解释：它的周长是上面图片中的 16 个黄色的边
 * 示例 2：
 *
 * 输入：grid = [[1]]
 * 输出：4
 * 示例 3：
 *
 * 输入：grid = [[1,0]]
 * 输出：4
 * @date 2022/10/12 14:53
 */
public class IsLandPerimeter {
    public int islandPerimeter(int[][] grid) {
        for(int r = 0; r < grid.length; r++){
            for(int c = 0; c < grid[0].length; c++){
                if(grid[r][c] == 1){
                    return dfs(r,c,grid);
                }
            }
        }
        return 0;
    }

    public int dfs(int r,int c,int[][] grid){
        if(r < 0 || c < 0 || r >= grid.length || c >= grid[0].length)return 1;
        if(grid[r][c] == 0) return 1;
        if(grid[r][c] == 2) return 0;
        grid[r][c] = 2;
        return dfs(r+1,c,grid) + dfs(r-1,c,grid) + dfs(r,c+1,grid) + dfs(r,c-1,grid);
    }


}
