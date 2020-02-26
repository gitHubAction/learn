/**
 * @Author: zhangsh
 * @Date: 2019/9/4 20:23
 * @Version 1.0
 * Description
 */
public class SychronizedTest {

    private static Object o1 = new Object();

    public static class T1 extends Thread{
        public T1(String name) {
            this.setName(name);
        }

        @Override
        public void run() {
            try {
                synchronized (o1){
                    o1.wait();
                }
            }catch (InterruptedException e){
                System.out.println(this.getName()+"中断 "+this.isInterrupted());
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args){
        T1 t1 = new T1("t1");
        t1.start();
        System.out.println("wait前 "+t1.isInterrupted());
        t1.interrupt();
        System.out.println("中断后"+t1.isInterrupted());
    }
}
