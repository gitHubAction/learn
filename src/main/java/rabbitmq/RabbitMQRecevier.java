//package rabbitmq;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//
///**
// * @author zhangshihao01
// * @version 1.0
// * @Description
// * @Date 2021/1/21 14:40
// */
//@Slf4j
//@Component
//public class RabbitMQRecevier {
//
//    /**
//     *  direct队列
//     */
//    @RabbitListener(queues = "SMPTestDirectQueue")
//    public void directRecevier1(@Payload String msg){
//        log.info("直接交换机第一个监听 DirectRecevier process receive msg:{}",msg);
//    }
//
//    @RabbitListener(queues = "SMPTestDirectQueue")
//    public void directRecevier2(@Payload String msg){
//        log.info("直接交换机第二个监听  DirectRecevier process receive msg:{}",msg);
//    }
//
//    /**
//     *  topic队列
//     */
//    @RabbitListener(queues = RabbitMQConfig.TOPIC_AX)
//    public void topicAxRecevier(@Payload String msg){
//        log.info("topicAxRecevier receive msg:{}",msg);
//    }
//
//
//    @RabbitListener(queues = RabbitMQConfig.TOPIC_AXB)
//    public void topicAxbRecevier(@Payload String msg){
//        log.info("topicAxbRecevier receive msg:{}",msg);
//    }
//}
