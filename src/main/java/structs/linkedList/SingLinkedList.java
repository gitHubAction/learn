package structs.linkedList;

import java.util.Stack;

public class SingLinkedList {


    public static void main(String[] args) {
        ListNode node1 = new ListNode(1,"张三");
        ListNode node2 = new ListNode(2,"李四");
        ListNode node3 = new ListNode(3,"王五");
        ListNode node4 = new ListNode(4,"周六");
        ListNode node5 = new ListNode(5,"哈哈");
        ListNode node6 = new ListNode(6,"哈哈");
        ListNode node7 = new ListNode(7,"哈哈");
        ListNode node8 = new ListNode(8,"哈哈");
        ListNode node9 = new ListNode(9,"哈哈");
        ListNode node10 = new ListNode(10,"哈哈");

        SingLinkedList singLinkedList = new SingLinkedList();
        singLinkedList.add(node3);
        singLinkedList.add(node1);
        singLinkedList.add(node2);
        singLinkedList.add(node5);
        singLinkedList.add(node4);
//        singLinkedList.add(node10);
//        singLinkedList.add(node7);
//        singLinkedList.add(node6);
//        singLinkedList.add(node9);
//        singLinkedList.add(node8);

//        singLinkedList.addByOrder(node1);
//        singLinkedList.addByOrder(node2);
//        singLinkedList.addByOrder(node2);
//        singLinkedList.addByOrder(node5);
//        singLinkedList.addByOrder(node3);
//        singLinkedList.addByOrder(node4);

        singLinkedList.list();

        singLinkedList.selectionSort(singLinkedList.getHead().next);
        System.out.println("排序后的链表-------");
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
        //将原链表的数据遍历添加到临时链表的第一个节点(采用头插法)
        ListNode newHead = new ListNode(0,"");
        //定义一个指针用于遍历原链表
        ListNode cur = head.next;
        //记录当前指针的下一个指针
        ListNode next = null;
        //开始遍历
        while (cur != null){
            //先存储当前节点的下一个节点
            next = cur.next;

            //头插法构建新链表
            cur.next = newHead.next;
            newHead.next = cur;

            //当前节点后移遍历
            cur = next;
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

    /**
     * 链表冒泡排序
     * @param node
     */
    public void bubleSort(ListNode node){
        //如果链表为空或者只有一个元素直接返回
        if(head.next == null || head.next.next == null)return ;

        //链表未排好书序最后一个节点
        ListNode tail = null;
        //当前节点的前一个节点(辅助节点的交换)
        ListNode pre = head;

        ListNode cur = node;
        while (cur.next != tail){
            //内循环
            while (cur.next != tail){
                if(cur.number > cur.next.number){
                    //交换节点
                    ListNode temp = cur.next;

                    cur.next = cur.next.next;

                    temp.next = cur;

                    pre.next = temp;
                }
                //辅助节点后移
                pre = pre.next;
                cur = pre.next;
            }
            //一趟排序结束之后，最大\小的节点到了链表最后的位置
            //设置尾结点
            tail = cur;
            //辅助节点回到头节点
            pre = head;
            //当前指针从头开始
            cur = pre.next;
        }
        head = pre;
    }


    //选择排序
    public void selectionSort(ListNode node){
        //如果链表为空或者只有一个元素直接返回
        if(head.next == null || head.next.next == null)return;

        //定义一前一后两个指针
        ListNode pre = head;
        ListNode cur = node;
        //内循环(寻找最小的节点)
        ListNode min = cur;
        while (cur.next != null){
            if(min.number > cur.next.number){
                min = cur.next;
                pre = pre.next;
            }
            cur = cur.next;
        }
        //将node和min节点交换位置
    }
}
