import org.apache.commons.lang3.StringUtils;
import sun.security.provider.MD5;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

public class ShortUrl {


    public static void main(String[] args) {
        String url = ShortUrl.shortURL("http://www.51bi.com/bbs/_t_278433840", "test");
        System.out.println(url);
        isPhone("12345678901");
    }


    public static boolean isPhone(String s){
        List<Character> table = new ArrayList<>();
        table.add('0');
        table.add('1');
        table.add('2');
        table.add('3');
        table.add('4');
        table.add('5');
        table.add('6');
        table.add('7');
        table.add('8');
        table.add('9');
        if(StringUtils.isBlank(s) || s.length() > 14 || s.length() != 11 || s.length() != 13)return false;
        if(s.length() == 11){
            for(char a : s.toCharArray()){
                if(!table.contains(a))return false;
            }
            return true;
        }else{
            char c = s.charAt(0);
            if(c != '+')return false;
            char[] chars = s.toCharArray();
            for(int i = 1; i < chars.length; i++){
                if(!table.contains(chars[i]))return false;
            }
            return true;
        }
//        Pattern compile = Pattern.compile("(?:\\+\\d{2})?\\d{11}");
//        boolean res = compile.matcher(s).matches();
//        System.out.println(res);
//        return res;
    }

    final static String[] chars = new String[]{
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
            "0","1","2","3","4","5","6","7","8","9"
    };
    final static String prefix = "https://t.cn/";

    public static String shortURL(String url, String salt){
        // 加盐
        if(StringUtils.isBlank(salt)){
            salt = "salt";
        }
        // 生成32位的md5值
        String hex = md5Hex(salt + url);
        // 每8位一组生成一个9位的随机大小写+数字的 字符串
        String[] resUrl = new String[4];
        for(int i = 0; i < resUrl.length; i++){
            // 去除8位字符串
            String substring = hex.substring(i * 8, i * 8 + 8);
            // 得到16进制的长整型数
            long hexLong = 0x3FFFFFFF & Long.parseLong(substring, 16);
            String outChars = "";
            for(int j = 0; j < 9; j++){
                long index = 0x0000003D & hexLong;
                outChars += chars[(int)index];
                hexLong = hexLong >> 3;
            }
            resUrl[i] = outChars;
        }
        Random random = new Random();
        return prefix + resUrl[random.nextInt(4)];
    }


    public static String md5Hex(String src){
       try {
           MessageDigest md5 = MessageDigest.getInstance("MD5");
           byte[] bytes = src.getBytes();
           md5.reset();
           md5.update(bytes);
           byte[] digest = md5.digest();
           String hs = "";
           String stmp = "";
           for(int i = 0; i < digest.length; i++){
               stmp = Integer.toHexString(digest[i] & 0xFF);
               if(stmp.length() == 1){
                   hs = hs+"0"+stmp;
               }else{
                   hs = hs + stmp;
               }
           }
           return hs.toUpperCase();
       }catch (Exception e){
           return "";
       }
    }


    private ShortUrl(){}
}
