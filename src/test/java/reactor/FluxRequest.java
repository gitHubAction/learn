package reactor;

import lombok.SneakyThrows;
import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/8/3 10:13
 */
public class FluxRequest {

    @Test
    public void request(){
        Flux<Integer> flux = Flux.range(1, 10).log();


        flux.subscribe(new BaseSubscriber<Integer>() {

            private int count = 0;
            private int requestCnt = 4;
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                // 订阅回调时，获取flux序列的requestCnt个事件源
                request(requestCnt);
            }

            @SneakyThrows
            @Override
            protected void hookOnNext(Integer value) {
                // 事件源处理回调
                count++;
                if(count == requestCnt){
                    Thread.sleep(3000);
                    request(requestCnt);
                    count = 0;
                }
            }
        });
    }
}
