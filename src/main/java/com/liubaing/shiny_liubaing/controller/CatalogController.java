package com.liubaing.shiny_liubaing.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liubaing.shiny_liubaing.service.CatalogManager;
import com.liubaing.shiny_liubaing.util.core.Const;

@Controller
@RequestMapping("/course")
public class CatalogController
{
	@Autowired
	private CatalogManager catalogManager;
	
	/**
	 * 
	  * 方法描述：根据用户机构获取当前机构中的课程目录
	  * 	目前参数没有userID,暂通过token获取
	  * @param
	  * @return 
	  * @author heshuai
	  * @version 2013-1-7 下午04:08:18
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public String get(@RequestHeader(value = "Authorization") String token)
	{
		return catalogManager.generateJSON(getOauth2Token(token));
	}
	
	@RequestMapping(value = "/timestamp", method = RequestMethod.GET)
	@ResponseBody
	public String getTimestamp(@RequestHeader(value = "Authorization") String token)
	{
		return catalogManager.getTimestamp(getOauth2Token(token));
	}
	
	@RequestMapping(value = "/reset/{aspID}", method = RequestMethod.GET)
	@ResponseBody
	public void reset(@PathVariable int aspID)
	{
		catalogManager.reset(aspID);
	}
	
	private String getOauth2Token(String token){
		return StringUtils.substringAfter(token, Const.AUTH_OAUTH2).trim();
	}
	
}
  