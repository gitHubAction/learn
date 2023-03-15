package netty.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/2/7 10:31
 */
public class AioClientHandler implements Runnable, CompletionHandler<Void, AioClientHandler> {
    private int port;

    private CountDownLatch latch;

    private AsynchronousSocketChannel asc;

    public AioClientHandler(int port) {
        try {
            this.port = port;
            this.asc = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.latch = new CountDownLatch(1);
        try {
            asc.connect(new InetSocketAddress("127.0.0.1",port), this, this);
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void completed(Void result, AioClientHandler attachment) {
        ByteBuffer reqBuffer = null;
        try {
            reqBuffer = ByteBuffer.wrap("QUERY TIME".getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 发送请求
        attachment.asc.write(reqBuffer, reqBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if(attachment.hasRemaining()){
                // 请求消息未发送完成
                    asc.write(attachment,attachment,this);
                }else{
                // 请求消息发送完成
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    asc.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            attachment.flip();
                            byte[] dst = new byte[attachment.remaining()];
                            attachment.get(dst);

                            String respMsg = null;
                            try {
                                respMsg = new String(dst,"UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                            System.out.println("resp body :"+ respMsg);

                            latch.countDown();
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            close(exc);
                        }
                    });
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                close(exc);
            }
        });
    }

    @Override
    public void failed(Throwable exc, AioClientHandler attachment) {
        close(exc);
    }

    void close(Throwable exc){
        exc.printStackTrace();
        try {
            asc.close();
            latch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
