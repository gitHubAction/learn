package mashibing.algorithm.class2;

import java.util.ArrayList;

/**
 * ClassName:    Code01_LinkedList
 * Package:    mashibing.algorithm.class2
 * Datetime:    2020/6/23   9:21
 * Author:   zsh
 * Description: 单、双链表反转
 */
public class Code01_LinkedList {


    public static void main(String[] args) {
        Node node = generateRandomLinkedList(5, 10);
        Node node1 = reverseSigleList(node);
        System.out.println(node1);
        Node node2 = testReverseLinkedList(node1);
        System.out.println(node2);
    }


    public static Node testReverseLinkedList(Node head) {
        if (head == null) {
            return null;
        }
        ArrayList<Node> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        list.get(0).next = null;
        int N = list.size();
        for (int i = 1; i < N; i++) {
            list.get(i).next = list.get(i - 1);
        }
        return list.get(N - 1);
    }


    public static Node generateRandomLinkedList(int len, int value) {
        int size = (int) (Math.random() * (len + 1));
        if (size == 0) {
            return null;
        }
        size--;
        Node head = new Node((int) (Math.random() * (value + 1)));
        Node pre = head;
        while (size != 0) {
            Node cur = new Node((int) (Math.random() * (value + 1)));
            pre.next = cur;
            pre = cur;
            size--;
        }
        return head;
    }

    public static class Node{
        public int value;
        public Node next;

        public Node(int data) {
            value = data;
        }
    }

    public static class DoubleNode {
        public int value;
        public DoubleNode last;
        public DoubleNode next;

        public DoubleNode(int data) {
            value = data;
        }
    }

    /**
     * 单链表反转
     * 1、自我反转(需要记录当前节点的前一节点和next节点，避免联表断裂)
     * 2、用栈的先进后出的特性进行反转
     * 3、采用头插法遍历单链表形成新的链表
     * @param head
     * @return
     */
    public static Node reverseSigleList(Node head){
        //用于保存记录当前节点的前一个节点
        Node pre = null;
        //用于记录当前节点的下一个节点
        Node next = null;
        while (head != null){
            //自我反转
            //保存当前节点的下一个节点，避免链表断裂
            next = head.next;
            //指针反转
            head.next = pre;

            //pre指针后移到下一个要反转的节点的前一个节点
            pre = head;
            //要反转的节点后移
            head = next;
        }
        return pre;
    }


    public static DoubleNode reverseDoubleNodeList(DoubleNode head){
        DoubleNode pre = null;
        DoubleNode next = null;
        while (head != null){
            //记录保存下一个要反转的节点
            next = head.next;

            //反转操作
            head.next = pre;
            head.last = next;
            //指针后移
            pre = head;
            head = next;
        }
        return pre;
    }

    Node reverseList(Node head){
        Node pre = null;
        Node next = null;
        while (head != null){
            next = head.next;

            head.next = pre;

            pre = head;
            head = next;
        }
        return pre;
    }

}
