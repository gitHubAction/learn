package reactor;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/8/1 16:48
 */
public class GroupFlux {

    public static void main(String[] args) {
        // 分组
        groupBy();

        // 窗口
        windowGroup();

        // buffer缓存
        bufferGroup();
    }

    private static void bufferGroup() {

        StepVerifier.create(
                Flux.range(1, 10)
                        .buffer(5, 3) // 缓存重叠
        )
                .expectNext(Arrays.asList(1, 2, 3, 4, 5))
                .expectNext(Arrays.asList(4, 5, 6, 7, 8))
                .expectNext(Arrays.asList(7, 8, 9, 10))
                .expectNext(Collections.singletonList(10))
                .verifyComplete();
    }

    private static void windowGroup() {

        Flux.range(1,10)
                .window(5, 3)
                .concatMap(g->g.defaultIfEmpty(-1)).subscribe(System.out::println);

        StepVerifier.create(
                // 1-10的序列，
                Flux.range(1,10)
                        // 在1-10的序列中，窗口大小为5，每个窗口开始相差3
                .window(5, 3)
                        // 默认显示-1
                .concatMap(g->g.defaultIfEmpty(-1))
        )
                .expectNext(1,2,3,4,5)
                .expectNext(4,5,6,7,8)
                .expectNext(7,8,9,10)
                .expectNext(10).verifyComplete();




        StepVerifier.create(
                Flux.just(1,3,5,2,4,6,11,12,13).windowWhile(i->i % 2 == 0).concatMap(g->g.defaultIfEmpty(-1))
        )
                .expectNext(-1,-1,-1)
                .expectNext(2,4,6)
                .expectNext(12)
                .expectNext(-1)
                .verifyComplete();
    }

    private static void groupBy() {
        StepVerifier.create(Flux.just(1,2,3,4,5,6,7,8,9,10)
                .groupBy(i -> i % 2 == 0 ? "even" : "odd")
                .concatMap(g->
                        g.defaultIfEmpty(-1)
                                .map(String::valueOf)
                                .startWith(g.key())))
                .expectNext("odd", "1", "3", "5", "7", "9")
                .expectNext("even", "2", "4", "6", "8", "10")
                .verifyComplete();

    }
}
