package classLoader;

/**
 * @Author: zhangsh
 * @Date: 2020/4/9 10:43
 * @Version 1.0
 * Description
 */
public class Parent {
    public static Print obj1 = new Print("父类静态变量obj1");

    public Print obj2 = new Print("父类实例变量obj2");
    public static Print obj3 = new Print("父类静态变量obj3");

    static{
        new Print("父类静态代码块");
    }

    public static Print obj4 = new Print("父类静态变量obj5");

    public Print obj5 = new Print("父类实例变量obj6");

    public Parent(){
        new Print("父类构造方法");
    }

    {
        System.out.print("父类构造代码块    ");
    }
}
