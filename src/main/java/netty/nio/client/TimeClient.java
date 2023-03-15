package netty.nio.client;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/2/6 17:28
 */
public class TimeClient {
    public static void main(String[] args) {
        new Thread(new MutliTimeClient(),"client-thread").start();
    }
}
