package rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @Author: zhangsh
 * @Date: 2020/2/26 15:44
 * @Version 1.0
 * Description
 */
public class Producer {

    private final static String QUEUE_NAME = "q_test_01";

    public static void main(String[] args) throws IOException {
        //获取mq连接
        Connection connection = ConnectionUtil.getContection();
        //创建channel信道
        Channel channel = connection.createChannel();

        channel.queueDeclare(
                QUEUE_NAME,//队列名称
                false,//是否需要持久化
                false,//是否为排他连接
                false,//是否自动删除
                null);//其他参数
        // 消息内容
        for (int i = 0; i < 200; i++) {
            String message = "Hello World!";
            message+=i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
