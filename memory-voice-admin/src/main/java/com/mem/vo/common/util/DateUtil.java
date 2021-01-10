package com.mem.vo.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {

    /*
     * 将字符串转换成日期
     */
    private static String[] datePatterns = {"yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"};

    private static String dateFormatString = "yyyy-MM-dd";
    private static String dateFormatLongString = "yyyy-MM-dd HH:mm:ss";

    private static String dateFormatLongString1 = "yyyyMMddHHmm";

    private static final String beginDateSuffix = " 00:00:00";
    private static final String endDateSuffix = " 23:59:59";

    public static Date parseDatetime(String date) {
        if (StringUtils.isNotBlank(date)) {
            try {
                return DateUtils.parseDate(date, datePatterns);
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static Date parseDate(String date, String pattern) {
        if (StringUtils.isNotBlank(date)) {
            try {
                return DateUtils.parseDate(date, new String[]{pattern});
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static String formatDatetimeFull(Date date) {
        return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss.SSS");
    }

    public static String formatDatetime(Date date) {
        return DateFormatUtils.format(date, "yyyy-MM-dd");
    }

    public static String formatDateStr(Date date) {
        return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 格式化日期  yyyyMMdd  20170202
     */
    public static String formatDateShort(Date date) {
        return DateFormatUtils.format(date, "yyyyMMdd");
    }

    /**
     * 获取当月第一天
     */
    public static Date getFirstDayOfMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatString);
        Calendar firstDate = Calendar.getInstance();
        firstDate.set(Calendar.DATE, 1);// 设为当前月的1号
        String firstDateString = sdf.format(firstDate.getTime());

        return parseDate(firstDateString, dateFormatString);
    }

    /**
     * 获取当前日期
     */
    public static Date getToday() {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatString);
        Calendar today = Calendar.getInstance();
        String todayString = sdf.format(today.getTime());

        return parseDate(todayString, dateFormatString);
    }


    /**
     * 获取年月字符串
     *
     * @return 格式 yyyy-MM
     */
    public static String getYearMonthStr(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String year = c.get(Calendar.YEAR) + "";
        String month = c.get(Calendar.MONTH) + 1 + "";
        if (month.length() == 1) {
            month = "0" + month;
        }
        return year + "-" + month;
    }

    /**
     * 获取当前时间yyyy-MM-dd hh:mm:ss
     */
    public static String getNowStrLong() {
        Date date = new Date();
        return DateFormatUtils.format(date, dateFormatLongString);
    }

    /**
     * 获取当前时间yyyy-MM-dd
     */
    public static String getNowStr() {
        Date date = new Date();
        return DateFormatUtils.format(date, dateFormatString);
    }

    /**
     * 获取当前时间yyyyMMddHHmm
     */
    public static String getNowStrFile() {
        Date date = new Date();
        return DateFormatUtils.format(date, dateFormatLongString1);
    }

    /**
     * 返回两个时间差值的毫秒数
     */
    public static Long getdiffTimeMs(Date d1, Date d2) {
        Long diff = d1.getTime() - d2.getTime();
        return Math.abs(diff);
    }

    /**
     * 两个时间大小比较
     * if date1 晚于 date2 返回true
     */
    public static boolean datecompare(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) > 0;
    }

    /**
     * 两个时间大小比较
     * if date1 早于 date2 返回true
     */
    public static boolean datebefore(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) < 0;
    }

    /**
     * 两个时间大小比较
     * if date1 晚于 date2 返回true
     */
    public static boolean dateafter(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) > 0;
    }


    /**
     * 组装开始时间
     */
    public static Date getBeginDate(String datestr) {
        if (datestr == null) {
            return null;
        }
        String dd = datestr + beginDateSuffix;
        return parseDate(dd, dateFormatLongString);
    }

    /**
     * 组装结束时间
     * 改造：获取第二天，Date 不像时间戳能存小时和分钟
     */
    public static Date getEndDate(String datestr) {
        if (StringUtils.isBlank(datestr)) {
            return null;
        }
        return getNextDay(parseDate(datestr, dateFormatString));
    }

    /**
     * 获取当前日期的下一天
     */
    public static Date getNextDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        return cal.getTime();
    }

    /**
     * 获取当前日期的下一月
     */
    public static Date getNextMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取月初第一天时间
     *
     * @param month yyyyMM
     * @return yyyy-MM-01 00:00:00
     */
    public static String getMonthFirstStr(String month) {
        if (StringUtils.isBlank(month)) {
            return month;
        }
        Date date = parseDate(month, "yyyyMM");
        Calendar firstDate = Calendar.getInstance();
        firstDate.setTime(date);
        firstDate.set(Calendar.DATE, 1);// 设为当前月的1号
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatString);
        String firstDateString = sdf.format(firstDate.getTime());
        return firstDateString + beginDateSuffix;
    }

    /**
     * 获取当月最后一天时间
     *
     * @param month yyyyMM
     * @return yyyy-MM-dd 23:59:59
     */
    public static String getMonthLastStr(String month) {
        if (StringUtils.isBlank(month)) {
            return month;
        }
        Date date = parseDate(month, "yyyyMM");
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatString);
        String lasetDateString = sdf.format(cale.getTime());
        return lasetDateString + endDateSuffix;
    }

    /**
     * 获取utc时间
     */
    public static Date getUtcTime(Date date) {
        long localTimeInMillis = date.getTime();
        /** long时间转换成Calendar */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(localTimeInMillis);
        /** 取得时间偏移量 */
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        /** 取得夏令时差 */
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        /** 从本地时间里扣除这些差量，即可以取得UTC时间*/
        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        /** 取得的时间就是UTC标准时间 */
        Date utcDate = new Date(calendar.getTimeInMillis());
        return utcDate;
    }

    public static boolean judgmentDate(String date1, String date2) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = sdf.parse(date1);
        Date end = sdf.parse(date2);
        long cha = end.getTime() - start.getTime();
        if (cha < 0L) {
            return false;
        }
        double result = cha * 1.0D / 3600000.0D;
        if (result <= 12.0D) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(getMonthFirstStr("20174"));
        System.out.println(getMonthLastStr("201704"));
    }
}
