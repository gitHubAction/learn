package linkedList;

/**
 * @Author: zhangsh
 * @Date: 2020/1/3 13:18
 * @Version 1.0
 * Description
 */
public class Node {
    int val;
    Node next;

    public Node(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
