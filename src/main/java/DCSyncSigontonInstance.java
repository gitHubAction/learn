
/**
 * @Author: zhangsh
 * @Date: 2019/8/16 9:59
 * @Version 1.0
 * Description
 * volatile关键字是为了防止第19行代码在jvm进行指令重拍之后，
 * 先对变量进行指针指向（此时变量不为null）而没有进行初始化，
 * 此时另外一个线程调用getInstance()方法导致返回一个未经初始化的队形而报错
 */
public class DCSyncSigontonInstance {

    private volatile static DCSyncSigontonInstance si = null;

    private DCSyncSigontonInstance(){
    }

    private static DCSyncSigontonInstance getInstance(){
        if(si==null){
            synchronized (DCSyncSigontonInstance.class){
                if(si==null){
                    si = new DCSyncSigontonInstance();
                }
            }
        }
        return si;
    }
}
