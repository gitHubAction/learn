package netty.nio;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/2/6 16:50
 */
public class MutliTimeServer implements Runnable{

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stopped;
    private ByteBuffer byteBuffer;

    public MutliTimeServer(int port){
        try {
            selector = Selector.open();

            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


            serverSocketChannel.socket().bind(new InetSocketAddress(8080), 1024);

            System.out.println("server is started! port: 8080");
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        System.out.println(this.stopped);
        while (!stopped){
            try {
                selector.select(1000);
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while(iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    handleKey(selectionKey);
                }
            }catch (Exception e){

            }
        }
        if(selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleKey(SelectionKey selectionKey) throws Exception {
        if(selectionKey.isValid()){
            if(selectionKey.isAcceptable()){
                // 处理读请求
                ServerSocketChannel ssc = (ServerSocketChannel)selectionKey.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);
            }else if(selectionKey.isReadable()){
                SocketChannel channel = (SocketChannel)selectionKey.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int read = channel.read(readBuffer);
                if(read > 0){
                    readBuffer.flip();
                    byte[] dst = new byte[readBuffer.remaining()];
                    readBuffer.get(dst);
                    String msg = new String(dst, "UTF-8");
                    System.out.println("receive msg from client:" + msg);
                    if("QUERY DATE".equals(msg)){
                        doWrite(channel, DateUtil.format(new Date(),DatePattern.ISO8601_FORMAT));
                    }
                }
            }else{
                // 忽略
            }
        }
    }

    private void doWrite(SocketChannel channel, String msg) throws Exception {
        if(StringUtils.isNotBlank(msg)){
            byte[] msgBytes = msg.getBytes();
            ByteBuffer byteBuffer = ByteBuffer.allocate(msgBytes.length);
            byteBuffer.put(msgBytes);
            byteBuffer.flip();
            channel.write(byteBuffer);
//            channel.write(ByteBuffer.wrap(msg.getBytes()));
        }
    }

    public void stop(){
        this.stopped = true;
    }

    public boolean getStopped(){
        return stopped;
    }
}
