import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.google.common.collect.Lists;

import java.io.File;
import java.util.*;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/3/2 14:59
 */
public class HttpUtils {
    public static void main(String[] args) {
        ExcelReader reader = ExcelUtil.getReader("C:\\Users\\zhangshihao01\\Desktop\\smp\\C1\\bindid.xlsx");
        List<List<Object>> bindIds = reader.read();
        Map<String,Object> param = new HashMap<>();
        HttpRequest req = HttpUtil.createPost("https://api.longhu.net/privacy-number-api-prod/api/zhonglian/ax/unbind");
        for (List<Object> bindId : bindIds) {
            param.put("bindId", bindId.get(0));
            param.put("appKey","8968270464293229");
            param.put("appSecret","F4Xwh7C3YMkHbdS6dQEwFnmSX14A24D2");
            HttpResponse reso = req.header("content-type", "application/json;charset=UTF-8")
                    .header("accept", "application/json;charset=UTF-8")
                    .header("x-gaia-api-key","dab88360-cb97-4faf-ba96-27faf1f5cc5f")
                    .body(JSONUtil.toJsonStr(param))
                    .execute();
            System.out.println(reso.body());
        }
    }


    /**
     * 生成摘要值
     *
     * @param map     加密数据
     * @param corpSecret 加密密钥
     * @return 加密值
     **/
    public static String getMsgDgtStr(Map<String, Object> map, String corpSecret) {
        String msgdgt = mapKeySort(map);
        String beforeDigit = String.format("%s%s%s", msgdgt, "corp_secret=", corpSecret);
        return MD5Util.md5Upper(beforeDigit);
    }

    public static String mapKeySort(Map<String, Object> map) {
        Object[] keys = map.keySet().toArray();
        Arrays.sort(keys);
        StringBuilder msgDgt = new StringBuilder();
        for (Object key : keys) {
            Object value = map.get(key);
            if ("sign".equals(key) || value == null) {
                continue;
            }
            msgDgt.append(key);
            msgDgt.append("=");
            msgDgt.append(value);
            msgDgt.append("&");
        }
        return msgDgt.toString();
    }
}
