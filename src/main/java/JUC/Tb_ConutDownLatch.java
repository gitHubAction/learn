package JUC;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Tb_ConutDownLatch {

     List<Object> list = new ArrayList<>();

    void add(Object o ){list.add(new Object());}

    int size(){return list.size();}


    public static void main(String[] args) {
        Tb_ConutDownLatch t = new Tb_ConutDownLatch();
        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch latch1 = new CountDownLatch(1);
        new Thread(()->{
            System.out.println("t2启动");
            if(t.size() != 5){
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            latch1.countDown();
            System.out.println("t2结束 ");
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        new Thread(()->{
            System.out.println("t1启动");
            for (int i = 0; i < 10; i++) {
                t.add(new Object());
                System.out.println("t1 add "+i);
                if(t.size() == 5){
                    latch.countDown();
                    try {
                        latch1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                /*try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        }).start();
    }
}
