package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description 位运算做 加  减  乘  除
 * @date 2022/7/20 16:17
 */
public class BitMapAddMinumMutliDivide {

    /**
     *  a + b = 无进位相加结果 + 进位
     * @param a
     * @param b
     * @return
     */
    public int add(int a, int b){
        int sum = a;
        while(b != 0){
            // 无进位相加
            sum = a ^ b;
            // 进位
            b = (a & b) << 1;

            a = sum;
        }
        return sum;
    }


    public int minus(int a, int b){
        return add(a, negNum(b));
    }

    private int negNum(int b) {
        return add(~b, 1);
    }

    public int mutli(int a, int b){
        int res = 0;
        while(b != 0){
            if((b & 1) != 0){
                res = add(res, a);
            }
            a <<= 1;
            b >>>= 1;
        }
        return res;
    }
}
