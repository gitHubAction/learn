package es;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/9/22 18:11
 */
public class A {
    public static void main(String[] args) {
        JSONObject jsonObject = JSON.parseObject("{\"city\":\"济南\",\"projectId\":\"480048164093919232\",\"projectChanel\":\"C4\",\"projectName\":\"天泰太阳树\",\"spredName\":\"\",\"alias\":\"天泰太阳树物业项目\",\"projectSpredName\":\"\",\"phaseList\":\"天泰太阳树-一期,天泰太阳树-二期    \"}");

        System.out.println(jsonObject);
    }
}
