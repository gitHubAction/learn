package rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * @Author: zhangsh
 * @Date: 2020/2/26 15:40
 * @Version 1.0
 * Description
 */
public class ConnectionUtil {

    public static Connection getContection() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.16.128");
        factory.setPort(5672);
        factory.setVirtualHost("zshtest");
        factory.setUsername("admin");
        factory.setPassword("admin");
        return factory.newConnection();
    }
}
