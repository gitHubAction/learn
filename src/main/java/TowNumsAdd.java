/**
 * @Author: zhangsh
 * @Date: 2019/8/19 15:28
 * @Version 1.0
 * Description
 */
public class TowNumsAdd {

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode cur = result;
        int jw = 0;
        while(l1 != null || l2 != null){
            int x = l1 == null ? 0 :l1.val;
            int y = l2 == null ? 0 :l2.val;

            cur.next = new ListNode(((x+y+jw)%10));
            cur = cur.next;
            jw = (x+y+jw)/10;

            if(l1 !=null)l1 = l1.next;
            if(l2 !=null)l2 = l2.next;

        }
        if(jw > 0){
            cur.next = new ListNode(jw);
        }
        return result.next;
    }


    static class ListNode{
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        ListNode l12 = new ListNode(4);
        ListNode l13 = new ListNode(3);
//        ListNode l14 = new ListNode(2);
//        ListNode l15 = new ListNode(6);

        l1.next = l12;
        l12.next = l13;
//        l13.next = l14;
//        l14.next = l15;

        ListNode l2 = new ListNode(5);
        ListNode l22 = new ListNode(6);
        ListNode l23 = new ListNode(9);
//        ListNode l24 = new ListNode(7);
//        ListNode l25 = new ListNode(5);

        l2.next = l22;
        l22.next = l23;
//        l23.next = l24;
//        l24.next = l25;

        ListNode listNode = addTwoNumbers(l1, l2);
    }
}
