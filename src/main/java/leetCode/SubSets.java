package leetCode;

import java.util.*;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/10/10 17:34
 */
public class SubSets {
    public static void main(String[] args) {
        SubSets s = new SubSets();
        List<List<Integer>> subsets = s.subsets(new int[]{1, 2, 3});
        System.out.println(subsets);
    }

    // 数组的幂
    // {1,2,3}   [],  [1],  [2],[1,2],  [3],[1,3],[2,3],[1,2,3]
    //  如上规律每个新的子集，都是在之前的所有子集的基础上加上当前元素
    public List<List<Integer>> subsetsD(int[] nums){
        Set<Integer> set = new HashSet<>();
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        for(int i = 0; i < nums.length; i++){
            int size = res.size();
            for(int j = 0; j < size; j++){
                ArrayList<Integer> t = new ArrayList<>(res.get(j));
                t.add(nums[i]);
                res.add(t);
            }
        }
        return res;
    }

    // 树形遍历
    public List<List<Integer>> subsets(int[] nums){
        // 对于每个元素都可以选择或不选择
        //                     [1,2,3]
        //             |                        |
        //1           [1]                       []
        //      |             |             |         |
        //2    [1]           [1,2]         [2]        []
        //    |    |       |      |      |    |     |    |
        //3 [1,3] [1]   [1,2,3] [1,2]  [2,3] [2]   [3]   []
        // 完全二叉树的遍历
        List<List<Integer>> res = new ArrayList<>();
        preOrder(0,nums,new ArrayList<>(),res);
        res.add(new ArrayList<>());
        return res;
    }

    public void preOrder(int i, int[] nums, List<Integer> subset, List<List<Integer>> res){
        if (i >= nums.length) return;
        // 到了新的状态，记录新的路径，要重新拷贝一份
        List<Integer> t = new ArrayList<Integer>(subset);
        // 这里
        res.add(t);
        preOrder(i + 1, nums, t, res);
        t.add(nums[i]);
        preOrder(i + 1, nums, t, res);
    }

}
