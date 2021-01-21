//package rabbitmq;
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
///**
// * @author zhangshihao01
// * @version 1.0
// * @Description rabbitmq相关配置
// * @Date 2021/1/21 14:03
// */
//@Configuration
//public class RabbitMQConfig {
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
//        RabbitTemplate rabbitTemplate = new RabbitTemplate();
//        rabbitTemplate.setConnectionFactory(connectionFactory);
//        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
//        rabbitTemplate.setMandatory(true);
//        //发送方消息确认机制
//        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
//            @Override
//            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//                System.out.println("ConfirmCallback:     "+"相关数据："+correlationData);
//                System.out.println("ConfirmCallback:     "+"确认情况："+ack);
//                System.out.println("ConfirmCallback:     "+"原因："+cause);
//            }
//        });
//        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
//            @Override
//            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//                System.out.println("ReturnCallback:     "+"消息："+message);
//                System.out.println("ReturnCallback:     "+"回应码："+replyCode);
//                System.out.println("ReturnCallback:     "+"回应信息："+replyText);
//                System.out.println("ReturnCallback:     "+"交换机："+exchange);
//                System.out.println("ReturnCallback:     "+"路由键："+routingKey);
//            }
//        });
//        return rabbitTemplate;
//
//    }
//
//    //队列 起名：TestDirectQueue
//    @Bean
//    public Queue TestDirectQueue() {
//        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
//        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
//        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
//        //   return new Queue("TestDirectQueue",true,true,false);
//
//        //一般设置一下队列的持久化就好,其余两个就是默认false
//        return new Queue("SMPTestDirectQueue",true);
//    }
//
//    //Direct交换机 起名：TestDirectExchange
//    @Bean
//    DirectExchange TestDirectExchange() {
//        //  return new DirectExchange("TestDirectExchange",true,true);
//        return new DirectExchange("SMPTestDirectExchange",true,false);
//    }
//
//    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
//    @Bean
//    Binding bindingDirect() {
//        return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with("SMPTestDirectRouting");
//    }
//
//
//    /**
//     * topic
//     */
//    public final static String TOPIC_EXCHANGE = "smpTopicExchange";
//    public final static String TOPIC_AX = "smp.topic.ax";
//    public final static String TOPIC_AXB = "smp.topic.axb";
//    @Bean
//    public Queue TestAXQueue(){
//        return new Queue(RabbitMQConfig.TOPIC_AX);
//    }
//
//    @Bean
//    public Queue TestAXBQueue(){
//        return new Queue(RabbitMQConfig.TOPIC_AXB);
//    }
//
//    @Bean
//    public TopicExchange TestTopicExchange(){
//        return new TopicExchange(RabbitMQConfig.TOPIC_EXCHANGE);
//    }
//
//    //将TestAXQueue和topicExchange绑定,而且绑定的键值为topic.man
//    //这样只要是消息携带的路由键是smp.topic.ax,才会分发到该队列
//    @Bean
//    Binding bindingExchangeMessage() {
//        return BindingBuilder.bind(TestAXBQueue()).to(TestTopicExchange()).with(TOPIC_AXB);
//    }
//
//    //将axqueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则smp.topic.#
//    // 这样只要是消息携带的路由键是以smp.topic.开头,都会分发到该队列
//    @Bean
//    Binding bindingExchangeMessage2() {
//        return BindingBuilder.bind(TestAXQueue()).to(TestTopicExchange()).with("smp.topic.#");
//    }
//
//}
