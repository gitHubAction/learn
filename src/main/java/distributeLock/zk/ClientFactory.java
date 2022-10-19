package distributeLock.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/10/18 11:16
 */
public class ClientFactory {

    public static CuratorFramework createSimple(String connectString){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
        return CuratorFrameworkFactory.newClient(connectString,retryPolicy);
    }

    public static CuratorFramework create(String connectString, RetryPolicy retryPolicy,
                                          int connectionTimeoutMs, int sessionTimeoutMs){
        return CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                .build();
    }
}
