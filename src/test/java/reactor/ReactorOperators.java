package reactor;

import lombok.SneakyThrows;
import org.junit.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * The cache will be primed (in the background) with initial values. Events for existing and new nodes will be posted.
 *
 * @author zhangshihao01
 * @version 1.0
 * @description 操作符
 * @date 2022/8/3 10:44
 */
public class ReactorOperators {

    @SneakyThrows
    @Test
    public void buffer(){
        System.out.println("buffer====================");
        Flux.range(1,100).buffer(20).subscribe(System.out::println);


        System.out.println("bufferUntil====================一直收集到Predicate返回true");
        Flux.range(1,10).bufferUntil(i->i % 2 == 0).subscribe(System.out::println);

        System.out.println("bufferWhile====================当Predicate返回true时才收集");
        Flux.range(1,10).bufferWhile(i->i % 2 == 0).subscribe(System.out::println);
        Flux.just(2,4,10).bufferWhile(i->i % 2 == 0).subscribe(System.out::println);
    }


    @Test
    public void window(){

        String s = Integer.toBinaryString(64);
        System.out.println(s);
        Flux.range(1,100).window(20).subscribe(System.out::println);
        //UnicastProcessor
        //UnicastProcessor
        //UnicastProcessor
        //UnicastProcessor
        //UnicastProcessor
        //window会创建新的序列，序列创建完毕之后就会执行onComplete方法
    }

    @Test
    public void zipwith(){
        //把当前流中的元素与另外一个流中的元素按照一对一的方式进行合并。
        // 结果：[a,c] [b,d]
        Flux.just("a", "b").zipWith(Flux.just("c", "d","e","f")).subscribe(System.out::println);
        // 结果：a-c  b-d
        Flux.just("a", "b").zipWith(Flux.just("c", "d"), (s1, s2) -> String.format("%s-%s", s1, s2)).subscribe(System.out::println);
    }


    @Test
    public void take(){
        /**
         * take操作
         * takeLast(long n)：提取流中的最后 N 个元素。
         * takeUntil(Predicate<? super T> predicate)：提取元素直到 Predicate 返回 true。
         * takeWhile(Predicate<? super T> continuePredicate)： 当 Predicate 返回 true 时才进行提取。
         * takeUntilOther(Publisher<?> other)：提取元素直到另外一个流开始产生元素。
         **/
        Flux.range(1, 100).take(3).subscribe(System.out::println);
        System.out.println("===================================================================================");
        Flux.range(1, 100).takeLast(3).subscribe(System.out::println);
        System.out.println("===================================================================================");
        Flux.range(1, 100).takeWhile(i -> i < 3).subscribe(System.out::println);
        System.out.println("===================================================================================");
        Flux.range(1, 100).takeUntil(i -> i == 3).subscribe(System.out::println);
        System.out.println("===================================================================================");
    }

    @SneakyThrows
    @Test
    public void interval(){
        Flux.interval(Duration.ofMillis(3000), Duration.ofMillis(1000)).take(9).subscribe(t-> System.out.println(t+" , 线程: "+Thread.currentThread().getName()));
        Thread.sleep(100000);
    }

    @Test
    public void test() throws InterruptedException {
        Flux.merge(Flux.interval(Duration.ZERO, Duration.ofMillis(100)).take(9),
                Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(2),
                Flux.interval(Duration.ofMillis(100), Duration.ofMillis(100)).take(3))
                .subscribe((t) -> System.out.println("Flux.merge :" + t + ",线程：" + Thread.currentThread().getName()));
        // 让主线程睡眠2s，保证上面的输出能够完整
        Thread.sleep(2000);
    }
}
