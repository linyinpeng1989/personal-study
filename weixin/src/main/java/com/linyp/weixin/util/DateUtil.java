package com.linyp.weixin.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * @author Yinpeng Lin
 * @date 2017/10/30
 * 时间工具类
 */
public class DateUtil {
    public static final DateTimeFormatter YMDHMS = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss");

    /**
     * 将日期毫秒数转换成指定格式的字符串
     * @param millis        毫秒数
     * @param formatter     转换器
     * @return
     */
    public static String formatLongToString(Long millis, DateTimeFormatter formatter) {
        return formatter.print(millis);
    }

    /**
     * 将日期转换成指定格式的字符串
     * @param date
     * @param formatter
     * @return
     */
    public static String formatDateToString(Date date, DateTimeFormatter formatter) {
        if (date == null) {
            return null;
        }
        return formatter.print(date.getTime());
    }

    public static Date formatStringToDate(String dateStr, DateTimeFormatter formatter) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        return formatter.parseDateTime(dateStr).toDate();
    }
}
