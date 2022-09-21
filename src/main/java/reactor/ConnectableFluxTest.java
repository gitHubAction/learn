package reactor;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/8/1 13:53
 */
public class ConnectableFluxTest {

    public static void main(String[] args) throws InterruptedException {
//        maulConnectableFlux();


        autoConnectableFlux();
    }

    private static void autoConnectableFlux() throws InterruptedException {
        Flux<Integer> autoConnectableFlux = Flux.range(1, 3).doOnSubscribe(s -> System.out.println("autoConnectableFlux"));

        autoConnectableFlux.publish().autoConnect(2);
        autoConnectableFlux.subscribe(System.out::println, e -> {}, () -> {});
        System.out.println("subscribed first");
        Thread.sleep(5000);
        System.out.println("subscribing second");
        autoConnectableFlux.subscribe(System.out::println, e -> {}, () -> {});
    }

    private static void maulConnectableFlux() throws InterruptedException {
        Flux<Integer> source = Flux.range(1, 3).doOnSubscribe(s -> System.out.println("subscribed to source"));
        ConnectableFlux<Integer> connectableFlux = source.publish();

        connectableFlux.subscribe(System.out::println, e->{}, ()->{});
        connectableFlux.subscribe(System.out::println, e->{}, ()->{});

        System.out.println("subscribe done");

        Thread.sleep(5000);
        System.out.println("will now connect");


        connectableFlux.connect();


    }
}