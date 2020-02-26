import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

/**
 * @Author: zhangsh
 * @Date: 2019/10/31 18:06
 * @Version 1.0
 * Description
 */
public class NodeTest {


    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        node1.setNext(node2);
        node2.setNext(node3);
        node3.setNext(node4);
        node4.setNext(node5);


//        Node node = reverseLinkedList(node1);
        Node node = reverseLoop(node1);
        System.out.println(node);



      /*  List<Node> studentList = Stream.of(students,
                asList(new Student("艾斯", 25, 183),
                        new Student("雷利", 48, 176)))
                .flatMap(students1 -> students1.stream()).collect(Collectors.toList());*/
    }


    static Node reverseLinkedList(Node node) {
        if (node == null || node.next == null) {//当节点为null或者下一个节点为null时开始弹栈
            return node;
        } else {
            Node headNode = reverseLinkedList(node.next);//一直压栈
            node.next.next = node;
            node.next = null;
            return headNode;
        }
    }

    static Node reverseLoop(Node node){
        if(node == null || node.next == null )return null;
        Node first = node;
        Node reverse = null;        //reverse用于储存前一节点，second用于储存后一节点*
        while (first != null) {
            Node second = first.next;

            first.next = reverse;
            reverse = first;

            first = second;
        }

        return reverse;
    }



    static class Node{
        private int val;
        private Node next;

        Node(int val){
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public Node next() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
