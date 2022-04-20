package springframework.circleRefrence;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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


    @org.junit.Test
    public void test(){
        JSONObject jsonObject = JSON.parseObject("{\"linkToB\":\"http://www.baidu.com\",\"paramKey\":\"bucode,lmid,mobile,lztoken,orderID,orderType\",\"orderType\":\"2\",\"highlightTags\":\"[\\\"高亮标签\\\"]\",\"from_client\":\"webim_longzhu\",\"enableCard\":\"1\",\"orderID\":\"orderskadlf123123\",\"bot_code\":\"LZZXKFWBYH\",\"mobile\":\"12819293129\",\"channel\":\"c1\",\"cardType\":\"0\",\"title\":\"商品标题\",\"set_navigation_bar\":0,\"lztoken\":\"lztokenlkdsllafjaksldf9034123\",\"lmid\":\"123lkdsaf\",\"tags\":[\"普通标签\"],\"args\":{\"orderType\":\"2\",\"orderID\":\"orderskadlf123123\",\"mobile\":\"12819293129\",\"channel\":\"c1\",\"project\":\"\",\"bucode\":\"bucode\",\"lztoken\":\"lztokenlkdsllafjaksldf9034123\",\"lmid\":\"123lkdsaf\"},\"forcePeopleSeat\":1,\"skill\":\"ff8080817322ac6c017332966cad0049\",\"name\":\"嘿嘿嘿\",\"bucode\":\"bucode\",\"entrance\":\"mobile-lhlhsmal-lzsh-dd\",\"visitorID\":\"zhangshihao01111112131230110101\"}");
        JSONArray highlightTags = jsonObject.getJSONArray("highlightTags");
        System.out.println(jsonObject);
    }
}
