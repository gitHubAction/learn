package a;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @Date 2022/1/21 1:28
 */
public class Valid  implements IValid{

    /**
     * 验证一般情况下使用interrupt() 中断执行线程的例子
     */
    public static void threadStopTest() {
        Thread testThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //第一种情况：检测线程是否收到中断信令，收到则返回true，并清除当前的线程状态，重新变更为未中断；
                while (!Thread.interrupted()) {
                    System.out.println("线程内代码执行");
                }
                //此时再检测当前该线程是否收到外界中断信令，得到结果为false，因为使用Thread.interrupted()，在收到中断信令后，会清除当前的线程状态，所以此处进行判断时则返回结果为false，线程状态未收到中断信令
                System.out.println(Thread.currentThread().isInterrupted());
                System.out.println(Thread.currentThread().isInterrupted());
                /*//第二种情况：检测线程是否收到中断信令，收到则返回true，只是检测当前是否收到中断信令，不清除当前的线程状态，
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("----线程内代码执行");
                }
                //此时检测当前该线程是否收到外界中断信令，true表示收到，此处获取结果为 true
                System.out.println("----线程内部监测线程状态："+Thread.currentThread().isInterrupted()); //true
                System.out.println(Thread.currentThread().isInterrupted()); //true*/
                //线程被中断后执行该代码块，进行回收工作
                System.out.println("----线程收到外部中断通知，已进行线程内部回收，中断完成");
                /*while (true) {

                }*/
            }
        });
        testThread.start();
        try {
            Thread.sleep(5000);
            //等待5秒后 发出中断信号，通知testThread线程进行中断
            testThread.interrupt();
            //判断当前该线程是否中断完成
            boolean flag = true;
            int index = 0;
            Thread.sleep(1000);
            while (flag) {
                //获取指定线程是否收到中断信号，返回true表示线程已经收到中断信号，但线程正在运行，处理中;或者是已经收到了中断信令，但是选择了不中断继续执行；
                // 如果返回false则存在两种情况
                //1、是当前该线程已经执行完毕，完成中断；由于此时线程已经执行完成了，那么此处再获取该线程的信令时则返回为false，
                //2、该线程没有完成中断，但是该线程代码内部使用了Thread.interrupted() 清除了线程的信令状态，此时则也是返回结果为false，
                System.out.println("检测线程的中断信号：" + testThread.isInterrupted());
                //循环检测10秒钟，10秒后则跳出循环
                Thread.sleep(1000);
                index++;
                if (index == 10) {
                    //停止检测
                    flag = false;
                }
            }
            if (!testThread.isInterrupted()) {
                //TODO: testThread线程中断完成，则执行该代码块
                System.out.println("外部检测testThread中断完成");
            } else {
                //TODO: 否则，则执行另外代码块
                System.out.println("外部检测testThread中断失败");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

   volatile int a = 0;

    public static void main(String[] args) throws ParseException {
        Valid v = new Valid();
        System.out.println(v.test1());
//        threadStopTest();
//        System.out.println(1<<0);
//        System.out.println(1<<2);
//        System.out.println(1<<3);
//        System.out.println(1<<4);
//        SimpleTrigger build = TriggerBuilder.newTrigger()
//                .startAt(new Date())
//                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(45)).build();
//        System.out.println(build.toString());
//
//
//        Date start = DateUtil.parse("2023-02-13 15:18:00", DatePattern.NORM_DATETIME_FORMATTER).toJdkDate();
//        Date endDate = DateUtil.offsetHour(start, 1).toJdkDate();
//
//
//        List<Date> dates = new ArrayList<>();
//        CronTriggerImpl cron = new CronTriggerImpl();
//        cron.setCronExpression("0 0/3 * * * ?");//这里写要准备猜测的cron表达式
//        dates.addAll(TriggerUtils.computeFireTimesBetween(cron, null, start, endDate));//这个是重点，一行代码搞定~~
//        cron.setCronExpression("45 0/3 * * * ?");
//        dates.addAll(TriggerUtils.computeFireTimesBetween(cron, null, start, endDate));//这个是重点，一行代码搞定~~
//        cron.setCronExpression("30 1/3 * * * ?");
//        dates.addAll(TriggerUtils.computeFireTimesBetween(cron, null, start, endDate));//这个是重点，一行代码搞定~~
//        cron.setCronExpression("15 2/3 * * * ?");
//        dates.addAll(TriggerUtils.computeFireTimesBetween(cron, null, start, endDate));//这个是重点，一行代码搞定~~
//        System.out.println(dates.size());
//        Collections.sort(dates);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        for(int i =0;i < dates.size();i ++){
//            System.out.println(dateFormat.format(dates.get(i)));
//        }
    }

    private int test1() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                a++;
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                a++;
            }
        });
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return a;
    }

    private Map<String,Object> parameterMap = new ConcurrentHashMap<>(16);


    public Map<String,Object> getParameterMap(){
        return parameterMap;
    }

    public Object addInitParameter(String key, Object value) {
        return parameterMap.put(key, value);
    }
}
