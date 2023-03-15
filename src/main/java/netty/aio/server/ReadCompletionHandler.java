package netty.aio.server;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/2/7 10:19
 */
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel asc;

    public ReadCompletionHandler(AsynchronousSocketChannel result) {
        if(this.asc == null){
            this.asc = result;
        }
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        // 转换byteBuffer模式
        attachment.flip();

        byte[] dst = new byte[attachment.remaining()];
        attachment.get(dst);

        try {
            String reqMsg = new String(dst, "UTF-8");
            System.out.println("receive from client msg: "+ reqMsg);
            doWrite(DateUtil.format(new Date(), DatePattern.ISO8601_FORMAT));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void doWrite(String resp) {
        if(StringUtils.isNotBlank(resp)){
            ByteBuffer respBuffer = null;
            try {
                respBuffer = ByteBuffer.wrap(resp.getBytes("UTF-8"));
                asc.write(respBuffer, respBuffer,
                        // 响应完成回调处理器
                        new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        // 如果发生拆包，继续写
                        if(attachment.hasRemaining()) {
                            asc.write(attachment,attachment,this);
                        }
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        exc.printStackTrace();
                        try {
                            asc.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        exc.printStackTrace();
        try {
            this.asc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
