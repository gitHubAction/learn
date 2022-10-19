package distributeLock.zk;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/10/18 11:12
 */
@Slf4j
@Data
public class ZkClient {

    private CuratorFramework client;

    private static final String ZK_ADDRESS = "node01:2181";

    public static ZkClient instance = null;

    static {
        instance = new ZkClient();
        instance.init();
    }


    private ZkClient(){}


    public void init(){
        if (null != client) {
            return;
        }
        //创建客户端
        client = ClientFactory.createSimple(ZK_ADDRESS);

        //启动客户端实例,连接服务器
        client.start();
    }

    public void destroy() {
        CloseableUtils.closeQuietly(client);
    }



    public void createNode(String path,String data){
        try {
            byte[] payload = "data to set".getBytes("UTF-8");
            if(StringUtils.isNotBlank(data)){
                payload = data.getBytes("UTF-8");
            }
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path,payload);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteNode(String path){
        try {
            if(!isNodeExist(path)){
                return;
            }
            client.delete().forPath(path);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isNodeExist(String path) {
        try{
            Stat stat = client.checkExists().forPath(path);
            if(stat == null){
                return false;
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public String createEphemralSeqNode(String srcpath){
        try {
            String path = client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(srcpath);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
