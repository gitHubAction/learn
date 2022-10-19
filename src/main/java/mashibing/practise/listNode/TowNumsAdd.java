package mashibing.practise.listNode;

import leetCode.AddTowNum;

/**
 * @Author: zhangsh
 * @Date: 2019/8/19 15:28
 * @Version 1.0
 * Description
 */
public class TowNumsAdd {

    public static ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
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


    public ListNode addTwoNumbers(ListNode l1, ListNode l2){
        if(l1 == null || l2 == null){
            return l1 == null ? l2 : l1;
        }
        int len1 = length(l1);
        int len2 = length(l2);

        ListNode l = len1 >= len2 ? l1 : l2;
        ListNode s = l == l1 ? l2 : l1;
        ListNode ans = l;
        ListNode end = l;
        int carry = 0;
        while(s != null){
            int t = s.val + l.val + carry;
            l.val = t % 10;
            carry = t / 10;

            end = l;
            s = s.next;
            l = l.next;
        }

        while(l != null){
            int t = l.val + carry;
            l.val = t % 10;
            carry = t / 10;

            end = l;
            l = l.next;
        }

        if(carry != 0)end.next = new ListNode(carry);
        return ans;
    }

    public int length(ListNode l1) {
        int ans = 0;
        while(l1 != null){
            ans++;
            l1 = l1.next;
        }
        return ans;
    }


    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        ListNode l12 = new ListNode(4);
        ListNode l13 = new ListNode(3);
//        Node l14 = new Node(2);
//        Node l15 = new Node(6);

        l1.next = l12;
        l12.next = l13;
//        l13.next = l14;
//        l14.next = l15;

        ListNode l2 = new ListNode(5);
        ListNode l22 = new ListNode(6);
        ListNode l23 = new ListNode(9);
//        Node l24 = new Node(7);
//        Node l25 = new Node(5);

        l2.next = l22;
        l22.next = l23;
//        l23.next = l24;
//        l24.next = l25;
        TowNumsAdd a = new TowNumsAdd();
        ListNode listNode = a.addTwoNumbers(l1, l2);
    }
}
