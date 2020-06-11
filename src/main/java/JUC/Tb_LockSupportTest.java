package JUC;

import jodd.time.TimeUtil;
import org.springframework.cglib.core.internal.LoadingCache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Tb_LockSupportTest {
    /*volatile*/ List<Object> list = new ArrayList<>();

    void add(Object o ){list.add(new Object());}

    int size(){return list.size();}
    static Thread t1,t2;

    public static void main(String[] args) {
        Tb_LockSupportTest t = new Tb_LockSupportTest();

        t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                t.add(new Object());
                System.out.println("add "+i);
                if(t.size() == 5){
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
            }
        }, "t1");

        t2 = new Thread(()->{
//            if(t.size() != 5){
                LockSupport.park();
//            }
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName()+"结束");
        },"t2");

        t2.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.start();
    }

}
