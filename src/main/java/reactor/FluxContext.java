package reactor;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/8/2 15:24
 */
public class FluxContext {

    public static void main(String[] args) {
        String key = "message";
        Mono<String> mo = Mono.just("hello")
                .contextWrite(ctx -> ctx.put(key, "world"))
                .flatMap(s ->
//                    Mono.subscriberContext().map(ctx -> s + " " + ctx.getOrDefault(key, "Reactor"))
                    Mono.deferContextual(ctx -> Mono.just(s + " " + ctx.getOrDefault(key, "Reactor")))
                );

        StepVerifier.create(mo).expectNext("hello Reactor").verifyComplete();
    }
}
