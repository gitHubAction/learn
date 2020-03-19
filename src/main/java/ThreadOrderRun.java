import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;

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
