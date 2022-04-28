package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/4/26 11:25
 */
public class TotalNQueens {

    public static void main(String[] args) {
        int n = 15;
        TotalNQueens queens = new TotalNQueens();
        long start = System.currentTimeMillis();
        System.out.println(queens.fastTotalNQueens(n));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println(queens.totalNQueens(n));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");
    }

    public int fastTotalNQueens(int n){
        if(n > 31 || n < 1){
            return 0;
        }
        int limit = n == 31 ? -1 : (1 << n) - 1;
        return fastProcess(limit, 0, 0, 0);
    }

    private int fastProcess(int limit, int colLimit, int leftLimit, int rightLimit) {
        // 在colLimit都为1（即所有皇后均已摆好）的情况下，则返回1标识有一种摆法
        if(colLimit == limit){
            return 1;
        }
        int res = 0;
        // 计算出当前行可以摆法的位置
        int loc =  limit & (~(colLimit | leftLimit | rightLimit));
        int cur = 0;
        // 枚举当前行所有可以摆放的位置
        while(loc != 0){
            // 提取位置信息
            cur = loc & ((~loc) + 1);
//            loc = loc ^ cur;
            // 去掉cur摆放的位置
            loc = loc - cur;
            // 递归下一行
            res += fastProcess(
                    limit,
                    colLimit | cur,
                    (leftLimit | cur) << 1,
            (rightLimit | cur) >> 1
            );
        }
        return res;
    }


    public int totalNQueens(int n){
        int[] record = new int[n];
        return process(0,record,n);
    }

    /**
     *
     * @param i 第i行
     * @param record  第i行皇后摆放在了record[i]列
     * @param n 皇后的数量  固定
     * @return
     */
    private int process(int i, int[] record, int n) {
        if(i == n){
            return 1;
        }
        int res = 0;
        for(int j = 0; j < n; j++){
            if(isValid(record, i, j)){
                record[i] = j;
                res += process(i+1, record, n);
            }
        }
        return res;
    }

    private boolean isValid(int[] record, int row, int col) {
        for(int i = 0; i < row; i++){
            if(record[i] == col ||
            Math.abs(row - i) == Math.abs(record[i] - col)){
                return false;
            }
        }
        return true;
    }
}
