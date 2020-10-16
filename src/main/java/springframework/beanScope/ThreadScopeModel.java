package springframework.beanScope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * ClassName:    User
 * Package:    springframework.beanScope
 * Datetime:    2020/10/15   15:17
 * Author:   zsh
 * Description:
 */
@Component("threadScopeModel")
@Scope(ThreadScope.THREAD_SCOPE)
public class ThreadScopeModel {
    public ThreadScopeModel(String scope){
        System.out.println(String.format("线程:%s,create ThreadScopeModel,{scope=%s},{this=%s}", Thread.currentThread(), scope, this));
    }
}
