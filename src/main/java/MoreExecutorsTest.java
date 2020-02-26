import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhangsh
 * @Date: 2019/12/10 12:09
 * @Version 1.0
 * Description
 */
public class MoreExecutorsTest {

    public static void main(String[] args) {
        ExecutorService single = Executors.newFixedThreadPool(5);
        try {
            ListeningExecutorService listeningDecorator = MoreExecutors.listeningDecorator(single);
            ListenableFuture<Integer> submit = listeningDecorator.submit(() -> {
                System.out.println("111111111");
                //休眠2秒，默认耗时
                TimeUnit.SECONDS.sleep(2);
                int i = 10 / 0;
                return 10;
            });

            submit.addListener(()->{
                System.out.println("我被回调了");
            },MoreExecutors.directExecutor());
            System.out.println(submit.get());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            single.shutdown();
        }
    }
}
