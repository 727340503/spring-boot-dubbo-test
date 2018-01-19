package com.cherrypicks.tcc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * timeZone转换日期、时间相关的一些常用工具方法.
 * <p>
 * 日期(时间)的常用格式(formater)主要有: <br>
 * yyyy-MM-dd HH:mm:ss <br>
 * yyyy-MM-dd HH:mm <br>
 * </p>
 *
 * @author kelvin
 */
public final class TimeZoneConvert {

	private static Log LOGGER = LogFactory.getLog(TimeZoneConvert.class);
//	public static final String DEFAULT_TIMEZONE = "Asia/Hong_Kong";
	public static final String DEFAULT_TIMEZONE = getCurrentServerTimeZone();
	public static final String DEFAULT_DATE_FORMATER = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_DATE_FORMATER_MINUTE = "yyyy-MM-dd HH:mm";

	/**
	 * 获取所有的时区编号. <br>
	 * 排序规则:按照ASCII字符的正序进行排序. <br>
	 * 排序时候忽略字符大小写.
	 *
	 * @return 所有的时区编号(时区编号已经按照字符[忽略大小写]排序).
	 */
	public static String[] fecthAllTimeZoneIds() {

		return TimeZone.getAvailableIDs();
	}

	/**
	 * 格式化时间格式
	 *
	 * @param formater
	 * @param date
	 * @return 输出格式化后的时间字符串
	 */
	public static String dateString(final String formater, final Date date) {
		if (formater == null || "".equals(formater)) {
            return null;
        }
		if (date == null) {
            return null;
        }
		return (new SimpleDateFormat(formater)).format(date);
	}

//	/**
//	 * 将日期时间字符串根据转换为指定时区的日期时间.
//	 *
//	 * @param srcDateTime
//	 *            待转化的日期时间.
//	 * @param dstTimeZoneId
//	 *            目标的时区编号.
//	 *
//	 * @return 转化后的日期时间 格式 （(yyyy-MM-dd HH:mm:ss)
//	 * @see #stringTimezone(String, Date, String, String)
//	 */
//	public static String stringTimezoneDefault(Date date, String timeZoneFrom, String timeZoneTo) {
//		return stringTimezone(DEFAULT_DATE_FORMATER, date, timeZoneFrom, timeZoneTo);
//	}
//
//	/**
//	 * 将默认时区时间转换为指定时区的日期时间.
//	 *
//	 * @param formater
//	 *            待转化的日期时间的格式.
//	 * @param Date
//	 *            待转化的日期时间.
//	 * @param timeZoneTo
//	 *            要转换的时区
//	 *
//	 * @return 转化后时区的日期时间格式.
//	 */
//	public static String stringDefaultTimezone(String formater, Date date, String timeZoneTo) {
//		try {
//
//			Date d = new Date(date.getTime() - getDiffOffset(date, TimeZone.getDefault().getID(), timeZoneTo));
//			return dateString(formater, d);
//		} catch (Exception e) {
//			LOGGER.info("" + e.getMessage());
//			return null;
//		}
//	}
//
//	/**
//	 * 将系统时区时间转换为指定时区的日期时间.
//	 *
//	 * @param formater
//	 *            待转化的日期时间的格式.
//	 * @param Date
//	 *            待转化的日期时间.
//	 * @param timeZoneFrom
//	 *            指定时区
//	 * @param timeZoneTo
//	 *            要转换的时区
//	 *
//	 * @return 转化后时区的日期时间格式.
//	 */
//	public static String stringTimezone(String formater, Date date, String timeZoneFrom, String timeZoneTo) {
//		try {
//
//			/* 转换成timeZoneFrom指定的时区时间 */
//			Calendar ca = Calendar.getInstance();
//			ca.setTimeZone(TimeZone.getTimeZone(timeZoneFrom));
//			ca.setTime(date);
//			Date d = new Date(ca.getTimeInMillis() - getDiffOffset(date, timeZoneFrom, timeZoneTo));
//
//			return dateString(formater, d);
//		} catch (Exception e) {
//			LOGGER.info("" + e.getMessage());
//			return null;
//		}
//	}
//
//	/**
//	 * 将系统时区时间字符串转换为指定时区的日期时间.
//	 *
//	 * @param formater
//	 *            待转化的日期时间的格式.
//	 * @param dateTime
//	 *            待转化的日期时间字符串.
//	 * @param timeZoneFrom
//	 *            指定时区
//	 * @param timeZoneTo
//	 *            要转换的时区
//	 *
//	 * @return 转换后时区的日期时间
//	 */
//	public static Date dateTimezone(String formater, String dateTime, String timeZoneTo) {
//		try {
//			SimpleDateFormat s = new SimpleDateFormat(formater);
//			Date d = s.parse(dateTime);
//			return new Date(d.getTime() - getDiffOffset(d, TimeZone.getDefault().getID(), timeZoneTo));
//
//		} catch (Exception e) {
//			LOGGER.info("" + e.getMessage());
//			return null;
//		}
//	}
//
//	/**
//	 * 将日期时间字符串转换为指定时区的日期时间.
//	 *
//	 * @param formater
//	 *            待转化的日期时间的格式.
//	 * @param dateTime
//	 *            待转化的日期时间字符串.
//	 * @param timeZoneFrom
//	 *            指定时区
//	 * @param timeZoneTo
//	 *            要转换的时区
//	 *
//	 * @return 转换后时区的日期时间
//	 */
//	public static Date dateTimezone(String formater, String dateTime, String timeZoneFrom, String timeZoneTo) {
//
//		try {
//			SimpleDateFormat s = new SimpleDateFormat(formater);
//
//			Date d = s.parse(dateTime);
//			/* 转换成timeZoneFrom指定的时区时间 */
//			Calendar ca = Calendar.getInstance();
//			ca.setTimeZone(TimeZone.getTimeZone(timeZoneFrom));
//			ca.setTime(d);
//
//			return new Date(ca.getTimeInMillis() - getDiffOffset(d, timeZoneFrom, timeZoneTo));
//
//		} catch (Exception e) {
//			LOGGER.info("" + e.getMessage());
//			return null;
//		}
//	}

