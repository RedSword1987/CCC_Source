package com.ccc.dreamtag.tag.function;

/**
 * @author RedSword
 * @date 2011-11-17 22:31:38
 * @version 2.0
 */
public class TagUtil {
    public static String date_convertLongToString(String time, String dateFormat) {
        return DateUtil.convertLongToString(time, dateFormat);
    }

    /**
     * get now time String
     * 
     * @return
     */
    public static String date_getTimeString_Now(String dateFormat) {
        return DateUtil.getTimeString_Now(dateFormat);
    }
}
