package commonAlgorithm;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2021/4/23 15:48
 */
public class LeastSquare {
    public static boolean canReorderDoubled(int[] arr) {
        Arrays.sort(arr);
        // 初始化表
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0; i < arr.length; i++){
            map.put(arr[i], map.getOrDefault(arr[i],0)+1);
        }
        List<Map.Entry<Integer, Integer>> list = map.entrySet().stream().sorted(new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return Math.abs(o1.getKey()) - Math.abs(o2.getKey());
            }
        }).collect(Collectors.toList());
        for(Map.Entry<Integer,Integer> entry : list){
            Integer i = entry.getKey();
            Integer cnt = entry.getValue();
            Integer i2 = 2 * i;
            Integer i2Cnt = map.get(i2);
            if(i2Cnt == null || cnt == 0)continue;
            if(i.equals(i2)){
                if(i == 0 && cnt % 2 == 0){
                    map.put(i, 0);
                }else{
                    map.put(i, cnt - 2);
                }
            }else{
                if(cnt == i2Cnt){
                    map.put(i,0);
                    map.put(i2,0);
                }else if(cnt > i2Cnt){
                    map.put(i, cnt-i2Cnt);
                    map.put(i2, 0);
                }else{
                    map.put(i, 0);
                    map.put(i2, i2Cnt-cnt);
                }
            }
        }
        for(Map.Entry<Integer,Integer> entry : map.entrySet()){
            if(entry.getValue() != 0)return false;
        }
        return true;
    }

    public static void main(String[] args) {
//        canReorderDoubled(new int[]{-62,86,96,-18,43,-24,-76,13,-31,-26,-88,-13,96,-44,9,-20,-42,100,-44,-24,-36,28,-32,58,-72,20,48,-36,-45,14,24,-64,-90,-44,-16,86,-33,48,26,29,-84,10,46,50,-66,-8,-38,92,-19,43,48,-38,-22,18,-32,-48,-64,-10,-22,-48});
//        List<Integer> minHeightTrees = findMinHeightTrees(6, new int[][]{{3, 0}, {3, 1}, {3, 2}, {3, 4}, {5, 4}});
//        System.out.println(minHeightTrees);

        int i = maxValue(new int[][]{{1, 2, 5}, {3, 4, 6}, {3, 5, 7}}, 2);
        System.out.println(i);
    }


    /**
     * 1751. 最多可以参加的会议数目 II
     * @param events
     * @param k
     * @return int
     * @author zhangshihao01
     * @date 2022/4/7 10:38
     */
    public static int maxValue(int[][] events, int k) {
        // 令f[i][j]表示前i个会议不超过j次选择的最大价值
        // 则当不选择第i个会议时，选择次数j不变，价值也不变，则：f[i][j] = f[i-1][j];
        // 选择第i个会议选择次数为j时，价值为i会议与之前pre个会议不冲突情况下（次数为j-1）的值加上i会议的值，则：f[i][j] = f[pre][j-1]+valuei;
        // f[i][j]为上边 选择i会议 和 不选择i会议 两者的最大值
        int n = events.length;
        int[][] f = new int[n+1][k+1];
        // 以结束时间排序
        Arrays.sort(events,(o1,o2)->o1[1]-o2[1]);
        for(int i = 1; i <= n; i++){
            int[] preE = events[i-1];
            int start = preE[0], end = preE[1], value = preE[2];
            // 定位pre
            int pre = 0;
            for (int p = i - 1; p >= 1; p--) {
                int[] prev = events[p - 1];
                if (prev[1] < start) {
                    pre = p;
                    break;
                }
            }
            // 构造dp
            for(int j = 1; j <= k;j++){
                f[i][j] = Math.max(f[i-1][j], f[pre][j-1]+value);
            }
        }
        return f[n][k];
    }


    /**
     * 310. 最小高度树
     * @param n
     * @param edges
     * @return java.util.List<java.lang.Integer>
     * @author zhangshihao01
     * @date 2022/4/6 15:20
     */
    public static List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> ans = new ArrayList<Integer>();
        if(n == 1){
            ans.add(0);
            return ans;
        }
        // 1、构造树结构
        List<Integer>[] tree = new List[n];
        for(int i = 0; i < n; i++){
            tree[i] = new ArrayList<>();
        }
        for(int[] edge : edges){
            // 无向边，互为父子关系
            tree[edge[0]].add(edge[1]);
            tree[edge[1]].add(edge[0]);
        }
        // 2、查找距离0节点最远的节点x，距离x最远的节点y
        int[] p = new int[n];
        Arrays.fill(p,-1);
        int x = bfs(0, p, tree);
        int y = bfs(x, p, tree);
        // 3、找到y->x的路径
        List<Integer> path = new ArrayList<>();
        // x节点的父节点为-1
        p[x] = -1;
        while(y != -1){
            path.add(y);
            // y来到y的父节点
            y = p[y];
        }
        // 4、获取结果节点
        int size = path.size();
        if(size % 2 == 0){
            ans.add(path.get(size / 2 - 1));
        }
        ans.add(path.get(size / 2));
        return ans;
    }

    private static int bfs(int i, int[] p, List<Integer>[] tree) {
        int node = -1;
        boolean[] visit = new boolean[tree.length];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(i);
        visit[i] = true;
        while(!queue.isEmpty()){
            int cur = queue.poll();
            node = cur;
            for(int child : tree[cur]){
                if(!visit[child]){

                    visit[child] = true;
                    p[child] = cur;
                    queue.add(child);
                }
            }
        }
        return node;
    }

}
