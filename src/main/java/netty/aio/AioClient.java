package netty.aio;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/2/7 9:57
 */
public class AioClient {

    public static void main(String[] args) {
        new Thread(new AioClientHandler(8080), "aio-client-thread").start();
    }
}
