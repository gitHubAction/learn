package distributeLock;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @Date 2022/10/18 10:45
 */
public interface Lock {

    boolean lock();


    boolean unlock();
}
