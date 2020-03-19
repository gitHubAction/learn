package finalTest;

/**
 * @Author: zhangsh
 * @Date: 2019/12/18 10:24
 * @Version 1.0
 * Description
 *      final修饰的成员变量：
 *            a.类变量(静态变量),必须在声明时或者静态代码块中进行赋值
 *            b.实例变量，只能在声明时、非静态代码块或者构造中进行赋值
 *
 *      基本数据类型:
 *
 *          final域写：禁止final域写与构造方法重排序，即禁止final域写重排序到构造方法之外，
 *                      从而保证该对象对所有线程可见时，该对象的final域全部已经初始化过。
 *          final域读：禁止初次读对象的引用与读该对象包含的final域的重排序。(必须先读到该对象引用之后，才能读取对象所包含的final域)
 *      引用数据类型：
 *
 *          额外增加约束：禁止在构造函数对一个final修饰的对象的成员域的写入与随后将这个被构造的对象的引用赋值给引用变量 重排序
 */
public class FinalTest {
    final int count = 1;
    public int updateCount() {
        return count;
    }
    public final int sum() {
        int number = count+10;
        return number;
    }

    public void a(){
        final Object b;
        b = new Object();

    }


    //在声明final实例成员变量时进行赋值
    /**
     * 当final修饰基本数据类型变量时，不能对基本数据类型变量重新赋值，因此基本数据类型变量不能被改变。
     * 而对于引用类型变量而言，它仅仅保存的是一个引用，final只保证这个引用类型变量所引用的地址不会发生改变，
     * 即一直引用这个对象，但这个对象属性是可以改变的。
     */
    private final static Person person = new Person(24, 170);
    public static void main(String[] args) {
        //对final引用数据类型person进行更改
        person.age = 22;
        System.out.println(person.toString());
    }
    static class Person {
        private int age;
        private int height;

        public Person(int age, int height) {
            this.age = age;
            this.height = height;
        }
        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    ", height=" + height +
                    '}';
        }
    }

}
