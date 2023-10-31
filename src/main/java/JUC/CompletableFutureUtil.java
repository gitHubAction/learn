package JUC;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.util.CharsetUtil;
import org.apache.commons.collections4.KeyValue;
import org.apache.commons.lang.CharSet;

import java.time.Duration;
import java.util.concurrent.*;
import java.util.function.Function;

import static cn.hutool.crypto.asymmetric.KeyType.PublicKey;

/**
 * @author zhangshihao01
 * @version 1.0
 * @Description
 * @Date 2020/12/9 12:51
 */
public class CompletableFutureUtil {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Lists.newArrayList(
                "138****2377",
                "170****6932",
                "137****9788",
                "133****3211",
                "134****1824",
                "159****6262",
                "183****9995",
                "131****6666",
                "138****5698",
                "189****9905",
                "136****9965",
                "133****3729",
                "138****8886",
                "139****2194",
                "158****1000",
                "137****5961",
                "187****3533",
                "189****0702",
                "135****9163",
                "187****4152"
        ).forEach(p->{
            System.out.println(Base64.encode(SecureUtil.rsa(null, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDiihpiSDcLlhet/Cenhcs8FQ75fp6ZHTMELmKY8IxZGaIcvByU2HSCBtWbUtDPnir7abc9TkSZmg6tPuItZQ/Utk8lxhIjd5XBM4y9hFknrVI+4meNJ0m6K7FUvKYADd6top2X8N4ALlY2oCVnyzaPjBI/KltEOLAJU0ovF1+XCQIDAQAB").encrypt(p, PublicKey)));
        });
    }

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
