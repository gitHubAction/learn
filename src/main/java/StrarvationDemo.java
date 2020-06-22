import java.util.concurrent.*;

/**
 * @Author: zhangsh
 * @Date: 2019/8/27 9:23
 * @Version 1.0
 * Description
 */
public class StrarvationDemo {
    private static ExecutorService single = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable task   = new MyCallable();
        AnotherCallable task1   = new AnotherCallable();
        Future<String> submit1 = single.submit(task1);
        Future<String> submit = single.submit(task);

        System.out.println(submit1.get());
        System.out.println("over");
        single.shutdown();
    }

    public static class AnotherCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("in AnotherCallable");

            return "annother success";
        }
    }


    public static class MyCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("in MyCallable");

            Future<String> submit = single.submit(new AnotherCallable());

            return "success:" + submit.get();
        }
    }
}
