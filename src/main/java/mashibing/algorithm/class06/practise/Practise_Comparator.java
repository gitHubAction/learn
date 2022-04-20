package mashibing.algorithm.class06.practise;

import java.util.Comparator;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/2/10 16:16
 */
public class Practise_Comparator {

    public static class Teacher {
        public String name;
        public int id;
        public int age;

        public Teacher(String name, int id, int age) {
            this.name = name;
            this.id = id;
            this.age = age;
        }
    }


    public static class MyIdAscComparator implements Comparator<Teacher>{

        @Override
        public int compare(Teacher o1, Teacher o2) {
            return o1.id != o2.id ? o1.id - o2.id : o1.age - o2.age;
        }
    }
}
