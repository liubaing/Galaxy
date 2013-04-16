package com.ulearning.ulms.appbackend.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ulearning.ulms.appbackend.util.support.CacheManager;

/**
 * 类说明:持久化token
 * @author heshuai
 * @version 2012-5-2
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Component
public class TokenStorage {
	
	@Autowired 
	private CacheManager memcachedTemplate;
	
	public void persist(String token, long userID)
	{
		memcachedTemplate.set("token_"+token, userID);
	}
}
