package netty.aio.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/2/7 10:07
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AioServerHandler> {
    @Override
    public void completed(AsynchronousSocketChannel result, AioServerHandler attachment) {
        // 连接完成回调至此，继续让AsynchronousServerSocketChannel 重复使用
        attachment.getAsyncSsc().accept(attachment, this);
        // 开始处理读入事件
        ByteBuffer dst = ByteBuffer.allocate(1024);
        result.read(dst,dst, new ReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, AioServerHandler attachment) {
        exc.printStackTrace();

        // 连接失败，直接退出
        attachment.getLatch().countDown();
    }
}
