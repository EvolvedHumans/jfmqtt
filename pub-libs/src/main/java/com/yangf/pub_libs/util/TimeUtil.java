package com.yangf.pub_libs.util;


import java.util.Calendar;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 01日 12时 18分
 * @Data： 时间工具类
 * @TechnicalPoints： 写当前时间的类型
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public final class TimeUtil {
    /**
     * 时间格式: yyyy-MM-dd HH:mm:ss 格式
     */
    public static String modernClock(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR); //年
        int month = calendar.get(Calendar.MONTH); //月
        int day = calendar.get(Calendar.DAY_OF_MONTH); //日
        int hour = calendar.get(Calendar.HOUR_OF_DAY);//24小时格式    HOUR(12小时格式)
        int minute = calendar.get(Calendar.MINUTE);//分
        int second = calendar.get(Calendar.SECOND);//秒

        return year+"年"+month+"月"+day+"日"+" "+hour+":"+minute+":"+second;
    }

    /**
     * 获取系统当前时间戳
     */
    public static long timestamp(){
        return System.currentTimeMillis();
    }

    /**
     * 字符串类型时间戳
     */
    public static String sTimestamp(){
        return String.valueOf(timestamp());
    }


}
