package com.zhide.app.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hasee on 2017/12/25.
 */

public class DateUtils {

    public static final String DATE_FORMAT_YEAR = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_FORMAT_YEAR2 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_DAY = "yyyy/MM/dd";
    public static final String DATE_FORMAT_DAY2 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_MONTH = "yyyy/MM";
    public static final String DATE_FORMAT_MONTH2 = "yyyy-MM";



    public static String dateToStamp(long time)
    {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_YEAR2);
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;

    }


    public static String getFormatSystemTime(long time) {
        if (time == 0) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_YEAR, Locale.CHINA);
        Date date = new Date(time);
        return format.format(date);
    }

    public static String getFormatSystemTime(long time, String formatStr) {
        if (time == 0) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(formatStr, Locale.CHINA);
        Date date = new Date(time);
        return format.format(date);
    }

    public static String getFormatDay(long time) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_DAY2, Locale.CHINA);
        Date date = new Date(time);
        String timeFormat = format.format(date);
        if (timeFormat == null) {
            return "1";
        }
        String[] split = timeFormat.split("-");
        if (split.length > 2) {
            return split[2];
        }
        return "1";
    }

    /**
     * 获取当前年份往后五年的时间
     *
     * @return
     */
    public static long getNextFiveYear() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, +5);
        return c.getTimeInMillis();
    }

    public static boolean getIsSameDate(long time1, long tim2) {

        String formatTime1 = getFormatSystemTime(time1, DATE_FORMAT_DAY2);//

        String formatTime2 = getFormatSystemTime(tim2, DATE_FORMAT_DAY2);
        if (formatTime1.equals(formatTime2)) {
            return true;
        }
        return false;
    }

    public static boolean getIsSameMonth(long time1, long tim2) {

        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(time1);
        int month1 = cal1.get(Calendar.MONTH) + 1; // 获取当前月份

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(tim2);
        int month2 = cal2.get(Calendar.MONTH) + 1; // 获取当前月份
        if (month1 == month2) {
            return true;
        }
        return false;
    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_DAY, Locale.CHINA);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getFormatDayTime(long time) {
        if (time == 0) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_DAY, Locale.CHINA);
        Date date = new Date(time);
        return format.format(date);
    }

    public static String getFormatDayTime(long time, String formatStr) {
        if (time == 0) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(formatStr, Locale.CHINA);
        Date date = new Date(time);
        return format.format(date);
    }

    public static long getSystemTime() {
        return System.currentTimeMillis();
    }

    public static int getCurrentYear() {
        Calendar c = Calendar.getInstance();//
        int year = c.get(Calendar.YEAR); // 获取当前年份
        if (year < 2017) { // 做个系统时间异常保护
            return 2017;
        }
        return year;
    }

    public static int getCurrentMonth() {
        Calendar c = Calendar.getInstance();//
        int month = c.get(Calendar.MONTH) + 1; // 获取当前月份
        Log.d("xyc", "getCurrentMonth: month=" + month);
        if (month < 1 || month > 12) { // 做个系统时间异常保护
            return 1;
        }

        return month;
    }

    public static int getYearByTime(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.get(Calendar.YEAR);
    }

    public static int getMonthByTime(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static String getMonthDouble(int month) {
        String months;
        if (month < 10) {
            months = "0" + month;
        } else {
            months = String.valueOf(month);
        }
        return months;
    }

    public static long getLongDate(int year, int month, int day) {
        String time = year + "/" + month + "/" + day;
        Date date = stringToDate(time);
        if (date == null) {
            return 0;
        }
        long time1 = date.getTime();
        Log.d("xyc", "getLongDate: time1=" + time1);
        return date.getTime();
    }

    public static long getCurrentDayBt31Day(long currentTime, int days) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currentTime);
        Calendar afterDay = getAfterDay(cal, days);
        return afterDay.getTimeInMillis();
    }

    public static long getCurrentDayNextDay(int year, int month, int day) {
        //后一天
        Calendar cl = setCalendar(year, month, day);
        Calendar afterDay = getAfterDay(cl, 1);

        return afterDay.getTimeInMillis();
    }

    /**
     * minutes to Hours
     *
     * @return
     */
    public static String getMinuteToHour(int minutes) {
        int hour = minutes / 60;
        int minute = minutes - hour * 60;
        return hour + "小时 " + minute + "分钟";
    }

    /**
     * 获取当前时间的后一天时间
     *
     * @param cl
     * @return
     */
    public static Calendar getAfterDay(Calendar cl, int days) {
        //使用roll方法进行回滚到后一天的时间
        //cl.roll(Calendar.DATE, 1);
        //使用set方法直接设置时间值
        int day = cl.get(Calendar.DATE);
        cl.set(Calendar.DATE, day + days);
        return cl;
    }

    /**
     * 当前时间的前一天
     *
     * @param currentTime
     * @return
     */
    public static long getCurrentBeforeDay(long currentTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currentTime);
        cal.add(Calendar.DATE, -1);
        return cal.getTimeInMillis();
    }

    /**
     * 设置前一天时间
     *
     * @param year
     * @param month
     * @param date
     * @return
     */
    public static Calendar setCalendar(int year, int month, int date) {
        Calendar cl = Calendar.getInstance();
        cl.set(year, month - 1, date);
        return cl;
    }

    /**
     * 相隔多少天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static float daysBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);

        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);

        long time2 = cal.getTimeInMillis();
        return ((float) time2 - (float) time1) / ((float) 1000 * (float) 3600);
    }

    /**
     * 相隔多少天
     *
     * @return
     */
    public static float daysBetween(long time1, long time2) {
        return ((float) time2 - (float) time1) / ((float) 1000 * (float) 3600 * 24);
    }

    public static long getNextDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        return cal.getTimeInMillis();
    }

    public static long getYesTodayTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTimeInMillis();
    }

    public static long getBeforeYesTodayTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        return cal.getTimeInMillis();
    }

    /**
     * 通过年份和月份 得到当月的日子
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDays(int year, int month) {
        month++;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return -1;
        }
    }

    /**
     * 取得当月天数
     */
    public static int getCurrentMonthDays() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    public static int getMonthTotalDays(int year, int month) {
        int actualMaximum = 30;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM");
        try {
            calendar.setTime(simpleDate.parse(year + "/" + month));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return actualMaximum;
    }

    /**
     * 取得当月天数
     */
    public static int getCurrentMonthDays2(String months) {
        int actualMaximum = 30;
        Calendar a = Calendar.getInstance();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM");
        try {
            a.setTime(simpleDate.parse(months));
            actualMaximum = a.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return actualMaximum;
    }

    /**
     * 返回当前月份1号位于周几
     *
     * @param year  年份
     * @param month 月份，传入系统获取的，不需要正常的
     * @return 日：1		一：2		二：3		三：4		四：5		五：6		六：7
     */
    public static int getFirstDayWeek(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        Log.d("DateView", "DateView:First:" + calendar.getFirstDayOfWeek());
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 根据列明获取周
     *
     * @param column
     * @return
     */
    public static String getWeekName(int column) {
        switch (column) {
            case 0:
                return "周日";
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            case 6:
                return "周六";
            default:
                return "";
        }
    }

    public static long getTimeByYearAndMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getTimeInMillis();
    }

    public static long getTimeByDay(String selectTime) {
        if (selectTime==null||selectTime.isEmpty()) {
            return 0;
        }
        Date date = stringToDate(selectTime);
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }

    public static int[] getHourMinuteByDate(Date date) {
        int[] times = new int[2];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        times[0] = hour;
        times[1] = minute;
        return times;
    }

    /**
     * 将时间戳转化为时间串
     */
    public static String getTimeID() {
        long mill = System.currentTimeMillis();
        Date date = new Date(mill);
        String strs = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            strs = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }
}
