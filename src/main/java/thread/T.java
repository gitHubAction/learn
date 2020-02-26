package thread;

/**
 * @Author: zhangsh
 * @Date: 2020/2/25 11:18
 * @Version 1.0
 * Description
 */
public class T {

    public static void main(String[] args) {
        Thread thread = new Thread(()->{
            try {
                System.out.println(System.currentTimeMillis()+"  "+Thread.currentThread().getName()+"sleep前");
                /**
                 * sleep方法都有static修饰，既然是静态方法，
                 * 在Thread中的惯例就是针对于：当前线程，当前线程，当前线程
                 */
                Thread.sleep(500);
                System.out.println(System.currentTimeMillis()+"  "+Thread.currentThread().getName()+"sleep后");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-0");

        thread.start();

        while (true){
            try {
                System.out.println(System.currentTimeMillis()+"  "+Thread.currentThread().getName()+"sleep前---循环体");
                Thread.sleep(50);
                System.out.println(System.currentTimeMillis()+"  "+Thread.currentThread().getName()+"sleep后---循环体");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"---循环体，"+thread.getName()+" 状态->"+thread.getState());
            if(Thread.State.TERMINATED.equals(thread.getState())){
                System.out.println(System.currentTimeMillis()+"  "+Thread.currentThread().getName()+"---循环体，"+thread.getName()+" 状态->"+thread.getState());
                break;
            }
        }
    }
}
