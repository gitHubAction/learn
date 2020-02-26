import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhangsh
 * @Date: 2019/10/8 9:47
 * @Version 1.0
 * Description
 *  finally块的语句在try或catch中的return语句执行之后返回之前执行
 *  且finally里的修改语句可能影响也可能不影响try或catch中 return已经确定的返回值，
 *  若finally里也有return语句则覆盖try或catch中的return语句直接返回。
 */
public class FinallyTest {

    public static int test4() {
        int b = 20;

        try {
            System.out.println("try block");

            b = b / 0;

            return b += 80;
        } catch (Exception e) {

            b += 15;
            System.out.println("catch block");
        } finally {

            System.out.println("finally block");

            if (b > 25) {
                System.out.println("b>25, b = " + b);
            }

            b += 50;
        }

        return b;
    }


    public static int test5() {
        int b = 20;

        try {
            System.out.println("try block");

            b = b /0;

            return b += 80;
        } catch (Exception e) {

            System.out.println("catch block");
            return b += 15;
        } finally {

            System.out.println("finally block");

            if (b > 25) {
                System.out.println("b>25, b = " + b);
            }

            b += 50;
//            return b;
        }

        //return b;
    }



    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("KEY", "INIT");

        try {
            map.put("KEY", "TRY");
            return map;
        }
        catch (Exception e) {
            map.put("KEY", "CATCH");
        }
        finally {
            map.put("KEY", "FINALLY");
            map = null;
        }

        return map;
    }



    public static void main(String[] args) throws Exception{
        /*System.out.println(test5());
        System.out.println("<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(test4());
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(getMap().get("KEY"));*/

        System.out.println("Angel 王橙溪".getBytes("utf-8"));
    }




}
