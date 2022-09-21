package reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/7/29 11:45
 */
@Slf4j
public class FluxPubliser {

    public static void main(String[] args) {
        Flux.range(1, 10000)
                .publishOn(Schedulers.parallel())
                .subscribe();
    }
}
