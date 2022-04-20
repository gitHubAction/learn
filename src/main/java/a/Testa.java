package a;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/1/21 1:55
 */
public class Testa {

    public static void main(String[] args) {
        Valid a = new Valid();
        a.addInitParameter("123","kasdfa");

        Valid b = new Valid();
        b.addInitParameter("3123","kasdfa");

        System.out.println(a.getParameterMap().keySet());
        System.out.println(b.getParameterMap().keySet());
    }
}
