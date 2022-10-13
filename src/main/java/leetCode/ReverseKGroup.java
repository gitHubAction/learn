package leetCode;

import java.util.Stack;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description https://leetcode.cn/problems/reverse-nodes-in-k-group/
 * 25. K 个一组翻转链表
 * 给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
 *
 * k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 *
 * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
 *
 *
 *
 * 示例 1：
 *
 *
 * 输入：head = [1,2,3,4,5], k = 2
 * 输出：[2,1,4,3,5]
 * 示例 2：
 *
 *
 *
 * 输入：head = [1,2,3,4,5], k = 3
 * 输出：[3,2,1,4,5]
 * @date 2022/10/13 15:07
 */
public class ReverseKGroup {

    public class ListNode {
     int val;
     ListNode next;
     ListNode() {}
     ListNode(int val) { this.val = val; }
     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 }


    public ListNode reverseKGroupWithStack(ListNode head, int k) {
        Stack<ListNode> stack = new Stack<>();
        ListNode res = new ListNode();
        ListNode resT = res;
        int t = k;
        while(head != null){
            stack.push(head);
            head = head.next;
            t--;
            if(t == 0){
                while(!stack.isEmpty()){
                    resT.next = stack.pop();
                    resT = resT.next;
                }
                t = k;
                resT.next = null;
            }
        }
        // 最后的可能不够k个
        while(stack.size() > 1){
            stack.pop();
            if(stack.size() == 1){
                resT.next = stack.pop();
            }
        }
        return res.next;
    }


    public ListNode reverseKGroup(ListNode head,int k){
        ListNode start = head;
        ListNode end = groupK(start,k);
        if(end == null)return head;
        // 反转start->...->end之间的节点
        reverse(start,end);
        head = end;
        ListNode lastEnd = start;
        while(lastEnd.next != null){
            start = lastEnd.next;
            end = groupK(start,k);
            if(end == null)return head;
            reverse(start,end);
            lastEnd.next = end;
            lastEnd = start;
        }
        return head;
    }

    private void reverse(ListNode start, ListNode end) {
        ListNode endNext = end.next;
        ListNode pre = null,next = null,cur = start;
        while(cur != end){
            next = cur.next;

            cur.next = pre;
            pre = cur;

            cur = next;
        }
        // 避免断开
        start.next = endNext;
    }

    private ListNode groupK(ListNode start, int k) {
        while(start != null && --k != 0){
            start = start.next;
        }
        return start;
    }

}
