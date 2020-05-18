package springframework.circleRefrence;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ClassName:    Test
 * Package:    springframework.circleRefrence
 * Datetime:    2020/5/18   17:29
 * Author:   zsh
 * Description:
 */
public class Test {

    @org.junit.Test
    public void circleRefrence(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.addBeanFactoryPostProcessor(beanFactory -> {
            if(beanFactory instanceof DefaultListableBeanFactory){
                //二级缓存中早期暴露的bean在经过包装之后，是否允许原始注入
                ((DefaultListableBeanFactory) beanFactory).setAllowRawInjectionDespiteWrapping(true);
            }
        });
        context.register(Config.class);
        context.refresh();

        ServiceA serviceA = context.getBean(ServiceA.class);


        ServiceB serviceB = context.getBean(ServiceB.class);

        serviceB.m1();
        System.out.println("==============");
        serviceA.m1();

        System.out.println(serviceB.getServiceA() == serviceA);
    }
}
