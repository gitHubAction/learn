package springframework.beanScope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ClassName:    ThreadScope
 * Package:    springframework.beanScope
 * Datetime:    2020/10/15   14:44
 * Author:   zsh
 * Description: bean自定义线程作用域
 */
public class ThreadScope implements Scope {

    public static final String THREAD_SCOPE = "thread";

    private static ThreadLocal<Map<String,Object>> threadBeanMap = ThreadLocal.withInitial(()->new HashMap<>());

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Object bean = threadBeanMap.get().get(name);
        if(Objects.isNull(bean)){
            bean = objectFactory.getObject();
            threadBeanMap.get().put(name,bean);
        }
        return bean;
    }

    @Override
    public Object remove(String name) {
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {

    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        //返回当前线程名称
        return Thread.currentThread().getName();
    }
}
