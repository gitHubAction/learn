package agent.bytebuddy;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2024/9/4 14:38
 */
public class Interceptor {
    @RuntimeType
    public Object intercept(
            @This Object obj,
            @AllArguments Object[] allArgs,
            @SuperCall Callable<?> zuper,
            @Origin Method originMethod,
            @Super ByteBuddyTest.DelegeteFoo delegeteFoo
    ) throws Exception {
        System.out.println("obj :"+obj);
        System.out.println("delegeteFoo: "+delegeteFoo);
        try {
            return zuper.call();
        } finally {

        }
    }
}
