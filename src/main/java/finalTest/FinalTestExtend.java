package finalTest;

import com.google.common.primitives.Ints;

import java.util.*;

/**
 * @Author: zhangsh transmission
 * @Date: 2019/12/18 10:24
 * @Version 1.0
 * Description
 */
public class FinalTestExtend extends FinalTest {
    int count = sum();    // 继承父类 FinalTest 中的 sum()方法

    private String name;

    private String desc;

    public FinalTestExtend(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }


    @Override
    public String toString() {
        return "FinalTestExtend{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    public static void main(String[] args) {

        /*Map<String,FinalTestExtend> map = new HashMap<>();

        FinalTestExtend fet = new FinalTestExtend("aa","bb");
        map.put(fet.desc,fet);
        fet = new FinalTestExtend("11","22");
        map.put(fet.desc,fet);




        System.out.println(fet.count);

        System.out.println(-1L ^ (-1L << 5));*/
        int i = 1;
        int j = i++;//j=1
        System.out.println(i);//i=2
        if(i == (++j) && (i++) == j){//j=2 i=3
            i+= j;
        }
        System.out.println("i="+i);

//        threeSum(new int[]{-1,0,1,2,-1,-4});

    }

    public static List<List<Integer>> threeSum(int[] nums) {
        if(nums == null || nums.length ==0)return null;
        List<Integer> resultList= Ints.asList(nums);
        Collections.sort(resultList);
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> list = null;
        for(int i = 0;i<resultList.size();i++){
            for(int j = i+1 ; j <resultList.size() ; j++){
                for(int k = j+1 ; k < resultList.size() ; k++){

                    if(nums[i] + nums[j] + nums[k] ==0) {
                        list = new ArrayList<>();
                        list.add(resultList.get(i));
                        list.add(resultList.get(j));
                        list.add(resultList.get(k));
                        result.add(list);
                    }
                }
            }
        }
        return result;
    }

}
