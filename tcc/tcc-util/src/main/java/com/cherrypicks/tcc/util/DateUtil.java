package com.cherrypicks.tcc.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

/**
 * 日期转换工具
 */
public final class DateUtil {
    public static final String DATE_DIVISION = "-";
    public static final String DATETIME_PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN_DEFAULT = "yyyy-MM-dd";
    public static final String DATE_PATTERN_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_PATTERN_YYMM = "yyMM";
    public static final String TIME_PATTERN_DEFAULT = "HH:mm:ss";
    public static final String TIME_PATTERN_HHMMSSSSS = "HH:mm:ss.SSS";
    public static final String MONTH_PATTERN_DEFAULT = "yyyy-MM";
    public static final String DATE_PATTERN_MMDDYYYY = "MM-dd-yyyy";
    public static final String DATE_PATTERN_YYYYMMDDTHHMMSSZ = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String DATETIME_PATTERN_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String DATETIME_PATTERN_MINUTE_SHORT = "yyyyMMddHHmm";
    public static final String DATEHOUR_PATTERN_YYYYMMDD = "yyyyMMddHH";

    public static final String  DATE_PATTERN_YY_MM_DD = "yyyy MM dd";
    public static final String TIME_PATTERN_HHMM = "HH:mm";

    public static final int ONE_SECOND = 1000;
    public static final int ONE_MINUTE = 60 * ONE_SECOND;
    public static final int ONE_HOUR = 60 * ONE_MINUTE;
    public static final long ONE_DAY = 24 * ONE_HOUR;

    public static final String GMT8 = "GMT+8";

    private DateUtil() {
    }

    /**
     * Return the current date
     *
     * @return － DATE<br>
     */
    public static Date getCurrentDate() {
        final Calendar cal = Calendar.getInstance();
        final Date currDate = cal.getTime();

        return currDate;
    }

    /**
     * Return the current date string
     *
     * @return － 产生的日期字符串<br>
     */
    public static String getCurrentDateStr() {
        final Calendar cal = Calendar.getInstance();
        final Date currDate = cal.getTime();

        return format(currDate);
    }

    /**
     * Return the current date in the specified format
     *
     * @param strFormat
     * @return
     */
    public static String getCurrentDateStr(final String strFormat) {
        final Calendar cal = Calendar.getInstance();
        final Date currDate = cal.getTime();

        return format(currDate, strFormat);
    }

    public static synchronized Date getTime(final long millis) {
        final Date date = new Date();
        if (millis > 0) {
            date.setTime(millis);
        }
        return date;
    }

    /**
     * Parse a string and return a date value
     *
     * @param dateValue
     * @return
     * @throws Exception
     */
    public static Date parseDate(final String dateValue) {
        return parseDate(DATE_PATTERN_DEFAULT, dateValue);
    }

    /**
     * Parse a strign and return a datetime value
     *
     * @param dateValue
     * @return format(yyyy-MM-dd HH:mm:ss)
     */
    public static Date parseDateTime(final String dateValue) {
        return parseDate(DATETIME_PATTERN_DEFAULT, dateValue);
    }

    /**
     * Parse a string and return the date value in the specified format
     *
     * @param strFormat
     * @param dateValue
     * @return
     * @throws ParseException
     * @throws Exception
     */
    public static Date parseDate(String strFormat, final String dateValue) {
        if (dateValue == null) {
            return null;
        }

        if (strFormat == null) {
            strFormat = DATETIME_PATTERN_DEFAULT;
        }

        final SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
        Date newDate = null;

        try {
            newDate = dateFormat.parse(dateValue);
        } catch (final ParseException pe) {
            newDate = null;
        }
        return newDate;
    }

    /**
     * 将Timestamp类型的日期转换为系统参数定义的格式的字符串。
     *
     * @param aTsDatetime
     *            需要转换的日期。
     * @return 转换后符合给定格式的日期字符串
     */
    public static String format(final Date aTsDatetime) {
        return format(aTsDatetime, DATE_PATTERN_DEFAULT);
    }

    /**
     * 将Timestamp类型的日期转换为系统参数定义的格式的字符串。
     *
     * @param aTsDatetime
     *            需要转换的日期。
     * @return 转换后符合给定格式的日期字符串
     */
    public static String formatTime(final Date aTsDatetime) {
        return format(aTsDatetime, DATETIME_PATTERN_DEFAULT);
    }

    /**
     * 将Date类型的日期转换为系统参数定义的格式的字符串。
     *
     * @param aTsDatetime
     * @param asPattern
     * @return
     */
    public static String format(final Date aTsDatetime, final String asPattern) {
        if (aTsDatetime == null || asPattern == null) {
            return null;
        }
        final SimpleDateFormat dateFromat = new SimpleDateFormat();
        dateFromat.applyPattern(asPattern);

        return dateFromat.format(aTsDatetime);
    }

