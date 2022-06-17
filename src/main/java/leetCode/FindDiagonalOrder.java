package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/6/14 14:06
 */
public class FindDiagonalOrder {

    public static void main(String[] args) {
        int[][] t =new int[][]{{1,2,3},{4,5,6},{7,8,9}};
        FindDiagonalOrder fdo = new FindDiagonalOrder();
        int[] ans = fdo.findDiagonalOrder(t);
        for(int a : ans){
            System.out.print(a + "  ");
        }
    }

    public int[] findDiagonalOrder(int[][] mat) {
        int idx = 0, row = mat.length, col = mat[0].length;
        int dir = 1, x = 0, y = 0;
        int[] ans = new int[row*col];
        while(idx != ans.length){
            ans[idx++] = mat[x][y];
            int nx = x, ny = y;
            if(dir == 1){
                nx = x - 1;
                ny = y + 1;
            }else{
                nx = x + 1;
                ny = y - 1;
            }
            if(idx < ans.length && (nx < 0 || nx >= row || ny < 0 || ny >= col)){
                if(dir == 1){
                    nx = y + 1 < col ? x : x + 1;
                    ny = y + 1 < col ? y+1 : y;
                }else{
                    nx = x + 1 < row ? x + 1 : x;
                    ny = x + 1 < row ? y : y+1;
                }
                dir *= -1;
            }
            x = nx;
            y = ny;
        }
        return ans;
    }
}
