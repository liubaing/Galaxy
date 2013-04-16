package com.ulearning.ulms.appbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ulearning.ulms.appbackend.service.SCOManager;

/**
 * 类说明:学习记录
 * @author heshuai
 * @version 2012-3-23
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Controller
@RequestMapping("/course/records")
public class SCOController {

	@Autowired
	private SCOManager scoManager;
	
	@RequestMapping(value="/{courseID}/{userID}",method = RequestMethod.GET)
	@ResponseBody
	public String get(@PathVariable int courseID,@PathVariable int userID)
	{
		return scoManager.get(courseID,userID);
	}

	@RequestMapping(value="/{courseID}/{userID}",method = RequestMethod.POST)
	@ResponseBody
	public void add(@PathVariable int courseID,@PathVariable int userID,
			@RequestBody String record)
	{
		scoManager.add(record,courseID,userID);
	}
	
}
