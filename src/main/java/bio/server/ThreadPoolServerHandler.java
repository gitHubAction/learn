package bio.server;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhangsh
 * @Date: 2020/2/18 10:36
 * @Version 1.0
 * Description
 */
public class ThreadPoolServerHandler {

    private ExecutorService executorService;

    /**
     *
     * @param maxPoolSize 最大线程数
     * @param queueSize 队列大小
     */
    public ThreadPoolServerHandler(int maxPoolSize,int queueSize) {
        this.executorService = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                maxPoolSize,
                120,TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable task){
        executorService.execute(task);
    }
}
