package cn.xiaomeng.datepicker.tools;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类名：DateUtil
 * 编辑时间：2018/4/8
 * 编辑人：崔婧
 * 简介：时间工具类
 */
public class DateUtil {


    public static Date getDate(String date) {
        if (validateDateTimeFormat(date)) {
            return getDate(date, "yyyy-MM-dd HH:mm:ss");
        } else if (validateDateFormat(date)) {
            return getDate(date, "yyyy-MM-dd");
        } else if (validateTimeFormat(date)) {
            return getDate(date, "HH:mm:ss");
        } else {
            return null;
        }
    }

    public static Date getDate(String dateStr, @NonNull String format) {

        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String format(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return formatter.format(date);
    }

    public static String format(Date date, @NonNull String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
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


    //日期的正则表达式
    public static boolean validateDateFormat(String date) {
        String regex = "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]|[0-9][1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    //日期时间的正则表达式
    public static boolean validateDateTimeFormat(String dateTime) {
        String regex = "(\\d{4})(?:\\-)?([0]\\d|[1][0-2])(?:\\-)?([0-2]\\d|[3][0-1])(?:\\s)?([0-1]\\d|[2][0-3])(?::)?([0-5]\\d)(?::)?([0-5]\\d)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dateTime);
        return matcher.matches();
    }

    //时间的正则表达式
    public static boolean validateTimeFormat(String time) {
        String regex = "^(([1-9])|([0-1][0-9])|([1-2][0-3])):([0-5][0-9]):([0-5][0-9])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }
}
