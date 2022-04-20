package structs.linkedList;

import lombok.Data;

/**
 * 链表节点
 */
@Data
public class ListNode {

    int number;
    String name;
    ListNode next;
    ListNode pre;

    public ListNode(int no ,String name){
        this.number = no;
        this.name = name;
    }
    @Override
    public String toString(){
        return "number : ["+this.number+"] , name : ["+this.name+"]";
    }
}
