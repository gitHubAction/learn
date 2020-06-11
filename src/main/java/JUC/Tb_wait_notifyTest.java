package JUC;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Tb_wait_notifyTest {
    /*volatile*/ List<Object> list = new ArrayList<>();

    void add(Object o ){list.add(new Object());}

    int size(){return list.size();}


    public static void main(String[] args) {
        Tb_wait_notifyTest t = new Tb_wait_notifyTest();
        final Object o = new Object();
        new Thread(()->{
            synchronized (o){
                if(t.size() != 5){
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"结束");
                    o.notify();
                }
            }
        },"t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            synchronized (o){
                for (int i = 0; i < 10; i++) {
                    t.add(new Object());
                    System.out.println("add i "+i);
                    if(t.size() == 5){
                        o.notify();
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        },"t1").start();
    }

}
