package json;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author zhangshihao01
 * @version 1.0
 * @Description
 * @Date 2020/12/1 15:26
 */
@Data
@ToString
public class TestDto {
    private List<String> phaseList;
    private String channel;
    private Boolean hasOwner;
}
