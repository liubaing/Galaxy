package com.liubaing.galaxy.service.apns;

import com.liubaing.galaxy.entity.Apns;

/**
 * 接口说明:推送类型，可以随时扩展
 * @author heshuai
 * @version 2012-11-1
 *
 */
public interface IPush {
	
	public final static String USER_KEY = "USER_ID_";
	public final static String ASP_KEY = "ASP_ID_";

	public void pushNotifications(Apns apns);
	
	public void sendNotifications(String message, String[] tokens);
	
	public String[] getInvalidDeviceTokens();
	
}
