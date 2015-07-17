package com.liubaing.galaxy.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * 时间格式化及时区转换
 *
 * @author heshuai
 */
public class DateTimeUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateTimeUtils.class);

    public static final String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();

    public static SimpleDateFormat getDateFormat() {
        SimpleDateFormat dateFormat = threadLocal.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(DATE_FORMAT);
            threadLocal.set(dateFormat);
        }
        return dateFormat;
    }

    /**
     * 方法描述：字符串转时间
     */
    public static Date StrToDate(String dateStr) {
        Date date = null;
        if (StringUtils.isNotEmpty(dateStr)) {
            try {
                date = getDateFormat().parse(dateStr);
            } catch (Exception e) {
                LOGGER.error("字符串格式化为时间出现异常，时间[" + dateStr + "]", e);
            }
        }
        return date;
    }

    /**
     * 方法描述：时间转字符串
     */
    public static String DateToStr(Date date) {
        String dateStr = null;
        if (date != null) {
            try {
                dateStr = getDateFormat().format(date);
            } catch (Exception e) {
                LOGGER.error("时间格式化为字符串出现异常，时间[" + date + "]", e);
            }
        }
        return dateStr;
    }

    /**
     * 方法描述：自定义格式化时间
     */
    public static String DateToStr(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateStr = null;
        if (date != null) {
            try {
                dateStr = formatter.format(date);
            } catch (Exception e) {
                LOGGER.error("时间格式化为字符串出现异常，时间[" + date + "]", e);
            }
        }
        return dateStr;
    }

    public static Date StrToDate(String dateStr, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = null;
        format = StringUtils.isEmpty(format) ? FULL_DATE_FORMAT : format;
        if (StringUtils.isNotEmpty(dateStr)) {
            try {
                date = formatter.parse(dateStr);
            } catch (Exception e) {
                LOGGER.error("字符串格式化为时间出现异常，时间[" + dateStr + "]", e);
            }
        }
        return date;
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

    public enum TimePeriod {
        HOUR("h", "时"),
        DAY("d", "天"),
        WEEK("w", "周"),
        MONTH("m", "月"),
        QUARTER("q", "季"),
        YEAR("y", "年");

        public String key;
        public String value;

        TimePeriod(String key, String value) {
            this.key = key;
            this.value = value;
        }

        static TimePeriod get(String key) {
            for (TimePeriod period : TimePeriod.values()) {
                if (StringUtils.equalsIgnoreCase(period.key, key)) {
                    return period;
                }
            }
            return DAY;
        }
    }

    private static final DateTimeFormatter HOUR_FORMATTER = DateTimeFormat.forPattern("HH");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
    private static final DateTimeFormatter WEEK_FORMATTER = DateTimeFormat.forPattern("yyyy\'wk\'ww");
    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormat.forPattern("yyyy-MM");
    private static final DateTimeFormatter YEAR_FORMATTER = DateTimeFormat.forPattern("yyyy");

    /**
     * 获取时间轴
     *
     * @param start  开始时间
     * @param end    结束时间
     * @param period 时间周期
     * @return
     */
    public static List<String> timeline(String start, String end, String period) {
        List<String> timeline = Lists.newArrayList();
        DateTime startDateTime = new DateTime(DateTime.parse(start, DATE_FORMATTER));
        DateTime endDateTime = new DateTime(DateTime.parse(end, DATE_FORMATTER));
        TimePeriod timePeriod = TimePeriod.get(period);
        switch (timePeriod) {
            case HOUR:
                for (int size = Hours.hoursBetween(startDateTime, DateTime.now()).getHours() + 1, i = 0; i < size; i++) {
                    timeline.add(startDateTime.plusHours(i).toString(HOUR_FORMATTER));
                }
                break;
            case DAY:
                for (int size = Days.daysBetween(startDateTime, endDateTime).getDays() + 1, i = 0; i < size; i++) {
                    timeline.add(startDateTime.plusDays(i).toString(DATE_FORMATTER));
                }
                break;
            case WEEK:
                for (int size = Weeks.weeksBetween(startDateTime, endDateTime).getWeeks() + 1, i = 0; i < size; i++) {
                    timeline.add(startDateTime.plusWeeks(i).toString(WEEK_FORMATTER));
                }
                break;
            case MONTH:
                for (int size = Months.monthsBetween(startDateTime, endDateTime).getMonths() + 1, i = 0; i < size; i++) {
                    timeline.add(startDateTime.plusMonths(i).toString(MONTH_FORMATTER));
                }
                break;
            case QUARTER:
                int startY = startDateTime.getYear();
                int endY = endDateTime.getYear();
                int startQ = (startDateTime.getMonthOfYear() - 1) / 3 + 1;
                int endQ = (endDateTime.getMonthOfYear() - 1) / 3 + 1;
                int _size = (endY - startY) * 4 + (endQ - startQ + 1);
                for (int i = 0; i < _size; i++) {
                    startQ = startQ + i;
                    if (startQ == 5) {
                        startQ = 1;
                        startY = startY + 1;
                    }
                    timeline.add(startY + "Q" + startQ);
                }
                break;
            case YEAR:
                for (int size = Years.yearsBetween(startDateTime, endDateTime).getYears() + 1, i = 0; i < size; i++) {
                    timeline.add(startDateTime.plusYears(i).toString(YEAR_FORMATTER));
                }
                break;
            default:

        }
        return timeline;
    }


}
