package juc;

import JUC.CompletableFutureUtil;

import java.time.Duration;
import java.util.concurrent.*;

/**
 * ClassName:    JUCTest
 * Package:    juc
 * Datetime:    2020/6/4   15:12
 * Author:   zsh
 * Description:
 */
public class JUCTest {
    public void whenComplete() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 100);
        future.whenComplete((l, r) -> System.out.println(l));
    }

    public void thenApply() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 100);
        future.thenApply(i -> -i);
    }

    public void thenAccept() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 100);
        future.thenAccept(System.out::println);
    }

    public void thenRun() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 100);
        future.thenRun(() -> System.out.println("Done"));
    }

    public void thenAcceptBoth() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 100);
        CompletableFuture<Integer> other = CompletableFuture.supplyAsync(() -> 200);
        future.thenAcceptBoth(other, (x, y) -> System.out.println(x + y));
    }

    public void acceptEither() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 100);
        CompletableFuture<Integer> other = CompletableFuture.supplyAsync(() -> 200);
        future.acceptEither(other, System.out::println);

    }

    public void allOf() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 100);
        CompletableFuture<Integer> second = CompletableFuture.supplyAsync(() -> 200);
        CompletableFuture<Integer> third = CompletableFuture.supplyAsync(() -> 300);

    }

    public void anyOf() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() ->{
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        } );
        CompletableFuture<Integer> second = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 200;
        });
        CompletableFuture<Integer> third = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 300;
        });
        CompletableFuture<Object> objectCompletableFuture = CompletableFuture.anyOf(future, second, third);

        System.out.println(objectCompletableFuture.get());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Long.MAX_VALUE);
        JUCTest juc = new JUCTest();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletableFuture<String> resultA = CompletableFutureUtil.exec(
                CompletableFuture.supplyAsync(() -> juc.getA(),executorService )
                , Duration.ofSeconds(10),TimeUnit.SECONDS);

        CompletableFuture<String> resultB = CompletableFutureUtil.exec(
                CompletableFuture.supplyAsync(() -> juc.getB(), executorService)
                , Duration.ofSeconds(1),TimeUnit.SECONDS).exceptionally(e->{
            System.out.println("异常。。。。。。。。。。。");
                    e.printStackTrace();
                    return null;
                });
        CompletableFuture.allOf(resultA, resultB);
        String s = resultA.get();
        System.out.println(s);
        String b = resultB.get();
        System.out.println(b);
    }


    public String getA(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("5s返回A");
        return "A";
    }

    public String getB(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "B";
    }

}
