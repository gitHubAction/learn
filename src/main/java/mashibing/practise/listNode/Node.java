package mashibing.practise.listNode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/10/12 10:42
 */
public class Node<V> {
    private V val;
    private Node next;

    public Node(V val){
        this.val = val;
    }


    static class MyStack<V>{
        Node<V> head;
        int size;
        public MyStack(){

        }

        public void offer(V val){
            Node cur = new Node(val);
            if(head == null){
                head = cur;
            }else{
                cur.next = head;
                head = cur;
            }
            size++;
        }

        public V pop(){
            if(head == null)return null;
            V res = head.val;
            head = head.next;
            size--;
            return res;
        }

        public int size(){
            return size;
        }
    }


    static class MyQueue<V>{
        Node<V> head;
        Node<V> tail;
        int size;

        public MyQueue(){}

        public void offer(V v){
            Node cur = new Node(v);
            if(head == null || tail == null){
                head = cur;
                tail = cur;
            }else{
                tail.next = cur;
                tail = cur;
            }
            size++;
        }

        public V pop(){
            if(head == null)return null;
            V res = head.val;
            head = head.next;
            // 保证头尾指针同步
            if(head == null)tail = null;
            size--;
            return res;
        }

        public int size(){
            return size;
        }
    }


    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        MyStack<Integer> myStack = new MyStack<>();

        Queue<Integer> queue = new LinkedList<>();
        MyQueue<Integer> myQueue = new MyQueue<>();
        for(int i = 0; i < 10000; i++){
            stack.push(i);
            myStack.offer(i);

            queue.offer(i);
            myQueue.offer(i);
        }

        while(!stack.isEmpty()){
            if(stack.size() != myStack.size()){
                System.out.println("stack opps...!!!!!");
            }
            if(!stack.pop().equals(myStack.pop())){
                System.out.println("stack opps...!!!!!");
            }

            if(queue.size() != queue.size()){
                System.out.println("queue opps...!!!!!");
            }
            if(!queue.poll().equals(myQueue.pop())){
                System.out.println("queue opps...!!!!!");
            }
        }
    }
}
