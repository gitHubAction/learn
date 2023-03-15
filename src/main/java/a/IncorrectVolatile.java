package a;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/2/15 10:48
 */
public class IncorrectVolatile implements Runnable {
    volatile int a = 0;

    //原子类, 统计执行了多少次
    AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException, IOException {


        ObjectMapper om = new ObjectMapper();
        List<String> strings = om.readValue("[\"12312\",\"dadfasf\"]", new TypeReference<List<String>>() {
        });
        strings.forEach(System.out::println);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            a++;
            atomicInteger.incrementAndGet();
        }
    }
}