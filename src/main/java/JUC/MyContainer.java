package JUC;

public class MyContainer {

    public static void main(String[] args) {
        int a = 7;
        int b = 8;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println(a);
        System.out.println(b);
    }

    //一个数组中有两种出现奇数次的数，找出并打印这两种数（使用 异或 运算符）
    public static void prindOddTimesNum2(int[] arr){
        //由异或运算符的规律
        // N = N^0   N^N =0  异或运算具有结合律和交换律的性质
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        //得到的eor肯定不为0
        //找到eor二进制最右侧的1的位置
        int rightOne = eor & ((~eor +1));
        //则数组可以被分为两大类  一类为eor  最右侧1位置相同的位置是1  另一类该位置为0，
        // 但是 a和b肯定不会再同一类中
        int onlyOne = 0;
        for (int i = 0; i < arr.length; i++) {
            if((arr[i] & rightOne) != 0){
                onlyOne ^= arr[i];
            }
        }
        System.out.println(onlyOne+"   "+ (onlyOne^eor));
    }
}
