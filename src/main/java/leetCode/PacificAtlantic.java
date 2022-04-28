package leetCode;

import java.util.*;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description 417. 太平洋大西洋水流问题  收集可以流入太平洋和大西洋的点，然后比较两个结果中相等的点同时都可以流入两个海域即最终结果
 * @date 2022/4/27 14:55
 */
public class PacificAtlantic {

    // 上  左  下  右
    int[][] way = new int[][]{{-1,0},{0,-1},{1,0},{0,1}};

    public static void main(String[] args) {
        PacificAtlantic p = new PacificAtlantic();
        int[][] a = new int[][]{{1,2,2,3,5},{3,2,3,4,4},{2,4,5,3,1},{6,7,1,4,5},{5,1,1,2,4}};
        List<List<Integer>> lists = p.pacificAtlantic(a);
        for(List<Integer> l : lists){
            System.out.println(l.get(0)+"  "+l.get(1));
        }
//        [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
    }

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int i = heights.length, j = heights[0].length;
        // 可以流入太平洋的点
        boolean[][] b1 = new boolean[i][j];
        Deque<int[]>  dig1 = new ArrayDeque<>();

        // 可以流入大西洋的点
        boolean[][] b2 = new boolean[i][j];
        Deque<int[]>  dig2 = new ArrayDeque<>();

        for (int row = 0; row < heights.length; row++){
            for (int col = 0; col < heights[row].length; col++){
                if(row == 0 || col == 0){
                    b1[row][col] = true;
                    dig1.addLast(new int[]{row, col});
                }
                if(row == heights.length -1 || col == heights[0].length - 1){
                    b2[row][col] = true;
                    dig2.addLast(new int[]{row, col});
                }
            }
        }
        bfs(heights,b1, dig1);
        bfs(heights,b2, dig2);
        List<List<Integer>> res = new ArrayList<>();
        for (int row = 0; row < heights.length; row++){
            for (int col = 0; col < heights[row].length; col++){
                if(b1[row][col] && b2[row][col]){
                    List<Integer> a = new ArrayList<>();
                    a.add(row);a.add(col);
                    res.add(a);
                }
            }
        }
        return res;
    }

    private void bfs(int[][] heights, boolean[][] b, Deque<int[]> dig) {
        while (!dig.isEmpty()){
            int[] point = dig.pollFirst();
            int row = point[0];
            int col = point[1];
            for(int i = 0; i < way.length; i++){
                int nextR = row + way[i][0];
                int nextC = col + way[i][1];
                if(nextR >= heights.length || nextR < 0 || nextC >= heights[0].length || nextC < 0)continue;
                if(b[nextR][nextC] || heights[nextR][nextC] < heights[row][col])continue;
                dig.addLast(new int[]{nextR,nextC});
                b[nextR][nextC] = true;
            }
        }
    }
}
