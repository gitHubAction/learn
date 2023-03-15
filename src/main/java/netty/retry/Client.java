package netty.retry;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.retry.handler.MyReconnectHandler;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/3/2 15:36
 */
public class Client {

    private String host;

    private int port;

    private RetryPolicy retryPolicy;

    private Bootstrap bootstrap;

    private Channel channel;

    public Client(String host, int port){
        this.host = host;
        this.port = port;
        this.retryPolicy = new DefaultRetryPolicy(10, 50);
        // 初始化相关组件
        init();
    }

    private void init() {
        bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new Initializer(Client.this));
    }

    public RetryPolicy getRetryPolicy() {
        if(retryPolicy == null){
            return new DefaultRetryPolicy(10, 50);
        }
        return retryPolicy;
    }


    public void connect(){
        synchronized (bootstrap){
            ChannelFuture connect = bootstrap.connect(this.host, this.port);
            connect.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(!future.isSuccess()){
                        System.out.println("连接失败！！！！！！！！！！！");
                        // 这一步才会使的进入到 自定义的ReconnectHandler重连Handler的 channelInactive方法
                        future.channel().pipeline().fireChannelInactive();
                    }
                }
            });
            this.channel = connect.channel();

        }
    }

    public Channel getChannel(){
        return this.channel;
    }

    public static void main(String[] args) {

        Client client= new Client("localhost", 123);
        client.connect();

    }
}
