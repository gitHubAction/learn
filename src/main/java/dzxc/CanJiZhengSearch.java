package dzxc;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ntp.TimeStamp;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/10/31 12:02
 */
public class CanJiZhengSearch {
    public static BufferedImage process(BufferedImage bin) {

        int endX = bin.getWidth();
        int endY = bin.getHeight();
        // 这里对图片黑白处理,增强识别率.这里先通过截图,截取图片中需要识别的部分
        BufferedImage textImage = ImageHelper.convertImageToGrayscale(ImageHelper.getSubImage(bin, 0, 0, endX, endY));
        // 图片锐化,自己使用中影响识别率的主要因素是针式打印机字迹不连贯,所以锐化反而降低识别率
//         textImage = ImageHelper.convertImageToBinary(textImage);
        // 图片放大5倍,增强识别率(很多图片本身无法识别,放大5倍时就可以轻易识,但是考滤到客户电脑配置低,针式打印机打印不连贯的问题,这里就放大5倍)
        textImage = ImageHelper.getScaledInstance(textImage, endX * 5, endY * 5);
        AsFile(textImage);
        return textImage;
    }

    public static void AsFile(BufferedImage textImage) {
        File gifFile = new File("D:\\captcha\\" + System.currentTimeMillis() + ".gif");
        OutputStream out = null;
        try {
            out = new FileOutputStream(gifFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ImageIO.write(textImage, "gif", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        IOUtils.closeQuietly(out);
    }


    public static void main1(String[] args) {
        //验证码图片存储地址
//        if(!imageFile.exists()){
//            System.out.println("图片不存在");;
//        }
        Tesseract tessreact = new Tesseract();
        tessreact.setDatapath("E:\\code\\tesseract-main\\tessdata");
        String result;
        try {
            InputStream imageFile = new FileInputStream("E:\\Download\\ucode.jpg");
            BufferedImage bin = ImageIO.read(imageFile);
            bin = process(bin);
            result = "测验结果：" + tessreact.doOCR(bin);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Person {
        public String name;
        public String idcard;
        public String res;

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", idcard='" + idcard + '\'' +
                    ", res='" + res + '\'' +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getRes() {
            return res;
        }

        public void setRes(String res) {
            this.res = res;
        }
    }

    public static void main3(String[] args) {
        ExcelWriter writer = ExcelUtil.getWriter("D:\\名单结构.xlsx");
        // 跳过写入的行
        writer.passRows(writer.getRowCount());
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String,Object> tMap = new HashMap<>();
        tMap.put("姓名","name");
        tMap.put("身份证","idcard");
        tMap.put("查询结果","resJson");
        resultList.add(tMap);
        writer.write(resultList, false);
        writer.close();
    }
    public static void main(String[] args) throws InterruptedException {
        List<Map<String,Object>> resultList = new ArrayList<>();
        ExcelReader reader = ExcelUtil.getReader(new File("D:\\名单.xlsx"));
        List<List<Object>> read = reader.read(1);
        String code, idcard = null, name = null, cjyResStr;
        Map<String,Object> formMap = new HashMap<>();
        int i = 0;
        for (List<Object> rowdata: read) {
            name = rowdata.get(0)+"";
            idcard = rowdata.get(1)+"";

            if( i < 63 || rowdata.size() == 3){
                System.out.println(name+"\t"+idcard+"\t"+rowdata.get(2));
                i++;
                if("系统查无此人！".equals(rowdata.get(2)+"")
                        || "此人残疾人证状态为过期！".equals(rowdata.get(2)+"")
                        || "此人是持证残疾人！".equals(rowdata.get(2)+"")){
                    continue;
                }
            }
                Map<String,Object> tMap = new HashMap<>();
            try {
                // 获取验证码
                String url = "https://2dzcx.cdpf.org.cn/cms/resource/ucode.jpg?time=" + TimeStamp.getCurrentTime().getTime();
                System.out.println(url);
//            ByteArrayInputStream byteIn = new ByteArrayInputStream(HttpRequest.get(url).execute().bodyBytes());
//            BufferedImage bufferedImage = ImgUtil.read(byteIn);
//            bufferedImage = process(bufferedImage);
//            Tesseract tessreact = new Tesseract();
//            tessreact.setDatapath("E:\\code\\tesseract-main\\tessdata");


                //username=test&password=1
                cjyResStr = CanJiZhengSearch.PostPic("lanchunyu","!@#$%^","954126","1004","4",HttpRequest.get(url).execute().bodyBytes());
                JSONObject cjyRes = JSONUtil.parseObj(cjyResStr);
                if(!Integer.valueOf(0).equals(cjyRes.getInt("err_no"))){
                    System.out.println(cjyRes.getStr("err_str"));
                    break;
                }
                code = cjyRes.getStr("pic_str");
                formMap.put("name",name);
                formMap.put("idcard",idcard);
                formMap.put("code",code);
                System.out.println(name+"\t"+idcard+"\t"+code);
                String result = HttpUtil.post("https://2dzcx.cdpf.org.cn/cms/addons/check/user", formMap);
                JSONObject resJson = JSONUtil.parseObj(result);
                System.out.println(name+"\t"+idcard+"\t"+resJson.getStr("object"));
                tMap.put("姓名",name);
                tMap.put("身份证",idcard);
                tMap.put("查询结果",resJson.getStr("object"));
                resultList.add(tMap);
                Thread.sleep(1000*20);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(name+"\t"+idcard+"\t"+"异常");
                tMap.put("姓名",name);
                tMap.put("身份证",idcard);
                tMap.put("查询结果","异常");
                resultList.add(tMap);
                Thread.sleep(1000*5);
            }
            i++;
        }
        ExcelWriter writer = ExcelUtil.getWriter("D:\\名单结构.xlsx");
        // 跳过写入的行
        writer.passRows(writer.getRowCount());
        writer.write(resultList, false);
        writer.close();
    }



    /**
     * 字符串MD5加密
     * @param s 原始字符串
     * @return  加密后字符串
     */
    public final static String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通用POST方法
     * @param url 		请求URL
     * @param param 	请求参数，如：username=test&password=1
     * @return			response
     * @throws IOException
     */
    public static String httpRequestData(String url, String param)
            throws IOException {
        URL u;
        HttpURLConnection con = null;
        OutputStreamWriter osw;
        StringBuffer buffer = new StringBuffer();

        u = new URL(url);
        con = (HttpURLConnection) u.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");

        osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
        osw.write(param);
        osw.flush();
        osw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(con
                .getInputStream(), "UTF-8"));
        String temp;
        while ((temp = br.readLine()) != null) {
            buffer.append(temp);
            buffer.append("\n");
        }

        return buffer.toString();
    }

    /**
     * 核心上传函数
     * @param param			请求参数，如：username=test&password=1
     * @param data			图片二进制流
     * @return				response
     * @throws IOException
     */
    public static String httpPostImage(String param, byte[] data) throws IOException {
        long time = (new Date()).getTime();
        URL u = null;
        HttpURLConnection con = null;
        String boundary = "----------" + MD5(String.valueOf(time));
        String boundarybytesString = "\r\n--" + boundary + "\r\n";
        OutputStream out = null;

        u = new URL("http://upload.chaojiying.net/Upload/Processing.php");

        con = (HttpURLConnection) u.openConnection();
        con.setRequestMethod("POST");
        //con.setReadTimeout(60000);
        con.setConnectTimeout(60000);
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setUseCaches(true);
        con.setRequestProperty("Content-Type",
                "multipart/form-data; boundary=" + boundary);

        out = con.getOutputStream();

        for (String paramValue : param.split("[&]")) {
            out.write(boundarybytesString.getBytes("UTF-8"));
            String paramString = "Content-Disposition: form-data; name=\""
                    + paramValue.split("[=]")[0] + "\"\r\n\r\n" + paramValue.split("[=]")[1];
            out.write(paramString.getBytes("UTF-8"));
        }
        out.write(boundarybytesString.getBytes("UTF-8"));

        String paramString = "Content-Disposition: form-data; name=\"userfile\"; filename=\""
                + "chaojiying_java.gif" + "\"\r\nContent-Type: application/octet-stream\r\n\r\n";
        out.write(paramString.getBytes("UTF-8"));

        out.write(data);

        String tailer = "\r\n--" + boundary + "--\r\n";
        out.write(tailer.getBytes("UTF-8"));

        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        String temp;
        while ((temp = br.readLine()) != null) {
            buffer.append(temp);
            buffer.append("\n");
        }

        return buffer.toString();
    }



    /**
     * 识别图片_按图片文件路径
     * @param username		用户名
     * @param password		密码
     * @param softid		软件ID
     * @param codetype		图片类型
     * @param len_min		最小位数
     * @param filePath		图片文件路径
     * @return
     * @throws IOException
     */
    public static String PostPic(String username, String password, String softid, String codetype, String len_min, String filePath) {
        String result = "";
        String param = String
                .format(
                        "user=%s&pass=%s&softid=%s&codetype=%s&len_min=%s", username, password, softid, codetype, len_min);
        try {
            File f = new File(filePath);
            if (null != f) {
                int size = (int) f.length();
                byte[] data = new byte[size];
                FileInputStream fis = new FileInputStream(f);
                fis.read(data, 0, size);
                if(null != fis) fis.close();

                if (data.length > 0)	result = CanJiZhengSearch.httpPostImage(param, data);
            }
        } catch(Exception e) {
            result = "未知问题";
        }


        return result;
    }

    /**
     * 识别图片_按图片二进制流
     * @param username		用户名
     * @param password		密码
     * @param softid		软件ID
     * @param codetype		图片类型
     * @param len_min		最小位数
     * @param byteArr		图片二进制数据流
     * @return
     * @throws IOException
     */
    public static String PostPic(String username, String password, String softid, String codetype, String len_min, byte[] byteArr) {
        String result = "";
        String param = String
                .format(
                        "user=%s&pass=%s&softid=%s&codetype=%s&len_min=%s", username, password, softid, codetype, len_min);
        try {
            result = CanJiZhengSearch.httpPostImage(param, byteArr);
        } catch(Exception e) {
            result = "未知问题";
        }


        return result;
    }

    /**
     * 识别图片_按图片base64字符串 请提前参考base64注意事项 http://www.chaojiying.com/api-46.html
     * @param username		用户名
     * @param password		密码
     * @param softid		软件ID
     * @param codetype		图片类型
     * @param len_min		最小位数
     * @param file_base64	图片base64字符串
     * @return
     * @throws IOException
     */
    public static String PostPic_base64(String username, String password, String softid, String codetype, String len_min, String file_base64) {

        // URL编码
        try {
            file_base64 = URLEncoder.encode(file_base64,"UTF-8");
        } catch (Exception e) {
            return "";
        }
        String param = String.format("user=%s&pass=%s&softid=%s&codetype=%s&len_min=%s&file_base64=%s", username, password, softid, codetype, len_min, file_base64);
        String result;
        try {
            result = CanJiZhengSearch.httpRequestData(
                    "http://upload.chaojiying.net/Upload/Processing.php", param);
        } catch (IOException e) {
            result = "未知问题";
        }
        return result;
    }


    /**
     * 报错返分
     * @param username	用户名
     * @param password	用户密码
     * @param softId	软件ID
     * @param id		图片ID
     * @return			response
     * @throws IOException
     */
    public static String ReportError(String username, String password, String softid, String id) {

        String param = String
                .format(
                        "user=%s&pass=%s&softid=%s&id=%s",
                        username, password, softid, id);
        String result;
        try {
            result = CanJiZhengSearch.httpRequestData(
                    "http://upload.chaojiying.net/Upload/ReportError.php", param);
        } catch (IOException e) {
            result = "未知问题";
        }

        return result;
    }


    /**
     * 查询题分
     * @param username	用户名
     * @param password	密码
     * @return			response
     * @throws IOException
     */
    public static String GetScore(String username, String password) {
        String param = String.format("user=%s&pass=%s", username, password);
        String result;
        try {
            result = CanJiZhengSearch.httpRequestData(
                    "http://upload.chaojiying.net/Upload/GetScore.php", param);
        } catch (IOException e) {
            result = "未知问题";
        }
        return result;
    }
}