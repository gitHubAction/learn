package bio.server;

import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * @Author: zhangsh
 * @Date: 2020/2/18 10:41
 * @Version 1.0
 * Description
 */
public class TimeServerHandler implements Runnable {

    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out  = null;
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            in = new BufferedReader(new InputStreamReader(inputStream));
            out = new PrintWriter(outputStream,true);

            String msg = null;
            while (true){
                msg = in.readLine();
                if(msg == null){
                    break;
                }
                System.out.println("服务端server->msg:"+msg);
                out.println(new Date(System.currentTimeMillis()).toString());
            }
        }catch (IOException e){

        }finally {
            if(in != null){
                try {
                    in.close();
                }catch (IOException e){

                }
            }
            if(out != null){
                try {
                    out.close();
                }catch (Exception e){

                }
                out = null;
            }

            if(this.socket != null){
                try {
                    socket.close();
                }catch (IOException e){

                }
                socket = null;
            }
        }
    }
}
