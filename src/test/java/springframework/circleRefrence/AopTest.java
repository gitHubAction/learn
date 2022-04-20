package springframework.circleRefrence;

import org.junit.Test;
import org.springframework.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/3/22 14:22
 */
public class AopTest {

    @Test
    public void testCallBackHelper(){
        Enhancer enhancer = new Enhancer();

        Callback costTimeCallback = (MethodInterceptor)(Object o, Method method, Object[] args, MethodProxy proxy) -> {
            System.out.println("o:" + o);
            System.out.println("method:" + method.getName());
            System.out.println("args:" + args);
            return proxy.invoke(o, args);
        };
        //下面这个用来拦截所有get开头的方法，返回固定值的
        Callback fixdValueCallback = (FixedValue) () -> "路人甲Java";

        CallbackHelper callbackHelper = new CallbackHelper(ServiceA.class, null) {
            @Override
            protected Object getCallback(Method method) {
                return method.getName().startsWith("insert") ? costTimeCallback : fixdValueCallback;
            }
        };
        enhancer.setCallbacks(callbackHelper.getCallbacks());
        enhancer.setCallbackFilter(callbackHelper);
    }
}
