package com.liubaing.galaxy.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.liubaing.galaxy.service.UserManager;
import com.liubaing.galaxy.util.support.CacheManager;

/**
 * 类说明:token校验
 * @author heshuai
 * @version 2012-5-2
 *
 */
@Component
public class TokenValidator {
	
	@Autowired 
	private CacheManager cacheManager;
	
	@Autowired
	private UserManager userManager;
	/**
	 * 
	  * 方法描述：校验token是否存在
	  * 		memcache中不存在时请求数据库
	  * @param: token  用户令牌
	  * @return: true:存在 
	  * @author: heshuai
	  * @version: 2012-5-3 上午11:07:55
	 */
	public boolean validateMethod(String token){
		
		boolean verify = false;
		if (cacheManager.get("token_"+token) != null) {
			verify = true;
		} else {
			verify = userManager.isUserExistByToken(token);
		}
		return verify;
    }

}
