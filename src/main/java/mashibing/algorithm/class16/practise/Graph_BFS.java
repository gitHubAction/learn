package mashibing.algorithm.class16.practise;

import mashibing.algorithm.class16.Node;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/7/13 11:45
 */
public class Graph_BFS {


    public void bfs(Node node){
        Queue<Node> queue = new LinkedList<>();
        // 访问过的节点集合
        Set<Node> visit = new HashSet<>();
        queue.add(node);
        visit.add(node);
        while(!queue.isEmpty()){
            Node poll = queue.poll();
            System.out.println(poll.value);
            for(Node t: poll.nexts){
                if(!visit.contains(t)){
                    queue.add(t);
                    visit.add(t);
                }
            }
        }
    }
}
