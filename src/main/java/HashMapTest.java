import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhangsh
 * @Date: 2019/9/27 13:51
 * @Version 1.0
 * Description
 */
public class HashMapTest {


    public static void main(String[] args) {
        HashMap map = new HashMap();
        for (int i = 0; i < 10; i++) {
            A a = new A();
            B b = new B();
            map.put(a,i);
            map.put(b,i);
        }
        for (Object o:map.keySet()) {
            System.out.println(map.get(o));
        }
    }



    static class A{
        @Override
        public int hashCode(){
            return 1;
        }

        @Override
        public String toString() {
            return this.hashCode()+"";
        }
    }


    static class B{
        @Override
        public int hashCode(){
            return 1;
        }
        @Override
        public String toString() {
            return this.hashCode()+"";
        }
    }
}
