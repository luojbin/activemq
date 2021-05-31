package com.jitai.amqDemo.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static Timestamp gettimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static Timestamp gettimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	public static boolean before(String date1, String date2, String pattern) {
		Date d1 = parse(date1, pattern);
		Date d2 = parse(date2, pattern);
		return before(d1, d2);
	}

	public static boolean after(String date1, String date2, String pattern) {
		Date d1 = parse(date1, pattern);
		Date d2 = parse(date2, pattern);
		return after(d1, d2);
	}

	public static boolean before(String date1, String date2) {
		Date d1 = toDate(date1);
		Date d2 = toDate(date2);
		return before(d1, d2);
	}

	public static boolean after(String date1, String date2) {
		Date d1 = toDate(date1);
		Date d2 = toDate(date2);
		return after(d1, d2);
	}

	public static boolean before(Date date1, Date date2) {
		return date1.before(date2);
	}

	public static boolean after(Date date1, Date date2) {
		return date1.after(date2);
	}

	public static Date toDate(String date) {
		return parse(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String toStr() {
		return toStr(new Date());
	}

	public static String toStr(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String format(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static Date parse(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
		}
		return null;
	}

	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / 86400000L;
		long hour = timeMillis / 3600000L - day * 24L;
		long min = timeMillis / 60000L - day * 24L * 60L - hour * 60L;
		long s = timeMillis / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;
		long sss = timeMillis - day * 24L * 60L * 60L * 1000L - hour * 60L * 60L * 1000L - min * 60L * 1000L - s * 1000L;
		return new StringBuilder().append(day > 0L ? new StringBuilder().append(day).append(",").toString() : "").append(hour).append(":").append(min).append(":").append(s).append(".").append(sss)
				.toString();
	}

	public static String timestampToString(Timestamp time) {
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			tsStr = sdf.format(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tsStr;
	}

	public static String getTimestamp() {
		return timestampToString(gettimestamp());
	}

	public static String getTimestamp(Date date) {
		return timestampToString(gettimestamp(date));
	}

	public static String dateToStamp(String s) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = simpleDateFormat.parse(s);

		String timestamp = String.valueOf(date.getTime());
		int length = timestamp.length();
		if (length > 3) {
			return String.valueOf(timestamp.substring(0, length - 3));
		}
		return "0";
	}

	public static int differentDaysByMillisecond(Date date1, Date date2) {
		int days = (int) ((date2.getTime() - date1.getTime()) / 86400000L);
		return days;
	}

	public static Date toDate(String date, String pattern) {
		return parse(date, pattern);
	}
}