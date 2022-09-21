package reactor;

import reactor.core.publisher.Flux;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/7/29 11:29
 */
public class FluxHandler {

    public static void main(String[] args) {
        FluxHandler fluxHandler = new FluxHandler();
        fluxHandler.handler();
    }

    public void handler(){
        Flux<Object> alphabet = Flux.just(-1, 20, 30, 9, 13).handle((i, sink) -> {
            String letter = alphabet(i);
            if (letter != null && letter != "") {
                sink.next(letter);
            }
        });
        alphabet.subscribe(System.out::println);
    }

    private String alphabet(Integer i) {
        if(i < 1 || i > 26)return null;
        return ""+(char)('A' + i -1);
    }
}
