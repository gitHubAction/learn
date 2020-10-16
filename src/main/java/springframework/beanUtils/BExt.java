package springframework.beanUtils;

import lombok.Data;
import lombok.ToString;

/**
 * ClassName:    BExt
 * Package:    springframework.beanUtils
 * Datetime:    2020/7/29   10:09
 * Author:   zsh
 * Description:
 */
@Data
@ToString(callSuper = true)
public class BExt extends B{
    private String c;
}