	/**
	 * 将日期时间字符串转换为指定时区的日期时间.
	 *
	 * @param dateTime
	 *            待转化的日期时间.
	 * @param timeZoneFrom
	 *            指定时区
	 * @param timeZoneTo
	 *            要转换的时区
	 *
	 * @return 转换后时区的日期时间
	 */
	public static Date dateTimezoneToDB(final Date dateTime, final String timeZoneFrom, final String timeZoneTo) {
	    if(dateTime == null) {
            return null;
        }
	    try {
			final SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			final String dateStr = f.format(dateTime);
			f.setTimeZone(TimeZone.getTimeZone(timeZoneFrom));
			final Date d = f.parse(dateStr);

			final Calendar ca = Calendar.getInstance();
			ca.setTimeZone(TimeZone.getTimeZone(timeZoneTo));
			ca.setTime(d);

			return ca.getTime();
//			final Calendar ca = Calendar.getInstance();
//			ca.setTimeZone(TimeZone.getTimeZone(timeZoneFrom));
//			ca.setTime(dateTime);
//
//			return new Date(ca.getTimeInMillis() - getDiffTimeZoneOffset(ca, timeZoneFrom, timeZoneTo));

		} catch (final Exception e) {
			LOGGER.info(e.getMessage());
			return null;
		}
	}


	/**
	 * 将日期时间字符串转换为指定时区的日期时间.
	 *
	 * @param dateTime
	 *            待转化的日期时间.
	 * @param timeZoneFrom
	 *            指定时区
	 * @param timeZoneTo
	 *            要转换的时区
	 *
	 * @return 转换后时区的日期时间
	 */
	public static Date dateTimezoneToUI(final Date dateTime, final String timeZoneFrom, final String timeZoneTo) {
	    if(dateTime == null) {
	        return null;
        }
	    try {
			final Calendar ca = Calendar.getInstance();
			ca.setTimeZone(TimeZone.getTimeZone(timeZoneFrom));
			ca.setTime(dateTime);

			return new Date(ca.getTimeInMillis() - getDiffTimeZoneOffset(ca, timeZoneFrom, timeZoneTo));

		} catch (final Exception e) {
			LOGGER.info("" + e.getMessage());
			return null;
		}
	}

