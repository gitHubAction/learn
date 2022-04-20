package mashibing.algorithm.class4;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description  数组的最小和
 * @date 2022/2/21 14:36
 */
public class Practis_smallNum {
    public static void main(String[] args) {
        int[] a = new int[]{2,3,4,5,1,24,3};
        int i = smallNum(a);
        System.out.println(i);
    }


    public static int smallNum2(int[] a){
        if(a == null || a.length < 2){
            return 0;
        }
        int res = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < i; j++) {
                res += a[j] < a[i] ? a[j] : 0;
            }
        }
        return res;
    }

    public static int smallNum(int[] a) {
        if(a == null || a.length < 2){
            return 0;
        }
        return process(a, 0, a.length-1);
    }

    private static int process(int[] a, int l, int r) {
        if(l == r){
            return 0;
        }
        int m = l + ((r - l) >> 1);
        return
                process(a, l, m)
                + process(a, m+1 , r)
                + merge(a, l, m, r);
    }

    public static int merge(int[] a, int l, int m, int r) {
        int[] help = new int[r-l+1];
        int p1 = l;
        int p2 = m+1;
        int i = 0;
        int res = 0;
        while(p1 <= m && p2 <= r){
            res += a[p1] < a[p2] ? (r - p2 + 1) * a[p1] : 0;
            help[i++] = a[p1] < a[p2] ? a[p1++] : a[p2++];
        }
        while (p1 <= m){
            help[i++] = a[p1++];
        }
        while (p2 <= r){
            help[i++] = a[p2++];
        }
        for (int j = 0; j <help.length; j++) {
            a[l+j] = help[j];
        }
        return res;
    }
}
