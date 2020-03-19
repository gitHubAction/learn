package linkedList;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhangsh
 * @Date: 2020/1/3 13:19
 * @Version 1.0
 * Description
 */
public class ListNodeEdu {
    public static void main(String[] args) {
        Node node= new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        node.next = node2;
        node2.next = node3;
        node3.next = node4;

        majorityElement(new int[]{1,2,3,4,5,6});



    }


    public static int majorityElement(int[] nums) {
        /*Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0;i< nums.length; i++){
            if(null != map.get(nums[i])){
                map.put(nums[i],map.get(nums[i])+1);
            }else {
                map.put(nums[i],1);
            }
        }
        int res = 0;
        Integer result = 0;
        for(Integer temp : map.keySet()){
            if(map.get(temp) > nums.length/2  && res<map.get(temp)) {
                res = Math.max(res,map.get(temp));
                result = temp;
            }
        }
        return result;
*/
        //投票法
        int canNum = nums[0];//候选人
        int cnt = 1;//票数
        for (int i = 0; i < nums.length; i++) {
            if(canNum == nums[i]){
                cnt++;
            }else {
                if(--cnt < 0){
                    canNum = nums[i];//替换候选人
                    cnt = 1;//设置票数为1
                }
            }
        }
        return canNum;

    }


    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }


    public static int diameterOfBinaryTree(TreeNode root) {
        //基于深度遍历找到左右子树的最大深度
        if(root == null) return 0;
        dfs(root);
        return res;
    }

    static int res = 0;
    private static int dfs(TreeNode root) {
        if(root == null){
            return 0;
        }
        int leftDepth = dfs(root.left);
        int rigthDepth = dfs(root.right);
        res = Math.max(res,leftDepth + rigthDepth);
        return Math.max(leftDepth,rigthDepth) + 1;
    }

    /**
     * 链表排序
     * @param node
     * @return
     */
    public static Node nodeSort(Node node){
        Node temp = node;
        Node tail = null;

        while (temp.next != tail){
            while (temp.next != tail){
                if(temp.val < temp.next.val){
                    int tempV = temp.val;
                    temp.val = temp.next.val;
                    temp.next.val = tempV;
                }
                temp = temp.next;
            }
            tail = temp;
            temp = node;
        }

        System.out.println(node);
        return node;
    }


    public static Node toSum(Node node1, Node node2){
        if(node1 == null && node2 ==null)return new Node(0);
        //定义返回结果
        Node headNode = new Node(0);
        //指定两个指针分别从两个链表的头部开始遍历
        Node l1 = node1,l2 = node2,resultNode = headNode;
        //定义进位
        int carry = 0;
        //遍历两个链表
        while (l1 != null || l2 != null){
            int a = l1 == null ? 0 : l1.getVal();
            int b = l2 == null ? 0 : l2.getVal();
            int sum = a + b + carry;
            carry = sum / 10;
            Node temp = new Node(sum % 10);
            resultNode.setNext(temp);
            resultNode = headNode.getNext();

            l1 = l1.getNext();
            l2 = l2.getNext();
        }

        if(carry >= 0){
            resultNode.setNext(new Node(carry));
        }

        return headNode.getNext();
    }
}
