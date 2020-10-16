package springframework.beanScope;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * ClassName:    ThreadScopeTest
 * Package:    springframework.beanScope
 * Datetime:    2020/10/15   15:09
 * Author:   zsh
 * Description:
 */
public class ThreadScopeTest {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getBeanFactory().registerScope(ThreadScope.THREAD_SCOPE,new ThreadScope());
        context.registerBean(ThreadScopeModel.class,ThreadScope.THREAD_SCOPE);
        context.refresh();
        //使用容器获取bean
        for (int i = 0; i < 2; i++) { //@2
            new Thread(() -> {
                System.out.println(Thread.currentThread() + "," + context.getBean("threadScopeModel"));
                System.out.println(Thread.currentThread() + "," + context.getBean("threadScopeModel"));
            }).start();
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
