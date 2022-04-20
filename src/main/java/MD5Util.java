import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

/**
 * MD5加密工具类
 * @author huhaiming
 * @version V1.0
 * @date 2021/4/14 15:22
 **/
@Slf4j
public class MD5Util {
    static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * HEX加密
     * @author: huhaiming
     * @date: 2021/4/14 15:24
     * @Param plainText 要加密的字符串
     * @return java.lang.String
     **/
    public static String md5Upper(String plainText) {
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 使用指定的字节更新摘要
            md.update(plainText.getBytes());

            // 获得密文
            byte[] mdResult = md.digest();
            // 把密文转换成十六进制的字符串形式
            int j = mdResult.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte result : mdResult) {
                // 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移
                str[k++] = HEX_DIGITS[result >>> 4 & 0xf];
                // 取字节中低 4 位的数字转换
                str[k++] = HEX_DIGITS[result & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            log.warn("md5Upper error", e);
            return null;
        }
    }

    private MD5Util() {
    }
}
