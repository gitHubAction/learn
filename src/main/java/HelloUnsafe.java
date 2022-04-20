import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class HelloUnsafe {
    static class M{
        private M(){}

        int m = 0;
    }

    public static void main(String[] args) throws InstantiationException {
        Map<String, Object> pushMsgMap = new HashMap<>();
        pushMsgMap.put("status","0");
        pushMsgMap.put("appCode","appCode");
        pushMsgMap.put("areaCode", "araeCode");
        pushMsgMap.put("telA", "telA");
        pushMsgMap.put("sysCode", "sysCode");
        pushMsgMap.put("assignCode", "sa");
        pushMsgMap.put("assignCode1", "sa");
        pushMsgMap.put("assignCode2", "sa");
        pushMsgMap.put("assignCode3", "sa");
        pushMsgMap.put("assignCode4", "sa");
        pushMsgMap.put("assignCode6", "sa");
        pushMsgMap.put("assignCode8", "sa");
        pushMsgMap.put("assignCode9", "sa");
        pushMsgMap.put("assignCode10", "sa");
        pushMsgMap.put("assignCode11", "sa");
        pushMsgMap.put("assignCode12", "sa");
        pushMsgMap.put("assignCode1231", "sa");
        pushMsgMap.put("assignCode121", "sa");
        pushMsgMap.put("assignCode123", "sa");
        pushMsgMap.put("assignCode122", "sa");
        pushMsgMap.put("assignCode124", "sa");
        pushMsgMap.put("assignCode125", "sa");
        pushMsgMap.put("assignCode126", "sa");
        pushMsgMap.put("assignCode127", "sa");
        pushMsgMap.put("assignCode128", "sa");
        pushMsgMap.put("assignCode129", "sa");
        pushMsgMap.put("assignCode130", "sa");
        pushMsgMap.put("assignCode131", "sa");
        pushMsgMap.put("assignCode132", "sa");
        pushMsgMap.put("assignCode133", "sa");
        pushMsgMap.put("assignCode134", "sa");
        pushMsgMap.put("assignCode135", "sa");
        pushMsgMap.put("assignCode136", "sa");
        pushMsgMap.put("assignCode137", "sa");
//        System.out.println(JSON.toJSONString());
//        SymmetricCrypto crypto = new SymmetricCrypto("algorithm");
//        String s = crypto.encryptBase64(JSON.toJSONString(pushMsgMap));
//        System.out.println(s);
//        String s1 = crypto.decryptStr(s);
//        System.out.println(s);
//        System.out.println(s.equals(s1));
        String origin = JSON.toJSONString(pushMsgMap);
        DES aes = SecureUtil.des();
        String s2 = aes.encryptHex(origin);
        System.out.println(s2);
        System.out.println(s2.length());
        String s3 = aes.decryptStr(s2);
        System.out.println(s3);
        System.out.println(s3.equals(origin));
        System.out.println("-----------------------------");
        String md5 = SecureUtil.md5(origin);
        System.out.println(md5);
        String s = SecureUtil.md5().digestHex(md5);
        System.out.println(s);
        System.out.println(s.equals(origin));



        String content = "{\"fileName\":\"{}\",\"url\":\"{}\"}";
        String bb = StrUtil.format(content,  "哈哈哈哈哈哈.xlsx","http://localhost:8080/attach/PA12312312313");
        System.out.println(bb);


        DateTime parse = DateUtil.parse("2021-09-01 10:00:00", "yyyy-MM-dd HH:mm:ss");
        System.out.println(parse.before(new Date()));


        System.out.println(new Date(1662458376571L));


        List<String> a = new ArrayList<>();
        a.add("");
        a.add(null);
        a.add("123");
        System.out.println(a.size());
        List<String> collect = a.stream().distinct().filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        System.out.println(collect);
    }
}
