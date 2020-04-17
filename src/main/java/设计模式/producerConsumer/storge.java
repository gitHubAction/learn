package 设计模式.producerConsumer;

import java.util.LinkedList;

/**
 * @Author: zhangsh
 * @Date: 2020/4/10 15:13
 * @Version 1.0
 * Description
 */
public class storge {

    private LinkedList<Object> list = new LinkedList<>();

    private final int MAX_SIZE = 10;

    public void produce(){
        synchronized (list){
            while (list.size() >= 10){
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(new Object());
            list.notifyAll();
        }
    }


    public void consume(){
        synchronized (list){
            while (list.size() <= 0){
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.remove();
            list.notifyAll();
        }
    }
}
