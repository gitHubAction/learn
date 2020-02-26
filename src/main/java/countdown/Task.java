package countdown;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: zhangsh
 * @Date: 2020/2/24 16:27
 * @Version 1.0
 * Description
 */
public class Task  implements Callable<List<Object>> {

    private CountDownLatch mainLatch;

    private CountDownLatch threadLatch;

    private RollBack rollBack;

    private BlockingDeque<Boolean> rollbackStatusList;

    private List<String> accList;


    public Task(CountDownLatch mainLatch, CountDownLatch threadLatch, RollBack rollBack, BlockingDeque<Boolean> rollbackStatusList, List<String> accList) {
        this.mainLatch = mainLatch;
        this.threadLatch = threadLatch;
        this.rollBack = rollBack;
        this.rollbackStatusList = rollbackStatusList;
        this.accList = accList;
    }

    @Override
    public List<Object> call() throws Exception {

        List<Object> result = Lists.newArrayList();
        Boolean rollBackStatus = false;
        System.out.println("子线程 {"+ Thread.currentThread().getName()+"} 开启事务");
        try {
            for (int i = 0; i < accList.size(); i++) {
                System.out.println("子线程 {"+ Thread.currentThread().getName()+"} 执行插入操作");
                result.add(accList.get(i));
                int number = (int)(Math.random()*5);//随机0-4
                int test=10/number;
            }
        }catch (Exception e){
            System.out.println("子线程 {"+ Thread.currentThread().getName()+"}} 发生异常" );
            rollBackStatus = true;
        }
        rollbackStatusList.add(rollBackStatus);
        threadLatch.countDown();
        System.out.println("子线程 {"+ Thread.currentThread().getName()+"} 计算过程已经结束，等待主线程通知是否需要回滚");
        try {
            mainLatch.await();
            System.out.println("子线程 {"+Thread.currentThread().getName()+"} 再次启动");
        } catch (InterruptedException e) {
            System.out.println("批量创建账户线程InterruptedException异常");
        }
        if (rollBack.getRollback()) {
            //子线程回滚
            System.out.println("批量创账户线程回滚, 线程: {"+Thread.currentThread().getName()+"}");
            System.out.println("子线程 {"+Thread.currentThread().getName()+"} 执行完毕，线程退出");
            return result;
        }
        System.out.println("子线程 {"+Thread.currentThread().getName()+"} 执行完毕，线程退出");
        return result;
    }
}