    /**
     * @param aTsDatetime
     * @param asFormat
     * @return
     */
    public static String formatTime(final Date aTsDatetime, final String asFormat) {
        if (aTsDatetime == null || asFormat == null) {
            return null;
        }
        final SimpleDateFormat dateFromat = new SimpleDateFormat();
        dateFromat.applyPattern(asFormat);

        return dateFromat.format(aTsDatetime);
    }

    public static String getFormatTime(final Date dateTime) {
        return formatTime(dateTime, TIME_PATTERN_DEFAULT);
    }

    /**
     * @param aTsDatetime
     * @param asPattern
     * @return
     */
    public static String format(final Timestamp aTsDatetime, final String asPattern) {
        if (aTsDatetime == null || asPattern == null) {
            return null;
        }
        final SimpleDateFormat dateFromat = new SimpleDateFormat();
        dateFromat.applyPattern(asPattern);

        return dateFromat.format(aTsDatetime);
    }

    /**
     * 取得指定日期N天后的日期
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(final Date date, final int days) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.add(Calendar.DAY_OF_MONTH, days);

        return cal.getTime();
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(final Date date1, final Date date2) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        final long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        final long time2 = cal.getTimeInMillis();
        final long betweenDays = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(betweenDays));
    }

    /**
     * 计算两个日期之间相差的小时
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int hoursBetween(final Date date1, final Date date2) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        final long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        final long time2 = cal.getTimeInMillis();
        final long betweenHours = (time2 - time1) / (1000 * 3600);

        return Integer.parseInt(String.valueOf(betweenHours));
    }

    /**
     * 计算当前日期相对于"1977-12-01"的天数
     *
     * @param date
     * @return
     */
    public static long getRelativeDays(final Date date) {
        final Date relativeDate = DateUtil.parseDate("yyyy-MM-dd", "1977-12-01");

        return DateUtil.daysBetween(relativeDate, date);
    }

    public static Date getDateBeforTwelveMonth() {
        String date = "";
        final Calendar cla = Calendar.getInstance();
        cla.setTime(getCurrentDate());
        final int year = cla.get(Calendar.YEAR) - 1;
        final int month = cla.get(Calendar.MONTH) + 1;
        if (month > 9) {
            date = String.valueOf(year) + DATE_DIVISION + String.valueOf(month) + DATE_DIVISION + "01";
        } else {
            date = String.valueOf(year) + DATE_DIVISION + "0" + String.valueOf(month) + DATE_DIVISION + "01";
        }

        final Date dateBefore = parseDate(date);
        return dateBefore;
    }

