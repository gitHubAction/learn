package mashibing.algorithm.class06.practise;

import mashibing.algorithm.class06.Code02_Heap;

import java.util.PriorityQueue;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/2/10 10:14
 */
public class Practise_Heap {

    public static class MyHeap{
        int[] heap;
        int heapSize;
        final int limit;

        public MyHeap(int limit){
           this.limit = limit;
           this.heap = new int[limit];
           this.heapSize = 0;
        }

        public boolean isFull(){
            return heapSize == limit;
        }

        public boolean isEmpty(){
            return heapSize == 0;
        }

        public int size(){
            return heapSize;
        }

        public void push(int i){
            if(heapSize == limit){
                throw new RuntimeException("heap is full");
            }
            // 插入数组尾部
            heap[heapSize] = i;
            // 向上移动
            heapUp(heap, heapSize++);
        }

        // 向上调整
        private void heapUp(int[] arr, int i) {
            while(arr[i] > arr[(i - 1) / 2 ]){
                swap(arr, i , (i-1)/2);
                i = (i-1)/2;
            }
        }

        // 向下调整
        private void heapify(int[] arr, int index, int heapSize){
            int left = index * 2 + 1;
            while (left < heapSize){
                int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
                largest = arr[index] > arr[largest] ? index : largest;
                if(largest == index){
                    break;
                }
                swap(arr, index, largest);
                index = largest;
                left = index * 2 + 1;
            }
        }

        public int pop(){
            if(isEmpty()){
                throw new RuntimeException("heap is empty");
            }
            int res = heap[0];
            swap(heap, 0, --heapSize);
            heapify(heap, 0, heapSize);
            return res;
        }

        private void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }



    public static class RightMaxHeap {
        private int[] arr;
        private final int limit;
        private int size;

        public RightMaxHeap(int limit) {
            arr = new int[limit];
            this.limit = limit;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("heap is full");
            }
            arr[size++] = value;
        }

        public int pop() {
            int maxIndex = 0;
            for (int i = 1; i < size; i++) {
                if (arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }
            int ans = arr[maxIndex];
            arr[maxIndex] = arr[--size];
            return ans;
        }

    }


    public static void main(String[] args) {
        int value = 1000;
        int limit = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            int curLimit = (int) (Math.random() * limit) + 1;
            MyHeap my = new MyHeap(curLimit);
            RightMaxHeap test = new RightMaxHeap(curLimit);
            int curOpTimes = (int) (Math.random() * limit);
            for (int j = 0; j < curOpTimes; j++) {
                if (my.isEmpty() != test.isEmpty()) {
                    System.out.println("Oops!");
                }
                if (my.isFull() != test.isFull()) {
                    System.out.println("Oops!");
                }
                if (my.isEmpty()) {
                    int curValue = (int) (Math.random() * value);
                    my.push(curValue);
                    test.push(curValue);
                } else if (my.isFull()) {
                    if (my.pop() != test.pop()) {
                        System.out.println("Oops!");
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * value);
                        my.push(curValue);
                        test.push(curValue);
                    } else {
                        if (my.pop() != test.pop()) {
                            System.out.println("Oops!");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");

    }

}
