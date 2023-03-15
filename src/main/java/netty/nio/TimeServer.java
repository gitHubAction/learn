package netty.nio;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/2/6 16:39
 */
public class TimeServer {

    public static void main(String[] args) {
        new Thread(new MutliTimeServer(8080),"server-thread").start();
    }
}
