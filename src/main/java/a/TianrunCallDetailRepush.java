//package a;
//
//import cn.hutool.json.JSONArray;
//import cn.hutool.json.JSONObject;
//import cn.hutool.json.JSONUtil;
//import http.HttpClientUtil;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * @author zhangshihao01
// * @version 1.0
// * @description
// * @date 2023/2/7 14:08
// */
//public class TianrunCallDetailRepush {
//
//    public static void main(String[] args) throws Exception {
//        File jsonFile = new File("E:\\Download\\export_result.json");
//        FileReader fileReader = new FileReader(jsonFile);
//        BufferedReader bufferedReader = new BufferedReader(fileReader);
//        String line;
//        StringBuilder sb = new StringBuilder();
//        while((line = bufferedReader.readLine()) != null){
//            sb.append(line);
//        }
//        JSONObject jsonObject = JSONUtil.parseObj(sb.toString());
//        JSONArray data = jsonObject.getJSONArray("data");
//        AtomicInteger cnt = new AtomicInteger();
//        data.stream().forEach(callDetailStr->{
//            JSONObject callDetail = JSONUtil.parseObj(callDetailStr);
//            callDetail.set("tel_redir", callDetail.get("dir"));
//            callDetail.remove("tel_redir");
//
//            callDetail.set("tel_a", callDetail.get("a"));
//            callDetail.remove("a");
//
//            callDetail.set("tel_x", callDetail.get("x"));
//            callDetail.remove("x");
//
//            callDetail.set("tel_b", callDetail.get("b"));
//            callDetail.remove("b");
//
//            callDetail.remove("url");
//
//            System.out.println(callDetail);
//            String res = HttpClientUtil.postJson("todo https://lxhaics.longfor.com/open/api/tianRun/axyb/finishCallRecord", callDetail.toString(), null, null, String.class);
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("callId:  "+ callDetail.getStr("call_id")+", res"+ res);
//            cnt.getAndIncrement();
//        });
//        System.out.println("一共补偿了"+ cnt.get() +"条");
//    }
//}
