package structs.linkedList;

/**
 * @Author: zhangsh
 * @Date: 2020/4/20 11:12
 * @Version 1.0
 * Description
 */
public class DoubleLinkedList {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1,"张三");
        ListNode node2 = new ListNode(2,"李四");
        ListNode node3 = new ListNode(3,"王五");
        ListNode node4 = new ListNode(4,"周六");
        ListNode node5 = new ListNode(5,"哈哈");

        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
//        singLinkedList.add(node1);
//        singLinkedList.add(node2);
//        singLinkedList.add(node5);
//        singLinkedList.add(node3);
//        singLinkedList.add(node4);

        doubleLinkedList.addByOrder(node1);
        doubleLinkedList.addByOrder(node2);
        doubleLinkedList.addByOrder(node2);
        doubleLinkedList.addByOrder(node5);
        doubleLinkedList.addByOrder(node3);
        doubleLinkedList.addByOrder(node4);

        doubleLinkedList.list();

        doubleLinkedList.remove(2);
        System.out.println("删除后的=======");
        doubleLinkedList.list();


        System.out.println("按顺序添加2后的=======");
        doubleLinkedList.addByOrder(node2);
        doubleLinkedList.list();

        System.out.println("添加2");
        ListNode node21 = new ListNode(2,"李四");
        doubleLinkedList.add(node21);
        doubleLinkedList.list();

    }

    public void list() {
        ListNode temp = getHead();
        while (temp.next!=null){
            System.out.println(temp.next);
            temp = temp.next;
        }
    }


    //头指针永远指向链表的头部
    ListNode head = new ListNode(0,"");

    public ListNode getHead(){
        return head;
    }

    public boolean remove(int number){
        ListNode temp = head;
        while (true){
            if(temp.next == null){
                return false;
            }
            //双向链表的删除,
            // 因为双向链表的节点存有前一个节点，所以可以直接使用当前节点进行删除
            //而单向链表需要通过删除节点的前一个节点进行删除
            if(temp.number == number){
                break;
            }
            temp = temp.next;
        }

        temp.pre.next = temp.next;
        temp.next.pre = temp.pre;
        return true;

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
            node.pre = temp;
            if(temp.next!=null){
                //将原位置上的节点（temp的下一个节点next）的前节点指向待插入的节点
                temp.next.pre = node;
            }
            //将temp的下一个节点next的指向需要插入的节点
            temp.next = node;
        }
    }

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
        node.pre = temp;
    }
}
