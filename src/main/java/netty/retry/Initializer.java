package netty.retry;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import netty.retry.handler.MyReconnectHandler;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/3/2 16:27
 */
public class Initializer extends ChannelInitializer<SocketChannel> {

    private MyReconnectHandler reconnectHandler;


    public Initializer(Client client){
        this.reconnectHandler = new MyReconnectHandler(client);
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(this.reconnectHandler);
    }
}
