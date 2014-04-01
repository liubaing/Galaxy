package com.liubaing.galaxy.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.liubaing.galaxy.util.support.CacheManager;

/**
 * 类说明:持久化token
 * @author heshuai
 * @version 2012-5-2
 *
 */
@Component
public class TokenStorage {
	
	@Autowired 
	private CacheManager memcachedTemplate;
	
	public void persist(String token,long userID)
	{
		memcachedTemplate.set("token_"+token, userID);
	}
}
