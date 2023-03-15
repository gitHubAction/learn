package netty.aio.server;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/2/7 9:58
 */
public class AioTimeServer {

    public static void main(String[] args) {
        new Thread(new AioServerHandler(8080),"aio-server-thread").start();
    }
}
