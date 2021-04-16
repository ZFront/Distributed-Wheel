package com.wheel.common.util;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @author: chenyf
 * @Date: 2018/1/5
 */
public class DateUtil {
    public static final String DATE_ONLY_REGEX = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";
    public static final String DATE_TIME_REGEX = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])(\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d)$";

    public static final DateTimeFormatter DATE_TIME_MILLS_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
    public static final DateTimeFormatter COMPACT_DATE_FORMATTER = DateTimeFormat.forPattern("yyyyMMdd");
    public static final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormat.forPattern("yyMMdd");
    public static final DateTimeFormatter COMPACT_DATE_TIME_MILLS_FORMATTER = DateTimeFormat.forPattern("yyyyMMddhhmmssSSS");

    /**
     * 比较 source 和 target 大小，如果 source > target 则返回1，如果 source = target 则返回0，如果 source < target 则返回-1
     *
     * @param source
     * @param target
     * @param withUnit Calendar.YEAR/MONTH/DATE/HOUR/MINUTE/SECOND/MILLISECOND
     * @return
     */
    public static int compare(Date source, Date target, int withUnit) {
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(source);

        Calendar otherDateCal = Calendar.getInstance();
        otherDateCal.setTime(target);
        switch (withUnit) {
            case Calendar.YEAR:
                dateCal.clear(Calendar.MONTH);
                otherDateCal.clear(Calendar.MONTH);
            case Calendar.MONTH:
                dateCal.set(Calendar.DATE, 1);
                otherDateCal.set(Calendar.DATE, 1);
            case Calendar.DATE:
                dateCal.set(Calendar.HOUR_OF_DAY, 0);
                otherDateCal.set(Calendar.HOUR_OF_DAY, 0);
            case Calendar.HOUR:
                dateCal.clear(Calendar.MINUTE);
                otherDateCal.clear(Calendar.MINUTE);
            case Calendar.MINUTE:
                dateCal.clear(Calendar.SECOND);
                otherDateCal.clear(Calendar.SECOND);
            case Calendar.SECOND:
                dateCal.clear(Calendar.MILLISECOND);
                otherDateCal.clear(Calendar.MILLISECOND);
            case Calendar.MILLISECOND:
                break;
            default:
                throw new IllegalArgumentException("withUnit 单位字段 " + withUnit + " 不合法！！");
        }
        return dateCal.compareTo(otherDateCal);
    }

    public static Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 增加一天，精确到秒
     *
     * @param timeStamp 时间戳，单位是秒
     * @param day
     * @return
     */
    public static int addDay(int timeStamp, int day) {
        String timestamp = String.valueOf(addDay(new Date(timeStamp * 1000L), day).getTime() / 1000);
        return Integer.valueOf(timestamp);
    }

    public static Date addMinute(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public static Date addSecond(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * 判断是否日期格式，如：2017-05-31
     *
     * @param str
     * @return
     */
    public static boolean isDateOnly(String str) {
        Matcher mat = Pattern.compile(DATE_ONLY_REGEX).matcher(str);
        return mat.matches();
    }

    /**
     * 判断是否是日期+时间格式，2017-05-31 15:24:31
     *
     * @return
     */
    public static boolean isDateTime(String str) {
        if (str == null) {
            return false;
        }
        Matcher mat = Pattern.compile(DATE_TIME_REGEX).matcher(str);
        return mat.matches();
    }


    public static String formatDateTime(Date date) {
        LocalDateTime dateTime = parseJodaDateTime(date);
        return dateTime.toString(DATE_TIME_FORMATTER);
    }

    public static String formatDateTimeMills(Date date) {
        LocalDateTime dateTime = parseJodaDateTime(date);
        return dateTime.toString(DATE_TIME_MILLS_FORMATTER);
    }

    public static String formatCompactDateTimeMills(Date date) {
        LocalDateTime dateTime = parseJodaDateTime(date);
        return dateTime.toString(COMPACT_DATE_TIME_MILLS_FORMATTER);
    }

    public static String formatDate(Date date) {
        LocalDateTime dateTime = parseJodaDateTime(date);
        return dateTime.toString(DATE_FORMATTER);
    }

    public static String formatShortDate(Date date) {
        LocalDateTime dateTime = parseJodaDateTime(date);
        return dateTime.toString(SHORT_DATE_FORMATTER);
    }

    public static String formatCompactDate(Date date) {
        LocalDateTime dateTime = parseJodaDateTime(date);
        return dateTime.toString(COMPACT_DATE_FORMATTER);
    }

    public static Date convertDate(Date date) {
        LocalDateTime dateTime = parseJodaDateTime(date);
        String dateStr = dateTime.toString(DATE_FORMATTER);
        return DATE_FORMATTER.parseDateTime(dateStr).toDate();
    }

    public static Date convertDateTime(Date date) {
        LocalDateTime dateTime = parseJodaDateTime(date);
        String dateStr = dateTime.toString(DATE_TIME_FORMATTER);
        return DATE_TIME_FORMATTER.parseDateTime(dateStr).toDate();
    }

    public static LocalDateTime parseJodaDateTime(Date date) {
        return new LocalDateTime(date);
    }

    public static LocalDateTime parseJodaDateTime(Long dateTime) {
        return new LocalDateTime(dateTime);
    }

    /**
     * 取得没有毫秒时间戳的日期
     *
     * @return
     */
    public static Date getDateWithoutMills(Date date) {
        LocalDateTime dateTime = parseJodaDateTime(date);
        return dateTime.withMillisOfSecond(0).toDate();
    }

    /**
     * 得到day的起始时间点
     *
     * @return
     */
    public static Date getDayStart(Date date) {
        LocalDateTime dateTime = parseJodaDateTime(date);
        return dateTime.withTime(0, 0, 0, 0).toDate();
    }

    /**
     * 得到day的起始时间点，精确到秒
     *
     * @return
     */
    public static int getDayStartSecond(Date date) {
        String timestamp = String.valueOf(getDayStart(date).getTime() / 1000);
        return Integer.valueOf(timestamp);
    }

    /**
     * 得到day的终止时间点.
     *currentTime
     * @param date
     * @return
     */
    public static Date getDayEnd(Date date) {
        LocalDateTime dateTime = parseJodaDateTime(date);
        return dateTime.withTime(23, 59, 59, 999).toDate();
    }

    public static long secondToMillSecond(int seconds){
        return seconds * 1000L; //此处把秒乘以1000转成毫秒时一定要是Long型的1000，否则会使时间变小
    }

    public static void main(String[] args) {
        Date date = DateUtil.getDayStart(new Date());
        Date date2 = DateUtil.getDayEnd(new Date());
        System.out.println(DateUtil.formatDateTime(date));
        System.out.println(DateUtil.formatDateTime(date2));
        System.out.println(getDayStartSecond(new Date()));
        System.out.println(formatDateTime(new Date()));
    }
}
