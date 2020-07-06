package mashibing.algorithm.class4;

/**
 * ClassName:    Code01_heap
 * Package:    mashibing.algorithm.class4
 * Datetime:    2020/7/6   15:03
 * Author:   zsh
 * Description: 数组构造堆结构
 */
public class Code01_heap {


    static class MyMaxHeap{
        int[] arr;
        int size;
        int limit;
        public MyMaxHeap(int limit){
            this.limit = limit;
            arr = new int[limit];
            size = 0;
        }

        public boolean isEmpty(){
            return size == 0;
        }

        public boolean isFull(){
            return size == limit;
        }

        public void push(int i){
            if(isFull()){
                throw new RuntimeException("堆已满");
            }
            arr[size] = i;
            //构造堆结果
            heapInsert(arr,size++);
        }


        //弹出堆顶元素(最大或者最小)
        public int pop(){
            if(isEmpty()){
                throw new RuntimeException("堆为空");
            }
            int res = arr[0];
            //将最后一个元素与堆顶元素互换位置，size--
            swap(arr,0, --size);
            //重新构造堆结构
            heapify(arr,0,size);
            return res;
        }

        //向下看，获取左右孩子中最大的与自己比较
        //越界时停，比孩子大时停
        private void heapify(int[] arr, int i, int size) {
            int left = (2 * i) + 1;
            while (left < size){
                //有右孩子，并且选出最大的孩子
                int largest = left+1 < size && arr[left+1] > arr[left] ? left+1 : left;

                largest = arr[largest] > arr[i] ? largest : i;
                if(largest == i){
                    break;
                }
                //交换
                swap(arr,i,largest);
                //i向下移动
                i = largest;
                left = (2 * i) + 1;
            }
        }


        //不断向上看，比较是否比父节点的值大，大则交换
        private void heapInsert(int[] arr, int i) {
            //父节点的index
            while (arr[i] > arr[(i - 1)/2]){
                swap(arr,i,(i - 1)/2);
                i = (i - 1)/2;
            }
        }

        private void swap(int[] arr, int i, int index) {
            int temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;
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
        int testTimes = 100;
        for (int i = 0; i < testTimes; i++) {
            int curLimit = (int) (Math.random() * limit) + 1;
            MyMaxHeap my = new MyMaxHeap(curLimit);
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
