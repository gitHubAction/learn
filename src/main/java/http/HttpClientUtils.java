package http;

/**
 * @Author: zhangsh
 * @Date: 2020/4/27 10:05
 * @Version 1.0
 * Description
 */
public class HttpClientUtils {

    public static void main(String[] args) {

        Object o = HttpClientUtil.postJson("http://192.168.5.133:8799/esReport/shopCenter/queryCustomerSettlement",
                "{\"groupId\":953,\"startDate\":20200201,\"endDate\":20200229,\"supplierId\":4220}", null, null,String.class);
        System.out.println(o);
    }
}
