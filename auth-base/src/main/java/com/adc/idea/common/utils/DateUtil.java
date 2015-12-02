package com.adc.idea.common.utils;

import java.util.Date;

public class DateUtil {

	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	public static void main(String[] args) {
		System.out.println(greaterThan(JodaDateUtils.plusDays(2).toDate(),
				new Date()));
	}

	public static boolean greaterThan(Date bigDate, Date smallDate) {
		return bigDate.getTime() - smallDate.getTime() > 0 ? true : false;
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * 
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
	}
}
