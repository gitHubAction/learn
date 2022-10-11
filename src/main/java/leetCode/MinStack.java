package leetCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/10/11 15:31
 */
public class MinStack {
    List<Integer> set = null;
    Stack<Integer> stack = null;
    public MinStack() {
        stack = new Stack<Integer>();
        set = new ArrayList<Integer>();
    }

    public void push(int val) {
        set.add(val);
        stack.push(val);
        Collections.sort(set);
    }

    public void pop() {
        Integer cur = stack.pop();
        set.remove(cur);
        Collections.sort(set);
    }

    public int top() {
        return set.get(set.size()-1);
    }

    public int getMin() {
        return set.get(0);
    }


    public static void main(String[] args) {
        MinStack stack = new MinStack();
        stack.push(-2);
        stack.push(0);
        stack.push(-3);
        System.out.println(stack.getMin());
        stack.pop();
        System.out.println(stack.top());
        System.out.println(stack.getMin());
    }
}
