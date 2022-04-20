package leetCode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/3/17 13:43
 */
public class AllOne {
    class Node{
        // 双向链表的前后
        Node pre,next;
        // 对应存在cnt次的key集合
        Set<String> keys = new HashSet<>();
        // 该节点是cnt次的字符串合集
        int cnt;

        public Node(int cnt){
            this.cnt = cnt;
        }
    }
    // hash表，存储key->node的映射实现查找o(1)
    Map<String,Node> map = new HashMap<>();
    // 头尾节点
    Node head,tail;
    public AllOne() {
        head = new Node(Integer.MIN_VALUE);
        tail = new Node(Integer.MIN_VALUE);
        // 收尾相连
        head.next = tail;
        tail.pre = head;
    }

    public void inc(String key) {
        // 是否存在
        if(map.containsKey(key)){
            Node cur = map.get(key);
            // 从当前keys中删除
            cur.keys.remove(key);
            Node next = null;
            // 是否有间隙  类似3->5->7这样
            if(cur.next.cnt == cur.cnt + 1){
                // 无间隙，添加到链表中
                next = cur.next;
            }else{
                // 有间隙，重新构造node并添加到链表
                next = new Node(cur.cnt + 1);
                Node temp = cur.next;
                cur.next = next;
                next.pre = cur;
                next.next = temp;
                temp.pre = next;
            }
            // 更新hash表
            map.put(key, next);
            next.keys.add(key);
            // 从链表中删除当前节点cur
            clear(cur);
        }else{
            // 不存在 追加到AllOne头部(头部的key的计数最小，尾部key计数最大)
            Node node = null;
            if(head.next.cnt == 1){
                node = head.next;
            }else{
                node= new Node(1);
                // 追加链表到头部
                Node temp = head.next;

                head.next = node;
                node.pre = head;
                node.next = temp;

                temp.pre = node;
            }
            // 添加到keys集合中
            node.keys.add(key);
            // 加到hash表
            map.put(key,node);
        }
    }
    public void dec(String key) {
        if(map.containsKey(key)){
            Node cur = map.get(key);
            // 先删除
            cur.keys.remove(key);
            if(cur.cnt == 1){
                map.remove(key);
            }else{
                Node node = null;
                if(cur.pre.cnt == cur.cnt - 1){
                    node = cur.pre;
                }else{
                    node = new Node(cur.cnt - 1);
                    // 添加节点
                    Node temp = cur.pre;
                    cur.pre = node;
                    node.next = cur;
                    node.pre = temp;
                    temp.next = node;
                }
                // 更新hash表
                map.put(key,node);
                // 添加key到node中
                node.keys.add(key);
            }
            // 清理node
            clear(cur);
        }
    }

    public String getMaxKey() {
        Node node = tail.pre;
        for(String key: node.keys){
            return key;
        }
        return "";
    }

    public String getMinKey() {
        Node node = head.next;
        for(String key: node.keys){
            return key;
        }
        return "";
    }

    public void clear(Node node){
        if(node.keys.size() == 0){
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }
    }
}
