package mashibing.algorithm.class1;

/**
 * ClassName:    Code07_EvenTimeOddTimes
 * Package:    mashibing.algorithm
 * Datetime:    2020/6/22   17:25
 * Author:   zsh
 * Description: 异或运算符查找数组中出现奇数次的数
 * * 异或^
 * * 不带进位的相加
 * * 运算符 具有结合率和交换率
 * * N ^ N = 0
 * * N ^ 0 = N
 */
public class Code07_EvenTimeOddTimes {


    /**
     * 数组中只有一种数出现的次数为奇数次，找出这个数
     * 因为 异或^运算符满足交换率  并且 a ^ a = 0
     * 所以数组中所有的数与0做 ^ 操作 {5,5,6,7,7,4,3,2,4,2,3}
     * 相当于 0 ^ 2 ^ 2 ^ 3 ^ 3 ^ 4 ^ 4 ^ 5 ^ 5 ^ 7 ^ 7 ^ 6 = 6
     * @param arr
     */
    public static void printOddTimeNum1(int[] arr){
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        System.out.println(eor);
    }

    /**
     *
     * 0110010000
     * 1001101111
     * 1001110000
     * 数组中只有两种数出现的次数为奇数次，找出这两种数
     * @param arr
     */
    public static void printOddTimeNum2(int[] arr){
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        //eor肯定不等于0
        // 100010101 最右边的1
        // 011101011 (011101010 + 1)
        // 000000001
        //找出eor中二进制位最右边的1
        int _eor = eor & (~eor + 1);
        //数组可以被分成为两位数，一类为在该位置上为1的，一类是为0的，
        // 但是出现奇数次的两种数肯定不为同一类
        for (int i = 0; i < arr.length; i++) {
            _eor ^= arr[i];
        }
        System.out.println(_eor+"   " + (_eor ^ eor));
    }
}
