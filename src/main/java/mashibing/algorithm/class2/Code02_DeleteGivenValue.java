package mashibing.algorithm.class2;

/**
 * ClassName:    Code2_DeleteGivenValue
 * Package:    mashibing.algorithm.class2
 * Datetime:    2020/6/23   11:34
 * Author:   zsh
 * Description: 链表删除给定的值
 * (1、头部就是要删除的直接头部后移
 *  2、来到头部不是要删除的位置，继续删除
 * )
 */
public class Code02_DeleteGivenValue {

    public static Code01_LinkedList.Node deleteGivenValue(Code01_LinkedList.Node head ,int value){

        while (head.value == value){
            head = head.next;
        }
        Code01_LinkedList.Node pre = head;
        Code01_LinkedList.Node cur = head;
        while (cur != null){
            if(cur.value == value){
                pre.next = cur.next;
            }else {
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }
}
