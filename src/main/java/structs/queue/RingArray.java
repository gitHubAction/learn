package structs.queue;

/**
 * ClassName:    ArrayQueue1
 * Package:    structs.queue
 * Datetime:    2020/6/28   14:10
 * Author:   zsh
 * Description: 数组实现队列
 */
public class RingArray {

    private int pushIndex;

    private int pollIndex;

    private int size;

    private int limit;

    private int[] arr;

    public RingArray(int size){
        this.arr = new int[size];
        this.limit = size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public boolean isFull(){
        return size == limit;
    }


    public void push(int ele){
        if(isFull()){
            throw new RuntimeException("队列已满");
        }
        size++;
        this.arr[pushIndex] = ele;
        pushIndex = pushIndex < limit -1 ? pushIndex + 1 : 0;
    }

    public int poll(){
        if(isEmpty()){
            throw new RuntimeException("队列为空");
        }
        size--;
        int i = this.arr[pollIndex];
        pollIndex = pollIndex < limit -1 ? pollIndex + 1 : 0;
        return i;
    }

}
