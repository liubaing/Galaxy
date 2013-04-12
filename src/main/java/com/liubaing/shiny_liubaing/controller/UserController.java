package com.liubaing.shiny_liubaing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liubaing.shiny_liubaing.service.UserManager;
import com.liubaing.shiny_liubaing.util.core.Const;
import com.liubaing.shiny_liubaing.util.core.ObjectUtils;

@Controller
@RequestMapping("/auth")
public class UserController
{
	@Autowired
	private UserManager userManager;
	
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	@ResponseBody
	public String login(@RequestBody String userStr)
	{
		if (ObjectUtils.isEmpty(userStr)) {
			return Const.ERROR_MSG_NULL;
		}
		return userManager.login(userStr);
	}
}
