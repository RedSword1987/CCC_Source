package com.ccc.dreamtag.tag.function;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author RedSword(416448382@qq.com)
 * @date 2013-11-10 22:21:27
 */
public class DateUtil {
    public static final SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String convertLongToString(Long time, String dateFormat) {
        if (time == null) {
            return "";
        }
        try {
            SimpleDateFormat simpledateformatL = new SimpleDateFormat(dateFormat);
            Date date = new Date();
            date.setTime(time * 1000);
            return simpledateformatL.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String convertLongToString(String time, String dateFormat) {
        if (time == null) {
            return "";
        }
        try {
            SimpleDateFormat simpledateformatL = new SimpleDateFormat(dateFormat);
            Date date = new Date();
            date.setTime(Long.valueOf(time) * 1000);
            return simpledateformatL.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Long convertStringToLong(String time, String dateFormat) {
        try {
            SimpleDateFormat simpledateformatL = new SimpleDateFormat(dateFormat);
            Date date = simpledateformatL.parse(time);
            return date.getTime() / 1000;
        } catch (Exception e) {
            return Long.valueOf(0);
        }
    }

    public static Long convertDateToLong(Date date) {
        if (date == null) {
            return Long.valueOf(0);
        }
        return date.getTime() / 1000;
    }

    /**
     * 获取今天的最早时间
     * 
     * @return
     */
    public static long getTimeLong_TodayBegin() {
        return convertStringToLong(getTimeString_Now("yyyy-MM-dd") + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * get today last second time.
     * 
     * @return
     */
    public static long getTimeLong_TodayEnd() {
        return convertStringToLong(getTimeString_Now("yyyy-MM-dd") + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * get now time
     * 
     * @return
     */
    public static long getTimeLong_Now() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * get now time String
     * 
     * @return
     */
    public static String getTimeString_Now(String dateFormat) {
        try {
            SimpleDateFormat simpledateformatL = new SimpleDateFormat(dateFormat);
            Date date = new Date();
            return simpledateformatL.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get now time String
     * 
     * @return
     */
    public static String getTimeString_Now() {
        try {
            SimpleDateFormat simpledateformatL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            return simpledateformatL.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get time move
     * 
     * @param time
     * @param offSet
     * @param dateFormat
     * @return
     */
    public static String getTimeOffSet(long time, long offSet, String dateFormat) {
        return convertLongToString(time + offSet, dateFormat);
    }

    /**
     * get time move
     * 
     * @param time
     * @param offSet
     * @param dateFormat
     * @return
     */
    public static List<String> getDayFormetBetween(long begin, long end, String dateFormat) {
        List<String> arrList = new ArrayList<String>();
        long index = begin;
        while (index <= end) {
            String dayString = convertLongToString(index, dateFormat);
            if (convertStringToLong(dayString, dateFormat) >= begin) {
                arrList.add(dayString);
            }
            index += 24 * 60 * 60;

        }

        return arrList;
    }

    /**
     * get week day,1-7
     * 
     * @return
     */
    public static int getDayOfWeek(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time * 1000);
        int re = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return re > 0 ? re : 7;
    }

}
