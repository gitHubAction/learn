import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: zhangsh
 * @Date: 2019/8/20 11:55
 * @Version 1.0
 * Description
 */
public class JDK8Stream {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        // 获取对应的平方数
        List<Integer> squaresList = numbers.stream()
                .map( i -> i*i)
                .distinct()//去重
                .collect(Collectors.toList());
        squaresList.forEach(System.out::println);


        List<String> list = Arrays.asList("abc","bb","cc","a","b");
        Function<String, Integer> stringIntegerFunction = String::length;
        System.out.println(stringIntegerFunction);
        Map<Integer, List<String>> collect = list.stream().collect(Collectors.groupingBy(stringIntegerFunction));
        System.out.println(collect.toString());
        System.out.println("----------------------------------------------------------");

        test();

        System.out.println("============================================================");

        List<String> wordList = Arrays.asList("tommy", "is", "a", "java", "developer");
        Stream<String> wordStream = wordList.stream();

        int wordCnt = wordStream.collect(Collector.of(
                ()-> new int[1],
                (result, item) -> result[0] += item.length(),
                (result1, result2) -> {
                    result1[0] += result2[0];
                    return result1;
                },
                total -> total[0]
        ));

        System.out.println("wordCnt = " + wordCnt);



        List<String> intArr = new ArrayList<>();
        intArr.add("1");
        intArr.add("2");
        intArr.add("1");
        intArr.add("1");
        intArr.add("1");
        intArr.add("1");

        intArr.stream().filter((i) -> {
            if("1".equals(i))return false;
            return true;
        }).forEach(System.out::println);
        System.out.println("======================");
        for (int i = 0; i < intArr.size(); i++) {
            if("1".equals(intArr.get(i)))intArr.remove(i);
        }

        intArr.stream().forEach(System.out::println);
        System.out.println("<><><><<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>");


                Arrays.asList(
                        new Person("Max", 18),
                        new Person("Peter", 23),
                        new Person("Pamela", 23),
                        new Person("David", 12)).stream().filter(person -> {
                            if(person.getName().equals("Max"))person.setName("max111");
                            return true;
                }).forEach(System.out::println);

    }



    static void test(){
        List<Person> persons =
                Arrays.asList(
                        new Person("Max", 18),
                        new Person("Peter", 23),
                        new Person("Pamela", 23),
                        new Person("David", 12));


        Collector<Person, StringJoiner, String> personNameCollector =
                Collector.of(
                        () -> {
                            System.out.println("supplier");
                            return new StringJoiner(" | ");
                        },
                        (j, p) -> {
                            System.out.format("accumulator: p=%s; j=%s\n", p, j);
                            j.add(p.name.toUpperCase());
                        },
                        (j1, j2) -> {
                            System.out.println("merge");
                            return j1.merge(j2);
                        },
                        j -> {
                            System.out.println("finisher");
                            return j.toString();
                        });

        String names = persons
                .stream()
                .collect(personNameCollector);

        System.out.println(names);

    }
}

class Person {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return name+"<>"+age;
    }
}
