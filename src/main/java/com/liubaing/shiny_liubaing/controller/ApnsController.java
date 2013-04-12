package com.liubaing.shiny_liubaing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liubaing.shiny_liubaing.service.ApnsManager;

/**
 * 类说明:消息推送 
 * 
 * @author heshuai
 * @version 2012-5-7
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Controller
@RequestMapping("/apns")
public class ApnsController {
	
	@Autowired
	private ApnsManager apnsManager;
	
	@RequestMapping(value = "/tags/{userID}", method = RequestMethod.GET)
	@ResponseBody
	public String getTags(@PathVariable int userID){
		return apnsManager.getTags(userID);
	}
	
	@RequestMapping (method = RequestMethod.POST)
	@ResponseBody
	public void add(@RequestBody String apns)
	{
		apnsManager.add(apns);
	}
	
	@RequestMapping (method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody String apns)
	{
		apnsManager.update(apns);
	}
}
