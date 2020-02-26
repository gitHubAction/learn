import java.util.concurrent.CountDownLatch;

/**
 * @Author: zhangsh
 * @Date: 2020/2/24 18:23
 * @Version 1.0
 * Description
 */
public class LoopPrint implements Runnable{
    //用来标识当前应该是a还是b或者c
    private int count=0;

    //    private ReentrantLock lock=new ReentrantLock();
    @Override
    public void run() {
        // TODO Auto-generated method stub
        String nameString=Thread.currentThread().getName();
//		lock.lock();
        //this的含义是什么
        while (count<30) {
            synchronized (this) {
                if (count%3==0) {
                    //a
                    if (nameString.equalsIgnoreCase("a")) {
                        System.out.print("a");
                        count++;

                        this.notifyAll();
                    }
                    else {
                        try {
                            //立即释放锁
                            this.wait();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                if (count%3==1) {
                    //b
                    if (nameString.equalsIgnoreCase("b")) {
                        System.out.print("b");
                        count++;
                        this.notifyAll();
                    }
                    else {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                if (count%3==2) {
                    if (nameString.equalsIgnoreCase("c")) {
                        System.out.print("c");
                        count++;
                        this.notifyAll();
                    }
                    else {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }


//		lock.unlock();
    }

    public static void main(String[] args) {
        LoopPrint task = new LoopPrint();
        Thread a = new Thread(task);
        a.setName("a");
        Thread b = new Thread(task);
        b.setName("b");
        Thread c = new Thread(task);
        c.setName("c");
        b.start();
        a.start();
        c.start();
    }
}
