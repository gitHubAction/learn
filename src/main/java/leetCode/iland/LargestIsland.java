package leetCode.iland;

import java.util.*;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description https://leetcode.cn/problems/making-a-large-island/
 * 827. 最大人工岛
 * 给你一个大小为 n x n 二进制矩阵 grid 。最多 只能将一格 0 变成 1 。
 *
 * 返回执行此操作后，grid 中最大的岛屿面积是多少？
 *
 * 岛屿 由一组上、下、左、右四个方向相连的 1 形成。
 *
 *
 *
 * 示例 1:
 *
 * 输入: grid = [[1, 0], [0, 1]]
 * 输出: 3
 * 解释: 将一格0变成1，最终连通两个小岛得到面积为 3 的岛屿。
 * 示例 2:
 *
 * 输入: grid = [[1, 1], [1, 0]]
 * 输出: 4
 * 解释: 将一格0变成1，岛屿的面积扩大为 4。
 * 示例 3:
 *
 * 输入: grid = [[1, 1], [1, 1]]
 * 输出: 4
 * 解释: 没有0可以让我们变成1，面积依然为 4。
 *
 * @date 2022/10/12 15:32
 */
public class LargestIsland {

    public static void main(String[] args) {
        LargestIsland l = new LargestIsland();
        int i = l.largestIsland(new int[][]{{1, 1}, {1, 0}});
        System.out.println(i);
    }


    public int largestIsland(int[][] grid) {
        // 计算每一个岛屿的面积，并计入到列表中
        int i = 2;
        Map<Integer,Integer> map = new HashMap<>();
        for(int r = 0; r < grid.length; r++){
            for(int c = 0; c < grid[0].length; c++){
                if(grid[r][c] == 1){
                    map.put(i,dfs(r,c,grid,i++));
                }
            }
        }
        // 全是海
        if(map.size() == 0)return 1;
        int ans = 0;
        //遍历所有的海洋，求海洋相邻的两个不同的岛屿的最大面积和
        for(int r = 0; r < grid.length; r++){
            for(int c = 0; c < grid[0].length; c++){
                if(grid[r][c] == 0){
                    // 上下左右求关联的陆地面积
                    int s = area(r+1,c,grid,map);
                    int x = area(r-1,c,grid,map);
                    int z = area(r,c-1,grid,map);
                    int y = area(r,c+1,grid,map);
                    Set<Integer> set = new HashSet<>();
                    set.add(s);set.add(x);set.add(z);set.add(y);
                    // 求最大值
                    ans = Math.max(ans,set.stream().map(item -> map.getOrDefault(item,0)).reduce(Integer::sum).orElse(0)+1);
                }
            }
        }
        // 全是岛屿
        if(ans == 0)return map.get(2);
        return ans;
    }

    private int area(int r, int c,int[][] grid, Map<Integer, Integer> map) {
        if(r < 0 || r >= grid.length || c < 0 || c >= grid[0].length)return 0;
        return grid[r][c];
    }

    private Integer dfs(int r, int c, int[][] grid,int index) {
        if(r < 0 || r >= grid.length || c < 0 || c >= grid[0].length)return 0;
        if(grid[r][c] != 1)return 0;
        grid[r][c] = index;
        return 1 + dfs(r+1,c,grid,index) + dfs(r-1,c,grid,index) + dfs(r,c+1,grid,index) + dfs(r,c-1,grid,index);

    }
}
