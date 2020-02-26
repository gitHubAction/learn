import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

import java.util.Random;

/**
 * @Author: zhangsh
 * @Date: 2019/8/7 9:34
 * @Version 1.0
 * Description
 */
public class JoinTest {


    public static void main(String[] args) throws Exception {
//        JoinTest joinTest = new JoinTest();
//        joinTest.test();

        /*Random r = new Random();
        String current = String.valueOf(System.currentTimeMillis());

        for(int i = 0; i < 5; ++i) {
            int num = r.nextInt(10);

            int charpos;
            for(charpos = r.nextInt(current.length() + 1); num == 0 && charpos == 0; num = r.nextInt(10)) {
            }

            current = current.substring(0, charpos) + num + current.substring(charpos);
        }
        System.out.println(Long.parseLong(current));*/
//        System.out.println(Long.parseLong("20190128000000000523"));
        String str = "朩";
        PinyinHelper.addPinyinDict("F:\\code\\springBootSelection\\learn\\src\\main\\resources\\my_pinyin.dict");
        PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_MARK); // nǐ,hǎo,shì,jiè
        PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_NUMBER); // ni3,hao3,shi4,jie4
        String s = PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_MARK);// ni,hao,shi,jie
        System.out.println(s);
        String shortPinyin = PinyinHelper.getShortPinyin(str);// nhsj
        System.out.println(shortPinyin);
        //PinyinHelper.addPinyinDict("user.dict
    }

    public void test(){
        Parent p = new Parent();
        p.setName("p1");
        p.start();
    }


   static class Parent extends Thread {
        int i = 0;
        @Override
        public void run() {
            Child child = new Child();
            child.setName("child");
            child.start();
            try {
                child.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
            System.out.println(this.getName()+"i->"+i);
        }
    }


 static class Child extends Thread{

        @Override
        public void run() {

            try {
                System.out.println("child"+this.getClass());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
