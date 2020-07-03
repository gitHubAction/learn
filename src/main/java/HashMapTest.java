import sun.misc.SharedSecrets;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

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
        System.out.println(tableSizeFor(3));

    }


    static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >=  1 << 30) ? 1 << 30 : n + 1;
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
