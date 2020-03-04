package rabbitmq.exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmq.ConnectionUtil;

import java.io.IOException;

/**
 * @Author: zhangsh
 * @Date: 2020/2/26 15:44
 * @Version 1.0
 * Description
 */
public class ProducerExchange {


    private final static String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, InterruptedException {
        //获取mq连接
        Connection connection = ConnectionUtil.getContection();
        //创建channel信道
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        String message = "exchage test!";
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
