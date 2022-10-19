package distributeLock.zk;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/10/18 14:30
 */
@Slf4j
public class ZkLockTest {

    static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                //创建锁
                ZkLock lock = new ZkLock();
                lock.lock();
                //每条线程，执行10次累加
                for (int j = 0; j < 10; j++) {
                    //公共的资源变量累加
                    count++;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("count = " + count);
                //释放锁
                lock.unlock();
            }).start();
        }
        Thread.sleep(30 * 1000);
        System.out.println("total = "+ count);
    }
}
