package Type;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
/**
 * @Author: zhangsh
 * @Date: 2020/1/19 17:00
 * @Version 1.0
 * Description
 */

interface Demo1I1 { //@1
}

interface Demo1I2 { //@2
}

/**
 * 类中泛型变量案例
 *
 * @param <T1>
 * @param <T2>
 * @param <T3>
 */
public class Demo1<T1, T2 extends Integer, T3 extends Demo1I1 & Demo1I2> { //@3

    public static void main(String[] args) {
        TypeVariable<Class<Demo1>>[] typeParameters = Demo1.class.getTypeParameters();//@4
        for (TypeVariable<Class<Demo1>> typeParameter : typeParameters) {
            System.out.println("变量名称:" + typeParameter.getName());
            System.out.println("这个变量在哪声明的:" + typeParameter.getGenericDeclaration());
            Type[] bounds = typeParameter.getBounds();
            System.out.println("这个变量上边界数量:" + bounds.length);
            System.out.println("这个变量上边界清单:");
            for (Type bound : bounds) {
                System.out.println(bound.getTypeName());
            }
            System.out.println("--------------------");
        }
    }
}