    /**
     * 传入时间字符串,加一天后返回Date
     *
     * @param date
     *            时间 格式 YYYY-MM-DD
     * @return
     */
    public static Date addDate(final String date) {
        if (date == null) {
            return null;
        }
        final Date tempDate = parseDate(DATE_PATTERN_DEFAULT, date);
        final String year = format(tempDate, "yyyy");
        final String month = format(tempDate, "MM");
        final String day = format(tempDate, "dd");

        final GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month) - 1,
                Integer.parseInt(day));

        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static Date getDate(final Date date, final int num) {
        final Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(date); // 把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, num); // 设置为前/后一天

        return calendar.getTime();
    }

    public static String getDateToString(final Date date, final int num, final String pattern) {
        final Date d = getDate(date, num);
        return parseDateToString(d, pattern);
    }

    public static String getDateByYearToString(final Date date, final int num, final String pattern) {
        final Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(date); // 把当前时间赋给日历
        calendar.add(Calendar.YEAR, num); // 设置为前/后一年
        return parseDateToString(calendar.getTime(), pattern);
    }

    public static String getDateByMonthToString(final Date date, final int num, final String pattern) {
        final Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(date); // 把当前时间赋给日历
        calendar.add(Calendar.MONTH, num); // 设置为前/后一个月
        return parseDateToString(calendar.getTime(), pattern);
    }

    public static Date getDateByHour(final Date date, final int num) {
        final Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(date); // 把当前时间赋给日历
        calendar.add(Calendar.HOUR, num); // 设置为前/后小时

        return calendar.getTime();
    }

    public static Date getDateByMin(final Date date, final int num) {
        final Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(date); // 把当前时间赋给日历
        calendar.add(Calendar.MINUTE, num); // 设置为前/后小时
        return calendar.getTime();
    }


    public static Date parseDate(final Date date, final String pattern) {
        final DateFormat df = new SimpleDateFormat(pattern);
        final String dateStr = df.format(date);
        return parseDate(pattern, dateStr);
    }

    public static String parseDateToString(final Date date, final String pattern) {
        final Date d = parseDate(date, pattern);
        final DateFormat df = new SimpleDateFormat(pattern, Locale.ENGLISH);

        return df.format(d);
    }

    public static String parseDateToString(final Date date, final String pattern, final Locale locale) {
        final Date d = parseDate(date, pattern);
        final DateFormat df = new SimpleDateFormat(pattern, locale);

        return df.format(d);
    }

    public static String getAMNNPNStr(final Date date, final Locale locale) {

        final Date date1 = parseDate(date, "HH:mm");
        final Date date2 = parseDate("HH:mm", "12:00");
        final Date date3 = parseDate("HH:mm", "12:59");

        if (date2.getTime() <= date1.getTime() && date1.getTime() <= date3.getTime()) {
            if (StringUtils.equals(locale.getLanguage(), Locale.ENGLISH.getLanguage())) {
                return "NN";
            }
            if (StringUtils.equals(locale.getLanguage(), Locale.CHINESE.getLanguage())) {
                return "中午";
            }
        }

        return parseDateToString(date, "a", locale);
    }

    public static Date getDate() {
        return new Date();
    }

    /**
     * 获取某日期是几号
     *
     * @param date
     * @return
     */
    public static int getDayOfMonth(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final int today = calendar.get(Calendar.DAY_OF_MONTH);
        return today;
    }

    /**
     * 获取某日期是星期几
     *
     * @param date
     * @return
     */
    public static int getDayOfWeek(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取某日期是星期几
     *
     * @param date
     * @return
     */
    public static String getDayOfWeekString(final Date date) {
        final int dayOfWeek = getDayOfWeek(date);
        switch (dayOfWeek) {
        case 1:
            return "Sunday";
        case 2:
            return "Monday";
        case 3:
            return "Tuesday";
        case 4:
            return "Wednesday";
        case 5:
            return "Thursday";
        case 6:
            return "Friday";
        case 7:
            return "Saturday";
        default:
            break;
        }
        return null;
    }

    /**
     * 获取某日期是星期几
     *
     * @param date
     * @return
     */
    public static String getDayOfWeekShortString(final Date date) {
        final int dayOfWeek = getDayOfWeek(date);
        switch (dayOfWeek) {
        case 1:
            return "Sun";
        case 2:
            return "Mon";
        case 3:
            return "Tue";
        case 4:
            return "Wed";
        case 5:
            return "Thu";
        case 6:
            return "Fri";
        case 7:
            return "Sat";
        default:
            break;
        }
        return null;
    }

    public static Date addWeeks(final Date date, final int num) {

        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.add(Calendar.WEEK_OF_MONTH, num);

        return cal.getTime();
    }

    public static Date addSecs(final Date date, final int num) {

        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.add(Calendar.SECOND, num);

        return cal.getTime();
    }

    public static Date addMins(final Date date, final int num) {

        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.add(Calendar.MINUTE, num);

        return cal.getTime();
    }

    public static Date addHours(final Date date, final int num) {

        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.add(Calendar.HOUR_OF_DAY, num);

        return cal.getTime();
    }

    public static Date addMonths(final Date date, final int num) {

        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.add(Calendar.MONTH, num);

        return cal.getTime();
    }

    public static int getYear(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static int getMonth(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static int getMins(final Date date) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * @param date
     * @return k->yyyy-MM-dd,v->MMM
     */
    public static Map<String, String> getMonthOfYearMap(final Date date) {
        final Map<String, String> dateMap = new LinkedHashMap<String, String>();

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, 0);

        final Date d = calendar.getTime(); // first month of year

        for (int i = 0; i < 12; i++) {
            final Date resultDate = DateUtil.addMonths(d, i);

            final String key = DateUtil.format(resultDate, "yyyy-MM");

            final DateFormat df = new SimpleDateFormat("MMM", Locale.ENGLISH);
            final String value = df.format(resultDate);

            dateMap.put(key, value);
        }

        return dateMap;
    }

    public static String parseDate2(final Date date, final String pattern) {
        final DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    public static Date getDateStartByMonth(Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        date = calendar.getTime();
        String dateStr = parseDate2(date, "yyyy-MM-dd");
        dateStr = dateStr + " 00:00:00";
        return parseDateTime(dateStr);
    }

    public static Date getDateEndByMonth(Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        date = calendar.getTime();
        String dateStr = parseDate2(date, "yyyy-MM-dd");
        dateStr = dateStr + " 23:59:59";
        return parseDateTime(dateStr);
    }

    public static Date getDateStart(final Date date) {
        String dateStr = parseDate2(date, "yyyy-MM-dd");
        dateStr = dateStr + " 00:00:00";
        return parseDateTime(dateStr);
    }

    public static Date getDateEnd(final Date date) {
        String dateStr = parseDate2(date, "yyyy-MM-dd");
        dateStr = dateStr + " 23:59:59";
        return parseDateTime(dateStr);
    }

    public static int get24Hours(final Date date) {

        final Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c.get(Calendar.HOUR_OF_DAY);
    }

    public static int get12Hours(final Date date) {

        final Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c.get(Calendar.HOUR);
    }

    public static Date toDateTime(final Date date, final Time time) {
        String dateStr = DateUtil.parseDateToString(date, DateUtil.DATE_PATTERN_DEFAULT);
        dateStr = dateStr + " " + time.toString();
        return DateUtil.parseDateTime(dateStr);
    }

    public static Date getDateStartByYear(Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        date = calendar.getTime();
        String dateStr = parseDate2(date, "yyyy-MM-dd");
        dateStr = dateStr + " 00:00:00";
        return parseDateTime(dateStr);
    }

    public static Date getDateEndByYear(Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 0);
        date = calendar.getTime();
        String dateStr = parseDate2(date, "yyyy-MM-dd");
        dateStr = dateStr + " 23:59:59";
        return parseDateTime(dateStr);
    }

    public static Date getDateMonth(final int month) {
        Date date = new Date();
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        date = cal.getTime();
        return date;
    }

    public static String getCurrentDateGMTString() {
        final Calendar cal = Calendar.getInstance();
        final Date currDate = cal.getTime();
        // Give it to me in GMT
        final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", new Locale("en", "US"));
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(currDate);
    }

    public static String getTs() {
        final Date timestamp = getCurrentDate();
        final long lDateTime = timestamp.getTime() / 1000;
        return String.valueOf(lDateTime);
    }

    public static String getTs(final Date date) {
        if (date != null) {
            final long lDateTime = date.getTime() / 1000;
            return String.valueOf(lDateTime);
        } else {
            return null;
        }
    }

    public static long getUTC(final String dateValue,final String strFormat) {
        final Date date = parseDate(strFormat, dateValue);
        final java.util.Calendar cal2 = java.util.Calendar.getInstance();
        cal2.setTime(date);
        final int month = cal2.get(Calendar.MONTH);
        final int year = cal2.get(Calendar.YEAR);
        final int dayOfMonth = cal2.get(Calendar.DAY_OF_MONTH);

        //1、取得本地时间：
        final java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
        //2、取得时间偏移量：
        final int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        //3、取得夏令时差：
        final int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        //4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        //之后调用cal.get(int x)或cal.getTimeInMillis()方法所取得的时间即是UTC标准时间。
        return cal.getTimeInMillis();
    }

    public static long getSurplusSecOfDay(final Date currDate) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(currDate);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() - currDate.getTime()) / 1000;
    }

    public static long[] getDistanceTime(final Date date1, final Date date2) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        final long time1 = date1.getTime();
        final long time2 = date2.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        final long[] times = {day, hour, min, sec};
        return times;
    }


    public static void main(final String[] args) {
//        System.out.println(getUTC("04-06-1973", DATE_PATTERN_MMDDYYYY));
    //	System.out.println(DateUtil.getCurrentDateStr("yyMM"));
    	//System.out.println(DateUtil.format(DateUtil.getDateByMin(new Date(),5), DateUtil.DATETIME_PATTERN_DEFAULT));
      /*  System.out.println(getDateStartByYear(new Date()));
        System.out.println(getDateEndByYear(new Date()));
        System.out.println(getDateStart(new Date()));
        System.out.println(getDateEnd(new Date()));
        System.out.println(getDateStartByMonth(new Date()));
        System.out.println(getDateEndByMonth(new Date()));
//    	System.out.println(DateUtil.format(DateUtil.getDateByMin(new Date(),5), DateUtil.DATETIME_PATTERN_DEFAULT));

    	System.out.println(format(getDateStartByMonth(new Date()),DATE_PATTERN_DEFAULT));
    	*/

        final Date date1 = parseDateTime("2017-11-23 22:17:34");
        final Date date2 = parseDateTime("2017-11-24 05:17:34");
        System.out.println(DateUtil.getDistanceTime(date1, date2));
System.out.println(TimeZoneConvert.dateTimezoneToUI(new Date(), TimeZoneConvert.DEFAULT_TIMEZONE, "Africa/Douala"));
        System.out.println(getSurplusSecOfDay(TimeZoneConvert.dateTimezoneToUI(new Date(), TimeZoneConvert.DEFAULT_TIMEZONE, "Africa/Douala")));
    }
}
