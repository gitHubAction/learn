package leetCode;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description <a href="https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/">...</a>
 * 105. 从前序与中序遍历序列构造二叉树
 * 给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历， inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。
 *
 *
 *
 * 示例 1:
 *
 *
 * 输入: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
 * 输出: [3,9,20,null,null,15,7]
 * 示例 2:
 *
 * 输入: preorder = [-1], inorder = [-1]
 * 输出: [-1]
 * @date 2022/10/19 10:30
 */
public class BuildTree {


    public static void main(String[] args) {
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        int[] preorder = new int[]{3, 9, 8, 5, 4, 10, 20, 15, 7}, inorder = new int[]{4, 5, 8, 10, 9, 3, 15, 20, 7};
        BuildTree bt = new BuildTree();
        TreeNode treeNode = bt.buildTreeItera(preorder, inorder);
        System.out.println(treeNode);
    }


    public TreeNode buildTreeItera(int[] preorder, int[] inorder) {
        if(preorder == null || preorder.length == 0){
            return null;
        }
        TreeNode root = new TreeNode(preorder[0]);
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        stack.push(root);
        int inorderIndex = 0;
        for(int i = 1; i < preorder.length; i++){
            int preVal = preorder[i];
            TreeNode node = stack.peek();
            if(node.val != inorder[inorderIndex]){
                node.left = new TreeNode(preVal);
                stack.push(node.left);
            }else{
                while(!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]){
                    node = stack.pop();
                    inorderIndex++;
                }
                node.right = new TreeNode(preVal);
                stack.push(node.right);
            }
        }
        return root;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder == null || inorder == null || preorder.length != inorder.length)return null;
        Map<Integer,Integer> headIndexMap = new HashMap<>();
        for(int i = 0; i < inorder.length; i++){
            headIndexMap.put(inorder[i],i);
        }
        return g(preorder,0,preorder.length-1, inorder, 0, inorder.length-1, headIndexMap);
    }

    private TreeNode g(int[] preorder, int l, int r, int[] inorder, int l1, int r1, Map<Integer, Integer> headIndexMap) {
        if(l > r)return null;
        TreeNode head = new TreeNode(preorder[l]);
        // 查找根节点在中序遍历中的位置
        int headIndex = headIndexMap.get(preorder[l]);
        // 根据根节点在中序遍历中的位置确定左树在前序遍历中的范围
        head.left = g(preorder, l+1, l + headIndex - l1, inorder, l1, headIndex - 1, headIndexMap);
        // 根据根节点在中序遍历中的位置确定右树在前序遍历中的范围
        head.right = g(preorder, l + headIndex - l1 + 1, r, inorder, headIndex + 1, r1, headIndexMap);
        return head;
    }


    public TreeNode buildTree1(int[] preorder, int[] inorder) {
        if(preorder.length != inorder.length)return null;
        return f(preorder,0,preorder.length-1, inorder, 0, inorder.length-1);
    }

    private TreeNode f(int[] preorder, int L, int R, int[] inorder, int L1, int R1) {
        if(L > R)return null;
        // 前序遍历的第一个为树的根结点
        TreeNode head = new TreeNode(preorder[L]);
        // 根节点在中序遍历结果中的位置
        int headIndexInorder = find(inorder,L1,R1,preorder[L]);
        head.left = f(preorder, L+1, L + headIndexInorder - L1, inorder, L1, headIndexInorder - 1);
        head.right = f(preorder, L + headIndexInorder - L1+1, R, inorder, headIndexInorder+1, R1);
        return head;
    }

    public int find(int[] inorder, int l, int r, int val) {
        for(int i = l; i <= r; i++){
            if(inorder[i] == val)return i;
        }
        return -1;
    }


    /**
     * Definition for a binary tree node.
     *
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
