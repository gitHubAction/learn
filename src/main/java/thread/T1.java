package thread;

/**
 * @Author: zhangsh
 * @Date: 2020/2/25 12:07
 * @Version 1.0
 * Description
 */
public class T1 {

    public static void main(String[] args) {
        Thread thread = new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println(System.currentTimeMillis()+"  "+Thread.currentThread().getName()+"sleep前");
                    /**
                     * sleep方法都有static修饰，既然是静态方法，
                     * 在Thread中的惯例就是针对于：当前线程，当前线程，当前线程
                     */
                    Thread.sleep(1000);
                    System.out.println(System.currentTimeMillis()+"  "+Thread.currentThread().getName()+"sleep后");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis()+"  "+Thread.currentThread().getName()+":working");
            }
        });

        thread.start();

        synchronized (thread){
            try {
                System.out.println(System.currentTimeMillis()+"  "+Thread.currentThread().getName()+"join前");
                thread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*try {
            System.out.println(System.currentTimeMillis()+"  "+Thread.currentThread().getName()+"join前");
            thread.join();
            System.out.println(System.currentTimeMillis()+"  "+Thread.currentThread().getName()+"join后");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.println(System.currentTimeMillis()+"  "+Thread.currentThread().getName()+":end");
    }
}
