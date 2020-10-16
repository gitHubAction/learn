package springframework.beanUtils;

import org.springframework.beans.BeanUtils;

/**
 * ClassName:    CopyTest
 * Package:    springframework.beanUtils
 * Datetime:    2020/7/29   10:10
 * Author:   zsh
 * Description:
 */
public class CopyTest {
    public static void main(String[] args) {
        AExt aExt = new AExt();
        aExt.setA("A");
        aExt.setB("B");
        aExt.setC("C");


        BExt bExt = new BExt();
        BeanUtils.copyProperties(aExt,bExt);
        System.out.println(bExt);
    }
}
