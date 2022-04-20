package longforCode;

import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2021/11/12 18:24
 */
public class MyStack {

    Stack<Integer> dataStack;

    Stack<Integer> maxStack;

    public MyStack(){
        this.dataStack = new Stack<>();
        this.maxStack = new Stack<>();
    }

    public void push(int i){
        if(maxStack.isEmpty()){
            maxStack.push(i);
        }else if(getMax() <= i){
            maxStack.push(i);
        }
        dataStack.push(i);
    }

    public int pop(){
        if(dataStack.isEmpty()){
            throw new RuntimeException("暂无元素");
        }
        Integer pop = dataStack.pop();
        if(pop == getMax()){
            maxStack.pop();
        }
        return pop;
    }

    public int top(){
        return dataStack.pop();
    }

    public int popMax(){
        if(maxStack.isEmpty()){
            throw new RuntimeException("暂无元素");
        }
        return maxStack.pop();
    }

    private int getMax() {
        if(maxStack.isEmpty()){
            throw new RuntimeException("暂无元素");
        }
        return maxStack.peek();
    }


    public static void main(String[] args) {
        Set<String> keys = null;
        if(keys != null){
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()){
                System.out.println(iterator.next());
            }
        }
    }
}
