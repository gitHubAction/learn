package thread;

/**
 * ClassName:    DeadLock
 * Package:    thread
 * Datetime:    2020/9/25   11:45
 * Author:   zsh
 * Description:
 */
public class DeadLock {

    private static final Object o1 = new Object();

    private static final Object o2 = new Object();

    public void a(){
        synchronized (o1){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o2){
                while (true){

                }
            }
        }
    }


    public void b(){
        synchronized (o2){
            synchronized (o1){
                while (true){

                }
            }
        }
    }


    public static void main(String[] args) {
        DeadLock deadLock = new DeadLock();
        Thread t1 = new Thread(() -> {
            deadLock.a();
        });

        Thread t2 = new Thread(() -> {
            deadLock.b();
        });

        t1.start();
        t2.start();
    }

}
