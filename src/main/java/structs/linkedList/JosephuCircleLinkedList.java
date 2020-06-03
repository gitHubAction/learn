package structs.linkedList;

/**
 * ClassName:    JosephuCircleLinkedList
 * Package:    structs.linkedList
 * Datetime:    2020/6/2   9:25
 * Author:   zsh
 * Description: 约瑟夫单向环形链表
 */
public class JosephuCircleLinkedList {


    public static void main(String[] args) {
        JosephuCircleLinkedList circleLinkedList = new JosephuCircleLinkedList();
        circleLinkedList.addNode(100);
        circleLinkedList.iteraNode();
    }



    private ListNode first;


    /**
     * 构造约瑟夫环
     * @param nums
     */
    public void addNode(int nums){
        if(nums < 1){
            System.out.println("参数不合法");
            return;
        }
        //尾结点指针
        ListNode tailNode = null;
        for (int i = 1; i <= nums; i++) {
            //需要添加到约瑟夫环中的节点
            ListNode curNode = new ListNode(i,"");
            if(i == 1){
                //第一个加入的节点，先让头结点指向该节点
                first = curNode;
                //形成环
                first.next = first;
            }else {
                //先让tail节点的下一个节点指向当前节点
                tailNode.next = curNode;
                //当前节点的下一个节点指向头节点形成环
                curNode.next = first;
            }
            //尾结点后移指向当前要插入的节点
            tailNode = curNode;
        }
    }


    public void iteraNode(){
        if(first == null){
            System.out.println("链表环为空");
        }
        //定义一个临时节点(不改变first节点的位置)
        ListNode temp = first;
        while (true){
            System.out.println("节点编号："+temp.number);
            temp = temp.next;
            if(temp == first){
                break;
            }
        }
    }
}
