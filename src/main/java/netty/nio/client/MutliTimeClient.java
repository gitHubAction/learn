package netty.nio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/2/6 17:27
 */
public class MutliTimeClient implements Runnable{

    private SocketChannel sc;

    private Selector selector;

    private volatile boolean stop;

    public MutliTimeClient() {
        try {
            sc = SocketChannel.open();
            selector = Selector.open();
            sc.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            doConnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        while(!stop){
            try {
                selector.select(1000);
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                SelectionKey key = null;
                while(iterator.hasNext()){
                    key = iterator.next();
                    iterator.remove();
                    try {
                        handlerKey(key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
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

    private void handlerKey(SelectionKey key) throws Exception {
        if(key.isValid()){
            SocketChannel sc = (SocketChannel)key.channel();
            if(key.isConnectable()){
                // 已经建立连接
                if(sc.finishConnect()){
                    sc.register(selector, SelectionKey.OP_READ);
                    // 发送请求获取当前时间
                    doWrite(sc);
                }else {
                    System.exit(1);
                }
            }
            // 接收到服务端响应信息
            if(key.isReadable()){
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int read = sc.read(byteBuffer);
                if(read > 0){
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    String msg = new String(bytes, "UTF-8");
                    System.out.println("now is :"+ msg);
                    this.stop = true;
                }else if(read < 0){
                    key.cancel();
                    sc.close();
                }
            }else{
                // 没读到响应信息
            }
        }
    }

    private void doWrite(SocketChannel sc) throws Exception {
        byte[] msg = "QUERY DATE".getBytes("UTF-8");
        ByteBuffer byteBuffer = ByteBuffer.allocate(msg.length);
        byteBuffer.put(msg);
        byteBuffer.flip();
        sc.write(byteBuffer);
        if (!byteBuffer.hasRemaining()){
            System.out.println("send query date 2 server succeed");
        }
    }

    private void doConnect() throws Exception {
        if(sc.connect(new InetSocketAddress("127.0.0.1",8080))){
            // 连接成功，注册读事件
            sc.register(selector, SelectionKey.OP_READ);
        }else {
            sc.register(selector, SelectionKey.OP_CONNECT);
        }

    }
}
