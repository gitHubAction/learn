package mashibing.algorithm.class2;

import java.util.Stack;

/**
 * ClassName:    Code04_getMinStack
 * Package:    mashibing.algorithm.class2
 * Datetime:    2020/7/2   15:32
 * Author:   zsh
 * Description:  获取栈中的最小的元素  时间复杂度为O(1)
 *
 */
public class Code04_getMinStack {
    public static class MyStack1{
        //数据栈
        private Stack<Integer> dataStack;
        //最小元素栈
        private Stack<Integer> minStack;

        public Integer pop(){
            Integer res = dataStack.pop();
            if(res.equals(minStack.peek())){
                minStack.pop();
            }
            return res;
        }
        public void push(Integer i){
            dataStack.push(i);
            //1、如果最小元素栈为空，直接压栈
            //2、如果当前元素小于栈顶元素，直接压栈
            if(minStack.isEmpty()){
                minStack.push(i);
            }else if(i <= minStack.peek()){
                minStack.push(i);
            }
        }

        public Integer getMin(){
            if(minStack.isEmpty()){
                throw new RuntimeException("the Stack is Empty");
            }
            return minStack.peek();
        }
    }



    public static class MyStack2{
        //数据栈
        private Stack<Integer> dataStack;
        //最小元素栈
        private Stack<Integer> minStack;

        public Integer pop(){
            if(dataStack.isEmpty()){
                throw new RuntimeException("the Stack is Empty");
            }
            Integer res = dataStack.pop();
            minStack.pop();
            return res;
        }
        public void push(Integer i){
            dataStack.push(i);
            //1、如果最小元素栈为空，直接压栈
            //2、如果当前元素小于栈顶元素，直接压栈
            //3、如果当前元素大于栈顶元素，将最小元素栈栈顶元素再次压栈
            if(minStack.isEmpty()){
                minStack.push(i);
            }else if(i <= minStack.peek()){
                minStack.push(i);
            }else{
                minStack.push(minStack.peek());
            }
        }

        public Integer getMin(){
            if(minStack.isEmpty()){
                throw new RuntimeException("the Stack is Empty");
            }
            return minStack.peek();
        }
    }
}
