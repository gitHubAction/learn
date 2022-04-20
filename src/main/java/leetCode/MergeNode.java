package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/3/1 14:58
 */
public class MergeNode {
    public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

    public ListNode mergeKLists(ListNode[] lists) {
        if(lists == null || lists.length < 1){
            return new ListNode();
        }
        ListNode res = new ListNode();
        int p = 0;
        while(p < lists.length - 1){
            res = func(res,lists[p], lists[++p]);
        }
        return res.next;
    }

    public ListNode func(ListNode head, ListNode l0, ListNode l1){
        ListNode temp = head;
        while(l0 != null && l1 != null){
            if(l0.val <= l1.val){
                head.next = l0;
                l0 = l0.next;
            }else{
                head.next = l1;
                l1 = l1.next;
            }
            head = head.next;
        }
        // l1循环完了  l0没有完
        while(l0 != null){
            head.next = l0;
            head = head.next;
            l0 = l0.next;
        }
        // l0循环完了  l1没有完
        while(l1 != null){
            head.next = l1;
            head = head.next;
            l1 = l1.next;
        }
        return temp;
    }

    public static void main(String[] args) {
        MergeNode mergeNode = new MergeNode();
        mergeNode.mergeKLists(new ListNode[2]);
    }
}
