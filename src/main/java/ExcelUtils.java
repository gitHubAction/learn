import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2021/9/15 11:16
 */
public class ExcelUtils {
    public static void main(String[] args) {

        ArrayList<String> strings = Lists.newArrayList("123", "456", "789");
        strings.remove("123");
        System.out.println(strings.stream().collect(Collectors.joining(",")));
//
//
//        ExcelReader zl = ExcelUtil.getReader(new File("C:\\Users\\zhangshihao01\\Downloads\\大连.xls"));
//
//        ExcelReader pm = ExcelUtil.getReader(new File("C:\\Users\\zhangshihao01\\Downloads\\大连2021-12月被叫.xlsx"));
//
//        List<List<Object>> readZl = zl.read(1);
//        List<String> callIdZl = readZl.stream().map(a -> a.get(0) + "").collect(Collectors.toList());
//        List<List<Object>> readPm = pm.read(1);
//        List<String> callIdPm = readPm.stream().map(a -> a.get(0) + "").collect(Collectors.toList());
//        List<String> notExist = callIdZl.stream().filter(a -> !callIdPm.contains(a)).collect(Collectors.toList());
//        notExist.stream().forEach(t->System.out.println(t));
////        ExcelWriter writerCD = ExcelUtil.getWriter(new File("C:\\Users\\zhangshihao01\\Desktop\\smp\\C5\\backup\\含更新时间C5塘鹅-成都-Y-小号平台.xlsx"));
//
////        writerCD.write(pmCDXList);
////        writerCD.flush();
//
//        System.out.println("hahah");
    }

    public static void pipeihaoma(){


        ExcelReader pmBJ = ExcelUtil.getReader(new File("C:\\Users\\zhangshihao01\\Desktop\\smp\\C5\\20211202退号\\01北京\\塘鹅-天润-北京-小号平台.xlsx"));
        pmBJ.setHeaderAlias(getHeaderAlias());

        ExcelReader c5Origin = ExcelUtil.getReader(new File("C:\\Users\\zhangshihao01\\Desktop\\smp\\C5\\20211202退号\\01北京\\C5塘鹅-北京-X-原始.xlsx"));
        c5Origin.setHeaderAlias(getHeaderAlias());


        List<Tel> pmCQXList = pmBJ.readAll(Tel.class);
        Map<String, List<Tel>> c5Map = c5Origin.readAll(Tel.class).stream()
                .collect(Collectors.groupingBy(Tel::getTel));
        // 会写更新时间
        List<Tel> collect = pmCQXList.stream().map(tel -> {
            List<Tel> tels = c5Map.get(tel.getTel());
            if (CollectionUtils.isEmpty(tels)) {
                System.out.println(tel.getTel() + "   未在底表中");
                return tel;
            }
            Tel temp = tels.get(0);
            tel.setAppId(temp.getAppId());
            tel.setMode(temp.getRemark());
            tel.setUpdateTime(temp.getUpdateTime());
            return tel;
        }).collect(Collectors.toList());

        System.out.println("===========================================================");
//        List<Tel> pmCDXList = pmCDX.readAll(Tel.class);
//        pmCDXList = pmCDXList.stream().map(tel->{
//            List<Tel> tels = c5Map.get(tel.getTel());
//            if(CollectionUtils.isEmpty(tels)){
//                System.out.println(tel.getTel()+ "   未在底表中");
//                return tel;
//            }
//            Tel temp = tels.get(0);
//            tel.setAppId(temp.getAppId());
//            tel.setMode(temp.getRemark());
//            tel.setUpdateTime(temp.getUpdateTime());
//            return tel;
//        }).collect(Collectors.toList());

        ExcelWriter writerCQ = ExcelUtil.getWriter(new File("C:\\Users\\zhangshihao01\\Desktop\\smp\\C5\\20211202退号\\01北京\\含更新时间小号平台北京.xlsx"));
        writerCQ.write(collect);
        writerCQ.flush();
    }


    private static Map<String, String> getHeaderAlias() {
        Map<String, String> header = new HashMap<>();
        header.put("目的码","tel");
        header.put("区号","areacode");
        header.put("已绑定量","num");
        header.put("状态更新时间","updateTime");
        header.put("备注","remark");
        header.put("企业ID","appId");
        return header;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Tel{
        private String tel;

        private String areacode;

        private String appId;

        private String mode;

        private Date updateTime;

        private int num;

        private String remark;
    }
}
