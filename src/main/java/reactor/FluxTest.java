package reactor;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/7/28 17:27
 */
public class FluxTest {

    public static void main(String[] args) {
//        Flux<String> seq = Flux.just("foo", "bar", "foobar");
//        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
//        Flux<String> seq2 = Flux.fromIterable(iterable);
//
//        Mono<String> foo = Mono.just("foo");
//        Flux<Integer> range = Flux.range(4, 100);
//        range.subscribe(System.out::println);
//        System.out.println("+============================");
//        range.subscribe(System.out::println,
//                e->System.out.println("error: " + e),
//                ()->System.out.println("done!!"));

//        System.out.println("-------------------------------");
//
//        Flux<Integer> range = Flux.range(4, 100);
//        SampleBaseSubscribe<Integer> integerSampleBaseSubscribe = new SampleBaseSubscribe<Integer>();
//        range.subscribe(i-> System.out.println("subscribe: "+i),
//                e-> System.out.println("error: " + e),
//                ()-> System.out.println("Done!!!!"),
//                s -> s.request(1));
//
//        range.subscribe(integerSampleBaseSubscribe);
//
//
//        Flux<Object> generate = Flux.generate(
//                () -> 0,
//                (state, sink) -> {
//                    sink.next("3 x " + state + "= " + 3 * state);
//                    if (state == 10) sink.complete();
//                    return state + 1;
//                });
//        generate.subscribe(System.out::println);
//
//
//        Flux<String> flux = Flux.generate(
//                AtomicLong::new,
//                (state, sink) -> {
//                    long i = state.getAndIncrement();
//                    sink.next("3 x " + i + " = " + 3*i);
//                    if (i == 10) sink.complete();
//                    return state;
//                }, (state) -> System.out.println("state: " + state));
//    flux.subscribe(System.out::println);


    MyEventSource<String> myEventProcessor = new MyEventSource<>();
    Flux.create(sink -> {
        myEventProcessor.register(
            new MyEventListener<String>() {

                @Override
                public void newEvent(MyEventSource.Event event) {
                    sink.next(event);
                }

                @Override
                public void eventSourceStopped() {
                    sink.complete();
                }
            }
        );
    }).subscribe(System.out::println);
//    Flux<String> bridge = Flux.push(sink -> {
//        myEventProcessor.register(
//                new SingleThreadEventListener<String>() {
//
//                    public void onDataChunk(List<String> chunk) {
//                        for(String s : chunk) {
//                            sink.next(s);
//                        }
//                    }
//
//                    public void processComplete() {
//                        sink.complete();
//                    }
//
//                    public void processError(Throwable e) {
//                        sink.error(e);
//                    }
//                });
//    });

    Flux.create(sink -> {
            myEventProcessor.register(
                    new MyEventListener<String>() {
                        @Override
                        public void newEvent(MyEventSource.Event event) {
                            sink.next(event);
                        }

                        @Override
                        public void eventSourceStopped() {
                            sink.complete();
                        }
                    });
            sink.onRequest(n -> {
                List<String> messages = myEventProcessor.request(n);
                for (String s : messages) {
                    sink.next(s);
                }
            });
        });
        for (int i = 0; i < 5; i++) {
            myEventProcessor.newEvent(new MyEventSource.Event(new Date(),i+""));
        }

    }

    interface MyEventListener<T> {
        // 监听新事件
        void newEvent(MyEventSource.Event event);
        // 监听事件源的终止
        void eventSourceStopped();
    }


    static class MyEventSource<T>{
        List<MyEventListener<T>> listeners = Lists.newArrayList();

        public void register(MyEventListener<T> listener){
            listeners.add(listener);
        }

        public void newEvent(Event event){
            for (MyEventListener listener : listeners) {
                listener.newEvent(event);
            }
        }

        public void eventStopped(){
            for (MyEventListener listener : listeners) {
                listener.eventSourceStopped();
            }
        }

        public List<T> request(long n) {
            List<T> res = new ArrayList<>();

            return res;
        }

        @Data
        @AllArgsConstructor
        public static class Event{
            private Date time;
            private String msg;
        }
    }

    static class SampleBaseSubscribe<T> extends BaseSubscriber<T>{
        @Override
        protected void hookOnSubscribe(Subscription subscription) {
            System.out.println("hookOnSubscribe :" + subscription.toString());
            request(1);
        }

        @Override
        protected void hookOnNext(T value) {
            System.out.println("hookOnNext value :" + value);
        }

        @Override
        protected void hookOnComplete() {
            System.out.println("hookOnComplete");
            super.hookOnComplete();
        }

        @Override
        protected void hookOnError(Throwable throwable) {
            System.out.println("throwable");
            super.hookOnError(throwable);
        }

        @Override
        protected void hookOnCancel() {
            System.out.println("hookOnCancel");
            super.hookOnCancel();
        }

        @Override
        protected void hookFinally(SignalType type) {
            System.out.println("hookFinally type:"+ type);
            super.hookFinally(type);
        }
    }
}
