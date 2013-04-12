package com.liubaing.shiny_liubaing.entity;

import org.apache.commons.lang3.StringUtils;

/**
 * An enum that specifies the Platform including Android or IOS etc
 * @author heshuai
 * @version 2012-10-29
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
public enum Platform {
	
	ANDROID,IOS;
	
	/**
	 * 
	  * 方法描述：判断client类型
	  * @param: <p>IOS:iStatsEdu/1.0 CFNetwork/548.1.4 Darwin/11.0.0</P>
	  * 		<P>ANDROID:ANDROID</p>
	  * @return: ANDROID或IOS
	  * @author: heshuai
	  * @version: 2012-11-29 下午02:33:32
	 */
	public static Platform check(String userAgent) {
//		if (ObjectUtils.isBlank(userAgent)) {
//			throw new IllegalArgumentException("userAgent 不能为空");
//		}
		if (StringUtils.containsIgnoreCase(userAgent, Platform.ANDROID.toString())) {
			return ANDROID;
		}
		return IOS;
	}
}
