package com.ulearning.ulms.appbackend.util.core;

/**
 * 类说明:常量
 * @author heshuai
 * @version 2012-3-23
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
public class Const {
	
	public static final String WEBROOTPATH = "webapp";			//应用根路径KEY
	
	public static final int DOWNLOAD_MAX = 5;  					//单个课程下载最大次数
	 
	public static final int PUSH_NOTIFICATION_STATUS = 1;		//允许推送
	public static final int UNPUSH_NOTIFICATION_STATUS = 0;		//不允许推送
	
	public static final String AUTH_OAUTH2 = "oauth2";
	
	public static final String ICON_PATH = "http://www.statsedu.com/upload/";
	
	/*
	 * Memcache
	 */
	public static final String LMS_CATALOG_JSON = "_catalog";					//课程目录json字符串
	public static final String LMS_CATALOG_TIMESTAMP = "_catalog_timestamp";	//课程目录时间戳
	public static final String LMS_COURSES_JSON = "_course";					//我的课程json字符串
	public static final String LMS_COURSES_TIMESTAMP = "_course_timestamp";		//我的课程时间戳
	/*
	 *分页 
	 */
	public static final int RESULT_MAX = 20;					//每页结果数
	 
	 /*
	  * HTTP状态信息
	  */
	public static final String HTTP_STATUS_400 = "请求的参数不符合要求";
	public static final String HTTP_STATUS_404 = "找不到要请求的资源";
	public static final String HTTP_STATUS_405 = "不允许对资源请求该方法";
	public static final String HTTP_STATUS_500 = "服务器内部错误，请稍后重试或联系管理员";
	public static final String HTTP_STATUS_204 = "请求已经被处理";
	public static final String HTTP_STATUS_200 = "请求已经成功处理";
	 
	/*
	 * 提示信息
	 */
	public static final String ERROR_MSG_NULL = "{\"status\":\"false\",\"error_msg\":\"用户名或密码有误，请重试\"}";
	public static final String ERROR_MSG_OAUTH = "{\"error\":\"invalid_request\",\"error_code\":21323,\"error_description\":\"请求不合法 \"}";
	
	 
	/*
	 * 订单付款状态
	 */
	public enum payStatus{
		paid     ((short)1,"支付成功"),
		unpaid   ((short)0,"支付失败");
		private short status;
		private String msg;
		private payStatus(short status,String msg)
		{
			this.status = status;
			this.msg = msg;
		}
		public short getStatus() {
			return status;
		}
		public String getMsg() {
			return msg;
		}
	}
}
