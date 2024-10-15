package agent.bytebuddy.intercepter;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2024/9/6 16:23
 */
public class ConstructorInterceptor {

    @RuntimeType
    public void intercept(
            @This Object obj,
            @AllArguments Object[] args
    ){
        System.out.println("构造参数"+args);
    }
}
