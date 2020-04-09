import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: zhangsh
 * @Date: 2019/8/16 10:08
 * @Version 1.0
 * Description
 */
public class ThreadOrderRun {
    static volatile Boolean flag1 = true;
    static volatile Boolean flag2 = false;
    static volatile Boolean flag3 = false;




    static Thread t1,t2,t3;
    public static void main(String[] args){
        String str = "java";
        String str1 ="ja" + new String("va");
        str1 = str1.intern();
        System.err.println(str == str1);
        System.out.println(16/6);
        System.out.println((1 << 31)-1);
//        int a = 200;
//        Integer integer = Integer.valueOf(200);
    }

    static void printAbc_ReentrentLock(){

        ReentrantLock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();
        CountDownLatch countDownLatch2 = new CountDownLatch(1);
        CountDownLatch countDownLatch3 = new CountDownLatch(1);
        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    System.out.print("A");
                    countDownLatch2.countDown();
                    System.out.println("countDown2后，唤醒2前--------");
                    condition2.signal();
                    System.out.println("countDown2后，唤醒2后+++++++++++");
                    condition1.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                condition2.signal();
                System.out.println("1解锁");
                lock.unlock();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {

            try {
                countDownLatch2.await();
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    System.out.print("B");
                    countDownLatch3.countDown();
                    condition3.signal();
                    condition2.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                condition3.signal();
                System.out.println("2解锁");
                lock.unlock();
            }
        }, "t2");


        Thread t3 = new Thread(() -> {

            try {
                countDownLatch3.await();
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    System.out.print("C");
                    condition1.signal();
                    condition3.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("3解锁");
                condition1.signal();
                lock.unlock();
            }
        }, "t3");


        t2.start();
        t3.start();
        t1.start();
    }



    void printAbc_lockSupport(){
        t1 = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                System.out.print("A");
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        });

        t2 = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                LockSupport.park();
                System.out.print("B");
                LockSupport.unpark(t3);
            }
        });

        t3 = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                LockSupport.park();
                System.out.print("C");
                LockSupport.unpark(t1);
            }
        });


        t1.start();t2.start();t3.start();
    }



    void printAbc_wait_notify(){
        Object o = new Object();
        Thread t1 = new Thread(()->{
            synchronized (o){
                for (int i = 0; i < 10; i++) {
                    //自旋
                    while(!flag1){
                        try {
                            o.wait();
                        }catch (Exception e){

                        }
                    }
                    System.out.print("A");
                    flag2 = true;
                    flag1 = false;
                    flag3 = false;
                    try {
                        o.notifyAll();
                        o.wait();
                    }catch (Exception e){

                    }
                }
                o.notify();
            }
        });


        Thread t2 = new Thread(()->{
            synchronized (o){
                for (int i = 0; i < 10; i++) {
                    //自旋
                    while(!flag2){
                        try {
                            o.wait();
                        }catch (Exception e){

                        }
                    }
                    System.out.print("B");
                    try {
                        flag3 = true;
                        flag1 = false;
                        flag2 = false;
                        o.notifyAll();
                        o.wait();
                    }catch (Exception e){

                    }
                }
                o.notify();
            }
        });


        Thread t3 = new Thread(()->{
            synchronized (o){
                for (int i = 0; i < 10; i++) {
                    //自旋
                    while(!flag3){
                        try {
                            o.wait();
                        }catch (Exception e){

                        }
                    }
                    System.out.print("C");
                    flag1 = true;
                    flag2 = false;
                    flag3 = false;
                    try {
                        o.notifyAll();
                        o.wait();
                    }catch (Exception e){

                    }
                }
                o.notify();
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }

}
