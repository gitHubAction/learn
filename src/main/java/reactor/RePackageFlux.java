package reactor;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/8/1 16:49
 */
public class RePackageFlux {

    public static void main(String[] args) {
        // 重用、打包

        // 所有的subcriber公用一个function，和直接在操作符上用是一样的
        transfer();

        System.out.println("---------------------------------------------");
        // 每一个 subscription 可以生成不同的操作链（通过维护一些状态值）
        compose();

    }

    private static void compose() {
        AtomicInteger a = new AtomicInteger();
        Function<Flux<String>,Publisher<String>> function = f->{
            if(a.getAndIncrement() == 1){
                return f.filter(color->!color.equals("blue")).map(String::toUpperCase);
            }
            return f.filter(color->!color.equals("red")).map(String::toUpperCase);
        };
        Flux<String> transform = Flux.fromIterable(Arrays.asList("blue", "orange", "red"))
                .doOnNext(System.out::println);
//                .compose(function);
        transform
                .subscribe(d-> System.out.println("Compose1111111Subscriber to Compose MapFilter: " +d ));

        transform
                .subscribe(d-> System.out.println("Compose2222222Subscriber to Compose MapFilter: " +d ));
    }

    private static void transfer() {
        AtomicInteger a = new AtomicInteger();
        Function<Flux<String>,Publisher<String>> function = f->{
            if(a.getAndIncrement() == 1){
                return f.filter(color->!color.equals("blue")).map(String::toUpperCase);
            }
            return f.filter(color->!color.equals("red")).map(String::toUpperCase);
        };
        Flux<String> transform = Flux.fromIterable(Arrays.asList("blue", "orange", "red"))
                .doOnNext(System.out::println)
                .transform(function);
        transform
                .subscribe(d-> System.out.println("1111111Subscriber to Transform MapFilter: " +d ));

        transform
                .subscribe(d-> System.out.println("2222222Subscriber to Transform MapFilter: " +d ));
    }
}
