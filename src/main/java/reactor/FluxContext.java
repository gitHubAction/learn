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
                .flatMap(s ->
//                    Mono.subscriberContext().map(ctx -> s + " " + ctx.getOrDefault(key, "Reactor"))
                    Mono.deferContextual(ctx -> {
                        String reactor = ctx.getOrDefault(key, "reactor");
                        System.out.println(reactor);
                        return Mono.just(s + " " + ctx.getOrDefault(key, "Reactor"));
                    })
                )
                .contextWrite(ctx -> ctx.put(key, "world"));

        StepVerifier.create(mo).expectNext("hello world").verifyComplete();
    }
}
