package com.ulearning.ulms.appbackend.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ulearning.ulms.appbackend.util.core.Const;
import com.ulearning.ulms.appbackend.util.core.ObjectUtils;

/**
 * 类说明:方法前拦截器
 * @author: heshuai
 * @version: 2012-5-2
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Component
public class OAuthTokenFilter extends HandlerInterceptorAdapter {

	@Autowired
	private TokenValidator tokenValidator;
	
	/*
	 * 忽略检验URL
	 */
	private String[] excludeMappingURL;
	
	public void setExcludeMappingURL(String[] excludeMappingURL) {
		this.excludeMappingURL = excludeMappingURL;
	}
			
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//ignore login or reset etc
		String uri = request.getRequestURI();
		for (String mapURL : excludeMappingURL) {
			if (uri.contains(mapURL)) {
				return true;
			}
		}
		String token = request.getHeader("Authorization");
		if (!ObjectUtils.isEmpty(token)) {
			//Authorization:oauth2 token
			return tokenValidator.validateMethod(token.substring(7));
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(Const.ERROR_MSG_OAUTH);
		return false;
	}
	
}
