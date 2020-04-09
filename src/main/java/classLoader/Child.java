package classLoader;

/**
 * @Author: zhangsh
 * @Date: 2020/4/9 10:44
 * @Version 1.0
 * Description
 */
public class Child extends Parent{

    {
        System.out.print("子类构造代码块    ");
    }
    static{
        new Print("子类静态代码块");
    }

    public static Print obj1 = new Print("子类静态变量obj1");

    public Print obj2 = new Print("子类实例变量obj2");

    public Child (){
        new Print("子类构造");
    }

    public static Print obj3 = new Print("子类静态变量obj3");

    public Print obj4 = new Print("子类实例变量obj4");

    public static void main(String [] args){
        Parent obj1 = new Child ();
        System.out.println("=========");
        Parent obj2 = new Child ();
    }
}
