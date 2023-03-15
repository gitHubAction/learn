package netty.retry;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @Date 2023/3/2 15:08
 */
public interface RetryPolicy {

    boolean allowRetry(int retryCount);

    long sleepTimeMs(int retryCount);
}
