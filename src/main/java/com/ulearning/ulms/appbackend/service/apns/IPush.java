package com.ulearning.ulms.appbackend.service.apns;

import com.ulearning.ulms.appbackend.entity.Apns;

/**
 * 接口说明:推送类型，可以随时扩展
 * @author heshuai
 * @version 2012-11-1
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
public interface IPush {
	
	public final static String USER_KEY = "USER_ID_";
	public final static String COURSE_KEY = "COURSE_ID_";
	public final static String CLASS_KEY = "CLASS_ID_";
	public final static String ORG_KEY = "ORG_ID_";
	public final static String ASP_KEY = "ASP_ID_";

	public void pushNotifications(Apns apns);
	
	public void sendNotifications(String message, String[] tokens);
	
	public String[] getInvalidDeviceTokens();
	
}
