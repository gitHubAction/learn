package netty.retry;

import java.util.Random;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/3/2 15:18
 */
public class DefaultRetryPolicy implements RetryPolicy{

    private static final int MAX_RETRY_LIMIT = 29;

    private static final int DEFAULT_MAX_SLEEP_MS = Integer.MAX_VALUE;

    private final Random random = new Random();

    private final long baseSleepTimeMs;

    private final int maxRetries;

    private final int maxSleepMs;

    public DefaultRetryPolicy(int baseSleepTimeMs, int maxRetries){
        this.baseSleepTimeMs = baseSleepTimeMs;
        this.maxRetries = maxRetries;
        this.maxSleepMs = DEFAULT_MAX_SLEEP_MS;
    }


    @Override
    public boolean allowRetry(int retryCount) {
        if(retryCount <= maxRetries)return true;
        return false;
    }

    @Override
    public long sleepTimeMs(int retryCount) {
        if(retryCount < 0){
            throw new IllegalArgumentException("重试次数不能小于0");
        }
        if(retryCount > MAX_RETRY_LIMIT){
            retryCount = MAX_RETRY_LIMIT;
        }
        // 随机一个睡眠时间，
        long sleepMs = baseSleepTimeMs * Math.max(1, random.nextInt(1 << retryCount));
        if(sleepMs > maxSleepMs){
            sleepMs = maxSleepMs;
        }
        return sleepMs;
    }
}
