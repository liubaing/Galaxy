package com.ulearning.ulms.appbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ulearning.ulms.appbackend.service.FeedManager;
import com.ulearning.ulms.appbackend.service.VersionManager;

/**
 * Controller for feed requests
 * @author heshuai
 * @version 2012-10-26
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
@Controller
@RequestMapping("/feeds")
public class FeedController {

	@Autowired
	private FeedManager feedManager;
	
	@Autowired
	private VersionManager versionManager;
	
	@RequestMapping(value="/feedback",method=RequestMethod.POST)
	@ResponseBody
	public void feedback(@RequestBody String feedbackStr)
	{
		feedManager.saveFeedback(feedbackStr);
	}
	
	@RequestMapping(value="/checkupdate",method=RequestMethod.GET)
	@ResponseBody
	public String checkUpdate()
	{
		return versionManager.checkUpdate();
	}
	
}
