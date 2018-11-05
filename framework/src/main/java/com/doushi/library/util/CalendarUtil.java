package com.doushi.library.util;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

	/**
	 * 获取传过来时间之前之�?(count+dateType"1+�?")的Date对象
	 * @param date Date类型
	 * @param dateType 日期类型 Calendar.DATE，Calendar.MONTH...
	 * @param count 多少"日期类型"(之前的传负数)
	 * @return
	 */
	public static Date getDate(Date date, int dateType, int count) {
		Calendar cal = changeDate(date);
		cal.add(dateType, count);  
		return cal.getTime();  
	}

	/**
	 *  将字符串转换�? Calender 类型
	 * @param date Date类型
	 * @return
	 */
	public static Calendar changeDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);  
		return calendar;
	}
	
	/**
	 * 获取时间(年|月|日|时|分|秒)
	 * @param dateStr 时间字符�?
	 * @param field 获取类型
	 * @return
	 */
	public static int getFieldTime(String dateStr, int field){
		Calendar calendar = changeDate(DateUtil.getDate(dateStr, DateUtil.FULL_DATE_PATTERN));
		return calendar.get(field);
	}

	public static int getFieldTime(long dataTime, int field){
		Calendar calendar = changeDate(new Date(dataTime));
		return calendar.get(field);
	}
}
