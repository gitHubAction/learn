package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 *    79. 单词搜索
 *    https://leetcode.cn/problems/word-search/
 * @date 2022/10/11 11:02
 */
public class ExistWords {

    // 回溯
    public boolean exist(char[][] board, String word){
        int row = board.length, col = board[0].length;
        // 不能重复访问
        boolean[][] visit = new boolean[row][col];
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                boolean flag = check(i,j,board,visit,word,0);
                if(flag)return true;
            }
        }
        return false;
    }

    private boolean check(int i, int j, char[][] board, boolean[][] visit, String word, int index) {
        // word检查完毕
        if(index == word.length()){
            return true;
        }
        // 如果当前字符不相等直接返回false
        if(board[i][j] != word.charAt(index)){
            return false;
        }
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        visit[i][j] = true;
        boolean res = false;
        for(int[] dir : dirs){
            int newi = i + dir[0], newj = j +dir[1];
            // 如果下一个位置合法并且没有访问过
            if(newi >= 0 && newi < board.length && newj >= 0 && newj < board[0].length){
                if(!visit[newi][newj]){
                    boolean flag = check(newi,newj,board,visit,word,index+1);
                    if(flag){
                        res = true;
                        break;
                    }
                }
            }
        }
        // 还原现场
        visit[i][j] = false;
        return res;
    }
}
