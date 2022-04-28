package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/4/26 16:18
 */
public class Divide {

    public static void main(String[] args) {
        int i= divide(-214748367
                ,-1);
        System.out.println(i);
    }


    public static int divide(int dividend, int divisor) {
        int i = 0;
        if(dividend == 0)return i;
        boolean flag = (dividend > 0 && divisor < 0) || (dividend < 0 && divisor > 0) ;
        dividend = dividend < 0 ? -dividend : dividend;
        divisor = divisor < 0 ? -divisor : divisor;
        int res = div(dividend, divisor);
        if(flag)return res>Integer.MAX_VALUE?Integer.MAX_VALUE:res;
        return res;
    }

    public static int div(int a , int b){
        if(a<b) return 0;
        int count = 1;
        int tb = b; // 在后面的代码中不更新b
        while((tb+tb)<=a){
            count = count + count; // 最小解翻倍
            tb = tb+tb; // 当前测试的值也翻倍
        }
        return count + div(a-tb,b);
    }


}
