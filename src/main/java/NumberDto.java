import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2021/7/29 15:06
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NumberDto{
    private Long id;

    private Long tel;

    private String carrier;

    private int limit;

    private String areaCode;

    private String sysCode;

    private Date enableTime;

    private Date expiredTime;

    private String createUser;
}
