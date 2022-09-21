package leetCode;

import java.util.*;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/6/17 16:28
 */
public class SortList {

    public static void main(String[] args) {
        ListNode node = new ListNode(4);
        ListNode node1 = new ListNode(2);
        ListNode node2 = new ListNode(1);
        ListNode node3 = new ListNode(3);
        node.next = node1;
        node1.next = node2;
        node2.next = node3;
        SortList sl = new SortList();
        ListNode listNode = sl.sortList(node);
        System.out.println(listNode);
    }


    public ListNode sortList(ListNode head) {
        if(head == null)return head;
        Map<Integer,List<ListNode>> table = new HashMap<>();
        ListNode t = head;
        while(t != null){
            List<ListNode> l = table.getOrDefault(t.val,new ArrayList<ListNode>());
            l.add(t);
            table.put(t.val, l);
            t = t.next;
        }
        ListNode dummy = new ListNode();
        ListNode temp = dummy;
        Integer[] keyArr = new Integer[table.keySet().size()];
        keyArr = table.keySet().toArray(keyArr);
        Arrays.sort(keyArr);
        for(Integer k : keyArr){
            List<ListNode> v = table.get(k);
            for(ListNode node : v){
                node.next = null;
                dummy.next = node;
                dummy = dummy.next;
            }
        }
        return temp.next;
    }


    static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
}
