package reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/8/2 14:05
 */
public class ParallelFluxTest {
    public static void main(String[] args) {
        parallelFlux();
    }

    private static void parallelFlux() {
        ParallelFlux<Integer> parallelFlux = Flux.range(1, 10)
                .parallel(2)
                .doOnNext(System.out::println)
                .runOn(Schedulers.parallel());
        parallelFlux.subscribe(i-> System.out.println(Thread.currentThread().getName() +"    "+ i));

    }
}
