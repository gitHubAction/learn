package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description 868. 二进制间距
 * @date 2022/4/24 9:45
 */
public class BinaryGap {

    public static void main(String[] args) {
        BinaryGap gap = new BinaryGap();
        int res = gap.binaryGap(8);
        System.out.println(res);
    }

    private int binaryGap(int i) {
        int j = 0;
        int pre = -1;
        int res = 0;
        while(i > 0) {
            if(i % 2 == 1 && pre != -1){
                res = Math.max(j - pre + 1, res);
            }
            if(i % 2 == 1){
                pre = j;
            }
            j++;
            i /= 2;
        }
        return res;
    }

}
