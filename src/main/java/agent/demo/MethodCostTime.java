package agent.demo;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description 通过javaagent代理方式实现记录方法调用耗时
 * @date 2024/9/6 16:37
 */
public class MethodCostTime {

    @RuntimeType
    public static Object intercept(@This Object obj,
                                   @Origin Method method,
                                   @SuperCall Callable zuper) throws Exception {
        long start = System.currentTimeMillis();
        try {
            return zuper.call();
        } finally {
            System.out.println("method :"+ method.getName()+" 执行耗时"+ (System.currentTimeMillis() - start) + "ms");
        }
    }
}
