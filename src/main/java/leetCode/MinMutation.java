package leetCode;

import java.util.*;

/**
 * ClassName:    MinMutation
 * Package:    leetCode
 * Datetime:    2022/5/7   11:15
 * Author:   zsh
 * Description:
 */
public class MinMutation {

    /**
     * "AACCGGTT"
     * "AAACGGTA"
     * ["AACCGATT","AACCGATA","AAACGATA","AAACGGTA"]
     * @param args
     */

    public static void main(String[] args) {
        MinMutation m = new MinMutation();
        int i = m.minMutation2(
                "AAAAAAAA",
                "CCCCCCCC",
                new String[]{"AAAAAAAA","AAAAAAAC","AAAAAACC","AAAAACCC","AAAACCCC","AACACCCC","ACCACCCC","ACCCCCCC","CCCCCCCA"});
        int j = m.minMutation1(
                "AAAAAAAA",
                "CCCCCCCC",
                new String[]{"AAAAAAAA","AAAAAAAC","AAAAAACC","AAAAACCC","AAAACCCC","AACACCCC","ACCACCCC","ACCCCCCC","CCCCCCCA"});
        System.out.println(i);
        System.out.println(j);
    }


    static char[] items = new char[]{'A', 'C', 'G', 'T'};
    public int minMutation2(String S, String T, String[] bank) {
        Set<String> set = new HashSet<>();
        for (String s : bank) set.add(s);

        Deque<String> d = new ArrayDeque<>();
        d.addLast(S);
        Map<String,Integer> map = new HashMap<>();
        map.put(S, 0);
        while (!d.isEmpty()) {
            String s = d.pollFirst();
            int res = map.get(s);
            char[] cs = s.toCharArray();
            for (int i = 0; i < 8; i++) {
                for (char c : items) {
                    if (cs[i] == c) continue;
                    char[] clone = cs.clone();
                    clone[i] = c;
                    String sub = String.valueOf(clone);
                    if (!set.contains(sub)) continue;
                    if(map.containsKey(sub))continue;
                    if (sub.equals(T)) return res + 1;
                    map.put(sub,res+1);
                    d.addLast(sub);
                }
            }
        }
        return -1;
    }


    public int minMutation1(String S, String T, String[] bank) {
        Set<String> set = new HashSet<>();
        for (String s : bank) set.add(s);

        Deque<String> d = new ArrayDeque<>();
        Map<String, Integer> map = new HashMap<>();
        d.addLast(S);
        map.put(S, 0);
        while (!d.isEmpty()) {
            int size = d.size();
            while (size-- > 0) {
                String s = d.pollFirst();
                char[] cs = s.toCharArray();
                int step = map.get(s);
                // bfs变换每个位置的基因
                for (int i = 0; i < 8; i++) {
                    for (char c : items) {
                        if (cs[i] == c) continue;
                        char[] clone = cs.clone();
                        clone[i] = c;
                        String sub = String.valueOf(clone);
                        // 基因是否有效
                        if (!set.contains(sub)) continue;
                        // 是否曾经变过
                        if (map.containsKey(sub)) continue;
                        // 是否为最终结果
                        if (sub.equals(T)) return step + 1;

                        map.put(sub, step + 1);
                        d.addLast(sub);
                    }
                }
            }
        }
        return -1;
    }

    public int minMutation(String start, String end, String[] bank) {
        Set<String> table = new HashSet<>();
        for(String b:bank){
            table.add(b);
        }
        if(!table.contains(end))return -1;
        // start和end按位异或 得到最终要变更的基因的索引位置
        List<Integer> b = new ArrayList<>();
        char[] sarr = start.toCharArray();
        char[] earr = end.toCharArray();
        for(int i = 0; i < sarr.length; i++){
            if(sarr[i] != earr[i]){
                b.add(i);
            }
        }
        if(b.size() > bank.length)return -1;
        Queue<String> q = new LinkedList<>();
        q.add(start);
        int res = 0;
        while(!q.isEmpty()){
            String beforeStr = q.poll();
            char[] beforeArr = beforeStr.toCharArray();
            for(int i = 0; i < b.size(); i++){
                Integer index = b.get(i);
                char[] clone = beforeArr.clone();
                clone[index] = earr[index];
                String afterStr = String.valueOf(clone);
                if(table.contains(afterStr)){
                    if(afterStr == end)return res+1;
                    res++;
                    q.add(afterStr);
                    // 删除位置
                    b.remove(index);
                    // 跳出for
                    break;
                }
            }
        }
        return res == 0 ? -1 : res;
    }
}
