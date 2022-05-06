package leetCode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description  933. 最近的请求次数
 * @date 2022/5/6 12:53
 */
public class RecentCounter {
    Deque<Integer> queue;
    public RecentCounter() {
       queue = new ArrayDeque<>();
    }

    public int ping(int t) {
        queue.add(t);
        while(!queue.isEmpty() && queue.peek() > t - 3000){
            queue.poll();
        }
        return queue.size();
    }
}
