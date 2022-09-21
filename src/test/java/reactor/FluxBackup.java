package reactor;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/8/2 16:51
 */
public class FluxBackup {


    // 下游订阅者处理每个元素（event事件）的时长
    private static final long PROCESS_DURATION_MS = 30;
    // 生成事件的数量
    private static final int EVENT_COUNT = 10;
    // 事件生成的间隔事件
    private static final int EVENT_DURATION_MS = 10;


    private Flux<EventSource.Event> fastPublisher;

    private SlowSubscriber slowSubscriber;

    private CountDownLatch countDownLatch;

    private EventSource eventSource;


    @Before
    public void init(){
        countDownLatch = new CountDownLatch(1);
        slowSubscriber = new SlowSubscriber();
        eventSource = new EventSource();
    }


    @Test
    public void testCreateBackPressureStratety(){
        fastPublisher =
                createFlux(FluxSink.OverflowStrategy.IGNORE)
                        .doOnRequest(n-> System.out.println("下游--------------》向上游请求了： "+ n + "个数据"))
                        .publishOn(Schedulers.newSingle("singleThread"),1);
    }

    private Flux<EventSource.Event> createFlux(FluxSink.OverflowStrategy strategy) {
        return Flux.create(sink->{
            eventSource.register(new EventListener() {
                @Override
                public void newEvent(EventSource.Event event) {
                    System.out.println("上游------>数据源创建了新事件：" + event.getMsg());
                    sink.next(event);
                }

                @Override
                public void eventSourceStopped() {
                    sink.complete();
                }
            });
        }, strategy);
    }


    /**
     * 触发订阅，使用CountDownLatch等待订阅者处理完成。
     */
    @After
    public void subscribe() throws InterruptedException {
        // 也就是Flux.subscribe(subscriber)
        fastPublisher.subscribe(slowSubscriber);
        // 创建数据源
        generateEvent(EVENT_COUNT, EVENT_DURATION_MS);
        // 阻塞等待防止程序退出
        countDownLatch.await(1, TimeUnit.MINUTES);
    }


    // 生成事件源
    private void generateEvent(int eventCount, int eventDurationMs) {
        for (int i = 0; i < eventCount; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(eventDurationMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 生成事件
            eventSource.newEvent(new EventSource.Event(new Date(),"Event-"+i));
        }
        eventSource.eventStopped();
    }


    static class EventSource<T>{

        List<EventListener> listeners = Lists.newArrayList();

        public void register(EventListener listener){
            listeners.add(listener);
        }

        public void newEvent(EventSource.Event event){
            for (EventListener listener : listeners) {
                listener.newEvent(event);
            }
        }

        public void eventStopped(){
            for (EventListener listener : listeners) {
                listener.eventSourceStopped();
            }
        }
        @Data
        @AllArgsConstructor
        static class Event{
            private Date date;
            private String msg;
        }
    }

    interface EventListener{
        void newEvent(EventSource.Event event);
        void eventSourceStopped();
    }

    // 慢的事件订阅者（处理慢）
    private class SlowSubscriber extends BaseSubscriber<EventSource.Event> {

        @Override
        protected void hookOnSubscribe(Subscription subscription) {
            request(1);
        }

        @Override
        protected void hookOnNext(EventSource.Event event) {
            System.out.println(Thread.currentThread().getName() + "接收数据： " + event.getMsg());
            try {
                TimeUnit.MILLISECONDS.sleep(PROCESS_DURATION_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            request(1);
        }

        @Override
        protected void hookOnComplete() {
            countDownLatch.countDown();
        }
    }
}
