import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zhangsh
 * @Date: 2020/4/8 18:55
 * @Version 1.0
 * Description
 */
public class JustTest {
    static AtomicInteger i = new AtomicInteger();

    static {
        //匿名内置类的加载是在 类加载的时候加载
        //不会死锁，因为两者之间没有依赖关系
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }, "t1");
        //lambda表达式是 invokedynamic 指令实现，不能运行，需要等待类加载完成
        //会产生死锁，因为ClassLoader类中的load方法也是同步方法等待当前子线程，而子线程又需要等待类加载器加载完成才能执行
//        Thread t = new Thread(()->{}, "t1");
//        Thread t = new Thread(()->i.getAndIncrement(), "t1");
        //方法引用是BootStrap或者System加载器加载的，这时已经加载完成
//        Thread t = new Thread(i::getAndIncrement, "t1");
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            System.out.println("中断异常");
            e.printStackTrace();
        }
    }


       static int i1 = 1;
    public static void main(String[] args) {
        String str = "aa";
        int[] arr = new int[]{1};
        incr(i1,str,arr);
        System.out.println(i1+"====="+str+"========"+arr[0]);
        System.out.println("1");

    }


    /**
     * java是引用传递还是值传递
     *
     * 实参到形参的传递是复制操作
     * 对于基本数据类型来说，
     * @param i
     * @param a
     * @param arr
     */
    static void incr(int i ,String a,int[] arr){
        i++;
        a = a+"12";
        arr[0] = 2;
    }
}
