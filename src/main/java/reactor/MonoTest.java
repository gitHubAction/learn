package reactor;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/3/1 15:41
 */
public class MonoTest {
    public static void main(String[] args) {
        ignoreEle();
    }

    private static void ignoreEle() {

        Mono.ignoreElements(Flux.range(0,10)).subscribe(System.out::println);
    }
}
