package a;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solu {
    static List<TreeNode> res = new ArrayList<>();


    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        node1.left = node2;
        node1.left.left = node3;
        node1.left.right = node4;
        node1.right = node5;
        node1.right.right = node6;
        flatten(node1);

    }
    public static void flatten(TreeNode root) {

        process(root);

        for (int i = 0; i < 1; i++) {

        }
    }

    public static void process(TreeNode node){
        if(node == null){
            return;
        }
        res.add(node);
        process(node.left);

        process(node.right);
    }

    static class TreeNode {
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
