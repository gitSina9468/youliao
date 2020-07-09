package com.seahorse.youliao.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：时间操作定义类
 *
 * @version 1.0
 * @author:
 * @date：
 */
public class DateUtils {


    public enum PatternEnum {

        //g  global全称，cn中国
        // 默认的年月日时分秒格式
        G_DATE_TIME_DEFAULT("yyyy-MM-dd HH:mm:ss"),
        // 中文格式
        G_DATE_TIME_FOR_CN("yyyy年MM月dd日 HH时mm分ss秒"),
        // 一般的天格式
        DATE_FOR_DAY_DEFAULT("yyyy-MM-dd"),
        DATE_FOR_MONTH_DEFAULT("yyyy-MM"),
        DATE_FOR_DAY("yyMMdd"),
        DATE_FOR_MONTH("yyyyMM"),
        DATE_FOR_YEARDAY("yyyyMMdd"),

        DATE_FOR_HOUR_MILLIONS("yyyyMMddHHmmss");


        private PatternEnum(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    // 用于存放不同模板的日期
    private static final ThreadLocal<Map<String, SimpleDateFormat>> LOCAL = new ThreadLocal<Map<String, SimpleDateFormat>>() {
        @Override
        protected Map<String, SimpleDateFormat> initialValue() {
            return new HashMap<>();
        }
    };

    /**
     * 返回一个SimpleDateFormat,每个线程只会new一次pattern对应的sdf
     *
     * @param pattern
     * @return
     */
    private static SimpleDateFormat getSdf(String pattern) {
        Map<String, SimpleDateFormat> map = LOCAL.get();
        SimpleDateFormat sdf = map.get(pattern);
        if (sdf == null) {
            sdf = new SimpleDateFormat(pattern);
            map.put(pattern, sdf);
        }
        return sdf;
    }


    /**
     * 获取当前日期
     *
     * @param pattern
     * @return
     */
    public static String getNow(PatternEnum pattern) {
        SimpleDateFormat sdf = getSdf(pattern.getName());
        return sdf.format(new Date());
    }

    /**
     * 获取过去一天的日期
     *
     * @return
     */
    public static Date getPastOneDay() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, -1);
        return c.getTime();
    }

    /**
     * 获取从指定日期起，半个小时以前的日期
     *
     * @return
     */
    public static Date getPastHalfHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int minute = calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.MINUTE, minute - 30);
        return calendar.getTime();
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static long getDistanceTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * string转化为指定模板的date
     *
     * @param str
     * @param pattern
     * @return
     */
    public static Date stringToDate(String str, PatternEnum pattern) {
        try {
            SimpleDateFormat sdf = getSdf(pattern.getName());
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * date类型转化为指定模板的string
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String dateToString(Date date, PatternEnum pattern) {
        SimpleDateFormat sdf = getSdf(pattern.getName());
        return sdf.format(date);
    }

    /**
     * date类型转化为指定模板的string
     *
     * @param date
     * @return
     */
    public static String dateToStringPass2Hour(Date date) {

        long time = 2*60*60*1000;
        Date afterDate = new Date(date.getTime() + time);
        return dateToString(afterDate, PatternEnum.DATE_FOR_HOUR_MILLIONS);
    }

    /**
     * 获取某个date的年份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取某个date的月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取某个date的day
     *
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 判断字符串是否是指定的格式
     *
     * @param date
     * @param pattern
     * @return
     */
    public static boolean isDatePattern(String date, PatternEnum pattern) {
        SimpleDateFormat sdf = getSdf(pattern.getName());
        try {
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取两个时间的时间差
     *
     * @param before
     * @param after
     * @param flag   0秒，1分，2时，3天
     * @return
     */
    public static int getDifferentNum(Date before, Date after, int flag) {
        if (before == null || after == null) {
            return 0;
        }
        long timeInterval = after.getTime() - before.getTime();
        switch (flag) {
            case 0:
                //秒
                return (int) (timeInterval / 1000);
            case 1:
                //分
                return (int) (timeInterval / (60 * 1000));
            case 2:
                //时
                return (int) timeInterval / (60 * 60 * 1000);
            case 3:
                //天
                return (int) timeInterval / (60 * 60 * 1000 * 24);
            default:
                return 0;
        }
    }


}