package reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.util.Arrays;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/8/1 13:44
 */
public class HotColdFlux {

    public static void main(String[] args) {
        hotFlux();

        coldFlux();
    }

    private static void hotFlux() {
        UnicastProcessor<String> hotSource = UnicastProcessor.create();
        Flux<String> hotFlux = hotSource.publish().autoConnect().map(String::toUpperCase);
        hotFlux.subscribe(d-> System.out.println("Subscribe 1 to hot Source:" + d));

        hotSource.onNext("blue");
        hotSource.onNext("green");

        hotFlux.subscribe(d-> System.out.println("Subscribe 2 to hot Source:" + d));
        hotSource.onNext("orange");
        hotSource.onNext("purple");

        hotSource.onComplete();
    }

    private static void coldFlux() {
        Flux<String> source = Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
                .doOnNext(System.out::println)
                .filter(s -> s.startsWith("o"))
                .map(String::toUpperCase);

        source.subscribe(d -> System.out.println("Subscriber 1: "+d));
        source.subscribe(d -> System.out.println("Subscriber 2: "+d));
    }
}
