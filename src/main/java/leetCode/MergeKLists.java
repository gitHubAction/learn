package leetCode;

import java.util.PriorityQueue;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description  https://leetcode.cn/problems/merge-k-sorted-lists
 * 23. 合并K个升序链表
 * 给你一个链表数组，每个链表都已经按升序排列。
 *
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 *
 *
 *
 * 示例 1：
 *
 * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
 * 输出：[1,1,2,3,4,4,5,6]
 * 解释：链表数组如下：
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * 将它们合并到一个有序链表中得到。
 * 1->1->2->3->4->4->5->6
 * 示例 2：
 *
 * 输入：lists = []
 * 输出：[]
 * 示例 3：
 *
 * 输入：lists = [[]]
 * 输出：[]
 * @date 2022/3/1 14:58
 */
public class MergeKLists {
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 小顶堆排序，一次性合并
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> minQueue = new PriorityQueue<>((o1, o2) -> o1.val - o2.val);
        for(ListNode head : lists){
            if(head != null){
                minQueue.add(head);
            }
        }
        ListNode head = minQueue.poll();
        ListNode t = head;
        if(t.next != null){
            minQueue.add(t.next);
        }
        while(!minQueue.isEmpty()){
            ListNode poll = minQueue.poll();
            t.next = poll;
            if(poll.next != null){
                minQueue.add(poll.next);
            }
            t = poll;
        }
        return head;
    }


    /**
     * 两两合并
     */
    public ListNode mergeKLists1(ListNode[] lists) {
        if (lists == null || lists.length < 1) {
            return new ListNode();
        }
        ListNode res = new ListNode();
        int p = 0;
        while (p < lists.length - 1) {
            res = func(res, lists[p], lists[++p]);
        }
        return res.next;
    }

    public ListNode func(ListNode head, ListNode l0, ListNode l1) {
        ListNode temp = head;
        while (l0 != null && l1 != null) {
            if (l0.val <= l1.val) {
                head.next = l0;
                l0 = l0.next;
            } else {
                head.next = l1;
                l1 = l1.next;
            }
            head = head.next;
        }
        // l1循环完了  l0没有完
        while (l0 != null) {
            head.next = l0;
            head = head.next;
            l0 = l0.next;
        }
        // l0循环完了  l1没有完
        while (l1 != null) {
            head.next = l1;
            head = head.next;
            l1 = l1.next;
        }
        return temp;
    }
}
