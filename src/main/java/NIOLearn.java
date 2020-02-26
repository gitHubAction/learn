import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: zhangsh
 * @Date: 2019/8/12 15:40
 * @Version 1.0
 * Description
 */
public class NIOLearn {


    public static void main(String[] args) throws IOException {
        RandomAccessFile raf = new RandomAccessFile("C:\\Users\\55403\\Desktop\\learn.xls","rw");
        //获取通道
        FileChannel channel = raf.getChannel();
        //建立缓冲区
        ByteBuffer  bb = ByteBuffer.allocate(48);
        //从通道中读取内容写到缓冲区
        while (true){
            int read = channel.read(bb);
            if(read == -1){
                break;
            }
            System.out.println("write in Buff:"+read);
            //切换缓冲区的读写模式
            bb.flip();
            //从缓冲区读取数据
            while (bb.hasRemaining()){
                System.out.println("read from Buff:"+bb.get());

            }

            //清楚缓冲区的已读数据
            bb.clear();//清楚整个缓冲区
        }
        //关闭流
        raf.close();
    }
}
