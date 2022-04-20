package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description 挖矿
 * @date 2022/2/28 17:47
 */
public class Solution {
    public int ans;
    public int getMaximumGold(int[][] grid) {
        if(grid == null || grid.length < 1){
            return 0;
        }
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j] != 0){
                    dfs(grid,i,j,0);
                }
            }
        }
        return ans;
    }
    // 方向 左、右、上、下
    int[][] fx = {{-1,0},{1,0},{0,-1},{0,1}};

    public void dfs(int[][] grid , int i, int j, int gold){
        gold += grid[i][j];
        ans = Math.max(ans,gold);
        int temp = grid[i][j];
        grid[i][j] = 0;
        // 上下左右四个方向枚举
        for(int k = 0; k < 4 ; k++){
            int nx = i + fx[k][0];
            int ny = j + fx[k][1];
            System.out.println("i:" +i +"          j:"+j);
            System.out.println(nx+"          "+ny);
            if(nx >= 0 && nx < grid.length &&
                    ny < grid[0].length && ny >= 0 &&
                    grid[nx][ny] > 0){
                dfs(grid,nx,ny,gold);
            }
        }
        grid[i][j] = temp;

    }
}
