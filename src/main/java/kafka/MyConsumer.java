package kafka;

import com.google.common.collect.Lists;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/3/13 15:58
 */
public class MyConsumer {

    public static Properties initConfig(){
        Properties conf = new Properties();
        conf.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,Producer.brokers);
        conf.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        conf.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // 自动提交
        conf.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
        conf.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"20000");

        // 偏移量
        /**
         * "What to do when there is no initial offset in Kafka or
         * if the current offset does not exist any more on the server
         * (e.g. because that data has been deleted):
         * <ul>
         *     <li>earliest: automatically reset the offset to the earliest offset</li>
         *     <li>latest: automatically reset the offset to the latest offset</li>
         *     <li>none: throw exception to the consumer if no previous offset is found for the consumer's group</li>
         *     <li>anything else: throw exception to the consumer.</li>
         * </ul>";
         */
        conf.setProperty(AUTO_OFFSET_RESET_CONFIG,"earliest");

        // 消费者组
        conf.setProperty(GROUP_ID_CONFIG,"group-zsh-4");

        // 拉取批次大小
//        conf.setProperty(MAX_POLL_RECORDS_CONFIG,"");

        return conf;
    }


    public static void main(String[] args) {
        Properties conf = initConfig();
        Consumer<String, String> consumer = new KafkaConsumer<String, String>(conf);
        consumer.subscribe(Lists.newArrayList("ooxx"));
//        ConsumerRecord<String, String> next = null;
        while (true){
            ConsumerRecords<String, String> records =
                    consumer.poll(Duration.ZERO);
            if(!records.isEmpty()){
                records.partitions().stream().forEach(p->{
                    records.records(p).stream().forEach(r->{
                        System.out.println("topic: "+ r.topic()+" partition: "+ r.partition()+" key: "+r.key()+" offset: "+ r.offset()+" value: "+r.value());
                    });
                });
//
//
//                Iterator<ConsumerRecord<String, String>> iterator = records.iterator();
//                while(iterator.hasNext()){
//                    next = iterator.next();
//                    System.out.println("topic: "+ next.topic()+" partition: "+ next.partition()+" key: "+next.key()+" offset: "+ next.offset()+" value: "+next.value());
//                }
            }
        }
    }
}
