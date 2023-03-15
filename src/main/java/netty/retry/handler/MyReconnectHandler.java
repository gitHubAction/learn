package netty.retry.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.retry.Client;
import netty.retry.RetryPolicy;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/3/2 15:41
 */
@ChannelHandler.Sharable
public class MyReconnectHandler extends ChannelInboundHandlerAdapter {


    private Client client;

    private RetryPolicy retryPolicy;

    private int retry = 0;

    public MyReconnectHandler(Client client){
        this.client = client;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 连接成功 重试次数置0
        retry = 0;
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        RetryPolicy retryPolicy = getRetryPolicy();
        if(retryPolicy.allowRetry(retry)){
            // 未达到重试上限
            long sleepTimeMs = retryPolicy.sleepTimeMs(retry);
            System.out.println(String.format("%d 毫秒后尝试重新连接服务， 这是第【%d】次重试！！！！", sleepTimeMs, retry));
            retry++;
            client.getChannel().eventLoop().schedule(()->{
                System.out.println("Reconnecting......................");
                client.connect();
            }, sleepTimeMs, TimeUnit.MILLISECONDS);
        }
        ctx.fireChannelInactive();
    }

    private RetryPolicy getRetryPolicy() {
        if(this.retryPolicy == null){
            this.retryPolicy = client.getRetryPolicy();
        }
        return this.retryPolicy;
    }


    public static void main(String[] args) {
        //
        String s = new String("java");
        s.intern();
        String s2 = "java";
        System.out.println(s == s2); // false

        //这里构建一个常量池不存在的字符串对象   intern之后会将S3对象的引用地址存入字符串常量池
        String s3 = new String("2") + new String("2");
        s3.intern();
        // s4 赋值时返回的是 字符串常量池 中 S3对象的引用地址（实际上就是S3的引用）
        String s4 = "22";
        System.out.println(s3 == s4);   // true

    }


}
