package springframework.circleRefrence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName:    ServiceA
 * Package:    springframework.circleRefrence
 * Datetime:    2020/5/18   17:24
 * Author:   zsh
 * Description:
 */
@Service
public class ServiceA {
    public void m1(){
        System.out.println("serviceA m1");
    }

    private ServiceB serviceB;

    @Autowired
    public void setServiceB(ServiceB serviceB) {
        this.serviceB = serviceB;
    }
}
