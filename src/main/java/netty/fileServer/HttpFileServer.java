package netty.fileServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.InetSocketAddress;

/**
 * @Author: zhangsh
 * @Date: 2020/2/19 14:32
 * @Version 1.0
 * Description
 */
public class HttpFileServer {

    //文件服务器的根目录
    private static final String DEFAULT_URL="/src/main/java/";

    public static void main(String[] args) {
        int port = 8081;
        if(args.length>0){
            try {
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        String url = DEFAULT_URL;
        if(args.length>1)url =  args[1];
        //启动http服务
        new HttpFileServer().run(port,url);
    }

    private void run(int port, String url) {
        NioEventLoopGroup parent = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parent,work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //http请求解码器
                            ch.pipeline().addLast("http-decoder"
                                    ,new HttpRequestDecoder());
                            //
                            ch.pipeline().addLast("http-aggregator"
                                    ,new HttpObjectAggregator(65536));
                            ch.pipeline().addLast("http-encoder"
                                    ,new HttpResponseDecoder());
                            //支持异步大文件流，不会抛出oom
                            ch.pipeline().addLast("http-chunked"
                                    ,new ChunkedWriteHandler());

                            ch.pipeline().addLast("fileServerHandler"
                                    ,new HttpFileServerHandler(DEFAULT_URL));

                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("文件目录服务器启动成功!url：http://127.0.0.1:"+port+url);
            future.channel().closeFuture().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            parent.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}
