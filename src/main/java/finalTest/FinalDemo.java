package finalTest;

/**
 * @Author: zhangsh
 * @Date: 2020/3/15 11:11
 * @Version 1.0
 * Description
 */
public class FinalDemo {
    private int a;                        //普通变量
    private final int b;                  //final变量
    private static FinalDemo finalDemo;

    public FinalDemo() {      //构造函数
        a = 1;                //写普通域
        b = 2;                //写final域
    }

    public static void writer() {    //写线程A执行
        System.out.println(Thread.currentThread().getName()+"执行writer。。。");
        finalDemo = new FinalDemo();
    }

    public static void reader() {        //读线程B执行
        System.out.println(Thread.currentThread().getName()+"执行reader。。。");
        FinalDemo object = finalDemo;    //读对象引用
        if(object !=null){

            int a = object.a;                //读普通域
            int b = object.b;                //读final域
            System.out.println(a+"------------b="+b);
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            FinalDemo.writer();
        },"t1");

        Thread t2 = new Thread(()->{
            FinalDemo.reader();
        },"t2");

        for (int i = 0; i < 10000; i++) {
            t1.start();
            t2.start();
            System.out.println("--------------");
        }
    }
}