	/**
     * 获取指定时区和日期的与UTC的时间差.(单位:毫秒)
     * @param Date
     *            指定时间
     * @return 系统当前默认时区与UTC的时间差.(单位:毫秒)
     */
	public static int getTimeZoneOffset(final Calendar ca, final String timeZoneId) {
        return TimeZone.getTimeZone(timeZoneId).getOffset(ca.getTimeInMillis());
    }

    /**
     * 获取指定时区和日期的和指定时区的的时间差.(单位:毫秒)
     * @param Date
     *            指定时间
     * @param timeZoneFrom
     *            指定时区
     * @param timeZoneTo
     *            要转换的时区
     * @return 时区和日期与指定时区的时间差.(单位:毫秒)
     */
    public static int getDiffTimeZoneOffset(final Calendar ca, final String timeZoneFrom, final String timeZoneTo) {
        return getTimeZoneOffset(ca, timeZoneFrom) - getTimeZoneOffset(ca, timeZoneTo);
    }

    /***
     * 获取当前服务器的时区
     *
     * @return 当前服务器所设的时区
     */
    public static String getCurrentServerTimeZone(){
    	final Calendar ca = Calendar.getInstance();
    	final TimeZone timeZone = ca.getTimeZone();

    	return timeZone.getID();
    }


    public static void main(final String[] args) {
		//System.out.println(dateTimezoneToUI(new Date(),getCurrentServerTimeZone(),timeZone));
		System.out.println();
//		System.out.println(TimeZoneConvert.dateTimezoneToUI(DateUtil.parseDate(DateUtil.DATETIME_PATTERN_DEFAULT,"2017-06-29 16:32:49"), TimeZoneConvert.DEFAULT_TIMEZONE, "Asia/Magadan"));
//		System.out.println(TimeZoneConvert.dateTimezoneToUI(new Date(), TimeZoneConvert.DEFAULT_TIMEZONE, "Asia/Magadan"));
		final Date date = TimeZoneConvert.dateTimezoneToDB(DateUtil.parseDate(DateUtil.DATETIME_PATTERN_DEFAULT,"2017-07-03 16:00:00"), "Asia/Jakarta",TimeZoneConvert.DEFAULT_TIMEZONE);
		System.out.println(date);
		System.out.println(TimeZoneConvert.dateTimezoneToDB(DateUtil.parseDate(DateUtil.DATETIME_PATTERN_DEFAULT,"2017-07-03 16:00:00"), "Asia/Jakarta",TimeZoneConvert.DEFAULT_TIMEZONE));
		System.out.println(TimeZoneConvert.dateTimezoneToUI(date,TimeZoneConvert.DEFAULT_TIMEZONE, "Asia/Jakarta"));
	}

//	/**
//	 * 获取指定时区和日期的与UTC的时间差.(单位:毫秒)
//	 *
//	 * @param Date
//	 *            指定时间
//	 * @return 系统当前默认时区与UTC的时间差.(单位:毫秒)
//	 */
//	public static int getOffset(Date date, String timeZoneId) {
//		return TimeZone.getTimeZone(timeZoneId).getOffset(date.getTime());
//	}
//
//	/**
//	 * 获取指定时区和日期的和指定时区的的时间差.(单位:毫秒)
//	 *
//	 * @param Date
//	 *            指定时间
//	 * @param timeZoneFrom
//	 *            指定时区
//	 * @param timeZoneTo
//	 *            要转换的时区
//	 * @return 时区和日期与指定时区的时间差.(单位:毫秒)
//	 */
//	public static int getDiffOffset(Date date, String timeZoneFrom, String timeZoneTo) {
//		return getOffset(date, timeZoneFrom) - getOffsete, timeZoneTo);
//	}
}
