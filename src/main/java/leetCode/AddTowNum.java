package leetCode;

/**
 * @Author: zhangsh
 * @Date: 2020/3/4 17:15
 * @Version 1.0
 * Description
 */
public class AddTowNum {


    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode resultNode = new ListNode(0);
        ListNode curr = resultNode;
        int p = 0;
        while (l1  != null || l2 != null){
            int num1 = l1== null ? 0 : l1.val;
            int num2 = l2== null ? 0 : l2.val;
            int sum = num1 + num2 + p;
            //取模(进位)
            p = sum / 10;
            //取余
            int y = sum % 10;

            curr.next = new ListNode(y);
            curr = curr.next;

            if(l1 != null)l1 = l1.next;
            if(l2 != null)l2 = l2.next;
        }
        if(p!= 0){
            //设置进位
            curr.next = new ListNode(p);
        }
        return resultNode.next;
    }

    class ListNode{
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}
