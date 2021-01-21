package JUC;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.time.Duration;
import java.util.concurrent.*;
import java.util.function.Function;

/**
 * @author zhangshihao01
 * @version 1.0
 * @Description
 * @Date 2020/12/9 12:51
 */
public class CompletableFutureUtil {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(
            1,
            new ThreadFactoryBuilder()
                    .setDaemon(true)
                    .setNameFormat("completableFutureUtil-timeoutScheduler-%d")
                    .build());
    private CompletableFutureUtil(){}

    public static <T> CompletableFuture<T> exec(CompletableFuture<T> future, Duration duration, TimeUnit unit){
        final CompletableFuture<T> promise = failAfter(duration,unit);
        return future.applyToEither(promise, Function.identity());
    }


    public static <T> CompletableFuture<T> failAfter(Duration duration, TimeUnit unit) {
        final CompletableFuture<T> promise = new CompletableFuture<>();
        scheduler.schedule(() -> {
            final TimeoutException ex = new TimeoutException("Timeout after " + duration);
            return promise.completeExceptionally(ex);
        }, duration.getSeconds(), unit);
        return promise;
    }
}
