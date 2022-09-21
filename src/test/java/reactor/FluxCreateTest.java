package reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/8/2 15:48
 */
public class FluxCreateTest {

    @Test
    public void create(){
        // 1.just（）：可以指定序列中包含的全部元素，创建出来的Flux序列会在发布这些元素之后自动结束
        Flux<String> f1 = Flux.just("str1", "str2", "str3");
        // 这里若输入俩参数，则会报错,Mono是一个或者空序列
        Mono<String> m1 = Mono.just("1");

        Flux<String> f2 = Flux.fromIterable(Arrays.asList("1", "2"));

        // 3.fromStream()从一个Stream对象中创建一个Flux对象
        ArrayList<Integer> numList = new ArrayList<>();
        numList.add(1);
        Flux<Integer> f3 = Flux.fromStream(numList.stream());

        // 4.fromArray()从一个数组对象中创建一个Flux对象
        Integer[] arr = new Integer[]{1, 2, 3};
        Flux<Integer> f4 = Flux.fromArray(arr);

        // 5.range（start，count） 表示从start开始，递增的生成count个数字，都是int类型的参数
        Flux<Integer> f5 = Flux.range(2, 5);

        // 6.创建一个不包含任何元素，只发布结束消息的序列。
        // 并且这种方式不会进行后续传递，需要switchIfEmpty（）方法来进行处理。
        // 因为响应式编程中，流的处理是基于元素的，而empty（）是没有元素的！
        Flux<Object> empty = Flux.empty();

        // 7.创建一个只包含错误消息的序列，里面的参数类型是Throwable
        Flux<Object> error = Flux.error(new Exception("error!"));

        // 8.创建一个不包含任何消息通知的序列，注意区别empty()，empty还是会发布结束消息的。
        Flux<Object> never = Flux.never();
    }
}
