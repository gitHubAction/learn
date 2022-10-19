package distributeLock.zk;

import distributeLock.Lock;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/10/18 10:47
 */
@Slf4j
public class ZkLock implements Lock {

    //ZkLock的节点链接
    private static final String PATH = "/test/lock";
    private static final String LOCK_PREFIX = PATH + "/";
    private static final long WAIT_TIME = 1000;
    //Zk客户端
    CuratorFramework client = null;

    private String locked_short_path = null;
    private String locked_path = null;
    private String prePath = null;
    final AtomicInteger lockCount = new AtomicInteger(0);
    private Thread thread;

    public ZkLock(){
        ZkClient.instance.init();
        synchronized (ZkClient.instance){
            if(!ZkClient.instance.isNodeExist(PATH)){
                ZkClient.instance.createNode(PATH,null);
            }
        }
        client = ZkClient.instance.getClient();
    }

    @Override
    public boolean lock() {
        // 可重入
        synchronized (this){
            if(lockCount.get() == 0){
                thread = Thread.currentThread();
                lockCount.incrementAndGet();
            }else{
                if(!thread.equals(Thread.currentThread())){
                    return false;
                }
                lockCount.incrementAndGet();
                return true;
            }
        }
        // 尝试获取锁
        try {
            boolean locked = false;
            locked = trylock();
            if (locked) {
                return true;
            }
            // 没获得锁
            while (!locked) {
                // 等待
                await();
                List<String> waiters = getWaiters();
                if (checkLocked(waiters)) {
                    locked = true;
                }
            }
            return true;
        }catch (Exception e){
            lockCount.decrementAndGet(); //减少计数
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkLocked(List<String> waiters) {
        //节点按照编号，升序排列
        Collections.sort(waiters);

        // 如果是第一个，代表自己已经获得了锁
        if (waiters.get(0).equals(locked_short_path)) {
            System.out.println("成功的获取分布式锁,节点为 " + locked_short_path);
            return true;
        }
        return false;
    }

    private void await() throws Exception {
        if(prePath == null){
            throw new Exception("prePath is null");
        }
        CountDownLatch latch = new CountDownLatch(1);
        // 添加watcher
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("[WatchedEvent]节点变化: " + watchedEvent);
                latch.countDown();
            }
        };

        client.getData().usingWatcher(watcher).forPath(prePath);
        // wait等待
        latch.await(WAIT_TIME, TimeUnit.SECONDS);
    }

    public boolean trylock() throws Exception {
        // 1、创建临时顺序节点
        // 2、判断是否为临时顺序节点的第一个，
        // 如果是说明加锁成功，
        // 如果不是获取到前一个节点并赋值给prePath
        locked_path = ZkClient.instance.createEphemralSeqNode(LOCK_PREFIX);
        List<String> waiters = getWaiters();
        if(locked_path == null){
            throw new Exception("zk error");
        }
        // 判断locked_path 是否为第一个
        locked_short_path = getShortPath(locked_path);
        if(checkLocked(waiters)){
            return true;
        }
        int index = Collections.binarySearch(waiters, locked_short_path);
        if(index < 0){
            throw new Exception("节点未找到!!  "+ locked_short_path);
        }
        // 前一个节点
        prePath = LOCK_PREFIX + waiters.get(index - 1);

        return false;
    }

    private String getShortPath(String locked_path) {
        System.out.println("locked_path: "+ locked_path);
        int index = locked_path.lastIndexOf(PATH + "/");
        if(index >= 0){
            index += PATH.length() + 1;
            return index <= locked_path.length() ? locked_path.substring(index) : "";
        }
        return null;
    }

    public List<String> getWaiters() {
        List<String> children = null;
        try {
            children = client.getChildren().forPath(PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return children;
    }

    @Override
    public boolean unlock() {
        if(!Thread.currentThread().equals(thread)){
            return false;
        }
        long count = lockCount.decrementAndGet();
        if(count < 0){
            throw new IllegalMonitorStateException("Lock count has gone negative for lock: " + locked_path);
        }
        // 重入
        if(count != 0){
            return true;
        }
        // 解分布式zk锁
        try {
            if(ZkClient.instance.isNodeExist(locked_path)){
                client.delete().forPath(locked_path);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
