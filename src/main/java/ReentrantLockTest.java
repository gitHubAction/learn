import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: zhangsh
 * @Date: 2019/9/4 18:13
 * @Version 1.0
 * Description
 */
public class ReentrantLockTest {

    private static ReentrantLock lock1 = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();
    private static Condition condition = lock1.newCondition();

    static class T extends Thread{
        int lock;

        public T(String name, int lock) {
            super(name);
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                if(lock == 1){
                    lock1.lockInterruptibly();
                    TimeUnit.SECONDS.sleep(1);
                    lock2.lockInterruptibly();
                }else {
                    lock2.lockInterruptibly();
                    TimeUnit.SECONDS.sleep(1);
                    lock1.lockInterruptibly();
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                //判断线程是否持有该锁isHeldByCurrentThread
                if (lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                }
                if (lock2.isHeldByCurrentThread()) {
                    lock2.unlock();
                }
            }
        }
    }




    static class AwaitT extends Thread{
        @Override
        public void run() {
            /*try {
                lock1.lock();
                System.out.println("LOCK  等待前");
                boolean await = condition.await(2, TimeUnit.SECONDS);
                System.out.println("等待标记位："+await);
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                lock1.unlock();
            }*/

            try {
                lock1.lock();
                System.out.println("终端前");
                condition.awaitUninterruptibly();
                System.out.println("中断后");
            }finally {
                lock1.unlock();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        /*T t1 = new T("t1", 1);
        T t2 = new T("t2", 2);

        t1.start();
        t2.start();


        TimeUnit.SECONDS.sleep(5);
        t2.interrupt();
        AwaitT t = new AwaitT();
        t.start();

        TimeUnit.SECONDS.sleep(1);
        t.interrupt();*/

        /*//休眠1秒之后，唤醒t1线程
        lock1.lock();
        try {
            condition.signal();
        } finally {
            lock1.unlock();
        }*/




        Thread t1 = new Thread(() ->{
            try {
                lock1.lock();

                System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + " start!");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + " 被唤醒!");

            }finally {
                System.out.println("t1线程释放线程没有");
                lock1.unlock();
            }
        });


        t1.setName("t1");
        t1.start();
        //休眠5秒
        TimeUnit.SECONDS.sleep(5);
        lock1.lock();
        try {
            condition.signal();
        } finally {
            System.out.println("主线程释放锁了么？");
            lock1.unlock();
        }

    }


}
