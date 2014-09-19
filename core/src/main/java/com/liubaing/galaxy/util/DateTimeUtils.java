package com.liubaing.galaxy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间格式化及时区转换
 *
 * @author heshuai
 */
public class DateTimeUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateTimeUtils.class);

    private static final String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();

    public static SimpleDateFormat getDateFormat() {
        SimpleDateFormat dateFormat = threadLocal.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(FULL_DATE_FORMAT);
            threadLocal.set(dateFormat);
        }
        return dateFormat;
    }

    /**
     * 方法描述：字符串转时间
     *
     * @version: 2012-3-13 下午05:59:18
     */
    public static Date StrToDate(String dateStr) {
        Date date = null;
        try {
            date = getDateFormat().parse(dateStr);
        } catch (ParseException e) {
            LOGGER.error("字符串格式化为时间出现异常，时间[" + dateStr + "]", e);
        }
        return date;
    }

    /**
     * 方法描述：时间转字符串
     *
     * @version: 2012-3-13 下午06:17:18
     */
    public static String DateToStr(Date date) {
        String dateStr = null;
        try {
            dateStr = getDateFormat().format(date);
        } catch (Exception e) {
            LOGGER.error("时间格式化为字符串出现异常，时间[" + dateStr + "]", e);
        }
        return dateStr;
    }

    /**
     * 方法描述：自定义格式化时间
     *
     * @version: 2012-4-13 上午11:13:42
     */
    public static String DateToStr(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateStr = null;
        try {
            dateStr = formatter.format(date);
        } catch (Exception e) {
            LOGGER.error("时间格式化为字符串出现异常，时间[" + dateStr + "]", e);
        }
        return dateStr;
    }

    private static final String GMT_ID = "GMT";

    /**
     * UTC时间
     */
    public static Date toUTC() {
        return toDate(GMT_ID, true);
    }

    /**
     * 转为UTC时间
     */
    public static Date toUTC(String sourceTimeZone, Date sourceDate) {
        return toDate(sourceTimeZone, sourceDate, GMT_ID, false);
    }

    /**
     * UTC时间转为给定时区的时间
     */
    public static Date fromUTC(String targetTimeZone, Date sourceDate) {
        return toDate(GMT_ID, sourceDate, targetTimeZone, false);
    }

    /**
     * 获取指定时区当前时间
     */
    public static Date toDate(String targetTimeZone, boolean onTheHour) {
        TimeZone defaultTimeZone = TimeZone.getDefault();
        Date sourceDate = Calendar.getInstance(defaultTimeZone).getTime();
        return toDate(defaultTimeZone.getID(), sourceDate, targetTimeZone, onTheHour);
    }

    /**
     * 暂不做参数校验
     *
     * @param sourceTimeZone 源时区
     * @param sourceDate     源时间
     * @param targetTimeZone 目标时区
     * @param onTheHour      整点
     * @return 目标时间
     */
    private static Date toDate(String sourceTimeZone, Date sourceDate, String targetTimeZone, boolean onTheHour) {
        TimeZone _sourceTimeZone = TimeZone.getTimeZone(sourceTimeZone);
        TimeZone _targetTimeZone = TimeZone.getTimeZone(targetTimeZone);

        Calendar _targetCalendar = Calendar.getInstance(_targetTimeZone);

        long utc = System.currentTimeMillis();
        //原始偏移
        long targetTime = sourceDate.getTime() - _sourceTimeZone.getOffset(utc) + _targetTimeZone.getOffset(utc);
        _targetCalendar.setTimeInMillis(targetTime);

        if (onTheHour) {
            _targetCalendar.set(Calendar.MINUTE, 0);
            _targetCalendar.set(Calendar.SECOND, 0);
        }
        return _targetCalendar.getTime();
    }

}
