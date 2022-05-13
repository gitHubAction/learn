package leetCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description 449. 序列化和反序列化二叉搜索树
 * @date 2022/5/11 9:53
 */
public class Codec {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        Codec codec = new Codec();
        Codec dodec = new Codec();
        String serialize = codec.serialize(root);
        System.out.println(serialize);
        TreeNode node = dodec.deserialize(serialize);
        System.out.println(node);
    }

    public String serialize(TreeNode root) {
        List<String> q = new ArrayList<>();
        process(q, root);
        return q.toString().substring(1,q.toString().length() - 1);
    }

    public void process(List<String> q, TreeNode root){
        if(root == null){
            q.add(null);
        }else{
            q.add(root.val+"");
            process(q, root.left);
            process(q, root.right);
        }
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(data == null || data == "" || data == "null"){
            return null;
        }
        String[] q = data.split(",");
        Queue<String> queue = new LinkedList<>();
        for(int i = 0; i < q.length; i++){
            queue.add(q[i].trim());
        }
        return pre(queue);
    }

    public TreeNode pre(Queue<String> q){
        String t = q.poll();
        if(t.trim().equals("null")){
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(t.trim()));
        root.left = pre(q);
        root.right = pre(q);
        return root;
    }
}
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
