package com.adc.idea.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;

@SuppressWarnings("static-access")
public final class JodaDateUtils {

	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String HH_MM_SS = "HH:mm:ss";
	public static final String HH_MM = "HH:mm";

	private static DateTime dateTime;

	static {
		dateTime = new DateTime();
	}

	public static void main(String[] args) {
		//System.out.println(plusDays(1).toString(DEFAULT_FORMAT));
		// System.out.println(plusDays(1).toString(DEFAULT_FORMAT));
		// System.out.println(daysBetween(new Date(), str2Date("2014-7-28")));
		// System.out.println(now());
		//System.out.println(dateTime.yearOfCentury());
		//System.out.println(getDateTime(getCurrentYear(), getCurrentMonth(), 1, 0, 0, 0, 0).toDate());
		//System.out.println(getFirstDayOfCurrentMonth() );
		//System.out.println(getFirstDayOfCurrentYear() );
		//System.out.println(dateTime.dayOfMonth().withMinimumValue().withTimeAtStartOfDay());
//		System.out.println(getTheDayLastClock(new Date()).toLocaleString());
//		System.out.println(getTheFirstLastClock(new Date()).toLocaleString());
	}
	
	public static Date getTheDayLastClock(Date date) {
		String d = date2Str(date) + " 23:59:59";
		return str2Date(d, DEFAULT_FORMAT);
	}
	
	public static Date getTheFirstLastClock(Date date) {
		String d = date2Str(date) + " 00:00:00";
		return str2Date(d, DEFAULT_FORMAT);
	}
	
	/**
	 *获取当前月的第一天 
	 */
	public static Date getFirstDateOfCurrentMonth(){
		return dateTime.dayOfMonth().withMinimumValue().withTimeAtStartOfDay().toDate();
	}
	
	/**
	 *获取当前月的第一天 
	 */
	public static String getFirstDayOfCurrentMonth(){
		//return date2Str(getDateTime(getCurrentYear(), getCurrentMonth(), 1, 0, 0, 0, 0).toDate(), YYYY_MM_DD);
		return date2Str(dateTime.dayOfMonth().withMinimumValue().withTimeAtStartOfDay().toDate(), YYYY_MM_DD);
	}
	
	/**
	 *获取当前年的第一天 
	 */
	public static String getFirstDayOfCurrentYear(){
		return date2Str(getDateTime(getCurrentYear(), 1, 1, 0, 0, 0, 0).toDate(), YYYY_MM_DD);
	}
	
	/**
	 * 获取当年
	 */
	public static int getCurrentYear(){
		return dateTime.getYear();
	}
	
	/**
	 * 获取当年
	 */
	public static int getCurrentMonth(){
		return dateTime.getMonthOfYear();
	}

	public static final DateTime now() {
		return dateTime.now();
	}

	public static final int secondsBetween(Date start, Date end) {
		return Seconds.secondsBetween(getDateTime(start), getDateTime(end))
				.getSeconds();
	}

	public static final int minutesBetween(Date start, Date end) {
		return Minutes.minutesBetween(getDateTime(start), getDateTime(end))
				.getMinutes();
	}

	public static final int hoursBetween(Date start, Date end) {
		return Hours.hoursBetween(getDateTime(start), getDateTime(end))
				.getHours();
	}

	public static final int daysBetween(Date start, Date end) {
		return Days.daysBetween(getDateTime(start), getDateTime(end)).getDays();
	}

	/**
	 * 是否是闰月
	 */
	public static final boolean isLeapMonth() {
		return dateTime.monthOfYear().isLeap();
	}

	/**
	 * 是否是闰月
	 */
	public static final boolean isLeapMonth(Date date) {
		return getDateTime(date).monthOfYear().isLeap();
	}

	/**
	 * 是否是闰年
	 */
	public static final boolean isLeapYear() {
		return dateTime.year().isLeap();
	}

	/**
	 * 是否是闰年
	 */
	public static final boolean isLeapYear(Date date) {
		return getDateTime(date).year().isLeap();
	}

	public static final DateTime plusWeeks(Date date, int weeks) {
		return getDateTime(date).plusWeeks(weeks);
	}

	public static final DateTime plusWeeks(int weeks) {
		return dateTime.plusWeeks(weeks);
	}

	public static final DateTime plusMinutes(Date date, int minutes) {
		return getDateTime(date).plusMinutes(minutes);
	}

	public static final DateTime plusMinutes(int minutes) {
		return dateTime.plusMinutes(minutes);
	}

	public static final DateTime pluseHours(Date date, int hours) {
		return getDateTime(date).plusHours(hours);
	}

	public static final DateTime plusHours(int hours) {
		return dateTime.plusHours(hours);
	}

	public static final DateTime plusMonths(Date date, int months) {
		return getDateTime(date).plusMonths(months);
	}

	public static final DateTime plusMonths(int months) {
		return dateTime.plusMonths(months);
	}

	public static final DateTime plusDays(Date date, int days) {
		return getDateTime(date).plusDays(days);
	}

	public static final DateTime plusDays(int days) {
		return dateTime.plusDays(days);
	}

	public static final Date str2Date(String str) {
		Date date = null;
		try {
			date = dateTime.parse(str).toDate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return date;
	}
	
	public static final Date str2Date(String str, String pattern) {
		Date date = null;
		try {
			date = getDateTime(str, pattern).toDate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return date;
	}

	/**
	 * @param date
	 * @return YYYY_MM_DD
	 */
	public static final String date2Str(Date date) {
		SimpleDateFormat df = null;
		String str = null;
		try {
			df = new SimpleDateFormat(YYYY_MM_DD);
			str = df.format(date);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return str;
	}

	public static final String date2Str(Date date, String format) {
		SimpleDateFormat df = null;
		String str = null;
		try {
			df = new SimpleDateFormat(format);
			str = df.format(date);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return str;
	}

	public static final DateTime getDateTime(String dateStr, String pattern) {
		DateTime dateTime = null;
		try {
			dateTime = DateTimeFormat.forPattern(pattern)
					.parseDateTime(dateStr);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return dateTime;
	}

	/**
	 * Date-->DateTime
	 */
	public static final DateTime getDateTime(Date date) {
		return new DateTime(date);
	}

	/**
	 * DateTime-->Date
	 */
	public static final Date getDate(DateTime dateTime) {
		return dateTime.toDate();
	}

	public static final DateTime getDateTime(int year, int month, int day,
			int hour, int minute, int second, int milli) {
		try {
			dateTime = new DateTime(year, month, day, hour, minute, second,
					milli);
			return dateTime;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static final Date getDate(int year, int month, int day, int hour,
			int minute, int second, int milli) {
		Date date = null;
		try {
			dateTime = new DateTime(year, month, day, hour, minute, second,
					milli);
			date = dateTime.toDate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return date;
	}

	public static final Date generateDate(int year, int month, int day){
		Date date = null;
		try {
			date = getDate(year, month, day, 0, 0, 0, 0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return date;
	}

}
