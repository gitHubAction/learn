package springframework.circleRefrence;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * ClassName:    MethodBeforeInterceptor
 * Package:    springframework.circleRefrence
 * Datetime:    2020/5/18   17:38
 * Author:   zsh
 * Description:
 */
@Component
public class MethodBeforeInterceptor implements BeanPostProcessor {

    @Nullable
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if("serviceA".equals(beanName)){
            ProxyFactory proxyFactory = new ProxyFactory(bean);
            proxyFactory.addAdvice(new MethodBeforeAdvice() {
                @Override
                public void before(Method method, Object[] objects, Object o) throws Throwable {
                    System.out.println("MethodBeforeInterceptor serviceA");
                }
            });
            return proxyFactory.getProxy();
        }
        return bean;
    }
}
