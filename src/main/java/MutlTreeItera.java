import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @Author: zhangsh
 * @Date: 2019/10/11 15:14
 * @Version 1.0
 * Description  多叉树的遍历
 */
public class MutlTreeItera {

    public static void main(String[] args) {
        TreeNode root = new TreeNode();
        root.setVal(0);

        List<TreeNode> list1 = new ArrayList<>();
        TreeNode l10 = new TreeNode();
        l10.setVal(10);
        TreeNode l11 = new TreeNode();
        l11.setVal(11);
        TreeNode l12 = new TreeNode();
        l12.setVal(12);
        TreeNode l13 = new TreeNode();
        l13.setVal(13);
        TreeNode l14 = new TreeNode();
        l14.setVal(14);
        TreeNode l15 = new TreeNode();
        l15.setVal(15);
        TreeNode l16 = new TreeNode();
        l16.setVal(16);
        TreeNode l17 = new TreeNode();
        l17.setVal(17);
        list1.add(l10);
        list1.add(l11);
        list1.add(l12);
        list1.add(l13);
        list1.add(l14);
        list1.add(l15);
        list1.add(l16);
        list1.add(l17);
        root.setChilds(list1);

        List<TreeNode> list20 = new ArrayList<>();
        TreeNode l20 = new TreeNode();
        l20.setVal(20);
        TreeNode l21 = new TreeNode();
        l21.setVal(21);
        TreeNode l22 = new TreeNode();
        l22.setVal(22);
        TreeNode l23 = new TreeNode();
        l23.setVal(23);
        list20.add(l20);
        list20.add(l21);
        list20.add(l22);
        list20.add(l23);
        l10.setChilds(list20);


        List<TreeNode> list21 = new ArrayList<>();
        TreeNode l24 = new TreeNode();
        l24.setVal(24);
        TreeNode l25 = new TreeNode();
        l25.setVal(25);
        TreeNode l26 = new TreeNode();
        l26.setVal(26);
        TreeNode l27 = new TreeNode();
        l27.setVal(27);
        list21.add(l24);
        list21.add(l25);
        list21.add(l26);
        list21.add(l27);
        l11.setChilds(list21);



        iteratorRange(root);
    }

    /**
     * 多叉树型结构
     */
    static class TreeNode{
        private int val;
        private List<TreeNode> childs;

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public List<TreeNode> getChilds() {
            return childs;
        }

        public void setChilds(List<TreeNode> childs) {
            this.childs = childs;
        }
    }


    /**
     * 广度遍历并记录深度
     * @param node
     */
    public static void iteratorRange(TreeNode node){
        int deep = 0;
        Queue<TreeNode> parent = new ArrayDeque<>();
        Queue<TreeNode> children = new ArrayDeque<>();
        parent.add(node);
        while (!parent.isEmpty() || !children.isEmpty()){
            if(!parent.isEmpty()){
                TreeNode treeNode = parent.poll();
                System.out.println(treeNode.val+"deep="+deep);
                if(treeNode.childs!=null){
                    treeNode.childs.forEach(child -> children.add(child));
                }
            }else {
                // 将parent队列替换为children 队列
                parent.addAll(children);
                // 清空children队列
                children.clear();
                deep++;
            }
        }


    }

}
