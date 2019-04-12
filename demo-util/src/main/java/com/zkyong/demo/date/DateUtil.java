package com.zkyong.demo.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.zkyong.demo.exception.DateFormatException;

/**
 * 时间格式化工具
 * 
 * @author zkyong
 * @version v 0.1 
 * @date 2019年04月12日 15:08:00
 */
public class DateUtil {
    /** 时间格式: 年-月-日 */
    static final String DATE_FORMAT_YMD      = "yyyy-MM-dd";
    /** 时间格式: 年-月-日 时:分:秒*/
    static final String DATE_FORMAT_YMDHMS   = "yyyy-MM-dd HH:mm:ss";
    /** 时间格式: 年-月-日 时:分:秒.毫秒*/
    static final String DATE_FORMAT_YMDHMSS  = "yyyy-MM-dd HH:mm:ss.sss";
    /** 时间格式: 年-月-日 时:分:秒.毫秒 时区*/
    static final String DATE_FORMAT_YMDHMSSZ = "yyyy-MM-dd HH:mm:ss.sss z";
    
    /** 时间格式: 年月日时分秒*/
    static final String DATE_FORMAT_YMDHMS_UNSIGNED   = "yyyyMMddHHmmss";
    
    /**
     * 将Date按指定格式转换为String
     * 
     * @param date 时间
     * @param format 时间格式
     * @return String 转换后的时间字符串
     */
    public static String date2String(Date date, String format) {
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.format(date);
        return null == date ? null : fmt.format(date);
    }

    /**
     * 将String格式的时间按指定格式转换为Date
     * 
     * @param date 时间字符串
     * @param format 时间格式
     * @return Date 转换后的时间字符串
     * @throws DateFormatException 
     */
    public static Date string2Date(String date, String format) throws DateFormatException {
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        try {
            return null == date ? null : fmt.parse(date);
        } catch (ParseException e) {
            System.out.println(e);
            new DateFormatException("时间格式错误");
        }
        return null;
    }

    public static String date2StringYMD(Date date) {
        return date2String(date, DATE_FORMAT_YMD);
    }

    public static String date2StringYMDHMS(Date date) {
        return date2String(date, DATE_FORMAT_YMDHMS);
    }

    public static String date2StringYMDHMSS(Date date) {
        return date2String(date, DATE_FORMAT_YMDHMSS);
    }

    public static String date2StringYMDHMSSZ(Date date) {
        return date2String(date, DATE_FORMAT_YMDHMSSZ);
    }

    public static String date2StringYMDHMSUnsigned(Date date) {
        return date2String(date, DATE_FORMAT_YMDHMS_UNSIGNED);
    }
    
    public static Date string2DateYMD(String date) throws DateFormatException {
        return string2Date(date, DATE_FORMAT_YMD);
    }

    public static Date string2DateYMDHMS(String date) throws DateFormatException {
        return string2Date(date, DATE_FORMAT_YMDHMS);
    }

    public static Date string2DateYMDHMSS(String date) throws DateFormatException {
        return string2Date(date, DATE_FORMAT_YMDHMSS);
    }

    public static Date string2DateYMDHMSSZ(String date) throws DateFormatException {
        return string2Date(date, DATE_FORMAT_YMDHMSSZ);
    }

    public static Date string2DateYMDHMSUnsigned(String date) throws DateFormatException {
        return string2Date(date, DATE_FORMAT_YMDHMS_UNSIGNED);
    }
    
    /**
     * 获取两个时间间隔天数
     * 
     * @param fromDate 原日期 
     * @param toDate 目标日期
     * @return int 两个日期之间的间隔天数
     */
    public static int getDateInterval(Date fromDate, Date toDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long from;
        long to;
        try {
            from = sdf.parse(sdf.format(fromDate)).getTime();
            to = sdf.parse(sdf.format(toDate)).getTime();
            return (int) ((to - from) / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
            new DateFormatException("计算时间间隔异常");
        }
        return 0;
    }
}
