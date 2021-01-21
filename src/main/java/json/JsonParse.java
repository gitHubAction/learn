package json;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateRange;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * @author zhangshihao01
 * @version 1.0
 * @Description
 * @Date 2020/12/1 15:28
 */
public class JsonParse {
    public static void main(String[] args) {
        List<String> b = new ArrayList<>();
        b.add("2");
        b.add("1");
        b.stream().filter(t->!"2".equals(t)).forEach((t1->{
            System.out.println(t1);
        }));




        System.out.println(StringUtils.isBlank(" "));

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime date = LocalDateTime.now();
        String lastDay = date.with(TemporalAdjusters.lastDayOfMonth()).format(fmt) +" 23:59:59";
        String firstDay = date.minusMonths(11).with(TemporalAdjusters.firstDayOfMonth()).format(fmt) + " 00:00:00";
        System.out.println(lastDay);
        System.out.println(firstDay);
        LocalDate today = LocalDate.now();
        for (long i = 11; i >= 0; i--) {
            LocalDate localDate = today.minusMonths(i);
            String month = localDate.toString().substring(0, 7);
            System.out.println(month);
        }


        DateRange range = DateUtil.range(DateUtil.offsetMonth(DateUtil.endOfMonth(new Date()), -12), new Date(), DateField.MONTH);
        while (range.hasNext()){
            System.out.println(range.next());
        }


        System.out.println( UUID.randomUUID().toString());

        JSONObject json = new JSONObject();
        List<String> pheseList = new ArrayList<>();
        pheseList.add("one");
        pheseList.add("two");
        pheseList.add("three");
        pheseList.add("four");
        json.put("phaseList",pheseList);
        json.put("channel","channel");
        json.put("hasOwner",true);
        TestDto testDto = JSON.parseObject(json.toJSONString(), TestDto.class);
        System.out.println(testDto);

        System.out.println(1/70);

        List<String> a = new ArrayList<>();
        a.add("456512");
        a.add("65412");
        a.add("64512");
        a.add("55412");
        a.add("4312");
        a.add("1243532s");
        a.add("1243w2");
        a.add("12123");
        a.add("1232");
        a.add("1as21");
        a.add("122asd");
        a.add("12sa3");
        int totalSize = a.size();
        int pageSize = 8;
        int totalPage = totalSize % pageSize == 0 ? (totalSize / pageSize) : (totalSize / pageSize + 1);

        IntStream.range(0,totalPage).forEach(index->{
                List<String> strings = a.subList(index*pageSize, ((index+1) * pageSize) > totalSize ? totalSize : ((index+1) * pageSize));
                System.out.println(strings);
        });

    }
}
