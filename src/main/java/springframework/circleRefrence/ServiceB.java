package springframework.circleRefrence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName:    ServiceB
 * Package:    springframework.circleRefrence
 * Datetime:    2020/5/18   17:25
 * Author:   zsh
 * Description:
 */
@Service
public class ServiceB {
    public void m1(){
        System.out.println("serviceB m1");
        serviceA.m1();
    }

    private ServiceA serviceA;

    @Autowired
    public void setServiceA(ServiceA serviceA) {
        this.serviceA = serviceA;
    }

    public ServiceA getServiceA() {
        return serviceA;
    }
}
