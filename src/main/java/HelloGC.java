import java.util.LinkedList;
import java.util.List;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/8/4 17:19
 */
public class HelloGC {
    public static void main(String[] args) throws Exception {
        System.out.println("hello GC");
        List list = new LinkedList<>();
        for(;;){
            byte[] b = new byte[1024*1024];
            Thread.sleep(1000*2);
            list.add(b);
        }
    }
}
