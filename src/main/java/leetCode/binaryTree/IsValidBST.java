package leetCode.binaryTree;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description https://leetcode.cn/problems/validate-binary-search-tree/
 * 98. 验证二叉搜索树
 * 给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。
 *
 * 有效 二叉搜索树定义如下：
 *
 * 节点的左子树只包含 小于 当前节点的数。
 * 节点的右子树只包含 大于 当前节点的数。
 * 所有左子树和右子树自身必须也是二叉搜索树。
 *
 *
 * 示例 1：
 *
 *
 * 输入：root = [2,1,3]
 * 输出：true
 * 示例 2：
 *
 *
 * 输入：root = [5,1,4,null,null,3,6]
 * 输出：false
 * 解释：根节点的值是 5 ，但是右子节点的值是 4 。
 * @date 2022/10/21 10:43
 */
public class IsValidBST {
    /**
     * Definition for a binary tree node.
     */
     public class TreeNode {
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

     public class Info{
         private int max;
         private int min;
         private boolean isBST;

         public Info(int ma, int mi, boolean isB){
             max = ma;
             min = mi;
             isBST = isB;
         }
     }

    public boolean isValidBST(TreeNode root) {
         return process(root).isBST;
    }

    private Info process(TreeNode root) {
         if(root == null){
             return null;
         }
         Info leftInfo = process(root.left);
         Info rightInfo = process(root.right);
         int max = root.val;
         int min = root.val;
         boolean isBST = true;
         // 合并左右树为自己的信息
        if(leftInfo != null){
            max = Math.max(root.val,leftInfo.max);
            min = Math.min(root.val,leftInfo.min);
        }
        if(rightInfo != null){
            max = Math.max(root.val,rightInfo.max);
            min = Math.min(root.val,rightInfo.min);
        }
        if (leftInfo != null && !leftInfo.isBST) {
            isBST = false;
        }
        if (rightInfo != null && !rightInfo.isBST) {
            isBST = false;
        }
        if (leftInfo != null && leftInfo.max >= root.val) {
            isBST = false;
        }
        if (rightInfo != null && rightInfo.min <= root.val) {
            isBST = false;
        }
        return new Info(max,min,isBST);
    }

}
