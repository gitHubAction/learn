package http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

/**
 * @Author: zhangsh
 * @Date: 2020/4/27 10:05
 * @Version 1.0
 * Description
 */
public class HttpClientUtils {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*Object o = HttpClientUtil.postJson("http://172.20.47.31:9012/data/query",
                "{\"data\":[{\"createTime\":1590662223566,\"notes\":\"测试分类信息20200528\",\"pcCode\":\"PIG\"," +
                        "\"pcName\":\"猪肉类\",\"status\":0,\"updateTime\":1590662223566}],\"appKey\":\"zMSWfGq88BkdNB6R\"," +
                        "\"type\":\"goodsType\"}", null, null,String.class);*/

        /*String o = HttpClientUtil.postJson("http://hw.test.wareic.22city.com/shopmall/inner/1.0/apiRouter",
                "{\"cs\":\"web\",\"traceID\":\"b584fd4d-afd1-452d-8e74-cbb4679f8ed3\",\"cv\":\"1\"," +
                        "\"data\":{\"provinceCode\":\"11\",\"farmProduceName\":\"真知棒\",\"marketCode\":\"1101003\"," +
                        "\"pageSize\":100000,\"pageNum\":1},\"pv\":\"100096\",\"groupID\":\"569\"," +
                        "\"source\":\"supply-chain\"}", null, null,String.class);
        JSONObject resultJo = null;
        try {
            resultJo = JSON.parseObject(o);
        }catch (Exception e){
        }
        if(resultJo == null || !resultJo.getBoolean("success")){
        }
        Object data = resultJo.get("data");

        System.out.println(data);*/


        /*Integer successNum = Integer.valueOf(5);
        Integer errorNum = Integer.valueOf(6);
        Integer totalNum = Integer.valueOf(10);
        System.out.println(Integer.valueOf(successNum + errorNum).compareTo(totalNum));*/
//        System.out.println(BigDecimal.ZERO.compareTo(BigDecimal.valueOf(0)));
/*
        Boolean a = null;
        if(a){
            System.out.println("1213123");
        }
        System.out.println(Integer.valueOf(110).compareTo(Integer.valueOf(10)));

*/

        System.out.println(20160101L/10000L);
        System.out.println(20160101L% 10000L / 100);

        try {
            System.out.println(new String("哈哈哈哈哈".getBytes("UTF-8"),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
