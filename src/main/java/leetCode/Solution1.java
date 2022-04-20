package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/3/3 9:41
 */
public class Solution1 {
        public static ListNode mergeKLists(ListNode[] lists) {
            if(lists == null || lists.length == 0){
                return null;
            }
            ListNode res = new ListNode(0);
            ListNode temp = lists[0];
            res.next = temp;
            int p = 1;
            while(p < lists.length){
                temp = merge(temp,lists[p++]);
                res.next = temp;
            }
            return res.next;
        }

        public static ListNode merge(ListNode l0, ListNode l1){
            ListNode res = new ListNode(0);
            ListNode temp = res;
            while(l0 != null && l1 != null){
                if(l0.val <= l1.val){
                    temp.next = l0;
                    l0 = l0.next;
                }else{
                    temp.next = l1;
                    l1 = l1.next;
                }
                temp = temp.next;
            }
            // l1循环完了  l0没有完
            while(l0 != null){
                temp.next = l0;
                temp = temp.next;
                l0 = l0.next;
            }
            // l0循环完了  l1没有完
            while(l1 != null){
                temp.next = l1;
                temp = temp.next;
                l1 = l1.next;
            }
            return res.next;
        }


  public static class ListNode {
     int val;
     ListNode next;
     ListNode() {}
     ListNode(int val) { this.val = val; }
     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 }
}