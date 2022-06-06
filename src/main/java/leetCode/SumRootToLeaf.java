package leetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description retention
 * @date 2022/5/30 16:06
 */
public class SumRootToLeaf {


    public static void main(String[] args) {
        System.out.println(0 << 1);
    }

    public int sumRootToLeaf(TreeNode root) {
        return dfs(root, 0);
    }

    private int dfs(TreeNode root,int row) {
        int ans = 0, nrow = (row << 1) + root.val;
        if(root.left != null) ans += dfs(root.left, nrow);
        if(root.right != null) ans += dfs(root.right, nrow);
        return root.left == null && root.right == null ? nrow : ans;
    }

    static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
}
