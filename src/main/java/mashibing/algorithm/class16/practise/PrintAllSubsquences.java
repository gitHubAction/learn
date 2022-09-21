package mashibing.algorithm.class16.practise;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2022/7/13 14:13
 */
public class PrintAllSubsquences {


    public List<String> print(String str){
        List<String> ans = new ArrayList<>();
        String sub = "";
        process(str, 0, ans, sub);
        return ans;
    }

    private void process(String str, int i, List<String> ans, String sub) {
        if(i == str.length()){
            ans.add(sub);
            return;
        }
        process(str, i+1, ans, sub);
        process(str, i+1, ans, sub + str.charAt(i));
    }
}
