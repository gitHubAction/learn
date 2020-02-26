package countdown;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: zhangsh
 * @Date: 2020/2/24 16:27
 * @Version 1.0
 * Description
 */
public class LongTxThread {


    public void longTxToThread(){

        List<String> accountPOList=new ArrayList<>();
        for (int i = 0; i <15 ; i++) {
            accountPOList.add("TR-01"+System.currentTimeMillis());
        }

        //每条线程最小处理任务数
        int perThreadHandleCount = 1;
        //线程池的最大线程数
        int nThreads = 10;
        int taskSize = 15;
        if (taskSize > nThreads * perThreadHandleCount) {
            perThreadHandleCount = taskSize % nThreads == 0 ? taskSize / nThreads : taskSize / nThreads + 1;
            nThreads = taskSize % perThreadHandleCount == 0 ? taskSize / perThreadHandleCount : taskSize / perThreadHandleCount + 1;
        } else {
            nThreads = taskSize;
        }
        //监控主线程
        CountDownLatch mainLatch = new CountDownLatch(1);
        //监控子线程
        CountDownLatch threadLatch = new CountDownLatch(nThreads);
        //根据子线程执行结果判断是否需要回滚
        BlockingDeque<Boolean> rollbackStatusList = new LinkedBlockingDeque<>(nThreads);
        //必须要使用对象，如果使用变量会造成线程之间不可共享变量值
        RollBack rollBack = new RollBack(false);
        //创建线程池
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(nThreads);


        List<Future<List<Object>>> futures = Lists.newArrayList();
        List<Object> returnDataList = Lists.newArrayList();
        //给每个线程分配任务
        for (int i = 0; i < nThreads; i++) {
            int lastIndex = (i + 1) * perThreadHandleCount;
            List<String> accList = accountPOList.subList(i * perThreadHandleCount, lastIndex >= taskSize ? taskSize : lastIndex);
            Task addAccountThread = new Task(mainLatch, threadLatch, rollBack, rollbackStatusList,accList);
            Future<List<Object>> future = threadPool.submit(addAccountThread);
            futures.add(future);
        }

        /** 存放子线程返回结果. */
        List<Boolean> backUpRollbackStatusList = Lists.newArrayList();
        try {
            //等待所有子线程执行完毕
            boolean threadAwait = threadLatch.await(20, TimeUnit.SECONDS);
            //如果超时，直接回滚
            if (!threadAwait) {
                rollBack.setRollback(true);
            } else {
                System.out.println("创建账户子线程执行完毕，共 {"+nThreads+"} 个线程");
                //查看执行情况，如果有存在需要回滚的线程，则全部回滚
                for (int i = 0; i < nThreads; i++) {
                    Boolean rollbackStatus = rollbackStatusList.take();
                    backUpRollbackStatusList.add(rollbackStatus);
                    System.out.println("子线程返回是否回滚结果: {"+rollbackStatus+"}");
                    if (rollbackStatus) {
                        //有线程执行异常，需要回滚子线程.
                        rollBack.setRollback(true);
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println("等待所有子线程执行完毕时，出现异常");
            rollBack.setRollback(true);
        } finally {
            //子线程再次开始执行
            mainLatch.countDown();
            System.out.println("关闭线程池，释放资源");
            threadPool.shutdown();
        }

        //检查子线程是否有异常，有异常整体回滚.
        for (int i = 0; i < nThreads; i++) {
            if (!backUpRollbackStatusList.isEmpty()) {
                Boolean backUpRollbackStatus = backUpRollbackStatusList.get(i);
                if (backUpRollbackStatus) {
                    System.out.println("创建账户失败，主线程整体回滚");
                    //TODO 主线程回滚方法，主线程可考虑编程式事务
                }
            } else {
                System.out.println("创建账户失败，主线程整体回滚");
                //TODO 主线程回滚方法，主线程可考虑编程式事务
            }
        }
        //拼接结果
        try {
            for (Future<List<Object>> future : futures) {
                returnDataList.addAll(future.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LongTxThread c = new LongTxThread();
        c.longTxToThread();
    }
}
