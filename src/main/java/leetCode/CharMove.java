//package leetCode;
//
//import cn.hutool.core.codec.Base64;
//import cn.hutool.core.date.DateUnit;
//import cn.hutool.core.date.DateUtil;
//import cn.hutool.core.util.CharsetUtil;
//import cn.hutool.crypto.asymmetric.KeyType;
//import cn.hutool.crypto.asymmetric.RSA;
//import cn.hutool.crypto.digest.MD5;
//import cn.hutool.crypto.symmetric.AES;
//import cn.hutool.json.JSONUtil;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.util.StopWatch;
//
//import java.io.UnsupportedEncodingException;
//import java.security.SecureRandom;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.regex.Pattern;
//
//import static cn.hutool.core.util.CharsetUtil.UTF_8;
//
///**
// * @author zhangshihao01
// * @version 1.0
// * @description
// * @date 2021/9/16 16:08
// */
//public class CharMove {
//    private static final int MAXIMUM_CAPACITY = 1 << 30;
//
//
//    public static void swap(int[] arr, int i, int j) {
//        int tmp = arr[i];
//        arr[i] = arr[j];
//        arr[j] = tmp;
//    }
//
//    // 已知arr中，只有0~n-1这些值，并且都出现1次
//    public static int minSwap2(int[] arr) {
//        int ans = 0;
//        for (int i = 0; i < arr.length; i++) {
//            while (i != arr[i]) {
//                swap(arr, i, arr[i]);
//                ans++;
//            }
//        }
//        return ans;
//    }
//
//    public static void main(String[] args) throws UnsupportedEncodingException {
//        String b12 = "|";
//        String s = "AXB|asfasdfasdfas";
//        System.out.println(s.substring(0,s.lastIndexOf(b12)));
//        System.out.println(s.substring(s.lastIndexOf(b12)+1));
//
//
//        String a12 = "\\|";
//        String[] split = s.split(a12);
//        for (String a1:
//             split) {
//            System.out.println(a1);
//        }
//
//        System.out.println(1800000/60/1000);
//
//
//        int i1 = minSwap2(new int[]{2, 0, 1});
//        System.out.println(i1);
//
//        System.out.println(String.format("%s参数配置异常，请联系配置管理员!","技能组[skill]"));
//
//        System.out.println("+id,-updateTime".contains("id"));
//        System.out.println("+id,-updateTime".contains("updateTime"));
//
//
//        Boolean flag = Boolean.FALSE;
//        boolean process = process(flag);
//        System.out.println(flag);
//
//
//        String privacyKey =
//                        "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALaHbJkpnSlv6NRl" +
//                        "PpVQ7F8x4ee5DPxGSYUh0phbqFf7E2w6pJGWrq+5UYCzPv38LZ3CuCOzyadb44O5" +
//                        "t5Oi7hFVtICvgqzawWXrZlboSV10mHXoVAkRSBxusZzXRGOsWgrlo6mM84b2SLnT" +
//                        "rl5jV+BKF/5XrgKTu09Q787nVoBRAgMBAAECgYEAmj764Luv2hJ9+O7N4jSOayum" +
//                        "McHJXW6wNlcMtj/OlPrRwQYViAqD+G9slovi0ZNHoSepoFIQOOOH8gsBPTvUXVfu" +
//                        "//RxOsdKUFrRQ2omE2Sbd/oGaSZplCAL2IBjjGHwQEhd8Gka8SRzoNm5rAbm6jV+" +
//                        "ZvDv0xYbm96bKOJBhAECQQDjr6jYoUqzRzPgpYmy9Cot4AvL31S4mGNX7bqp7yWq" +
//                        "bFzAdemwcbotpEbGEe0Lz8UKLXQnE51x9+V3tby1laIxAkEAzToyIv9NrwLwKsBd" +
//                        "swTyli91SAQWT4V4rnuo8z9F1x8oZ+qeaJalYCmUNVMfa53O7As1jle4ih3IQ/kE" +
//                        "Z1gYIQJAaU4qMp9OtGI/LSyC5mClTXhtHErbnVDlyZorFkOEUTEzKNSgU7oV2Nw8" +
//                        "24kY8LiUsmNZcGVTEY4M9HP6pqVhUQJBAIn9BDL12CTBY/xKxXWV8Qo8NK5MWTf0" +
//                        "tVUUPl/3fh0LjR556TLHf8BtlIJaNnkkbTnWu5bOsnzjU4EokOZvnCECQElcYQ1U" +
//                        "TG5To4RfDUSziQldLQ+9LCLVVkB49X9CL/CAa7fhgut7D98SDk0OT+PmEaNNeHMN" +
//                        "rNAw2igGEnG5DYQ=";
//
//        String publicKey =
//                        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2h2yZKZ0pb+jUZT6VUOxfMeHn" +
//                        "uQz8RkmFIdKYW6hX+xNsOqSRlq6vuVGAsz79/C2dwrgjs8mnW+ODubeTou4RVbSA" +
//                        "r4Ks2sFl62ZW6ElddJh16FQJEUgcbrGc10RjrFoK5aOpjPOG9ki5065eY1fgShf+" +
//                        "V64Ck7tPUO/O51aAUQIDAQAB";
//
//        RSA rsaPub = new RSA(null,publicKey);
//        String key = Base64.encode(rsaPub.encrypt("欢迎来到chacuo.net", KeyType.PublicKey));
//        System.out.println(key);
//        System.out.println();
////
////        String s = Base64.decodeStr(key);
//        RSA rsaPri = new RSA(privacyKey,null);
//        String param1 = rsaPri.decryptStr("iZnkaGd6PBvv+m6S7IiwtjwH1EXp62cMIPZkQBqbZKCegjK+OmyoYa2aO3VYN7Y6qwgRQy1eR3wOnO12MdjW9qayquFHghA8lBwhaxEZGtnhZMcUZEtdf68v+67zVQZhUKbRapVUkp417lpswo343T/EOQp+eFBJ8vN2nq4KyDk=",KeyType.PrivateKey);
//        System.out.println(param1);
//        System.out.println("-======================================================");
//        System.out.println("-======================================================");
//        System.out.println("-======================================================");
//        System.out.println("-======================================================");
//
//
//       String ca =  MD5.create().digestHex("https://cc-pre.longfor.com/im/text/8ab6809b6913ad260169142e719e00a2.html?\n" +
//                "skill=8ab680966a26eaef016a4e70ee2e017a&\n" +
//                "skill=8ab680966a26eaef016a4e70ee2e017a&\n" +
//                "skill=8ab680966a26eaef016a4e70ee2e017a&\n" +
//                "forcePeopleSeat=1&\n" +
//                "forcePeopleSeat=1&\n" +
//                "forcePeopleSeat=1&\n" +
//                "project_id=15440683982682589%23%23%23%E5%8C%97%E4%BA%AC&\n" +
//                "project_id=15440683982682589%23%23%23%E5%8C%97%E4%BA%AC&\n" +
//                "project_id=15440683982682589%23%23%23%E5%8C%97%E4%BA%AC&\n" +
//                "bot_code=LXHRSDH&\n" +
//                "bot_code=LXHRSDH&\n" +
//                "bot_code=LXHRSDH&\n" +
//                "mobile=13520404096&\n" +
//                "mobile=13520404096&\n" +
//                "mobile=13520404096&\n" +
//                "from_client=app_hr&\n" +
//                "from_client=app_hr&\n" +
//                "from_client=app_hr&\n" +
//                "oa_account=lichen8&\n" +
//                "oa_account=lichen8&\n" +
//                "oa_account=lichen8&\n" +
//                "name=%E6%9D%8E%E6%99%A8&\n" +
//                "name=%E6%9D%8E%E6%99%A8&\n" +
//                "name=%E6%9D%8E%E6%99%A8&\n" +
//                "args=%7B%22project%22%3A%22%22,%22city%22%3A%22%E6%98%86%E6%98%8E%22,%22channel%22%3A%22LF01%22,%22company%22%3A%22114%22%7D&\n" +
//                "args=%7B%22project%22%3A%22%22,%22city%22%3A%22%E6%98%86%E6%98%8E%22,%22channel%22%3A%22LF01%22,%22company%22%3A%22114%22%7D&\n" +
//                "args=%7B%22project%22%3A%22%22,%22city%22%3A%22%E6%98%86%E6%98%8E%22,%22channel%22%3A%22LF01%22,%22company%22%3A%22114%22%7D&\n" +
//                "token=jwja728818-671%C2%A5uwiiw&\n" +
//                "token=jwja728818-671%C2%A5uwiiw&\n" +
//                "token=jwja728818-671%C2%A5uwiiw&\n" +
//                "visitorID=1111111232434&\n" +
//                "visitorID=1111111232434&\n" +
//                "visitorID=1111111232434&\n" +
//                "entrance=ZN-MP-yts-lxh-RS&\n" +
//                "entrance=ZN-MP-yts-lxh-RS&\n" +
//                "entrance=ZN-MP-yts-lxh-RS", UTF_8);
//
//        System.out.println(ca.length());
//
//
//        AES aes = new AES("1234567891234567".getBytes(UTF_8));
//        Map<String,Object> param = new HashMap<>();
//        // 用户唯一id
//        param.put("vistorID", 1231231231);
//        // 业务编码
//        param.put("bot_code", "botCode");
//        // 传入项目ID（主数据项目ID） 业务定义参数
//        param.put("projectCode", "projectId");
//        // 传入客服联系电话
//        param.put("contactPhone", 17801081240L);
//        // 在线客服租户编码 (去除)
//        // param.put("tenant", "");
//        // 在线客服技能组ID
//        param.put("skill", "123ooi12312i3o1231io");
//        // 在线客服渠道编码
//        param.put("from_client", "app_client");
//        // 1为在线客服，0为机器人
//        param.put("forcePeopleSeat", 1);
//        // 现在在线客服对话窗口中的用户昵称
//        param.put("name", "测试账户");
//        // 坐席显示客户oa账号，如果不传入则坐席端不显示
//        param.put("oa_account", "test01");
//        // 入口参数
//        param.put("entrance", "entrance_code");
//        Map<String, String> argsMap = new HashMap<>();
//        argsMap.put("a","12313");
//        argsMap.put("b","b123123");
//        param.put("args", argsMap);
//        String encryptBase64 = aes.encryptBase64(JSONUtil.toJsonStr(param));
//        System.out.println(encryptBase64);
//        System.out.println("-----------------------===============");
//        System.out.println(aes.decryptStr("OWTanhQG4ffK5fexB40RqivTWkFQ HSUL7olRfcjd2xosH2MOb83VzaZve14ykXWlhd8nJCmfbGou/qF/81oLbxAA57Ox8lo/9WdpHwE20s pxM9LVyxLmLY5xi gmdNBX/yPj F1m7NHh3BIXuhHdpy5tV3H6McVu9FsAUmIY5iIuCPU6LS53mbVlRXozzyR4OCakdCeR79RrMPC1EuoS2j8Ys0y5PjBJdKv45/AcEPwHL6fQL7nrwIFCJQpdMGz3E1FDyy3LmxwjkSiGd50YofS R4gn2nRlSx4edOXGmMMyTGc5kDxFdgAzsj5GiAdHI4B ai7nae99jvVfB1XHg7i0louBmqn2/iJtLrc yAB3NHPQzNDQmnaiSu611HyWyRsSol 608bPvtnSkCNk/nuFzLUeOqcpXx8u8uxqB1oPGmt1YXgZmYOhCggsSS7Q F/EHLNjT647B7Mtc6kllY/VUeTxpie3FO/h5m/3NUVd4aE9uYT2Z9B6DaKLB0"));
//
//
//        System.out.println(Integer.MAX_VALUE > 100 * 365 * 24 * 60 * 60);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//        int appendix = (int)(new SecureRandom().nextDouble()*(9999-1000+1))+1000;
//        System.out.println("DX" + sdf.format(new Date())+appendix);
//
//        A a = new A();
//        System.out.println(a.getA());
//
//        StopWatch sw = new StopWatch();
//        sw.start();
//        for (int i = 0; i < 3000; i++) {
//            Pattern.matches("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", RandomStringUtils.randomAlphanumeric(11));
//        }
//        sw.stop();
//        System.out.println(sw.getTotalTimeMillis());
////        String s = "abc";
////        int[][] shift = {{0,1},{1,2}};
////        // 拆分字符串为环形数组
////        CircleStr circleStr = char2Array(s);
////        // 移动环型数组索引位置
////        String result = move(circleStr,shift);
//
////        String a = "";
////        boolean matches = Pattern.matches("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", "4007080080");
////        System.out.println(matches);
//
//
//        long day = DateUtil.between(DateUtil.parseDate("2020-12-31 12:00:00.000"), DateUtil.parseDate("2020-12-31 12:00:00.000"), DateUnit.DAY, false);
//        System.out.println(day);
//
//        System.out.println(StringUtils.equals("0", null));
//
//
//        int size = tableSizeFor(8 + (8 >>> 1) + 1);
//        System.out.println(size);
//
//
//        Map<String, String> aM = new HashMap<>();
//        aM.put("1","1");
//        aM.put("2","1");
//        aM.put("3","1");
//        aM.put("4","1");
//        aM.put("5","1");
//        aM.put("6","1");
//        aM.put("7","1");
//        aM.put("8","1");
//
//        Map<String, String> cM = new ConcurrentHashMap<>(aM);
//
//
//
//
//        Long 了 = Long.parseLong("0");
//        System.out.println(了 == 0);
//    }
//
//    private static boolean process(Boolean a) {
//        a = Boolean.TRUE;
//        return false;
//    }
//
//    private static final int tableSizeFor(int c) {
//        int n = c - 1;
//        n |= n >>> 1;
//        n |= n >>> 2;
//        n |= n >>> 4;
//        n |= n >>> 8;
//        n |= n >>> 16;
//        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
//    }
//
//
//    private static String move(CircleStr circleStr, int[][] shift) {
//        for (int i = 0; i < shift.length; i++) {
//            for (int j = 0; j < shift[i].length; j++) {
//                int direction = shift[i][0];
//                int amount = shift[i][1];
//                direction = direction == 0 ? 1 : -1;
//                circleStr.setHead(circleStr.getHead() + (amount / circleStr.arr.length));
//                circleStr.setTail(circleStr.getTail() + (amount * direction));
//            }
//        }
//        return "";
//    }
//
//    private static CircleStr char2Array(String s) {
//        CircleStr circleStr = new CircleStr();
//        circleStr.setArr(s.toCharArray());
//        circleStr.setHead(0);
//        circleStr.setTail(s.length()-1);
//        return circleStr;
//    }
//
//    @Data
//    @NoArgsConstructor
//    public static class CircleStr{
//        // 字符数据
//        char[] arr;
//        // 起始索引位置
//        int head;
//        // 结束索引位置
//        int tail;
//    }
//}
