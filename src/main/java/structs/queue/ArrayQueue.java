package structs.queue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayQueue {

    //指向队列第一个元素的地址
    private int front = -1;

    //队列的对尾元素
    private int tail = -1;

    //
    private int[] arr;

    //队列最大长度
    private int maxSize = 10;


    public ArrayQueue(int size){

        arr = new int[(size < 0 || size > maxSize) ? maxSize : size];
    }

    //是否为空
    public boolean isEmpty(){
        return front == tail;
    }

    //是否已满
    public boolean isFull(){
        return tail  == arr.length - 1;
    }

    //添加元素到队列
    public ArrayQueue add(int i){
        //队尾后移
        arr[++tail] = i;
        return this;
    }

    //出队操作
    public int poll(){
        //头指针向后移动
        front++;
        return arr[front];
    }


    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue(5);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);

        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());

        List<Integer> ints = new ArrayList<>();
        ints.add(1);
        ints.add(2);
        ints.add(3);
        ints.add(4);
        ints.add(5);
        ints.add(2,3);
        ints.forEach(System.out::println);
    }
}
