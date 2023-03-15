package netty.websocket;

import cn.hutool.core.date.DateUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/2/8 14:35
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private WebSocketServerHandshaker handShaker;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 如果是http请求(websocket第一个请求为http握手请求)
        if(msg instanceof FullHttpRequest){
            handlerHttpReq(ctx, (FullHttpRequest)msg);
            return;
        }
        // websocket数据帧请求
        if(msg instanceof WebSocketFrame){
            handlerWebsocket(ctx, (WebSocketFrame)msg);
        }
    }

    private void handlerWebsocket(ChannelHandlerContext ctx, WebSocketFrame msg) {
        if(msg instanceof CloseWebSocketFrame){
            handShaker.close(ctx.channel(), (CloseWebSocketFrame)msg);
            return;
        }
        if(msg instanceof PingWebSocketFrame){
            ctx.channel().writeAndFlush(new PongWebSocketFrame(msg.content().retain()));
            return;
        }
        if(!(msg instanceof TextWebSocketFrame)){
            throw new UnsupportedMessageTypeException("not support "+ msg.getClass());
        }
        TextWebSocketFrame text = (TextWebSocketFrame) msg;
        String content = text.content().toString(CharsetUtil.UTF_8);
        System.out.println("receive from client: "+ content);
        ctx.channel().writeAndFlush(new TextWebSocketFrame(content+", now is "+ DateUtil.now()));
    }

    private void handlerHttpReq(ChannelHandlerContext ctx, FullHttpRequest request) {
        // websocket第一个请求为http握手请求,请求头中有Upgrade:websocket 标识
        if(!request.getDecoderResult().isSuccess()
        || !"websocket".equals(request.headers().get("Upgrade"))){
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
        }
        // 构造握手响应
        WebSocketServerHandshakerFactory handShakerFactory =
                new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket", null, false);
        handShaker = handShakerFactory.newHandshaker(request);
        if(handShaker == null){
            // 响应错误信息
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        }else{
            handShaker.handshake(ctx.channel(),request);
        }
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, DefaultFullHttpResponse response) {
        if(response.getStatus().code() != 200){
            // 追加响应状态到内容中
            ByteBuf byteBuf = Unpooled.copiedBuffer(response.getStatus().toString().getBytes(CharsetUtil.UTF_8));
            response.content().writeBytes(byteBuf);
            byteBuf.release();
            // 重新设置响应内容长度
            setContentLength(response, response.content().readableBytes());
        }
        // 写响应到通道
        ChannelFuture future = ctx.channel().writeAndFlush(response);
        if(!isKeepAlive(request) || response.getStatus().code() != 200){
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
