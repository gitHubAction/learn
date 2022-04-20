package a;

import org.apache.commons.collections.CollectionUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @Date 2022/1/21 1:28
 */
public class Valid  implements IValid{

    private Map<String,Object> parameterMap = new ConcurrentHashMap<>(16);


    public Map<String,Object> getParameterMap(){
        return parameterMap;
    }

    public Object addInitParameter(String key, Object value) {
        return parameterMap.put(key, value);
    }
}
