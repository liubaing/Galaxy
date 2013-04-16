package com.ulearning.ulms.appbackend.util.support;

import java.util.concurrent.TimeoutException;

import com.ulearning.ulms.appbackend.util.log.LogUtils;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * 类说明:cache
 * @author heshuai
 * @version 2012-11-2
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
public class CacheManager {
	
	//过期时间  单位毫秒
	private static final int DEFAULT_EXPIRETIME = 0;
	
	private static final String PREFIX_KEY = "lei_app_";
	
	private int expireTime = DEFAULT_EXPIRETIME;
	
	private MemcachedClient memcachedClient;
	
	public CacheManager() {
	}
	
	public CacheManager(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}
	
	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}
	
	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}
	
	public <T> T get(final String key)
	{
		try {
			return memcachedClient.get(PREFIX_KEY + key);
		} catch (TimeoutException e) {
			LogUtils.doLog("Memcached连接超时", e);
			//throw new com.ulearning.ulms.appbackend.exception.MemcachedException("Memcached连接超时", e);
		} catch (InterruptedException e) {
			//TODO
			//忽略该异常，应用内无法处理
			//throw new com.ulearning.ulms.appbackend.exception.MemcachedException("Memcached线程中断异常", e);
		} catch (MemcachedException e) {
			//throw new com.ulearning.ulms.appbackend.exception.MemcachedException("Memcached内部异常", e);
		}
		return null;
	}
	
	public void set(final String key, final Object value)
	{
		this.set(key, expireTime, value);
	}
	
	public void set(final String key, final int exp, final Object value)
	{
		try {
			memcachedClient.set(PREFIX_KEY + key, exp, value);
		} catch (TimeoutException e) {
			LogUtils.doLog("Memcached连接超时", e);
			//throw new com.ulearning.ulms.appbackend.exception.MemcachedException("Memcached连接超时", e);
		} catch (InterruptedException e) {
			//TODO
			//忽略该异常，应用内无法处理
			//throw new com.ulearning.ulms.appbackend.exception.MemcachedException("Memcached线程中断异常", e);
		} catch (MemcachedException e) {
			//throw new com.ulearning.ulms.appbackend.exception.MemcachedException("Memcached内部异常", e);
		}
	}
	

}
