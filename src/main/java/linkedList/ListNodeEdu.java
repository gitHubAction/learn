package linkedList;

/**
 * @Author: zhangsh
 * @Date: 2020/1/3 13:19
 * @Version 1.0
 * Description
 */
public class ListNodeEdu {
    public static void main(String[] args) {

    }


    public static ListNode toSum(ListNode node1,ListNode node2){
        if(node1 == null && node2 ==null)return new ListNode(0);
        //定义返回结果
        ListNode headNode = new ListNode(0);
        //指定两个指针分别从两个链表的头部开始遍历
        ListNode l1 = node1,l2 = node2,resultNode = headNode;
        //定义进位
        int carry = 0;
        //遍历两个链表
        while (l1 != null || l2 != null){
            int a = l1 == null ? 0 : l1.getVal();
            int b = l2 == null ? 0 : l2.getVal();
            int sum = a + b + carry;
            carry = sum / 10;
            ListNode temp = new ListNode(sum % 10);
            resultNode.setNext(temp);
            resultNode = headNode.getNext();

            l1 = l1.getNext();
            l2 = l2.getNext();
        }

        if(carry >= 0){
            resultNode.setNext(new ListNode(carry));
        }

        return headNode.getNext();
    }
}
