package com.liubaing.galaxy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.liubaing.galaxy.task.ApnsManager;

/**
 * 类说明:消息推送 
 * 
 * @author heshuai
 * @version 2012-5-7

 */
@Controller
@RequestMapping("/apns")
public class ApnsController {
	
	@Autowired
	private ApnsManager apnsManager;
	
}
