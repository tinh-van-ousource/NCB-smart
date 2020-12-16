/**
 * 
 */
package com.tvo.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Ace
 *
 */
public class DateTimeUtil {
	public static final int DEFAULT_DEADLINE_YEAR = 3999;

	public static final String DEFAULT_FILENETDATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public static final String DEFAULT_FILENETDATE_IGNORE_MILISECOND_FORMAT = "yyyy-MM-dd'T'HH:mm':00Z'";
	public static final String DEFAULT_ACCEDATEFORMAT = "yyyyMMdd'T'HHmmss'Z'";
	public static final String DEFAULT_SHORTDATEFORMAT = "dd/MM/yyyy";
	public static final String DEFAULT_SIMPLEDATEFORMAT1 = "yyyy/MM/ddHHmmssSSS";
	public static final String DEFAULT_SIMPLEDATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String DEFAULT_SIMPLEDATEFORMAT_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static final String DEFAULT_FOLDERNAME_TIME = "yyyyMMddHHmmssSSS";
	public static final String DEFAULT_IMPORTFILE_TIME = "yyyyMMdd_HHmmssSSS";
	public static final String DEFAULT_SHORTDATE_FTPFOLDER_ = "ddMMyyyy";
	public static final String DEFAULT_SHORT_TIME_FORMAT = "HH:mm:ss";

	public static final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat(DEFAULT_SHORTDATEFORMAT);
	public static final SimpleDateFormat SIMPLE_DATE_FORMAT1 = new SimpleDateFormat(DEFAULT_SIMPLEDATEFORMAT1);
	public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DEFAULT_SIMPLEDATEFORMAT);
	public static final SimpleDateFormat FOLDERNAME_TIME_FORMAT = new SimpleDateFormat(DEFAULT_FOLDERNAME_TIME);
	public static final SimpleDateFormat FILENET_DATE_FORMAT = new SimpleDateFormat(
			DEFAULT_FILENETDATE_IGNORE_MILISECOND_FORMAT);
	public static final SimpleDateFormat ACCE_DATE_FORMAT = new SimpleDateFormat(DEFAULT_ACCEDATEFORMAT);
	public static final SimpleDateFormat IMPORTFILE_TIME_FORMAT = new SimpleDateFormat(DEFAULT_IMPORTFILE_TIME);
	public static final SimpleDateFormat SIMPLE_DATE_FORMAT_TIMEZONE = new SimpleDateFormat(
			DEFAULT_SIMPLEDATEFORMAT_TIMEZONE);
	public static final SimpleDateFormat SHORTDATE_FTPFOLDER_FORTMAT = new SimpleDateFormat(
			DEFAULT_SHORTDATE_FTPFOLDER_);
	public static final SimpleDateFormat SHORT_TIME_FORTMAT = new SimpleDateFormat(DEFAULT_SHORT_TIME_FORMAT);

	public static Date totalDate(final Date date, final int seconds) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
	}

	public static Date getNow() {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return calendar.getTime();
	}

	public static Date getNowIgnoreMiliSecond() {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Calendar getTimeNow() {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return calendar;
	}

	public static Date getDate(final long value) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(value);
		return calendar.getTime();
	}

	public static Date createDate(final String date) throws ParseException {
		final DateFormat dateFormat = DateTimeUtil.SIMPLE_DATE_FORMAT1;
		final Date d = dateFormat.parse(date + "000000000");
		return d;
	}

	public static Date setFixTime(final Date date, final Date fixTime) {
		final Calendar calendarFixTime = Calendar.getInstance();
		calendarFixTime.setTime(fixTime);

		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendarFixTime.get(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendarFixTime.get(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendarFixTime.get(Calendar.SECOND));
		return calendar.getTime();
	}

	public static Date createBeginYear(final int year) {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(year, Calendar.JANUARY, 1, 0, 0, 0);
		return calendar.getTime();
	}

	public static Date createEndYear(final int year) {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
		return calendar.getTime();
	}

	public static Date createStartTime(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	public static Date plusSevenHour(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 7);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date createEndTime(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}
	public static int getYearNow() {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return calendar.get(Calendar.YEAR);
	}

	public static int getYear(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public static int getDayOfWeek(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public static int getDay(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static int getMonth(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static boolean equalOrBefore(final Date date1, final Date date2) {
		return DateTimeUtil.convertToString(date1).compareTo(DateTimeUtil.convertToString(date2)) <= 0;
	}

	public static boolean equalOrAfter(final Date date1, final Date date2) {
		return DateTimeUtil.convertToString(date1).compareTo(DateTimeUtil.convertToString(date2)) >= 0;
	}

	public static boolean after(final Date date1, final Date date2) {
		return date1.compareTo(date2) == 1;
	}

	public static boolean before(final Date date1, final Date date2) {
		return date1.compareTo(date2) == -1;
	}

	public static long convertToPETime(final Date date) {
		final long value = date.getTime();
		return value / 1000;
	}

	public static String convertToString(final Date date) {
		return SIMPLE_DATE_FORMAT.format(date);
	}

	public static String convertToShortString(final Date date) {
		return SHORT_DATE_FORMAT.format(date);
	}

	public static String convertToShortTimeString(final Date date) {
		return SHORT_TIME_FORTMAT.format(date);
	}

	public static Date convertFromShortString(final String date) throws ParseException {
		return SHORT_DATE_FORMAT.parse(date);
	}

	public static String convertToFolderName(final Date date) {
		return FOLDERNAME_TIME_FORMAT.format(date);
	}

	public static String convertToFileName(final Date date) {
		return IMPORTFILE_TIME_FORMAT.format(date);
	}

	public static String convertToShortStringFtp(final Date date) {
		return SHORTDATE_FTPFOLDER_FORTMAT.format(date);
	}

	public static Object[] parseQuarterString(final String value) {
		final Object[] quarterYearStr = new Object[2];
		if (value.length() == 5) {
			quarterYearStr[0] = Integer.parseInt(value.substring(0, 4));
			quarterYearStr[1] = value.substring(4, 5);
			if (Integer.parseInt((String) quarterYearStr[1]) == 0) {
				quarterYearStr[1] = null;
			}
		} else if (value.length() == 4) {
			quarterYearStr[0] = Integer.parseInt(value.substring(0, 4));
			quarterYearStr[1] = null;
		}
		return quarterYearStr;
	}

	public static Date createDefaultDeadlineDate() {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.YEAR, DEFAULT_DEADLINE_YEAR);
		return calendar.getTime();
	}
}
