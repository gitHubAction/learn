package finalTest;

/**
 * @Author: zhangsh
 * @Date: 2019/12/18 10:24
 * @Version 1.0
 * Description
 */
public class FinalTest {
    final int count = 1;
    public int updateCount() {
        return count;
    }
    public final int sum() {
        int number = count+10;
        return number;
    }
}
