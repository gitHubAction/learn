package bio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: zhangsh
 * @Date: 2020/2/18 10:31
 * @Version 1.0
 * Description
 */
public class TimeServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = null;
        try {
            //创建服务端监听
            server = new ServerSocket(8080);
            //客户端连接
            Socket socket = null;
            //创建一个处理任务的线程池
            ThreadPoolServerHandler executor = new ThreadPoolServerHandler(50,1000);
            while (true){
                socket = server.accept();
                executor.execute(new TimeServerHandler(socket));
            }
        }finally {
            //关闭连接
        }
    }
}
