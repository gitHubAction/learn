package mashibing.algorithm.class16.practise;

import mashibing.algorithm.class16.Node;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/7/13 11:50
 */
public class Graph_DFS {

    public void dfs(Node node){
        Stack<Node> stack = new Stack<>();
        stack.add(node);
        Set<Node> visit = new HashSet<>();
        visit.add(node);
        System.out.println(node.value);
        while(!stack.isEmpty()){
            Node cur = stack.pop();
            for(Node t : cur.nexts){
                if(!visit.contains(t)){
                    stack.push(cur);
                    stack.push(t);
                    visit.add(t);
                    System.out.println(t.value);
                    break;
                }
            }
        }
    }
}
