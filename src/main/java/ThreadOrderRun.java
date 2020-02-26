/**
 * @Author: zhangsh
 * @Date: 2019/8/16 10:08
 * @Version 1.0
 * Description
 */
public class ThreadOrderRun {


    public static void main(String[] args){
        Thread t1 = new MyThread();
        t1.setName("t1");
        Thread t2 = new MyThread(t1);
        t2.setName("t2");
        Thread t3 = new MyThread(t2);
        t3.setName("t3");
        t1.start();
        t2.start();
        t3.start();
    }



    static class MyThread extends Thread{
        Thread t = null;

        MyThread(Thread t){
            this.t = t;
        }

        MyThread(){}

        void printStr(){
            for (int i = 0; i < 10; i++) {
                System.out.println(i+"===="+currentThread().getName());
            }
        }


        @Override
        public void run() {
            try {
                if(t!=null){
                    t.join();
                    printStr();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
