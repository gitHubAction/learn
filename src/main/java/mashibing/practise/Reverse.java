package mashibing.practise;

import java.util.Stack;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description 栈的逆序
 * @date 2022/1/18 9:11
 */
public class Reverse {

    /**
     * 把栈底部的元素返回
     * @param stack
     * @return
     */
    public Integer f(Stack<Integer> stack){
        Integer pop = stack.pop();
        if(stack.isEmpty()){
            return pop;
        }else {
            Integer res = f(stack);
            stack.push(pop);
            return res;
        }
    }
}
