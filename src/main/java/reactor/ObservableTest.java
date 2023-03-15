package reactor;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/2/23 16:50
 */
public class ObservableTest {

    public static void main(String[] args) {
//        observable();


//        reactorDo();
//        复杂的序列创建 generate()
//        generator();
//        复杂的序列创建 并且带有初始状态的
//        BiFunction<S, SynchronousSink<T>, S> generator
//        generatorState();

        // create
//        create();

        // 按所有流中的实际产生的序列的顺序
        Flux.merge(Flux.interval(Duration.ZERO, Duration.ofMillis(100)).take(5), Flux.interval(Duration.ofMillis(50),
                Duration.ofMillis(100)).take(5)).toStream().forEach(System.out::println);
        // 按照所有流被订阅的顺序
        Flux.mergeSequential(Flux.interval(Duration.ZERO, Duration.ofMillis(100)).take(5), Flux.interval(Duration.ofMillis(50),
                Duration.ofMillis(100)).take(5)).toStream().forEach(System.out::println);
    }

    private static void create() {
        Flux.create(fluxSink -> {
            for (int i = 0; i < 10; i++) {
                fluxSink.next(i);
            }
            fluxSink.complete();
        }).subscribe(System.out::println);
    }

    private static void generatorState() {
        Random random = new Random();
        Flux.generate(ArrayList::new, (list, sink)->{
            int i = random.nextInt();
            sink.next(i);
            list.add(i);
            if(list.size() > 10){
                sink.complete();
            }
            return list;
        }).subscribe(System.out::println);
    }

    private static void generator() {
        Flux.generate(sink->{
            sink.next("hi world");
            sink.complete();
        }).subscribe(System.out::println);
    }

    private static void reactorDo() {
        Flux.just("a","b","c","d")
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) {
                        subscription.request(0);
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        System.out.println("doOnNext: "+ s);
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(2);
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("s "+ s);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complete");
                    }
                });

    }

    private static void observable() {
        // 数据源
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 5; i++) {
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        });

        // 添加订阅
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("订阅："+ d);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("接收到observable发出的值："+ integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("异常"+ e);
            }

            @Override
            public void onComplete() {
                System.out.println("完成");
            }
        });
    }
}
