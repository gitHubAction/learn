package structs.linkedList;

import java.util.Stack;

public class SingLinkedList {


    public static void main(String[] args) {
        ListNode node1 = new ListNode(1,"张三");
        ListNode node2 = new ListNode(2,"李四");
        ListNode node3 = new ListNode(3,"王五");
        ListNode node4 = new ListNode(4,"周六");
        ListNode node5 = new ListNode(5,"哈哈");

        SingLinkedList singLinkedList = new SingLinkedList();
//        singLinkedList.add(node1);
//        singLinkedList.add(node2);
//        singLinkedList.add(node5);
//        singLinkedList.add(node3);
//        singLinkedList.add(node4);

        singLinkedList.addByOrder(node1);
        singLinkedList.addByOrder(node2);
        singLinkedList.addByOrder(node2);
        singLinkedList.addByOrder(node5);
        singLinkedList.addByOrder(node3);
        singLinkedList.addByOrder(node4);

        singLinkedList.list();

        System.out.println("反转后的链表=========");
        SingLinkedList.inverse(singLinkedList.getHead());
        singLinkedList.list();

//        inverse.remove(2);
//        System.out.println("删除后的========");
//        inverse.list();


    }


    //头指针永远指向链表的头部
    ListNode head = new ListNode(0,"");

    public ListNode getHead(){
        return head;
    }
    //将元素追加到链表的尾部
    public void add(ListNode node){
        //遍历链表找到尾部元素
        ListNode temp = head;
        while (true){
            if(temp.next == null){
                break;
            }
            temp = temp.next;
        }
        //设置尾部元素的next为要添加的元素
        temp.next = node;
    }


    public void addByOrder(ListNode node){
        //遍历链表找到要插入的位置
        ListNode temp = head;
        boolean flag = false;
        while (true){
            //如果当前指针的下一个元素为null，直接插入
            if(temp.next == null){
                break;
            }
            //按照顺序插入，找到比插入node大的元素的前一个元素
            if(temp.next.number > node.number){
                break;
            }
            if(temp.next.number == node.number){
                flag = true;
                break;
            }
            //指针后移
            temp = temp.next;
        }
        if(flag){
            System.out.println("元素重复"+node.number);
        }else{
            //将插入node的next指向temp.next;
            node.next = temp.next;
            //将temp的下一个节点next指向需要插入的节点
            temp.next = node;
        }
    }

    //遍历链表
    public void list(){
        ListNode temp = head;
        while (true){
            if(temp.next == null){
                return;
            }
            temp = temp.next;
            System.out.println(temp);
        }
    }

    public boolean remove(int number){
        ListNode temp = head;
        while (true){
            if(temp.next == null){
                return false;
            }
            if(temp.next.number == number){
                break;
            }
            temp = temp.next;
        }
        temp.next = temp.next.next;
        return true;

    }


    public static void inverse(ListNode head){
        //如果链表为空或者只有一个元素直接返回
        if(head.next == null || head.next.next == null)return ;
        //新建一个临时链表
        //将原链表的数据遍历添加到临时链表的第一个节点
        ListNode newHead = new ListNode(0,"");
        //定义一个指针用于遍历原链表
        ListNode cur = head.next;
        //记录当前指针的下一个指针
        ListNode next = null;
        //开始遍历
        while (cur != null){
            next = cur.next;//先存储当前节点的下一个节点
            cur.next = newHead.next;//将当前节点 “单独” 拎出来添加到新节点的下一个节点的前边
            newHead.next = cur;//将新的链表的头结点的下一个节点与当前节点相连接
            cur = next;//当前节点后移遍历
        }
        //将原来的头结点的下一个节点指向将新的节点的下一个节点
        head.next = newHead.next;
    }

    //通过栈数据结构实现链表反转
    public SingLinkedList inverseByStack(){
        ListNode temp = head;
        Stack<ListNode> stack = new Stack();
        while (true){
            if(temp.next == null){
                break;
            }
            stack.add(temp.next);
            temp = temp.next;
        }
        SingLinkedList result = new SingLinkedList();
        int size = stack.size();
        for (int i = 0; i < size; i++) {
            ListNode pop = stack.pop();

            result.add(new ListNode(pop.number,pop.name));
        }
        return result;
    }
}
