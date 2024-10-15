package agent.bytebuddy.intercepter;

import agent.bytebuddy.ByteBuddyTest;
import agent.bytebuddy.OverwriteCallable;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2024/9/6 15:32
 */
public class IntercepterMorph {
@RuntimeType
    public Object intercept(
        @This Object obj,
        @AllArguments Object[] allArgs,
        @Origin Method method,
        @SuperCall Callable zuper,
        @Super ByteBuddyTest.DelegeteFoo delegeteFoo,
        @Morph OverwriteCallable callable
        ) throws Exception {
    try {
        System.out.println("obj="+obj);
        System.out.println("delegeteFoo ="+ delegeteFoo);
        System.out.println("method ="+method);
        System.out.println("callable ="+callable);
        System.out.println("allArguments ="+allArgs);
        System.out.println("before");
        System.out.println("原方法调用  "+ zuper.call());
        // 通过 OverrideCallable.call()方法调用目标方法，此时需要传递参数
        allArgs[0]="word replaced";
        Object result = callable.call(allArgs);
        System.out.println("result ="+result);
        System.out.println("after");
        return result;
    } catch (Throwable t) {
        throw t;
    } finally {
        System.out.println("finally");
    }
    }

}
