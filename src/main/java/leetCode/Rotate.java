package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/6/21 11:37
 */
public class Rotate {

    public static void main(String[] args) {
        Rotate r = new Rotate();
        int[][] a = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
        r.rotate(a);
        System.out.println(a);
    }

    public void rotate(int[][] matrix) {
        // 从外层往内层顺时针循环
        int times = 0,n = matrix.length;
        while(times <= (n >> 1)){
            int len = n - (times << 1);
            for(int i = 0; i < len-1; i++){
                int t = matrix[times][times+i];
                matrix[times][times+i] = matrix[len-1-i+times][times];
                matrix[len-1-i+times][times] = matrix[len-1+times][len-1-i+times];
                matrix[len-1+times][len-1-i+times] = matrix[times+i][len-1+times];
                matrix[times+i][len-1+times] = t;
            }
            times++;
        }
    }
}
