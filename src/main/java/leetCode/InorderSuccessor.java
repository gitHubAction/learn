package leetCode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description  面试题 04.06. 后继者
 * 设计一个算法，找出二叉搜索树中指定节点的“下一个”节点（也即中序后继）。
 * @date 2022/5/16 10:40
 */
public class InorderSuccessor {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        TreeNode left = new TreeNode(1);
        TreeNode right = new TreeNode(3);
        root.left = left;
        root.right = right;
        InorderSuccessor i = new InorderSuccessor();
        TreeNode res = i.inorderSuccessor(root, left);
        System.out.println(res.val);

    }

    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        Queue<TreeNode> list = new LinkedList<>();
        preInorder(root, list);
        while(!list.isEmpty()){
            TreeNode node = list.poll();
            if(node.val == p.val){
                return list.poll();
            }
        }
        return null;
    }

    public void preInorder(TreeNode root, Queue<TreeNode> list){
        if(root == null){
            return;
        }
        preInorder(root.left, list);
        list.add(root);
        preInorder(root.right, list);
    }
}
