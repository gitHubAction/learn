package netty.aio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/2/7 9:59
 */
public class AioServerHandler implements Runnable {

    private AsynchronousServerSocketChannel asyncSsc;

    private CountDownLatch latch;

    private int port;

    public AioServerHandler(int port) {
        this.port = port;
        try {
            asyncSsc = AsynchronousServerSocketChannel
                    .open()
                    .bind(new InetSocketAddress(port), 1024);
            latch = new CountDownLatch(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            doAccept();
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doAccept() {
        asyncSsc.accept(this,new AcceptCompletionHandler());
    }

    public AsynchronousServerSocketChannel getAsyncSsc() {
        return asyncSsc;
    }

    public void setAsyncSsc(AsynchronousServerSocketChannel asyncSsc) {
        this.asyncSsc = asyncSsc;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
