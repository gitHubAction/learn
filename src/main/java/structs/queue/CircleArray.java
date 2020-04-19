package structs.queue;

import java.util.Scanner;

/**
 * 环形数组模拟队列
 *  1、改变头指针的含义，让其直接指向数组的第一个元素
 *  2、尾指针指向最后一个元素的后一个位置
 *  3、添加元素和弹出元素的时候注意对数组索引进行除余取模，防止数组越界
 *  4、尾索引的下个索引为front头索引时，说明对列已满（(tail+1)%size == front）
 *  5、对列中的元素个数为 (tail-front)%size
 *  为了防止出现负数可以
 *      让尾索引加上对列大小 (tail+size-front)%size
 *      或者  对公式取绝对值 (tail-front)%size < 0 ? (front-tail)%size : (tail-front)%size
 */
public class CircleArray {
    //指向队列第一个元素，默认为0
    private int front ;

    //队列的对尾元素的后一个元素，默认为0
    private int tail;

    //
    private int[] arr;

    private int size;

    public CircleArray(int size){
        arr = new int[size];
        this.size = arr.length;
    }

    //是否为空
    public boolean isEmpty(){
        return front == tail;
    }

    //是否已满
    public boolean isFull(){
        return (tail + 1) % size == front;
    }

    //添加元素到队列
    public void add(int i){
        if(isFull()){
            System.out.println("对列已满");
            return;
        }
        arr[tail] = i;
        //队尾后移
        tail = (tail+1)%size;
    }

    //出队操作
    public int peek() throws Exception {
        if(isEmpty()){
            System.out.println("对列为空");
            throw new Exception("对列为空");
        }
        int i = arr[front];
        front = (front+1)%size;
        return i;
    }



    public int headQueue() throws Exception {
        if(isEmpty()){
            System.out.println("对列为空");
            throw new Exception("对列为空");
        }
        return arr[front];
    }

    public int size(){
//        return s
//        (tail-front)%size < 0 ? (front-tail)%size : (tail-front)%size;
        return (tail + size -front) % size;
    }

    public void getElement(){
        for (int i = front; i < front+size(); i++) {
            System.out.printf("arr[%d] = %d\t",i%size,arr[i%size]);
        }
    }


    public static void main(String[] args) {
        //测试一把
        System.out.println("测试数组模拟环形队列的案例~~~");

        // 创建一个环形队列
        CircleArray queue = new CircleArray(4); //说明设置4, 其队列的有效数据最大是3
        char key = ' '; // 接收用户输入
        Scanner scanner = new Scanner(System.in);//
        boolean loop = true;
        // 输出一个菜单
        while (loop) {
            System.out.println("s(show): 显示队列");
            System.out.println("e(exit): 退出程序");
            System.out.println("a(add): 添加数据到队列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");
            key = scanner.next().charAt(0);// 接收一个字符
            switch (key) {
                case 's':
                    queue.getElement();
                    break;
                case 'a':
                    System.out.println("输出一个数");
                    int value = scanner.nextInt();
                    queue.add(value);
                    break;
                case 'g': // 取出数据
                    try {
                        int res = queue.peek();
                        System.out.printf("取出的数据是%d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h': // 查看队列头的数据
                    try {
                        int res = queue.headQueue();
                        System.out.printf("队列头的数据是%d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e': // 退出
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出~~");
    }

}
