package http;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import org.apache.http.HttpException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @Author: zhangsh
 * @Date: 2020/4/27 10:05
 * @Version 1.0
 * Description
 */
public class HttpClientUtils {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException, HttpException {
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



        HttpClient client = new HttpClient();
//        ResponseStatus responseStatus = client.get("http://ivc-pro-storage.oos-cn.ctyunapi.cn/entId_34_2040017141714662021021919140540449701603-mix.mp3?AWSAccessKeyId=ab16cbf6548de2580d9d&Expires=1629803671&Signature=PJNKbu0mPquFkMts9zpqQ%2B%2BWqAM%3D");
        ResponseStatus responseStatus = client.get("http://121.31.255.235:12301/V2/20210122175507/TRCSD_44600aa0fb2edc14_15122907956_18511897668_20210122175507.mp3");
        System.out.println(responseStatus.getContentBytes());
        System.out.println(20160101L/10000L);
        System.out.println(20160101L% 10000L / 100);
        String a = "createTime,creatBy,updateBy,updateTime";
        String[] split = a.split(",", 3);
        for (String a11: split
             ) {
            System.out.println(a11);
        }
        int a1 = 20;
        byte b = 20;
        System.out.println(a1 == b);
        String objectName = "123123.1231.mp3";
        System.out.println(objectName.substring(0,objectName.lastIndexOf(".")));

        System.out.println(new StringBuilder().append(1213).toString());

        ExcelReader reader = ExcelUtil.getReader(FileUtil.file("C:\\Users\\zhangshihao01\\Desktop\\smp\\number.xlsx"));
        List<List<Object>> dataList = reader.read(1);
        dataList.forEach(obs->{
            obs.forEach(ob->{
                System.out.printf(ob+" ");
            });
            System.out.println();
        });
    }
}
