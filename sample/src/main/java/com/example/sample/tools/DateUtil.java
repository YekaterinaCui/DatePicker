package com.example.sample.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间格式器
 * Created by cj on 2015/12/18.
 */
public class DateUtil {

    public static Date getDate(String date) {
        return getDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date getDate(String date, String format) {

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String format(Date date, String format) {
        if (null == format) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }


    public static String convert(String date, String sourceFormat, String destFormat) {
        return format(getDate(date, sourceFormat), destFormat);
    }

    //时间戳转化为StingDate
    public static String timestampToDate(long timestamp) {
        return timestampToDate(timestamp, "yyyy-MM-dd HH:mm:ss");
    }

    //时间戳转化为StingDate
    public static String timestampToDate(long timestamp, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return simpleDateFormat.format(timestamp);
    }

    //Date或者String转化为时间戳
    public static long dateToTimestamp(String time) {
        return dateToTimestamp(time, "yyyy-MM-dd HH:mm:ss");
    }

    //Date或者String转化为时间戳
    public static long dateToTimestamp(String time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date != null ? date.getTime() : 0;
    }

    public static String formatConvert(String time, String format) {
        return timestampToDate(dateToTimestamp(time), format);
    }

    public static String formatConvert(String time) {
        return timestampToDate(dateToTimestamp(time));
    }

    //获取今日0点的时间戳
    public static long getZeroTimestamp() {
        //获取今天00：00时间戳的方法
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    //获取昨日的时间
    public static String getYesterdayTime() {
        long yesterdayTimeMills = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
        String yesterday = timestampToDate(yesterdayTimeMills);
        return yesterday;
    }
}
