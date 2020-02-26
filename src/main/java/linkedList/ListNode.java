package linkedList;

/**
 * @Author: zhangsh
 * @Date: 2020/1/3 13:18
 * @Version 1.0
 * Description
 */
public class ListNode {
    private int val;
    private ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }
}
