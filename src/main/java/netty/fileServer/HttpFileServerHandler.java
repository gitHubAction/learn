package netty.fileServer;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;
import static io.netty.handler.codec.http.HttpUtil.setContentLength;

/**
 * @Author: zhangsh
 * @Date: 2020/2/19 14:49
 * @Version 1.0
 * Description
 */
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
/*
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if(ctx.channel().isActive()){
            sendError(ctx,HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private final String url;
    public HttpFileServerHandler(String url){
        this.url=url;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if(!request.getDecoderResult().isSuccess()){
            //http请求解码失败，发送失败消息
            sendError(ctx,HttpResponseStatus.BAD_REQUEST);
            return;
        }
        if(!request.getMethod().name().equals("GET")){
            //请求方式
            sendError(ctx,HttpResponseStatus.METHOD_NOT_ALLOWED);
            return;
        }

        String uri = request.getUri();
        //解析地址
        String path = resolveUri(uri);

        if(path == null){
            //
            sendError(ctx,HttpResponseStatus.FORBIDDEN);
            return;
        }

        File file = new File(path);
        //如果是目录就发送目录的链接给客户端
        if(file.isDirectory()){
            if(uri.endsWith("/")){
                sendListing(ctx,file);
            }else {
                sendRedirect(ctx,uri+"/");
            }
            return;
        }
        if(file.isHidden() || !file.exists()){
            sendError(ctx,HttpResponseStatus.NOT_FOUND);
            return;
        }
        if(!file.isFile()){
            sendError(ctx,HttpResponseStatus.FORBIDDEN);
            return;
        }


        RandomAccessFile accessFile = null;
        try {
            accessFile = new RandomAccessFile(file,"r");//只读模式

        }catch (FileNotFoundException e){
            e.printStackTrace();
            sendError(ctx,HttpResponseStatus.NOT_FOUND);
            return;
        }

        long length = accessFile.length();

        HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        //设置响应头
        setContentLength(response,length);
        setContentTypeHeader(response,file);
        if(HttpHeaders.isKeepAlive(request)){
            response.headers().set(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
        }
        ctx.write(response);
        ChannelFuture sendFileFuture;
        //通过Netty的ChunkedFile对象直接将文件写入到发送缓冲区中
        sendFileFuture=ctx.write(new ChunkedFile(accessFile,0,length,8192),ctx.newProgressivePromise());
        //为sendFileFuture添加监听器，如果发送完成打印发送完成的日志
        sendFileFuture.addListener(new ChannelProgressiveFutureListener()
        {

            @Override
            public void operationComplete(ChannelProgressiveFuture future)
                    throws Exception
            {
                System.out.println("Transfer complete.");
            }

            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total)
                    throws Exception
            {
                if(total<0){
                    System.err.println("Transfer progress: "+progress);
                }else {
                    System.err.println("Transfer progress: "+progress+"/"+total);
                }
            }
        });
        //如果使用chunked编码，最后需要发送一个编码结束的空消息体，将LastHttpContent.EMPTY_LAST_CONTENT发送到缓冲区中，
        //来标示所有的消息体已经发送完成，同时调用flush方法将发送缓冲区中的消息刷新到SocketChannel中发送
        ChannelFuture lastContentFuture=ctx.
                writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        //如果是非keepAlive的，最后一包消息发送完成后，服务端要主动断开连接
        if(!HttpHeaders.isKeepAlive(request)) {
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private void setContentLength(HttpResponse response, long length) {
        response.headers().set(CONTENT_LENGTH,length);
    }

    private static final Pattern INSECURE_URI=Pattern.compile(".*[<>&\"].*");

    private String resolveUri(String uri) {
        try {
            uri = URLDecoder.decode(uri, "ISO-8859-1");
        }catch (UnsupportedEncodingException e){
            throw new Error();
        }
        //解码成功后对uri进行合法性判断，避免访问无权限的目录
        if(!uri.startsWith(url)){
            return null;
        }
        if(!uri.startsWith("/")){
            return null;
        }
        //将硬编码的文件路径分隔符替换为本地操作系统的文件路径分隔符
        uri=uri.replace('/', File.separatorChar);
        if(uri.contains(File.separator+".")||uri.contains('.'+File.separator)||
                uri.startsWith(".")||uri.endsWith(".")||INSECURE_URI.matcher(uri).matches()){
            return null;
        }
        //使用当前运行程序所在的工程目录+URI构造绝对路径
        return System.getProperty("user.dir")+File.separator+uri;
    }


    private static final Pattern ALLOWED_FILE_NAME=Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");
    //发送目录的链接到客户端浏览器
    private static void sendListing(ChannelHandlerContext ctx,File dir){
        //创建成功的http响应消息
        FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        //设置消息头的类型是html文件，不要设置为text/plain，客户端会当做文本解析
        response.headers().set(CONTENT_TYPE,"text/html;charset=UTF-8");
        //构造返回的html页面内容
        StringBuilder buf=new StringBuilder();
        String dirPath=dir.getPath();
        buf.append("<!DOCTYPE html>\r\n");
        buf.append("<html><head><title>");
        buf.append(dirPath);
        buf.append("目录：");
        buf.append("</title></head><body>\r\n");
        buf.append("<h3>");
        buf.append(dirPath).append("目录：");
        buf.append("</h3>\r\n");
        buf.append("<ul>");
        buf.append("<li>链接：<a href=\"../\">..</a></li>\r\n");
        for(File f:dir.listFiles()){
            if(f.isHidden()||!f.canRead()){
                continue;
            }
            String name=f.getName();
            if(!ALLOWED_FILE_NAME.matcher(name).matches()){
                continue;
            }
            buf.append("<li>链接：<a href=\"");
            buf.append(name);
            buf.append("\">");
            buf.append(name);
            buf.append("</a></li>\r\n");
        }
        buf.append("</ul></body></html>\r\n");
        //分配消息缓冲对象
        ByteBuf buffer=Unpooled.copiedBuffer(buf,CharsetUtil.UTF_8);
        //将缓冲区的内容写入响应对象，并释放缓冲区
        response.content().writeBytes(buffer);
        buffer.release();
        //将响应消息发送到缓冲区并刷新到SocketChannel中
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void sendRedirect(ChannelHandlerContext ctx,String newUri){
        FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
        response.headers().set(LOCATION,newUri);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void sendError(ChannelHandlerContext ctx,HttpResponseStatus status){
        FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,status,
                Unpooled.copiedBuffer("Failure: "+status.toString()+"\r\n", CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE,"text/html;charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void setContentTypeHeader(HttpResponse response,File file){
        MimetypesFileTypeMap mimetypesTypeMap=new MimetypesFileTypeMap();
        response.headers().set(CONTENT_TYPE,mimetypesTypeMap.getContentType(file.getPath()));
    }*/

    private String url;

    public HttpFileServerHandler(String url) {
        this.url = url;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        /** 解码失败 **/
        if (!request.decoderResult().isSuccess()) {
            FileProcessHelper.sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        /** 只支持GET方法 **/
        if (!request.method().equals(HttpMethod.GET)) {
            FileProcessHelper.sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
            return;
        }
        /** 格式化url并判断 **/
        final String uri = request.uri();
        final String path = FileProcessHelper.organizedURL(uri,url);
        if (null == path) {
            FileProcessHelper.sendError(ctx, HttpResponseStatus.FORBIDDEN);
            return;
        }
        /** 对文件路径的判断 **/
        File file = new File(path);
        if (file.isHidden() || !file.exists()) {
            FileProcessHelper.sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        }
        /** 判断请求的是否是文件夹 **/
        if (file.isDirectory()) {
            if (uri.endsWith("/")) {
                FileProcessHelper.fileList(ctx, file);
            } else {
                FileProcessHelper.sendRedirect(ctx, uri + "/");
            }
            return;
        }
        /** 文件的判断 **/
        if (!file.isFile()) {
            FileProcessHelper.sendError(ctx, HttpResponseStatus.FORBIDDEN);
            return;
        }

        RandomAccessFile randomAccessFile = null;
        try {
            /** 只读的方式打开文件 **/
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            FileProcessHelper.sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        }

        long fileLegth = randomAccessFile.length();
        /** 创建一个Http响应文件大小 **/
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        /** 设置响应文件大小 **/
        setContentLength(response, fileLegth);
        /** 设置content type **/
        FileProcessHelper.setContentTypeHeader(response, file);
        if (isKeepAlive(request)) {
            response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        ctx.write(response);

        ChannelFuture sendFileFuture = null;
        sendFileFuture = ctx.write(new ChunkedFile(randomAccessFile, 0, fileLegth, 8192), ctx.newProgressivePromise());
        sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                if (total < 0) {
                    System.err.println("Transfer progress: " + progress);
                } else {
                    System.err.println("Transfer progress:" + progress + "/" + total);
                }
            }

            @Override
            public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                System.out.print("Transfer Successed ! ");
            }
        });
        ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        /** 如果不支持keep-alive ，服务器端主动关闭请求 **/
        if (!isKeepAlive(request)) {
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (ctx.channel().isActive()) {
            FileProcessHelper.sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
