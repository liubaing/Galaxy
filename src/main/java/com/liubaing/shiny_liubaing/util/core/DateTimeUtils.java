package com.liubaing.shiny_liubaing.util.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.liubaing.shiny_liubaing.util.log.LogUtils;

/**
 * 类说明:时间格式转换
 * @author heshuai
 * @version 2012-3-13
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
public class DateTimeUtils {

	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	 /**
	  * 
	   * 方法描述：字符串转时间
	   * @param: 
	   * @return: 
	   * @author: heshuai
	   * @version: 2012-3-13 下午05:59:18
	  */
	 public static Date StrToDate(String dateStr)
	 {
		 Date date = null;
		 try {
			 date = format.parse(dateStr);
		 } catch (ParseException e) {
			 LogUtils.doLog("字符串格式化为时间出现异常" , e, dateStr, format);
		 }
		 return date;
	 }
	 /**
	  * 
	   * 方法描述：时间转字符串
	   * @param: date
	   * @return: String
	   * @author: heshuai
	   * @version: 2012-3-13 下午06:17:18
	  */
	 public static String DateToStr(Date date)
	 {
		 String dateStr = null;
		 try {
			 dateStr = format.format(date);
		 } catch (Exception e) {
			 LogUtils.doLog("时间格式化为字符串出现异常" , e, date, format);
		 }
		 return dateStr;
	 }
	 /**
	  * 
	   * 方法描述：自定义格式化时间
	   * @param: 
	   * @return: 
	   * @author: heshuai
	   * @version: 2012-4-13 上午11:13:42
	  */
	 public static String DateToStr(Date date,String format)
	 {
		 SimpleDateFormat formatter = new SimpleDateFormat(format);
		 String dateStr = null;
		 try {
			 dateStr = formatter.format(date);
		 } catch (Exception e) {
			 LogUtils.doLog("时间格式化为字符串出现异常" , e, date, format);
		 }
		 return dateStr;
	 }
	
	 
}
