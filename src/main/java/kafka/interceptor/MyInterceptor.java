package kafka.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/3/13 15:50
 */
public class MyInterceptor implements ProducerInterceptor<String,String> {
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        if(StringUtils.isBlank(record.key())){
            return new ProducerRecord<String, String>(record.topic(),"default-key", record.value());
        }
        System.out.println("onSend: "+ record.toString());
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        System.out.println("onAcknowledgement metadata: "+ metadata.toString());
    }

    @Override
    public void close() {
        System.out.println("close");
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
