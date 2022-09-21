package leetCode;

import leetCode.LeetCode.ListNode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description 反转链表K
 * @date 2022/7/20 9:55
 */
public class ReversListNode {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        ReversListNode a = new ReversListNode();
    }




    public ListNode reverseK(ListNode head, int k){
        ListNode start = head;
        ListNode end = groupK(start, k);
        if(end == null)return head;
        reverse(start, end);
        head = end;
        ListNode lastEnd = start;
        while(lastEnd.next != null){
            start = lastEnd.next;
            end = groupK(start,k);
            if(end == null){
                return head;
            }
            reverse(start, end);
            // 上一轮反转的尾结点 连上 本轮的头结点
            lastEnd.next = end;
            // 本轮的尾结点 替换 上一轮的尾结点作为新一轮的尾结点
            lastEnd = start;
        }
        return head;
    }


    public ListNode groupK(ListNode start, int k){
        while(--k != 0 && start != null){
            start = start.next;
        }
        return start;
    }

    public void reverse(ListNode start, ListNode end){
        // 记录下一个节点即反转结束的节点
        end = end.next;

        ListNode cur = start;
        ListNode pre = null;
        ListNode next = null;
        while(cur != end){
            // 记录下一个要处理的节点
            next = cur.next;
            // 处理当前节点
            cur.next = pre;
            pre = cur;
            // 要处理的节点往下跳
            cur = next;
        }
        // 反转之后的尾结点与之后的节点相连，保证链表不丢
        start.next = end;
    }
}
