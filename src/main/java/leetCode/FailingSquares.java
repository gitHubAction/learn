package leetCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/7/21 9:28
 */
public class FailingSquares {

    public static void main(String[] args) {
        List<Integer> res = fallingSquares(new int[][]{{4,9},{8,8},{6,8},{8,2},{1,2}});
    }

    public static List<Integer> fallingSquares(int[][] positions) {
        List<Integer> res = new ArrayList<>();
        List<Interval> all = new ArrayList<>();
        int maxHigh = 0;
        for(int i = 0; i < positions.length; i++){
            int high = positions[i][1];
            int start = positions[i][0];
            int end = high + start;
            int lastHigh = 0;
            for(Interval in : all){
                if(in.start >= end || in.end <= start){
                    continue;
                }
                lastHigh = Math.max(lastHigh, in.high);
            }
            int curHigh = lastHigh +high;
            all.add(new Interval(start, end, curHigh));
            maxHigh = Math.max(maxHigh, curHigh);
            res.add(maxHigh);
        }
        return res;
    }


    static class Interval{
        int start, end, high;
        public Interval(int start, int end, int high){
            this.start = start;
            this.end = end;
            this.high = high;
        }
    }




    public List<Integer> fallingSquares1(int[][] positions) {
        //创建返回值
        List<Integer> res = new ArrayList<>();
        //根节点，默认为零
        Node root = null;
        //目前最高的高度
        int curMax = 0;
        for (int[] position : positions) {
            //左坐标
            int left = position[0];
            //右坐标
            int right = position[0] + position[1];
            //边长
            int edge = position[1];
            //当前最高的高度
            int curMaxHigh = query(root, left, right);
            //更新线段树 给所有范围更新最大值为curMaxHigh + edge
            root = update(root, left, right, curMaxHigh + edge);
            //高度比较
            curMax = Math.max(curMax, curMaxHigh + edge);
            res.add(curMax);
        }
        return res;
    }

    static class Node {
        //左范围
        private final int leftX;
        //右范围
        private final int rightX;
        //最大高度
        private final int maxHeight;
        //右边界
        private int maxR;
        //左子树和右子树
        Node leftChild, rightChild;

        public Node(int leftX, int rightX, int height, int maxR) {
            this.leftX = leftX;
            this.rightX = rightX;
            this.maxHeight = height;
            this.maxR = maxR;
            this.leftChild = null;
            this.rightChild = null;
        }
    }

    /**
     * 做区间更新
     *
     * @param root   根节点
     * @param left   左范围
     * @param right  右范围
     * @param height 高度
     * @return
     */
    private Node update(Node root, int left, int right, int height) {
        if (root == null) {
            return new Node(left, right, height, right);
        }
        if (left <= root.leftX) {
            root.leftChild = update(root.leftChild, left, right, height);
        } else {
            root.rightChild = update(root.rightChild, left, right, height);
        }
        root.maxR = Math.max(right, root.maxR);
        return root;
    }

    /**
     * 查询范围内的最大高度
     *
     * @param root  根节点
     * @param left  左范围
     * @param right 右范围
     * @return
     */
    private int query(Node root, int left, int right) {
        //如果新节点的左边界大于等于当前树的maxR的话,不需要遍历整颗树
        if (root == null || left >= root.maxR) {
            return 0;
        }
        int curHeight = 0;
        //是否有交集
        if (right > root.leftX && left < root.rightX) {
            curHeight = root.maxHeight;
        }
        curHeight = Math.max(curHeight, query(root.leftChild, left, right));
        if (right > root.leftX) {
            curHeight = Math.max(curHeight, query(root.rightChild, left, right));
        }
        return curHeight;
    }
}
