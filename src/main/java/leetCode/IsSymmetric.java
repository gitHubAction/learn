package leetCode;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description  https://leetcode.cn/problems/symmetric-tree/submissions/
 * 剑指 Offer 28. 对称的二叉树
 * 请实现一个函数，用来判断一棵二叉树是不是对称的。如果一棵二叉树和它的镜像一样，那么它是对称的。
 *
 * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
 *
 *     1
 *    / \
 *   2   2
 *  / \ / \
 * 3  4 4  3
 * 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
 *
 *     1
 *    / \
 *   2   2
 *    \   \
 *    3    3
 *
 *
 *
 * 示例 1：
 *
 * 输入：root = [1,2,2,3,4,4,3]
 * 输出：true
 * 示例 2：
 *
 * 输入：root = [1,2,2,null,3,null,3]
 * 输出：false
 * @date 2022/10/19 10:22
 */
public class IsSymmetric {

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    public boolean isSymmetric(TreeNode root) {
        return ismirror(root,root);
    }

    public boolean ismirror(TreeNode h1, TreeNode h2) {
        if(h1 == null ^ h2 == null)return false;
        return h1.val == h2.val && ismirror(h1.left,h2.right) && ismirror(h1.right,h2.left);
    }
}
